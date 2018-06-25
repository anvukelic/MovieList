package ada.osc.movielist.interaction;

import ada.osc.movielist.App;
import ada.osc.movielist.Consts;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.MovieResponse;
import ada.osc.movielist.model.MovieVideosResponse;
import retrofit2.Callback;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class ApiInteractorImpl implements ApiInteractor {
    @Override
    public void getPopularMovies(int page, Callback<MovieResponse> callback) {
        App.getApiService().getPopularMovies(Consts.API_KEY,page).enqueue(callback);
    }

    @Override
    public void getTopRatedMovies(int page, Callback<MovieResponse> callback) {
        App.getApiService().getTopRatedMovies(Consts.API_KEY,page).enqueue(callback);
    }

    @Override
    public void getUpcomingMovies(int page, Callback<MovieResponse> callback) {
        App.getApiService().getUpcomingMovies(Consts.API_KEY,page).enqueue(callback);
    }

    @Override
    public void getMovieDetails(int movieId, Callback<MovieDetailsResponse> callback) {
        App.getApiService().getMovieDetails(movieId,Consts.API_KEY).enqueue(callback);
    }

    @Override
    public void getMovieTrailers(int movieId, Callback<MovieVideosResponse> callback) {
        App.getApiService().getMovieTrailers(movieId,Consts.API_KEY).enqueue(callback);

    }
}
