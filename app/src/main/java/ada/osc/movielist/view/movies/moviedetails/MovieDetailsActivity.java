package ada.osc.movielist.view.movies.moviedetails;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ada.osc.movielist.model.Genre;
import ada.osc.movielist.model.Video;
import ada.osc.movielist.presentation.MovieDetailsPresenter;
import ada.osc.movielist.view.movies.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsContract.View, ItemClickListener {

    private MovieDetailsContract.Presenter presenter;

    @BindView(R.id.imageview_movie_details_backdrop)
    ImageView movieImage;
    @BindView(R.id.textview_movie_details_title)
    TextView movieTitle;
    @BindView(R.id.textview_movie_details_original_title)
    TextView movieOriginalTitle;
    @BindView(R.id.textview_movie_details_runtime)
    TextView movieRuntime;
    @BindView(R.id.recycler_movie_details_genres)
    RecyclerView movieGenres;
    @BindView(R.id.textview_movie_details_overview)
    TextView movieOverview;
    @BindView(R.id.textview_movie_details_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.progressbar_movie_details)
    ProgressBar progressBar;
    @BindView(R.id.movie_details_container)
    LinearLayout container;
    @BindView(R.id.recycler_movie_details_trailers)
    RecyclerView movieTrailerLinks;
    @BindView(R.id.movie_details_trailers_divider)
    TextView linksDivider;

    GenreAdapter genreAdapter;
    VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new MovieDetailsPresenter();
        presenter.setView(this);
        if (getIntent().getExtras() != null) {
            presenter.getMovieDetails(getIntent().getIntExtra(Consts.MOVIE_ID, 0));
            presenter.getMovieTrailers(getIntent().getIntExtra(Consts.MOVIE_ID, 0));
        } else {
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
    public void showReleaseDate(String releaseDate) {
        movieReleaseDate.setText(releaseDate);
    }

    @Override
    public void removeProgressBar() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {
        WebView webView = new WebView(this);
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.YOUTUBE_BASE_URL);
        sb.append(videoAdapter.getKey(position));
        webView.loadUrl(sb.toString());
    }
}
