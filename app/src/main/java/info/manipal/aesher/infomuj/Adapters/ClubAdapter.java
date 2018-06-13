package info.manipal.aesher.infomuj.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.ScrappedResultClubs;
import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.ScrappedResultNormal;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder> {

    private CustomAlertDialog dialog;
    private String category;
    private List<ClubProvider> myProvider;
    private Context myContext;

    public ClubAdapter(Context mContext, List<ClubProvider> mProvider, CustomAlertDialog dialog, String category) {
        this.myContext = mContext;
        this.myProvider = mProvider;
        this.dialog = dialog;
        this.category = category;



    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_club, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ClubProvider provider = myProvider.get(position);
        holder.clubButton.setText(provider.getButtonText());
        holder.clubTitle.setText(provider.getLayoutTitle());
        holder.clubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(category.equals("club")){
                    Intent i = new Intent(myContext.getApplicationContext(), ScrappedResultClubs.class);
                    i.putExtra("Index", ""+provider.getIndex());
                    i.putExtra("Name",provider.getLayoutTitle());
                    myContext.startActivity(i);
                }
                else{
                    Intent i = new Intent(myContext.getApplicationContext(), ScrappedResultNormal.class);
                    i.putExtra("Index", ""+provider.getIndex());
                    i.putExtra("Name",provider.getLayoutTitle());
                    myContext.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myProvider.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button clubButton;
        TextView clubTitle;


        MyViewHolder(View itemView) {
            super(itemView);
            clubButton = itemView.findViewById(R.id.roundedButton);
            clubTitle = itemView.findViewById(R.id.clubTitle);

        }


    }



}
