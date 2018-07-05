package ada.osc.movielist.presentation;

import ada.osc.movielist.interaction.ApiInteractor;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.MovieResponse;
import ada.osc.movielist.view.movies.popular.PopularMovieContract;
import ada.osc.movielist.view.movies.searched.SearchedMoviesContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class SearchedMoviePresenter implements SearchedMoviesContract.Presenter {

    public static final int FIRST_PAGE = 1;
    public static final int NEXT_PAGE = 2;
    public static final int REFRESH_PAGE = 3;


    private final ApiInteractor apiInteractor;
    private SearchedMoviesContract.View movieView;

    public SearchedMoviePresenter() {
        apiInteractor = new ApiInteractorImpl();
    }

    @Override
    public void setView(SearchedMoviesContract.View view) {
        movieView = view;
    }

    @Override
    public void getSearchedMovies(String query) {
        apiInteractor.getSearchedMovies(query, 1, getSearchedMoviesCallback(FIRST_PAGE));
    }

    @Override
    public void getSearchedMoviesNextPage(String query, int page) {
        apiInteractor.getSearchedMovies(query, page, getSearchedMoviesCallback(NEXT_PAGE));
    }

    @Override
    public void refreshSearchedMovies(String query) {
        apiInteractor.getSearchedMovies(query, 1, getSearchedMoviesCallback(REFRESH_PAGE));
    }

    public Callback<MovieResponse> getSearchedMoviesCallback(final int action) {
        return new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieResponse movieResponse = response.body();
                    if (movieResponse.getMovies().size() == 0) {
                        movieView.showNoMoviesFound();
                    } else {
                        switch (action) {
                            case FIRST_PAGE:
                                movieView.displayMovies(movieResponse.getMovies());
                                break;
                            case NEXT_PAGE:
                                movieView.addMovies(movieResponse.getMovies());
                                break;
                            case REFRESH_PAGE:
                                movieView.refreshRecycler(movieResponse.getMovies());
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        };
    }
}
