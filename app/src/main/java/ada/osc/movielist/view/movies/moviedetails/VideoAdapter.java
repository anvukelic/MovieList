package ada.osc.movielist.view.movies.moviedetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ada.osc.movielist.R;
import ada.osc.movielist.model.Video;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by avukelic on 25-Jun-18.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private List<Video> videos;
    private VideoClickListener listener;

    public VideoAdapter(VideoClickListener listener, List<Video> videos) {
        this.listener = listener;
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);
        return new VideoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.title.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public String getKey(int position) {
        return videos.get(position).getKey();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_video_item_title)
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onItemClick(View view) {
            if (listener != null) {
                listener.onVideoClick(view, getAdapterPosition());
            }
        }
    }

    public interface VideoClickListener {
        void onVideoClick(View view, int position);
    }


}
