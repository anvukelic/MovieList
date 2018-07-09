package ada.osc.movielist.view.movies.popular;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.presentation.PopularMoviePresenter;
import ada.osc.movielist.view.movies.MovieAdapter;
import ada.osc.movielist.view.movies.PaginationScrollListener;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularMoviesFragment extends Fragment implements MovieAdapter.MovieClickAdapter, PopularMovieContract.View {


    public PopularMoviesFragment() {
        // Required empty public constructor
    }

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    @BindView(R.id.recycler_popular_movies)
    RecyclerView rvMovies;
    @BindView(R.id.swipe_popular_movies)
    SwipeRefreshLayout swipeRefreshLayout;


    MovieAdapter adapter;

    private PopularMovieContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new PopularMoviePresenter();
        presenter.setView(this);
        presenter.getPopularMovies(1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPopularMovies();
            }
        });
    }

    private void initRecyclerView(List<Movie> movies) {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new MovieAdapter(this, movies, getActivity());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(adapter);
        rvMovies.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one
                presenter.getPopularMoviesNextPage(currentPage);
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onMovieClick(View view, int position) {
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
    }

    @Override
    public void addMovies(List<Movie> movieList) {
        if (movieList.size() >= Consts.PAGE_SIZE) {
            adapter.addMovies(movieList);
            isLoading = false;
        } else {
            isLastPage = true;
        }
    }

    @Override
    public void refreshRecycler(List<Movie> movieList) {
        adapter.refreshData(movieList);
        currentPage = 1;
        swipeRefreshLayout.setRefreshing(false);
    }

}
