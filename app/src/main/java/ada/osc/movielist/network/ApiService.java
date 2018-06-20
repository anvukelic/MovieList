package ada.osc.movielist.network;

import ada.osc.movielist.model.MovieResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface ApiService {

    @GET("/3/movie/popular?")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);
    @GET("/3/movie/top_rated?")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);
    @GET("/3/movie/upcoming?")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key, @Query("page") int page);
}
