package com.nelsonalfo.cleantddapp.presentation.list;

import com.nelsonalfo.cleantddapp.presentation.models.MovieResumeViewModel;

import java.util.List;

public interface MovieListContract {

    interface Presenter {

        void initView();
        void bindView(MovieListContract.View view);

    }

    interface View {

        void showMovies(List<MovieResumeViewModel> movies);

        void showEmptyList();

        void showServiceError();

        void showServerError();
    }
}
