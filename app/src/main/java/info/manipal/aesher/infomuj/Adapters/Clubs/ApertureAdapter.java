package info.manipal.aesher.infomuj.Adapters.Clubs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.Threads.Interface.ApertureWrapper;

public class ApertureAdapter extends RecyclerView.Adapter<ApertureAdapter.MyViewHolder> {

    private ApertureWrapper apertureWrapper;
    private Context myContext;

    public ApertureAdapter(Context myContext, ApertureWrapper apertureWrapper) {
        this.myContext = myContext;
        this.apertureWrapper = apertureWrapper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_aperture, parent, false);
        return new ApertureAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        Log.w("Aperture", apertureWrapper.getUrl().get(holder.getAdapterPosition()));
        Picasso.get().load(Objects.requireNonNull(apertureWrapper.getUrl().get(holder.getAdapterPosition()))).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(apertureWrapper.getUrl().get(holder.getAdapterPosition())).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.imageView);
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(myContext, apertureWrapper.getName().get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (apertureWrapper.getName() != null)
            return apertureWrapper.getName().size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}


