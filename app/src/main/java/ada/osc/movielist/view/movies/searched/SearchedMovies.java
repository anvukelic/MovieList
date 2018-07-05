package ada.osc.movielist.view.movies.searched;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.presentation.SearchedMoviePresenter;
import ada.osc.movielist.view.movies.ItemClickListener;
import ada.osc.movielist.view.movies.MovieAdapter;
import ada.osc.movielist.view.movies.PaginationScrollListener;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchedMovies extends AppCompatActivity implements ItemClickListener, SearchedMoviesContract.View{

    private MovieAdapter adapter;
    @BindView(R.id.recycler_searched_movies)
    RecyclerView rvMovies;
    @BindView(R.id.swipe_searched_movies)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_screen_searched)
    FrameLayout emptyScreen;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;

    private SearchedMoviesContract.Presenter presenter;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_movies);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        presenter = new SearchedMoviePresenter();
        presenter.setView(this);
        if(getIntent()!=null){
            query = getIntent().getStringExtra("query");
            getSupportActionBar().setTitle(query);
            presenter.getSearchedMovies(query);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshSearchedMovies(query);
            }
        });
    }

    private void initRecyclerView(List<Movie> movies) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        adapter = new MovieAdapter(this, movies, this);
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(adapter);
        rvMovies.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1; //Increment page index to load the next one
                presenter.getSearchedMoviesNextPage(query, currentPage);
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
        Intent intent = new Intent(this, MovieDetailsActivity.class);
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

    @Override
    public void showNoMoviesFound() {
        swipeRefreshLayout.setVisibility(View.GONE);
        emptyScreen.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("No movies found");
    }
}
