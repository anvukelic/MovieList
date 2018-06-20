package ada.osc.movielist.presentation;

import ada.osc.movielist.interaction.ApiInteractor;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.MovieResponse;
import ada.osc.movielist.view.movies.toprated.TopratedMovieContract;
import ada.osc.movielist.view.movies.upcoming.UpcomingMovieContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class UpcomingMoviePresenter implements UpcomingMovieContract.Presenter {

    private final ApiInteractor apiInteractor;
    private UpcomingMovieContract.View movieView;

    public UpcomingMoviePresenter() {
        apiInteractor = new ApiInteractorImpl();
    }

    @Override
    public void setView(UpcomingMovieContract.View view) {
        movieView = view;
    }

    @Override
    public void getUpcomingMovies(int page) {
        apiInteractor.getUpcomingMovies(page, getPopularMoviesCallback());
    }

    public Callback<MovieResponse> getPopularMoviesCallback() {
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
}
