package info.manipal.aesher.infomuj.Adapters.Clubs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.Interface.IEEENewsWrapper;

public class IEEEAdapter extends RecyclerView.Adapter<IEEEAdapter.ViewHolder> {


    private IEEENewsWrapper newsWrapper;
    Context context;


    public IEEEAdapter(IEEENewsWrapper newsWrapper, Context context) {
        this.newsWrapper = newsWrapper;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_ieee,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.news.setText(newsWrapper.getTitle().get(holder.getAdapterPosition()));
        holder.title.setText(newsWrapper.getBy().get(holder.getAdapterPosition()));

        Date timePosted = new Date(newsWrapper.getTime()[holder.getAdapterPosition()]);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.S");
        holder.time.setText(formatter.format(timePosted));

        holder.clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsWrapper.getURL().get(holder.getAdapterPosition())));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(newsWrapper.getURL()!=null)
            return newsWrapper.getURL().size();
        else
            return 0;
    }

    static  class ViewHolder extends RecyclerView.ViewHolder{

        TextView clicker, time, title, news;

        ViewHolder(View itemView) {
            super(itemView);
            clicker = itemView.findViewById(R.id.full_story);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.user_name);
            news = itemView.findViewById(R.id.mainStory);
        }
    }
}
