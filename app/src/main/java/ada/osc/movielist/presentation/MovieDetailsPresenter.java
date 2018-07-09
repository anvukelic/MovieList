package ada.osc.movielist.presentation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.interaction.ApiInteractor;
import ada.osc.movielist.interaction.ApiInteractorImpl;
import ada.osc.movielist.model.Credit;
import ada.osc.movielist.model.CreditsResponse;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.VideosResponse;
import ada.osc.movielist.model.Video;
import ada.osc.movielist.utils.SharedPrefUtil;
import ada.osc.movielist.view.movies.moviedetails.MovieDetailsContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final ApiInteractor apiInteractor;
    private MovieDetailsContract.View movieView;

    FirebaseDatabase database;
    DatabaseReference dbRef;

    public MovieDetailsPresenter(Context context) {
        apiInteractor = new ApiInteractorImpl();
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference(SharedPrefUtil.getStringFromSharedPref(context,Consts.SP_NAME,Consts.USER_ID_PREF));
    }

    @Override
    public void setView(MovieDetailsContract.View view) {
        movieView = view;
    }

    @Override
    public void getMovieDetails(int movieId) {
        apiInteractor.getMovieDetails(movieId, getMovieDetailsCallback());
    }

    @Override
    public void getMovieTrailers(int movieId) {
        apiInteractor.getMovieTrailers(movieId, getMovieTrailersCallback());
    }

    @Override
    public void getMovieCredits(int movieId) {
        apiInteractor.getMovieCredits(movieId, getMovieCreditsCallback());
    }

    @Override
    public void faveMovie(MovieDetailsResponse movie) {
        dbRef.child("favorite").child(String.valueOf(movie.getId())).setValue(movie);
    }

    @Override
    public void unfaveMovie(MovieDetailsResponse movie) {
        dbRef.child("favorite").child(String.valueOf(movie.getId())).removeValue();
    }

    @Override
    public void checkIfMovieIsFaved(int movieId) {
       dbRef.child("favorite").child(String.valueOf(movieId)).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               movieView.changeFavItemIcon(dataSnapshot.getValue(MovieDetailsResponse.class)==null);
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
           }
       });
    }

    private Callback<MovieDetailsResponse> getMovieDetailsCallback() {
        return new Callback<MovieDetailsResponse>() {
            @Override
            public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetailsResponse details = response.body();
                    movieView.showImage(details.getImagePath());
                    movieView.showTitle(details.getTitle());
                    movieView.showOriginalTitle(details.getOriginalTitle());
                    movieView.showOverview(details.getOverview());
                    movieView.showRuntime(details.getRuntime());
                    movieView.showGenres(details.getGenres());
                    movieView.showReleaseDate(details.getReleaseDate());
                    movieView.removeProgressBar();
                    movieView.saveMovie(details);
                } else {
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }

    public Callback<VideosResponse> getMovieTrailersCallback() {
        return new Callback<VideosResponse>() {
            @Override
            public void onResponse(Call<VideosResponse> call, Response<VideosResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Video> videoList = new ArrayList<>();
                    for (Video video : response.body().getVideos()) {
                        if (video.getType().equals("Trailer")) {
                            videoList.add(video);
                        }
                    }
                    movieView.showVideoLinks(videoList);
                }
            }

            @Override
            public void onFailure(Call<VideosResponse> call, Throwable t) {
            }
        };
    }

    public Callback<CreditsResponse> getMovieCreditsCallback() {
        return new Callback<CreditsResponse>() {
            @Override
            public void onResponse(Call<CreditsResponse> call, Response<CreditsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Credit> creditList = new ArrayList<>();
                    for (Credit credit: response.body().getCredits()) {
                        if (credit.getCharacter()!=null) {
                            creditList.add(credit);
                        }
                    }
                    movieView.showCredits(creditList);
                }
            }

            @Override
            public void onFailure(Call<CreditsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };
    }
}
