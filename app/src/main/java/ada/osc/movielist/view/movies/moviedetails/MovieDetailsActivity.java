package ada.osc.movielist.view.movies.moviedetails;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Credit;
import ada.osc.movielist.model.Genre;
import ada.osc.movielist.model.MovieDetailsResponse;
import ada.osc.movielist.model.Video;
import ada.osc.movielist.presentation.MovieDetailsPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View, VideoAdapter.VideoClickListener, CreditAdapter.CreditClickListener {

    private MovieDetailsContract.Presenter presenter;

    @BindView(R.id.imageview_movie_details_backdrop)
    ImageView movieImage;
    @BindView(R.id.textview_movie_details_title)
    TextView movieTitle;
    @BindView(R.id.textview_movie_details_original_title)
    TextView movieOriginalTitle;
    @BindView(R.id.textview_movie_details_runtime)
    TextView movieRuntime;
    @BindView(R.id.textview_movie_details_overview)
    TextView movieOverview;
    @BindView(R.id.textview_movie_details_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.progressbar_movie_details)
    ProgressBar progressBar;
    @BindView(R.id.movie_details_container)
    LinearLayout container;
    @BindView(R.id.recycler_movie_details_genres)
    RecyclerView movieGenres;
    @BindView(R.id.recycler_movie_details_trailers)
    RecyclerView movieTrailerLinks;
    @BindView(R.id.recycler_movie_details_credits)
    RecyclerView movieCredits;
    @BindView(R.id.movie_details_trailers_divider)
    TextView linksDivider;

    GenreAdapter genreAdapter;
    VideoAdapter videoAdapter;
    CreditAdapter creditAdapter;

    private MovieDetailsResponse movie;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.movie_details_title));
        ButterKnife.bind(this);
        presenter = new MovieDetailsPresenter(this);
        presenter.setView(this);
        if (getIntent().getExtras() != null) {
            int movieId = getIntent().getIntExtra(Consts.MOVIE_ID, 0);
            presenter.getMovieDetails(movieId);
            presenter.getMovieTrailers(movieId);
            presenter.getMovieCredits(movieId);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        this.menu = menu;
        presenter.checkIfMovieIsFaved(getIntent().getIntExtra(Consts.MOVIE_ID, 0));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fave_movie:
                presenter.faveMovie(movie);
                item.setVisible(false);
                menu.findItem(R.id.unfave_movie).setVisible(true);
                return true;
            case R.id.unfave_movie:
                presenter.unfaveMovie(movie);
                item.setVisible(false);
                menu.findItem(R.id.fave_movie).setVisible(true);
                return true;
            case R.id.share_movie:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.check_this_link) + "https://www.imdb.com/title/" + movie.getImdb_id());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showImage(String imagePath) {
        if (imagePath != null) {
            StringBuilder url = new StringBuilder();
            url.append(Consts.IMAGE_BASE_URL);
            url.append(imagePath);
            Glide.with(this).load(url.toString()).into(movieImage);
        } else {
            movieImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.no_image));
        }
    }

    @Override
    public void showTitle(String title) {
        movieTitle.setText(title);
    }

    @Override
    public void showOriginalTitle(String originalTitle) {
        movieOriginalTitle.setText(originalTitle);
    }

    @Override
    public void showOverview(String overview) {
        movieOverview.setText(overview);
    }

    @Override
    public void showRuntime(int runtime) {
        StringBuilder sb = new StringBuilder();
        sb.append(runtime / 60);
        sb.append("h ");
        sb.append(runtime % 60);
        sb.append("min | ");
        movieRuntime.setText(sb.toString());
    }

    @Override
    public void showGenres(List<Genre> genres) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        genreAdapter = new GenreAdapter(genres);
        movieGenres.setLayoutManager(layoutManager);
        movieGenres.setAdapter(genreAdapter);
    }

    @Override
    public void showVideoLinks(List<Video> videos) {
        if (videos.size() != 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            videoAdapter = new VideoAdapter(this, videos);
            movieTrailerLinks.setItemAnimator(new DefaultItemAnimator());
            movieTrailerLinks.setLayoutManager(layoutManager);
            movieTrailerLinks.setAdapter(videoAdapter);
        } else {
            linksDivider.setVisibility(View.GONE);
        }
    }

    @Override
    public void showCredits(List<Credit> credits) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        creditAdapter = new CreditAdapter(this, credits,this);
        movieCredits.setItemAnimator(new DefaultItemAnimator());
        movieCredits.setLayoutManager(layoutManager);
        movieCredits.setAdapter(creditAdapter);
    }

    @Override
    public void showReleaseDate(String releaseDate) {
        movieReleaseDate.setText(releaseDate);
    }

    @Override
    public void removeProgressBar() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void saveMovie(MovieDetailsResponse movie) {
        this.movie = movie;
    }

    @Override
    public void changeFavItemIcon(boolean isFaved) {
        if (!isFaved ) {
            menu.findItem(R.id.fave_movie).setVisible(false);
            menu.findItem(R.id.unfave_movie).setVisible(true);
        }
    }

    @Override
    public void onVideoClick(View view, int position) {
        WebView webView = new WebView(this);
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.YOUTUBE_BASE_URL);
        sb.append(videoAdapter.getKey(position));
        webView.loadUrl(sb.toString());
    }

    @Override
    public void onCreditClick(View view, int position) {
    }
}
