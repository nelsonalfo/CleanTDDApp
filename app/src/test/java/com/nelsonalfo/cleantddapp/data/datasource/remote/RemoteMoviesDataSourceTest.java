package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.commons.exceptions.ClientErrorException;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.commons.exceptions.UnknownErrorException;
import com.nelsonalfo.cleantddapp.data.api.Constants;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.nelsonalfo.cleantddapp.StubFactory.createError401;
import static com.nelsonalfo.cleantddapp.StubFactory.createMovieResponseStub;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoteMoviesDataSourceTest {

    @Mock
    private TheMovieDbRestApi api;

    @Mock
    private ResponseBody responseBody;

    @InjectMocks
    private RemoteMoviesDataSource dataSource;

    @Before
    public void setUp() {
    }

    @Test
    public void given_apiReturnedMoviesSortByPopularity_when_getMostPopularMovies_then_returnTheMovies() {
        final MoviesResponse mostPopularMovies = createMovieResponseStub();
        doReturn(Single.just(mostPopularMovies)).when(api).getMovies(anyString(), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> testObserver = dataSource.getMostPopularMovies().test();

        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
        testObserver.assertSubscribed();
        testObserver.assertComplete();
        testObserver.assertValue(moviesResponse -> moviesResponse != null
                && !moviesResponse.results.isEmpty()
                && moviesResponse.results.size() == 2);
    }

    @Test
    public void given_apiReturnError401_when_getMostPopularMovies_then_returnClientErrorException() {
        final HttpException error = createError401();
        doReturn(Single.error(error)).when(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> test = dataSource.getMostPopularMovies().test();

        test.assertError(throwable -> throwable instanceof ClientErrorException);
        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
    }

    @Test
    public void given_apiReturnError500_when_getMostPopularMovies_then_returnServerErrorException() {
        final HttpException error = new HttpException(Response.error(500, mock(ResponseBody.class)));
        doReturn(Single.error(error)).when(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> test = dataSource.getMostPopularMovies().test();

        test.assertError(throwable -> throwable instanceof ServerErrorException);
        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
    }

    @Test
    public void given_apiReturnError460_when_getMostPopularMovies_then_returnUnknownErrorException() {
        final HttpException error = new HttpException(Response.error(460, responseBody));
        doReturn(Single.error(error)).when(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> test = dataSource.getMostPopularMovies().test();

        test.assertError(throwable -> throwable instanceof UnknownErrorException);
        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
    }

    @Test
    public void given_apiReturnIOException_when_getMostPopularMovies_then_returnTheExceptionHasGiven() {
        final IOException error = new IOException();
        doReturn(Single.error(error)).when(api).getMovies(anyString(), anyString());

        final TestObserver<MoviesResponse> test = dataSource.getMostPopularMovies().test();

        test.assertError(throwable -> throwable instanceof IOException);
        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
    }
}