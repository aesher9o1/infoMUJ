package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.graphics.Typeface;
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

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<ContactProvider> myProvider;
    private Context myContext;

    public ContactAdapter(Context mContext, List<ContactProvider> mProvider){
        this.myContext= mContext;
        this.myProvider = mProvider;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout,parent,false);
        return new ContactAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContactProvider provider = myProvider.get(position);
        holder.contactImage.setImageResource(provider.getImage());
        holder.contactPerson.setText(provider.getName());
    }

    @Override
    public int getItemCount() {
        return myProvider.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView contactImage;
        TextView contactPerson;
        MyViewHolder(View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactIcon);
            contactPerson = itemView.findViewById(R.id.contactPerson);

        }

    }
}
