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
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.ContactAdapter;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.Adapters.PlacesAdapter;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;
import info.manipal.aesher.infomuj.Constants.PlacesFeeder;
import info.manipal.aesher.infomuj.R;


public class WayPoints extends Fragment {


    @BindView(R.id.guideWay)
    RecyclerView recyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String passedString = getArguments().getString("dataToLoad");
        View v = inflater.inflate(R.layout.fragment_waypoints,container,false);
        ButterKnife.bind(this,v);

        PlacesFeeder contentFillers= new PlacesFeeder();
        List<String> provider = new ArrayList<>();
        PlacesAdapter adapter = new PlacesAdapter(getActivity(),provider);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        if(passedString.equals("gym"))
            contentFillers.Gym(provider);
        else if(passedString.equals("library"))
            contentFillers.Library(provider);
        else if (passedString.equals("admin"))
            contentFillers.Admin(provider);
        else if (passedString.equals("food"))
            contentFillers.Food(provider);
        else if (passedString.equals("finance"))
            contentFillers.Finance(provider);

        return v;
    }
}
