package com.nelsonalfo.cleantddapp.domain;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;
import com.nelsonalfo.cleantddapp.domain.usecase.GetTmdbConfigurationUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;


@RunWith(MockitoJUnitRunner.class)
public class GetTmdbConfigurationUseCaseTest {

    GetTmdbConfigurationUseCase useCase;

    @Mock
    MoviesRepository repositoryMock;
    @Mock
    Consumer<TmdbConfigurationEntity> consumerSuccess;

    @Mock
    Consumer<Throwable> consumerError;

    private TestScheduler testScheduler;


    @Before
    public void setUp(){
        testScheduler = new TestScheduler();
        useCase = new GetTmdbConfigurationUseCase(testScheduler, testScheduler, repositoryMock);
    }


    @Test
    public void given_fetchTmdbConfiguration_when_execute_then_consumerSuccessTmdbConfiguration() throws  Exception {

        TmdbConfigurationEntity conf = new TmdbConfigurationEntity();
        doReturn(Single.just(conf)).when(repositoryMock).fetchTmdbConfiguration();
        useCase.execute(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(repositoryMock).fetchTmdbConfiguration();
        verify(consumerSuccess).accept(eq(conf));
        verifyNoMoreInteractions(consumerError);
    }


    @Test
    public void given_repositoryReturnsServerError_when_execute_then_consumerErrorReceivesServerException() throws Exception {
        ServerErrorException serverError = new ServerErrorException();
        doReturn(Single.error(serverError)).when(repositoryMock).fetchTmdbConfiguration();

        useCase.execute(consumerSuccess,consumerError);
        testScheduler.triggerActions();

        verify(repositoryMock).fetchTmdbConfiguration();
        verify(consumerError).accept(eq(serverError));
        verifyZeroInteractions(consumerSuccess);
    }
}
