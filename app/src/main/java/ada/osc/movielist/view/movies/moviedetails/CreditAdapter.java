package ada.osc.movielist.view.movies.moviedetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ada.osc.movielist.Consts;
import ada.osc.movielist.R;
import ada.osc.movielist.model.Credit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by avukelic on 09-Jul-18.
 */
public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.MyViewHolder> {

    private List<Credit> credits;
    private Context context;
    private CreditClickListener listener;

    public CreditAdapter(CreditClickListener listener, List<Credit> credits, Context context) {
        this.credits = credits;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CreditAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_list_item, parent, false);
        return new CreditAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditAdapter.MyViewHolder holder, int position) {
        Credit credit = credits.get(position);
        holder.name.setText(credit.getName());
        holder.character.setText(credit.getCharacter());
        StringBuilder url = new StringBuilder();
        url.append(Consts.IMAGE_BASE_URL);
        url.append(credit.getProfilePath());
        Glide.with(context).load(url.toString()).apply(new RequestOptions().override(150, 200)).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return credits.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.credit_name)
        TextView name;
        @BindView(R.id.credit_character)
        TextView character;
        @BindView(R.id.credit_poster_image)
        ImageView poster;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        @OnClick
        public void onItemClick(View view) {
            if (listener != null) {
                listener.onCreditClick(view, getAdapterPosition());
            }
        }
    }
    public interface CreditClickListener {
        void onCreditClick(View view, int position);
    }

}
