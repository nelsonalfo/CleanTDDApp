package com.nelsonalfo.cleantddapp.domain;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;
import com.nelsonalfo.cleantddapp.domain.usecase.GetMostPopularMoviesUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetMostPopularMoviesUseCaseTest {

    private TestScheduler testScheduler;

    @Mock
    private MoviesRepository repository;
    @Mock
    private Consumer<MoviesResponseEntity> consumerSuccess;
    @Mock
    private Consumer<Throwable> consumerError;

    private GetMostPopularMoviesUseCase useCase;

    @Before
    public void setUp() {
        testScheduler = new TestScheduler();
        useCase = new GetMostPopularMoviesUseCase(testScheduler, testScheduler, repository);
    }

    @Test
    public void given_repositoryReturnMovies_when_execute_then_consumerSuccessReceiveMovies() throws Exception {
        final MoviesResponseEntity movies = new MoviesResponseEntity();
        doReturn(Single.just(movies)).when(repository).getPopularMovies();

        useCase.execute(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(repository).getPopularMovies();
        verify(consumerSuccess).accept(eq(movies));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_repositoryReturnServerException_when_execute_then_consumerErrorReceiveTheErrorException() throws Exception {
        final ServerErrorException exception = new ServerErrorException();
        doReturn(Single.error(exception)).when(repository).getPopularMovies();

        useCase.execute(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(repository).getPopularMovies();
        verify(consumerError).accept(eq(exception));
        verifyZeroInteractions(consumerSuccess);
    }
}