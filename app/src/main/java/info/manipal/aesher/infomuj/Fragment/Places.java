package info.manipal.aesher.infomuj.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.ContactAdapter;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;
import info.manipal.aesher.infomuj.R;

public class Places extends Fragment {

    @BindView(R.id.contactRecycler)
    RecyclerView contactRecycler;
    NavMenuMain contentFillers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_places, container, false);
        ButterKnife.bind(this, v);


        contentFillers = new NavMenuMain();
        List<ContactProvider> provider = new ArrayList<>();
        ContactAdapter adapter = new ContactAdapter(provider);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactRecycler.setLayoutManager(layoutManager);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setAdapter(adapter);
        contactRecycler.setNestedScrollingEnabled(false);
        contentFillers.contact(provider, getContext());

        return v;
    }
}
