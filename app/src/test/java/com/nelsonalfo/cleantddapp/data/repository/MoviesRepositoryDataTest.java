package com.nelsonalfo.cleantddapp.data.repository;

import com.nelsonalfo.cleantddapp.StubFactory;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.mappers.MoviesMapper;
import com.nelsonalfo.cleantddapp.data.models.MoviesResponse;
import com.nelsonalfo.cleantddapp.data.models.TmdbConfiguration;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MoviesRepositoryDataTest {

    @Mock
    private MoviesDataSource dataSource;

    private MoviesRepositoryData repository;


    @Before
    public void setUp() {
        final MoviesMapper mapper = new MoviesMapper();
        repository = new MoviesRepositoryData(dataSource, mapper);
    }

    @Test
    public void given_dataSourceReturnMovies_when_getPopularMovies_then_shouldReturnMoviesAsEntities() {
        final MoviesResponse moviesModel = StubFactory.createMoviesResponseStub();
        doReturn(Single.just(moviesModel)).when(dataSource).fetchPopularMovies();

        final TestObserver<MoviesResponseEntity> testObserver = repository.getPopularMovies().test();

        verify(dataSource).fetchPopularMovies();
        testObserver.assertValue(moviesEntity -> moviesEntity != null
                        && moviesEntity.results.size() == 2
                        && moviesEntity.results.get(0).title.equals("Titanic"));
    }

    @Test
    public void given_dataSourceReturnServerErrorException_when_getPopularMovies_then_returnExceptionGiven() {
        doReturn(Single.error(new ServerErrorException())).when(dataSource).fetchPopularMovies();

        final TestObserver<MoviesResponseEntity> testObserver = repository.getPopularMovies().test();

        verify(dataSource).fetchPopularMovies();
        testObserver.assertError(expectedError -> expectedError instanceof ServerErrorException);
    }

    @Test
    public void given_dataSourceTmdbConfiguration_when_fetchTmdbConfiguration_then_ShouldReturnTmdbConfigurationEntity(){
        TmdbConfiguration tmdbConfiguration = new TmdbConfiguration();
        TmdbConfigurationEntity tmdbConfigurationEntity = new TmdbConfigurationEntity();
        tmdbConfiguration.changeKeys = Arrays.asList("keyone", "keytwo");

        tmdbConfigurationEntity.changeKeys = tmdbConfiguration.changeKeys;

        doReturn(Single.just(tmdbConfiguration)).when(dataSource).getConfiguration();

        final TestObserver<TmdbConfigurationEntity> testObserver = repository.fetchTmdbConfiguration().test();

        testObserver.assertSubscribed();
        testObserver.assertValue(value -> value.changeKeys != null && value.changeKeys.size() == 2 );

    }

    @Test
    public void given_dataSourceReturnServerErrorException_when_getConfiguration_then_returnExceptionGiven() {

        doReturn(Single.error(new ServerErrorException())).when(dataSource).getConfiguration();

        TestObserver<TmdbConfiguration> testObserver = dataSource.getConfiguration().test();



    }
}