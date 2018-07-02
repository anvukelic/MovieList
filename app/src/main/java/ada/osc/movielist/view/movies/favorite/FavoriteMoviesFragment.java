package ada.osc.movielist.view.movies.favorite;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.presentation.FavoriteMoviePresenter;
import ada.osc.movielist.presentation.PopularMoviePresenter;
import ada.osc.movielist.view.movies.ItemClickListener;
import ada.osc.movielist.view.movies.MovieAdapter;
import ada.osc.movielist.view.movies.PaginationScrollListener;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMoviesFragment extends Fragment implements ItemClickListener, FavoriteMovieContract.View {


    public FavoriteMoviesFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_favorite_movies)
    RecyclerView rvMovies;
    @BindView(R.id.progressbar_favorite_movies)
    ProgressBar progressBar;
    @BindView(R.id.empty_screen_favorite)
    FrameLayout emptyScreen;

    MovieAdapter adapter;

    private FavoriteMovieContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new FavoriteMoviePresenter(getActivity());
        presenter.setView(this);
        presenter.getFavoriteMovies();
    }

    private void initRecyclerView(List<Movie> movies) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new MovieAdapter(this, movies, getActivity());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        showMovieDetails(adapter.getMovieId(position));
    }

    private void showMovieDetails(int movieId) {
        Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
        intent.putExtra(Consts.MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void displayMovies(List<Movie> movieList) {
        initRecyclerView(movieList);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyScreen() {
        emptyScreen.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (adapter!=null){
        adapter.clearMovies();}
    }

    @Override
    public void hideEmptyScreen() {
        emptyScreen.setVisibility(View.GONE);
    }


}
