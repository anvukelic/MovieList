package ada.osc.movielist.view.movies.toprated;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface TopratedMovieContract {

    interface View{
        void displayMovies(List<Movie> movieList);
    }

    interface Presenter{
        void setView(TopratedMovieContract.View view);

        void getTopRatedMovies(int page);
    }
}
