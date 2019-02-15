package com.nelsonalfo.cleantddapp.presentation.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nelsonalfo.cleantddapp.R;
import com.nelsonalfo.cleantddapp.commons.network.NetworkModule;
import com.nelsonalfo.cleantddapp.data.api.TheMovieDbRestApi;
import com.nelsonalfo.cleantddapp.data.datasource.MoviesDataSource;
import com.nelsonalfo.cleantddapp.data.datasource.remote.TMDbDataSourceRemote;
import com.nelsonalfo.cleantddapp.data.mappers.TMDbMapper;
import com.nelsonalfo.cleantddapp.data.repository.TMDbRepositoryData;
import com.nelsonalfo.cleantddapp.domain.repository.MoviesRepository;
import com.nelsonalfo.cleantddapp.domain.usecase.GetAllInformationPopularMoviesUseCase;
import com.nelsonalfo.cleantddapp.domain.usecase.GetMostPopularMoviesUseCase;
import com.nelsonalfo.cleantddapp.domain.usecase.GetTmdbConfigurationUseCase;
import com.nelsonalfo.cleantddapp.presentation.models.MovieResumeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MovieListActivity extends AppCompatActivity implements MovieListContract.View{

    @BindView(R.id.movies_list)
    RecyclerView recyclerView;

    private MoviesAdapter adapter;

    private MovieListContract.Presenter presenter;
    private GetMostPopularMoviesUseCase getMostPopularMoviesUseCase;
    private MoviesRepository moviesRepository;
    private MoviesDataSource moviesDataSource;
    private TMDbMapper mapper;
    private TheMovieDbRestApi api;
    private GetTmdbConfigurationUseCase getConfigurationUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NetworkModule module = new NetworkModule(this);
        api = module.createClient();
        mapper = new TMDbMapper();
        moviesDataSource = new TMDbDataSourceRemote(api);
        moviesRepository = new TMDbRepositoryData(moviesDataSource,mapper);
        getMostPopularMoviesUseCase = new GetMostPopularMoviesUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread(), moviesRepository);
        getConfigurationUseCase = new GetTmdbConfigurationUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread(), moviesRepository);

        presenter = new MovieListPresenter(new GetAllInformationPopularMoviesUseCase(Schedulers.io(),
                AndroidSchedulers.mainThread(), getMostPopularMoviesUseCase, getConfigurationUseCase));
        presenter.bindView(this);

        presenter.initView();
    }

    @Override
    public void showMovies(List<MovieResumeViewModel> movies) {
        adapter = new MoviesAdapter(movies);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showEmptyList() {

    }

    @Override
    public void showServiceError() {

    }

    @Override
    public void showServerError() {

    }
}
