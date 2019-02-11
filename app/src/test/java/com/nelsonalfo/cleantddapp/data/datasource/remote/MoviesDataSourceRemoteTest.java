package com.nelsonalfo.cleantddapp.data.datasource.remote;

import com.nelsonalfo.cleantddapp.StubFactory;
import com.nelsonalfo.cleantddapp.commons.Constants;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.models.Images;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoviesDataSourceRemoteTest {

    @Mock
    private TheMovieDbRestApi api;
    @Mock
    private ResponseBody responseBody;

    @Mock
    private TmdbConfiguration tmdbConfiguration;

    private MoviesDataSourceRemote dataSource;


    @Before
    public void setUp() throws Exception {
        dataSource = new MoviesDataSourceRemote(api);
    }

    @Test
    public void given_theApiReturnMovieList_when_fetchPopularMovies_then_shouldCallApi() {
        doReturn(Single.just(new MoviesResponse())).when(api).getMovies(anyString(), anyString());

        dataSource.fetchPopularMovies();

        verify(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));
    }

    @Test
    public void given_theApiReturnMovieList_when_fetchPopularMovies_then_shouldReturnTheMovies() {
        final MoviesResponse moviesResponse = StubFactory.createMoviesResponseStub();
        doReturn(Single.just(moviesResponse)).when(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> testObserver = dataSource.fetchPopularMovies().test();

        testObserver.assertSubscribed();
        testObserver.assertValue(value -> value != null && value.results.size() == 2);
    }

    @Test
    public void given_apiReturnError500_when_fetchPopularMovies_then_returnServerErrorException() {
        Throwable error = new HttpException((Response.error(500, responseBody)));
        doReturn(Single.error(error)).when(api).getMovies(eq(Constants.MOST_POPULAR_MOVIES), eq(Constants.API_KEY));

        final TestObserver<MoviesResponse> testObserver = dataSource.fetchPopularMovies().test();

        testObserver.assertSubscribed();
        testObserver.assertError(expectedError -> expectedError instanceof ServerErrorException);
    }

    //Get configuration

    @Test
    public void given_apiReturnTmbConfig_when_getConfiguration_then_returnTmbConfiguration(){

        final TmdbConfiguration  tmdbConfiguration = new TmdbConfiguration();
        Images images = new Images();
        images.baseUrl = "htt://images";
        List<String> sizesList = new ArrayList<>();
        images.backdropSizes = sizesList;
        tmdbConfiguration.images = images;
        List<String> logoSizes = new ArrayList<>();
        tmdbConfiguration.images.logoSizes = logoSizes;
        List<String> posterSizes = new ArrayList<>();
        tmdbConfiguration.images.posterSizes = posterSizes;
        List<String> profileSizes = new ArrayList<>();
        tmdbConfiguration.images.profileSizes = profileSizes;
        List<String> stillSizes = new ArrayList<>();
        tmdbConfiguration.images.stillSizes = stillSizes;
        doReturn(Single.just(tmdbConfiguration)).when(api).getConfiguration(Constants.API_KEY);

        final TestObserver<TmdbConfiguration> testObserver = dataSource.getConfiguration().test();

        verify(api).getConfiguration(eq(Constants.API_KEY));
        testObserver.assertSubscribed();
        testObserver.assertValue(value ->
                value == tmdbConfiguration
                        && value.images.baseUrl.equals("htt://images")
                        && value.images.backdropSizes == sizesList
                        && value.images.logoSizes == logoSizes
                        && value.images.posterSizes == posterSizes
                        && value.images.profileSizes == profileSizes
                        && value.images.stillSizes == stillSizes);

    }

    @Test
    public void given_apiReturnTmbConfig_when_getConfiguration_then_returnConfigurationError(){
        Throwable error = new HttpException((Response.error(500, responseBody)));
        doReturn(Single.error(error)).when(api).getConfiguration(Constants.API_KEY);

        final TestObserver<TmdbConfiguration> testObserver = dataSource.getConfiguration().test();

        testObserver.assertSubscribed();
        testObserver.assertError(expectedError -> expectedError instanceof ServerErrorException);
    }

}