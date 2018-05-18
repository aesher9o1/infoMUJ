package info.manipal.aesher.infomuj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Adapters.ClubAdapter;
import info.manipal.aesher.infomuj.Adapters.ClubProvider;
import info.manipal.aesher.infomuj.Adapters.ContactAdapter;
import info.manipal.aesher.infomuj.Adapters.ContactProvider;
import info.manipal.aesher.infomuj.Constants.NavMenuMain;

public class ContactActivity extends AppCompatActivity {



    @BindView(R.id.contactRecycler)
    RecyclerView contactRecycler;

    NavMenuMain contentFillers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ButterKnife.bind(this);


        contentFillers= new NavMenuMain();
        List<ContactProvider> provider = new ArrayList<>();
        ContactAdapter adapter = new ContactAdapter(this,provider);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        contactRecycler.setLayoutManager(layoutManager);
        contactRecycler.setHasFixedSize(true);
        contactRecycler.setAdapter(adapter);
        contactRecycler.setNestedScrollingEnabled(false);
        contentFillers.contact(provider,this);

    }
}
