package com.nelsonalfo.cleantddapp.presentation.list;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.usecase.GetAllInformationPopularMoviesUseCase;
import com.nelsonalfo.cleantddapp.presentation.models.MovieResumeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterTest {

    private MovieListPresenter movieListPresenter;

    @Mock
    MovieListContract.View view;
    @Mock
    GetAllInformationPopularMoviesUseCase useCase;
    @Captor
    ArgumentCaptor<Consumer<MoviesResponseEntity>> moviesSuccesCaptor;
    @Captor
    ArgumentCaptor<Consumer<Throwable>> moviesErrorCaptor;
    @Captor
    ArgumentCaptor<List<MovieResumeViewModel>> moviesViewModelCaptor;

    @Before
    public void setUp() throws Exception {
        movieListPresenter = new MovieListPresenter(useCase);
        movieListPresenter.bindView(view);
    }

    @Test
    public void when_initView_then_shouldCallUseCase() {
        movieListPresenter.initView();
        verify(useCase).execute(any(Consumer.class), any(Consumer.class));
    }

    @Test
    public void given_useCaseReturnsMovieEntities_when_initView_then_viewReceiveMovieViewModelList() throws Exception {
        final MoviesResponseEntity entity = new MoviesResponseEntity();
        final MovieResumeEntity titanic = new MovieResumeEntity(1, "Titanic");
        titanic.posterUrl = "www.domain.com/size1/titanic.jpg";
        entity.results = Collections.singletonList(titanic);

        movieListPresenter.initView();

        verify(useCase).execute(moviesSuccesCaptor.capture(), moviesErrorCaptor.capture());
        moviesSuccesCaptor.getValue().accept(entity);
        verify(view).showMovies(moviesViewModelCaptor.capture());
        final List<MovieResumeViewModel> viewModel = moviesViewModelCaptor.getValue();
        assertThat(viewModel).hasSize(1);
        assertThat(viewModel.get(0).title).isEqualTo("Titanic");
        assertThat(viewModel.get(0).posterImageUrl).isEqualTo("www.domain.com/size1/titanic.jpg");
    }

    @Test
    public void given_useCaseReturnEmptyList_when_initView_then_viewShowEmptyScreen() throws Exception {
        final MoviesResponseEntity entity = new MoviesResponseEntity();
        entity.results = Collections.emptyList();

        movieListPresenter.initView();

        verify(useCase).execute(moviesSuccesCaptor.capture(), moviesErrorCaptor.capture());
        moviesSuccesCaptor.getValue().accept(entity);
        verify(view).showEmptyList();
    }

    @Test
    public void given_useCaseReturnServerError_when_initView_then_viewShowServerErrorScreen() throws Exception {
        ServerErrorException error = new ServerErrorException();

        movieListPresenter.initView();

        verify(useCase).execute(moviesSuccesCaptor.capture(), moviesErrorCaptor.capture());
        moviesErrorCaptor.getValue().accept(error);
        verify(view).showServerError();
    }

    @Test
    public void given_useCaseReturnUnknownError_when_initView_then_viewShowServiceErrorScreen() throws Exception {
        Throwable error = new Exception();

        movieListPresenter.initView();

        verify(useCase).execute(moviesSuccesCaptor.capture(), moviesErrorCaptor.capture());
        moviesErrorCaptor.getValue().accept(error);
        verify(view).showServiceError();
    }
}