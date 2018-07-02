package ada.osc.movielist.view.movies.favorite;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 20-Jun-18.
 */
public interface FavoriteMovieContract {

    interface View {
        void displayMovies(List<Movie> movieList);
        void showEmptyScreen();
        void hideEmptyScreen();
    }

    interface Presenter {
        void setView(FavoriteMovieContract.View view);

        void getFavoriteMovies();
    }
}
