package com.nelsonalfo.cleantddapp.data.repository;

import com.nelsonalfo.cleantddapp.StubFactory;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.mappers.TMDbMapper;
import com.nelsonalfo.cleantddapp.data.models.Images;
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

    private TMDbRepositoryData repository;


    @Before
    public void setUp() {
        final TMDbMapper mapper = new TMDbMapper();
        repository = new TMDbRepositoryData(dataSource, mapper);
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
        testObserver.assertError(ServerErrorException.class);
    }

    @Test
    public void given_dataSourceReturnTmdbConfiguration_when_fetchTmdbConfiguration_then_ShouldReturnTmdbConfigurationEntity() {
        final TmdbConfiguration tmdbConfiguration = getConfigurationEntityStub();
        doReturn(Single.just(tmdbConfiguration)).when(dataSource).getConfiguration();

        final TestObserver<TmdbConfigurationEntity> testObserver = repository.fetchTmdbConfiguration().test();

        testObserver.assertSubscribed();
        testObserver.assertValue(value -> value.images != null
                && value.images.baseUrl.equals("www.domain.com")
                && value.images.posterSizes.size() == 2);

    }

    private TmdbConfiguration getConfigurationEntityStub() {
        TmdbConfiguration tmdbConfiguration = new TmdbConfiguration();
        tmdbConfiguration.images = new Images();
        tmdbConfiguration.images.baseUrl = "www.domain.com";
        tmdbConfiguration.images.posterSizes = Arrays.asList("size1", "size2");
        return tmdbConfiguration;
    }

    @Test
    public void given_dataSourceReturnServerErrorException_when_fetchConfiguration_then_returnExceptionGiven() {
        final ServerErrorException exception = new ServerErrorException();
        doReturn(Single.error(exception)).when(dataSource).getConfiguration();

        final TestObserver<TmdbConfigurationEntity> testObserver = repository.fetchTmdbConfiguration().test();

        testObserver.assertError(ServerErrorException.class);
        testObserver.assertNotComplete();
    }
}