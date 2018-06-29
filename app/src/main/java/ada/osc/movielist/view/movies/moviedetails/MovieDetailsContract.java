package ada.osc.movielist.view.movies.moviedetails;

import android.content.Context;

import java.util.List;

import ada.osc.movielist.model.Genre;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.Video;

/**
 * Created by avukelic on 25-Jun-18.
 */
public interface MovieDetailsContract {

    interface View{
        void showImage(String imagePath);
        void showTitle(String title);
        void showOriginalTitle(String originalTitle);
        void showOverview(String overview);
        void showRuntime(int runtime);
        void showGenres(List<Genre> genres);
        void showVideoLinks(List<Video> videos);
        void showReleaseDate(String releaseDate);
        void removeProgressBar();
        void saveMovie(MovieDetailsResponse movie);
        void changeFavItemIcon(boolean isFaved);
    }

    interface Presenter{
        void setView(MovieDetailsContract.View view);
        void getMovieDetails(int movieId);
        void getMovieTrailers(int movieId);
        void faveMovie(MovieDetailsResponse movie);
        void unfaveMovie(MovieDetailsResponse movie);
        void checkIfMovieIsFaved(int movieId);
    }
}
