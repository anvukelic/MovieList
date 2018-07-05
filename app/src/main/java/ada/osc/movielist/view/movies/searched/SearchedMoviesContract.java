package ada.osc.movielist.view.movies.searched;

import java.util.List;

import ada.osc.movielist.model.Movie;

/**
 * Created by avukelic on 05-Jul-18.
 */
public interface SearchedMoviesContract {

    interface View {
        void displayMovies(List<Movie> movieList);

        void addMovies(List<Movie> movieList);

        void refreshRecycler(List<Movie> movieList);

        void showNoMoviesFound();
    }

    interface Presenter {
        void setView(SearchedMoviesContract.View view);

        void getSearchedMovies(String query);

        void getSearchedMoviesNextPage(String query, int page);

        void refreshSearchedMovies(String query);

    }
}
