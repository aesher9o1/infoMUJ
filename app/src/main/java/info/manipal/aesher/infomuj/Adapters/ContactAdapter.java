package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.manipal.aesher.infomuj.Fragment.WayPoints;
import info.manipal.aesher.infomuj.R;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private List<ContactProvider> myProvider;
    private Context myContext;

    public ContactAdapter(Context mContext, List<ContactProvider> mProvider) {
        this.myContext = mContext;
        this.myProvider = mProvider;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ContactAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ContactProvider provider = myProvider.get(position);
        holder.contactImage.setImageResource(provider.getImage());
        holder.contactPerson.setText(provider.getName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFrag(view, provider.getNumber());
            }
        });
    }

    public void replaceFrag(View view, String clickedItem) {

        Bundle bundle = new Bundle();
        bundle.putString("dataToLoad", clickedItem);
        WayPoints fragmentWay = new WayPoints();
        fragmentWay.setArguments(bundle);
        FragmentTransaction ft = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout).replace(R.id.listfragments, fragmentWay, "places");
        ft.commit();
    }


    @Override
    public int getItemCount() {
        return myProvider.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView contactImage;
        TextView contactPerson;
        LinearLayout linearLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            contactImage = itemView.findViewById(R.id.contactIcon);
            contactPerson = itemView.findViewById(R.id.contactPerson);
            linearLayout = itemView.findViewById(R.id.body);

        }

    }
}
