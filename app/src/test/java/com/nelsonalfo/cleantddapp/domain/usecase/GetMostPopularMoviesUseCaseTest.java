package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.cleantddapp.domain.entities.MovieListEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static com.nelsonalfo.cleantddapp.StubFactory.createMovieListEntityStub;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetMostPopularMoviesUseCaseTest {

    @Mock
    private Consumer<MovieListEntity> movieListConsumer;
    @Mock
    private Consumer<Throwable> errorConsumer;
    @Mock
    private MoviesRepository repository;
    @Mock
    private ThreadExecutor threadExecutor;
    @Mock
    private PostExecutionThread postExecutionThread;

    private GetMostPopularMoviesUseCase mostPopularMoviesUseCase;

    private TestScheduler testScheduler;


    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        doReturn(testScheduler).when(threadExecutor).getScheduler();
        doReturn(testScheduler).when(postExecutionThread).getScheduler();

        mostPopularMoviesUseCase = new GetMostPopularMoviesUseCase(threadExecutor, postExecutionThread, repository);
    }

    @Test
    public void given_useCaseIsExecuted_when_execute_then_shouldCallRepository() {
        final MovieListEntity movies = createMovieListEntityStub();
        doReturn(Single.just(movies)).when(repository).getMostPopularMovies();

        mostPopularMoviesUseCase.execute(movieListConsumer, errorConsumer);

        verify(repository).getMostPopularMovies();
    }

    @Test
    public void given_useCaseReturnMovies_when_execute_then_shouldTriggerSuccessConsumer() throws Exception {
        final MovieListEntity movies = createMovieListEntityStub();
        doReturn(Single.just(movies)).when(repository).getMostPopularMovies();

        mostPopularMoviesUseCase.execute(movieListConsumer, errorConsumer);
        testScheduler.triggerActions();

        verify(repository).getMostPopularMovies();
        verify(movieListConsumer).accept(movies);
        verifyZeroInteractions(errorConsumer);
    }

    @Test
    public void given_useCaseReturnServerError_when_execute_then_shouldTriggerErrorConsumerWithTheError() throws Exception {
        doReturn(Single.error(ServerErrorException::new)).when(repository).getMostPopularMovies();

        mostPopularMoviesUseCase.execute(movieListConsumer, errorConsumer);
        testScheduler.triggerActions();

        verify(repository).getMostPopularMovies();
        verify(errorConsumer).accept(any(ServerErrorException.class));
        verifyZeroInteractions(movieListConsumer);
    }
}