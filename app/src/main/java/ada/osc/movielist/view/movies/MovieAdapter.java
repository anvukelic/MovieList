package ada.osc.movielist.view.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by avukelic on 20-Jun-18.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movies;
    private ItemClickListener listener;
    private Context context;

    public MovieAdapter(ItemClickListener listener, List<Movie> movieList, Context context) {
        this.listener = listener;
        movies = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        holder.release_date.setText(movie.getRelease_date());
        holder.rate.setText(String.valueOf(movie.getRate()));
        StringBuilder url = new StringBuilder();
        url.append(Consts.IMAGE_BASE_URL);
        url.append(movie.getPosterPath());
        Glide.with(context).load(url.toString()).into(holder.poster);
    }

    public Movie getMovie(int position){
        return movies.get(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_title)
        TextView title;
        @BindView(R.id.movie_release_date)
        TextView release_date;
        @BindView(R.id.movie_rate)
        TextView rate;
        @BindView(R.id.movie_poster_image)
        ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onItemClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
