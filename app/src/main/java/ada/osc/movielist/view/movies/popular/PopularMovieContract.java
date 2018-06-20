package ada.osc.movielist.view.movies.popular;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface PopularMovieContract {

    interface View{
        void displayMovies(List<Movie> movieList);
    }

    interface Presenter{
        void setView(PopularMovieContract.View view);

        void getPopularMovies(int page);
    }
}
