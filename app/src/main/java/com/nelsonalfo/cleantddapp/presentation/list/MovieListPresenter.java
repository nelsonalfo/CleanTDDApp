package com.nelsonalfo.cleantddapp.presentation.list;

import com.nelsonalfo.cleantddapp.commons.exceptions.ServerErrorException;
import com.nelsonalfo.cleantddapp.domain.entities.MovieResumeEntity;
import com.nelsonalfo.cleantddapp.domain.entities.MoviesResponseEntity;
import com.nelsonalfo.cleantddapp.domain.usecase.GetAllInformationPopularMoviesUseCase;
import com.nelsonalfo.cleantddapp.presentation.models.MovieResumeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieListPresenter implements MovieListContract.Presenter {

    private GetAllInformationPopularMoviesUseCase useCase;
    private MovieListContract.View view;

    public MovieListPresenter(GetAllInformationPopularMoviesUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void initView() {
        useCase.execute(this::success, this::onGetMovieError);
    }

    @Override
    public void bindView(MovieListContract.View view) {
        this.view = view;
    }

    private List<MovieResumeViewModel> mapToMovieViewModel(List<MovieResumeEntity> entities){
        final List<MovieResumeViewModel> viewModels = new ArrayList<>();

        for (MovieResumeEntity entity : entities) {
            final MovieResumeViewModel viewModel = new MovieResumeViewModel(entity.id, entity.title);
            viewModel.overview = entity.overview;
            viewModel.posterImageUrl = entity.posterUrl;
            viewModels.add(viewModel);
        }

        return viewModels;
    }

    private void success(MoviesResponseEntity response) {
        if (response.results.isEmpty()) {
            view.showEmptyList();
        } else {
            view.showMovies(mapToMovieViewModel(response.results));
        }
    }

    private void onGetMovieError(Throwable throwable) {

        if(throwable instanceof ServerErrorException){
            view.showServerError();
        }else{

            view.showServiceError();
        }


    }

}
