package info.manipal.aesher.infomuj.Adapters.Clubs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.Interface.LitmusPoemWrapper;

public class LitmusAdapter extends RecyclerView.Adapter<LitmusAdapter.ViewHolder> {


    private LitmusPoemWrapper litmusPoemWrapper;

    public LitmusAdapter(LitmusPoemWrapper litmusPoemWrapper) {
        this.litmusPoemWrapper = litmusPoemWrapper;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycleview_litmus, parent, false);
        return new LitmusAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.poem.setText(litmusPoemWrapper.getPoems().get(position));
        holder.title.setText(litmusPoemWrapper.getTitle().get(position));
    }

    @Override
    public int getItemCount() {
        if (litmusPoemWrapper.getPoems() != null)
            return litmusPoemWrapper.getPoems().size();
        else
            return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView poem, title;

        ViewHolder(View itemView) {
            super(itemView);
            poem = itemView.findViewById(R.id.poems);
            title = itemView.findViewById(R.id.poet);
        }
    }
}
