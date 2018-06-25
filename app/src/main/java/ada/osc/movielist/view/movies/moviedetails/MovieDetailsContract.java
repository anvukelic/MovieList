package ada.osc.movielist.view.movies.moviedetails;

import java.util.List;

import ada.osc.movielist.model.Genre;
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
    }

    interface Presenter{
        void setView(MovieDetailsContract.View view);
        void getMovieDetails(int movieId);
        void getMovieTrailers(int movieId);
    }
}
