package info.manipal.aesher.infomuj;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Constants.Links;


public class PlacesActivity extends AppCompatActivity {


    @BindView(R.id.list_links)
    RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Links,LinksViewHolder> recyclerAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("links");
        ref.keepSynced(true);



        Query query = ref.limitToFirst(100);

        FirebaseRecyclerOptions<Links> options = new FirebaseRecyclerOptions.Builder<Links>()
                                                    .setQuery(query,Links.class).build();


        recyclerAdapter = new FirebaseRecyclerAdapter<Links, LinksViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final LinksViewHolder holder, int position, @NonNull final Links model) {
                    holder.name.setText(model.getName());

                Picasso.get().load(Objects.requireNonNull(model.getIcon())).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).into(holder.linkImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getIcon()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.linkImage);
                    }
                });


                holder.linkBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink())));
                    }
                });

            }

            @NonNull
            @Override
            public LinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_recyclerview_links, parent, false);

                return new LinksViewHolder(view);
            }
        };

        recyclerView.setAdapter(recyclerAdapter);

    }



    public  class LinksViewHolder extends RecyclerView.ViewHolder{
        ImageView linkImage;
        TextView name;
        LinearLayout linkBody;

        LinksViewHolder(View itemView) {
            super(itemView);

            linkImage = itemView.findViewById(R.id.link_image);
            name = itemView.findViewById(R.id.link_name);
            linkBody = itemView.findViewById(R.id.link_body);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

}
