package ada.osc.movielist.view.movies.upcoming;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ada.osc.movielist.R;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.presentation.TopratedMoviePresenter;
import ada.osc.movielist.presentation.UpcomingMoviePresenter;
import ada.osc.movielist.view.movies.MovieAdapter;
import ada.osc.movielist.view.movies.toprated.TopratedMovieContract;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingMoviesFragment extends Fragment implements MovieAdapter.ItemClickListener, UpcomingMovieContract.View{


    public UpcomingMoviesFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recycler_upcoming_movies)
    RecyclerView rvMovies;

    MovieAdapter adapter;

    private UpcomingMovieContract.Presenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter = new UpcomingMoviePresenter();
        presenter.setView(this);
        presenter.getUpcomingMovies(1);
    }

    private void initRecyclerView(List<Movie> movies) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter = new MovieAdapter(this, movies, getActivity());
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void displayMovies(List<Movie> movieList) {
        initRecyclerView(movieList);
    }
}
