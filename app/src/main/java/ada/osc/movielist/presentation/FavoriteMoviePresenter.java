package ada.osc.movielist.presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.model.Movie;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.utils.SharedPrefUtil;
import ada.osc.movielist.view.movies.favorite.FavoriteMovieContract;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class FavoriteMoviePresenter implements FavoriteMovieContract.Presenter {

    private FavoriteMovieContract.View movieView;
    private DatabaseReference dbRef;

    public FavoriteMoviePresenter(Context context) {
        dbRef = FirebaseDatabase.getInstance().getReference(SharedPrefUtil.getStringFromSharedPref(context, Consts.SP_NAME, Consts.USER_ID_PREF));
    }

    @Override
    public void setView(FavoriteMovieContract.View view) {
        movieView = view;
    }

    @Override
    public void getFavoriteMovies() {
        Query favoriteMoviesFirstPage = dbRef.child("favorite");
        favoriteMoviesFirstPage.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Movie> movies = new ArrayList<>();
                MovieDetailsResponse movieDetails;
                for (DataSnapshot result : dataSnapshot.getChildren()) {
                    movieDetails = result.getValue(MovieDetailsResponse.class);
                    Movie movie = new Movie(movieDetails.getId(), movieDetails.getTitle(), movieDetails.getRate(), movieDetails.getPoster(), movieDetails.getReleaseDate());
                    movies.add(movie);
                }
                if (movies.size() > 0) {
                    movieView.hideEmptyScreen();
                    movieView.displayMovies(movies);
                } else {
                    movieView.showEmptyScreen();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
