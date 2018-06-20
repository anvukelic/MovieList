package ada.osc.movielist.view.movies.upcoming;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface UpcomingMovieContract {

    interface View{
        void displayMovies(List<Movie> movieList);
    }

    interface Presenter{
        void setView(UpcomingMovieContract.View view);

        void getUpcomingMovies(int page);
    }
}
