package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.domain.entities.ImagesEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GetAllInformationPopularMoviesUseCaseTest {

    @Mock
    private Consumer<MoviesResponseEntity> consumerSuccess;
    @Mock
    private Consumer<Throwable> consumerError;
    @Mock
    GetMostPopularMoviesUseCase moviesUseCase;
    @Mock
    GetTmdbConfigurationUseCase configUseCase;

    GetAllInformationPopularMoviesUseCase useCase;

    @Before
    public void setUp() {
        final TestScheduler testScheduler = new TestScheduler();

        useCase = new GetAllInformationPopularMoviesUseCase(testScheduler, testScheduler, moviesUseCase, configUseCase);
    }

    //TODO dado que llamamos a casos de uso de peliculas
    // y configuracion cuando execute llamar a casos de uso
    @Test
    public void given_mostPopularUseCase_when_execute_then_use() {
        final MoviesResponseEntity movies = new MoviesResponseEntity();
        doReturn(Single.just(movies)).when(moviesUseCase).buildObservable();
        final TmdbConfigurationEntity config = new TmdbConfigurationEntity();
        doReturn(Single.just(config)).when(configUseCase).buildObservable();

        useCase.execute(consumerSuccess, consumerError);

        verify(moviesUseCase).buildObservable();
        verify(configUseCase).buildObservable();
    }

    @Test
    public void given_usesCasesReturnAllData_when_buildObservable_then_consumerSuccessReceivesMoviesWithPosterUrls() {
        final MoviesResponseEntity sourceMovies = new MoviesResponseEntity();
        sourceMovies.results = Collections.singletonList(new MovieResumeEntity(1, "Dragon Ball Super", "image1.jpg"));
        doReturn(Single.just(sourceMovies)).when(moviesUseCase).buildObservable();
        final TmdbConfigurationEntity config = new TmdbConfigurationEntity();
        config.images = new ImagesEntity("www.domain.com/", "size1/");
        doReturn(Single.just(config)).when(configUseCase).buildObservable();

        final TestObserver<MoviesResponseEntity> testObserver = useCase.buildObservable().test();

        testObserver.assertValue(movies -> movies.results.size() == 1
        && movies.results.get(0).title.equals("Dragon Ball Super")
        && movies.results.get(0).posterUrl.equals("www.domain.com/size1/image1.jpg"));
        testObserver.assertNoErrors();
    }

    @Test
    public void given_moviesUsesCasesReturnError_when_buildObservable_then_returnTheGivenError() {
        final ServerErrorException exception = new ServerErrorException();
        doReturn(Single.error(exception)).when(moviesUseCase).buildObservable();
        final TmdbConfigurationEntity config = new TmdbConfigurationEntity();
        config.images = new ImagesEntity("www.domain.com/", "size1/");
        doReturn(Single.just(config)).when(configUseCase).buildObservable();

        final TestObserver<MoviesResponseEntity> testObserver = useCase.buildObservable().test();

        testObserver.assertError(ServerErrorException.class);
        testObserver.assertNotComplete();
    }
}