package ada.osc.movielist.presentation;

import ada.osc.movielist.interaction.ApiInteractor;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.MovieResponse;
import ada.osc.movielist.view.movies.toprated.TopratedMovieContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class TopratedMoviePresenter implements TopratedMovieContract.Presenter {

    private final ApiInteractor apiInteractor;
    private TopratedMovieContract.View movieView;

    public TopratedMoviePresenter() {
        apiInteractor = new ApiInteractorImpl();
    }

    @Override
    public void setView(TopratedMovieContract.View view) {
        movieView = view;
    }

    @Override
    public void getTopRatedMovies(int page) {
        apiInteractor.getTopRatedMovies(page, getTopRatedMoviesCallback());
    }

    @Override
    public void getTopRatedMoviesNextPage(int page) {
        apiInteractor.getTopRatedMovies(page, getTopRatedMoviesNextPageCallback());
    }

    @Override
    public void refreshTopRatedMovies() {
        apiInteractor.getTopRatedMovies(1, getRefreshTopRatedMoviesCallback());
    }

    public Callback<MovieResponse> getTopRatedMoviesCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    movieView.displayMovies(movieResponse.getMovies());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        };
    }

    public Callback<MovieResponse> getTopRatedMoviesNextPageCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    movieView.addMovies(movieResponse.getMovies());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        };
    }

    public Callback<MovieResponse> getRefreshTopRatedMoviesCallback() {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    movieView.refreshRecycler(movieResponse.getMovies());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        };
    }
}
