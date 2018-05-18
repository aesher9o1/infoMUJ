package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.manipal.aesher.infomuj.R;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder>{

    private List<ClubProvider> myProvider;
    private Context myContext;

    public ClubAdapter(Context mContext, List<ClubProvider> mProvider){
        this.myContext= mContext;
        this.myProvider = mProvider;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.club_layout,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    ClubProvider provider = myProvider.get(position);
    holder.clubButton.setText(provider.getButtonText());
    holder.clubTitle.setText(provider.getLayoutTitle());

    }

    @Override
    public int getItemCount() {
        return myProvider.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        Button clubButton;
        TextView clubTitle;
        MyViewHolder(View itemView) {
            super(itemView);
            clubButton = itemView.findViewById(R.id.roundedButton);
            clubTitle = itemView.findViewById(R.id.clubTitle);
        }

    }
}
