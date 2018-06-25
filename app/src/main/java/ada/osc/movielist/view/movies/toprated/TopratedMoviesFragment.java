package ada.osc.movielist.view.movies.toprated;


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
import ada.osc.movielist.presentation.TopratedMoviePresenter;
import ada.osc.movielist.view.movies.ItemClickListener;
import ada.osc.movielist.view.movies.MovieAdapter;
import ada.osc.movielist.view.movies.PaginationScrollListener;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopratedMoviesFragment extends Fragment implements ItemClickListener, TopratedMovieContract.View {


    public TopratedMoviesFragment() {
        // Required empty public constructor
    }

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    @BindView(R.id.recycler_top_rated_movies)
    RecyclerView rvMovies;
    @BindView(R.id.swipe_toprated_movies)
    SwipeRefreshLayout swipeRefreshLayout;

    MovieAdapter adapter;

    private TopratedMovieContract.Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toprated_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new TopratedMoviePresenter();
        presenter.setView(this);
        presenter.getTopRatedMovies(1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshTopRatedMovies();
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
                presenter.getTopRatedMoviesNextPage(currentPage);
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
