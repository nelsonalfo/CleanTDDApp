package com.nelsonalfo.cleantddapp.data.repository;

import com.nelsonalfo.cleantddapp.commons.exceptions.ClientErrorException;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static com.nelsonalfo.cleantddapp.data.datasource.remote.StubFactory.createMovieResponseStub;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoviesRepositoryDataTest {

    @Mock
    private MoviesDataSource dataSource;

    private MoviesRepositoryData repository;

    @Before
    public void setUp() {
        repository = new MoviesRepositoryData(dataSource);
    }

    @Test
    public void given_dataSourceReturnListOfMovies_when_getMostPopularMovies_then_returnMoviesHasEntity() {
        doReturn(Single.just(createMovieResponseStub())).when(dataSource).getMostPopularMovies();

        final TestObserver<MovieListEntity> testObserver = repository.getMostPopularMovies().test();

        verify(dataSource).getMostPopularMovies();
        testObserver.assertSubscribed();
        testObserver.assertComplete();
        testObserver.assertValue(movieListEntity -> movieListEntity != null
                && movieListEntity.results.size() == 2);
    }

    @Test
    public void given_dataSourceReturnServerErrorException_when_getMostPopularMovies_then_returnTheGivenException() {
        doReturn(Single.error(new ServerErrorException())).when(dataSource).getMostPopularMovies();

        final TestObserver<MovieListEntity> testObserver = repository.getMostPopularMovies().test();

        verify(dataSource).getMostPopularMovies();
        testObserver.assertError(throwable -> throwable instanceof ServerErrorException);
    }

    @Test
    public void given_dataSourceReturnClientErrorException_when_getMostPopularMovies_then_returnTheGivenException() {
        doReturn(Single.error(new ClientErrorException())).when(dataSource).getMostPopularMovies();

        final TestObserver<MovieListEntity> testObserver = repository.getMostPopularMovies().test();

        verify(dataSource).getMostPopularMovies();
        testObserver.assertError(throwable -> throwable instanceof ClientErrorException);
    }
}