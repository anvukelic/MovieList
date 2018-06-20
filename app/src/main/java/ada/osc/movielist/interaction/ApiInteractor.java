package ada.osc.movielist.interaction;

import ada.osc.movielist.model.MovieResponse;
import retrofit2.Callback;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface ApiInteractor {

    void getPopularMovies(int page, Callback<MovieResponse> callback);
    void getTopRatedMovies(int page, Callback<MovieResponse> callback);
    void getUpcomingMovies(int page, Callback<MovieResponse> callback);
}