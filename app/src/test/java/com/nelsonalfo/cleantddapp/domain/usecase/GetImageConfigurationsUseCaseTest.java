package com.nelsonalfo.cleantddapp.domain.usecase;

import com.nelsonalfo.cleantddapp.StubFactory;
import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.cleantddapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.cleantddapp.domain.entities.TmdbConfigurationEntity;
import com.nelsonalfo.cleantddapp.domain.repository.TMDbConfigurationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetImageConfigurationsUseCaseTest {

    @Mock
    private TMDbConfigurationRepository repository;
    @Mock
    private Consumer<TmdbConfigurationEntity> configurationConsumer;
    @Mock
    private Consumer<Throwable> errorConsumer;
    @Mock
    private ThreadExecutor backgroundThread;
    @Mock
    private PostExecutionThread uiThread;

    private TestScheduler testScheduler;

    private GetImageConfigurationsUseCase useCase;


    @Before
    public void setUp() {
        testScheduler = new TestScheduler();

        doReturn(testScheduler).when(backgroundThread).getScheduler();
        doReturn(testScheduler).when(uiThread).getScheduler();

        useCase = new GetImageConfigurationsUseCase(backgroundThread, uiThread, repository);
    }

    @Test
    public void given_repositoryReturnConfiguration_when_execute_then_shouldTriggerSuccessConsumer() throws Exception {
        final TmdbConfigurationEntity tmdbConfiguration = StubFactory.createTMDbConfigurationStub();
        doReturn(Single.just(tmdbConfiguration)).when(repository).getConfiguration();

        useCase.execute(configurationConsumer, errorConsumer);
        testScheduler.triggerActions();

        verify(repository).getConfiguration();
        verify(configurationConsumer).accept(eq(tmdbConfiguration));
    }

    @Test
    public void given_repositoryReturnServerError_when_execute_then_shouldTriggerErrorConsumer() throws Exception {
        final ServerErrorException error = new ServerErrorException();
        doReturn(Single.error(error)).when(repository).getConfiguration();

        useCase.execute(configurationConsumer, errorConsumer);
        testScheduler.triggerActions();

        verify(repository).getConfiguration();
        verify(errorConsumer).accept(any(ServerErrorException.class));
    }
}