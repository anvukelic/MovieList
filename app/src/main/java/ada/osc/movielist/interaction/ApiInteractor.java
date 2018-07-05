package ada.osc.movielist.interaction;

import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.MovieResponse;
import ada.osc.movielist.model.MovieVideosResponse;
import retrofit2.Callback;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface ApiInteractor {

    void getPopularMovies(int page, Callback<MovieResponse> callback);

    void getTopRatedMovies(int page, Callback<MovieResponse> callback);

    void getUpcomingMovies(int page, Callback<MovieResponse> callback);

    void getSearchedMovies(String query, int page, Callback<MovieResponse> callback);

    void getMovieDetails(int movieId, Callback<MovieDetailsResponse> callback);

    void getMovieTrailers(int movieId, Callback<MovieVideosResponse> callback);
}
