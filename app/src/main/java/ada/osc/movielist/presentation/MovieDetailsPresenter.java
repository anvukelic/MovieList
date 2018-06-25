package ada.osc.movielist.presentation;

import java.util.ArrayList;
import java.util.List;

import ada.osc.movielist.interaction.ApiInteractor;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.MovieVideosResponse;
import ada.osc.movielist.model.Video;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final ApiInteractor apiInteractor;
    private MovieDetailsContract.View movieView;

    public MovieDetailsPresenter() {
        apiInteractor = new ApiInteractorImpl();
    }

    @Override
    public void setView(MovieDetailsContract.View view) {
        movieView = view;
    }

    @Override
    public void getMovieDetails(int movieId) {
        apiInteractor.getMovieDetails(movieId, getMovieDetailsCallback());
    }

    @Override
    public void getMovieTrailers(int movieId) {
        apiInteractor.getMovieTrailers(movieId, getMovieTrailersCallback());
    }

    private Callback<MovieDetailsResponse> getMovieDetailsCallback() {
        return new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetailsResponse details = response.body();
                    movieView.showImage(details.getImagePath());
                    movieView.showTitle(details.getTitle());
                    movieView.showOriginalTitle(details.getOriginalTitle());
                    movieView.showOverview(details.getOverview());
                    movieView.showRuntime(details.getRuntime());
                    movieView.showGenres(details.getGenres());
                    movieView.showReleaseDate(details.getReleaseDate());
                    movieView.removeProgressBar();
                } else {
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

    public Callback<MovieVideosResponse> getMovieTrailersCallback() {
        return new Callback<MovieVideosResponse>() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Video> videoList = new ArrayList<>();
                    for (Video video : response.body().getVideos()) {
                        if (video.getType().equals("Trailer")) {
                            videoList.add(video);
                        }
                    }
                    movieView.showVideoLinks(videoList);
                }
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
            }
        };
    }
}
