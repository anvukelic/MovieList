package ada.osc.movielist.view.movies.moviedetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ada.osc.movielist.R;
import ada.osc.movielist.model.Genre;
import ada.osc.movielist.view.movies.MovieAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {

    private List<Genre> genres;

    public GenreAdapter(List<Genre> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public GenreAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_list_item, parent, false);
        return new GenreAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreAdapter.MyViewHolder holder, int position) {
        Genre genre = genres.get(position);
        StringBuilder sb = new StringBuilder();
        sb.append(genre.getGenre());
        if(position<genres.size()-1){
            sb.append(", ");
        }
        holder.title.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_genre_item_title)
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
