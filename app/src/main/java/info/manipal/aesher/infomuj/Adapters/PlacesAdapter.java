package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import info.manipal.aesher.infomuj.R;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {

    private List<String> myProvider;
    private Context myContext;

    public PlacesAdapter(Context mContext, List<String> mProvider) {
        this.myContext = mContext;
        this.myProvider = mProvider;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_navigation, parent, false);
        return new PlacesAdapter.MyViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String provider = myProvider.get(position);
        holder.waypoint.setText(provider);

    }


    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return myProvider.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView waypoint;
        TimelineView timelineView;

        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            waypoint = itemView.findViewById(R.id.waypoint);
            timelineView = itemView.findViewById(R.id.time_marker);
            timelineView.initLine(viewType);

        }

    }
}
