package info.manipal.aesher.infomuj.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.ChangeText;

import java.io.File;
import java.util.Objects;

import butterknife.ButterKnife;
import info.manipal.aesher.infomuj.Constants.UtilityFunctions;
import info.manipal.aesher.infomuj.Constants.VR;
import info.manipal.aesher.infomuj.R;
import info.manipal.aesher.infomuj.VRView;

public class VRFragment extends Fragment {


    public static final String textWhenMainScreen = "Info Muj";
    public static final String textWhenVRScreen = "Manipal 360";
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<VR, vrRecyclerView> VRAdapter;
    DatabaseReference databaseReference;
    boolean success = true;
    SharedPreferences prefs;
    TextView heading;
    ViewGroup transitionsContainer;
    File folder;


    UtilityFunctions utilityFunctions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_vr, container, false);
        transitionsContainer = Objects.requireNonNull(getActivity()).getWindow().getDecorView().findViewById(R.id.container);
        heading = getActivity().getWindow().getDecorView().findViewById(R.id.heading);
        replaceText();
        ButterKnife.bind(this, v);
        isStoreagePermission();
        utilityFunctions = new UtilityFunctions(getContext());

        folder = new File(Environment.getExternalStorageDirectory() + File.separator + "infoMUJAssets");
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (!success) {
            Toast.makeText(getContext(), "Sorry You Lack Storage", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        recyclerView = v.findViewById(R.id.virtualReality);
        databaseReference = FirebaseDatabase.getInstance().getReference("VR");
        databaseReference.keepSynced(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        Query query = databaseReference.limitToFirst(10);
        final FirebaseRecyclerOptions<VR> options =
                new FirebaseRecyclerOptions.Builder<VR>()
                        .setQuery(query, VR.class)
                        .build();

        System.gc();
        VRAdapter = new FirebaseRecyclerAdapter<VR, vrRecyclerView>(options) {


            @Override
            protected void onBindViewHolder(@NonNull final vrRecyclerView holder, final int position, @NonNull final VR model) {
                int color = utilityFunctions.getMatColor();
                holder.verticalLine.setBackgroundColor(color);
                FirebaseStorage storage = FirebaseStorage.getInstance();

                final StorageReference storageReference = storage.getReferenceFromUrl(model.getUrl());
                if (new File(folder + "/" + model.getPlace() + ".jpg").exists()) {

                    VRAdapter.getRef(position).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            VR vr = dataSnapshot.getValue(VR.class);
                            assert vr != null;

                            Picasso.get().load(model.getBgImage()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).fit().centerCrop() .into(holder.panoView, new Callback() {
                                @Override
                                public void onSuccess() {}

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getBgImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop() .into(holder.panoView);
                                }
                            });



                            if (vr.getSize() == (new File(folder + "/" + model.getPlace() + ".jpg").length())) {
                                holder.mainBody.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(getActivity(), VRView.class);
                                        i.putExtra("imageToLoad", folder + "/" + model.getPlace() + ".jpg");
                                        startActivity(i);
                                    }
                                });

                                holder.developerCredits.setText( holder.developerCredits.getText().toString()+model.getCredits());
                                holder.developerCredits.setVisibility(View.VISIBLE);
                                holder.name.setText(model.getPlace());
                                Log.w("Size", "All Clear");
                            } else {
                                Log.w("Size", "File Exist but downloading");
                                downloadFile(model.getPlace(), storageReference, holder,model.getBgImage(),model.getCredits());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    Log.w("Size", "Fresh Download");
                    downloadFile(model.getPlace(), storageReference, holder,model.getBgImage(), model.getCredits());
                }


            }

            @NonNull
            @Override
            public vrRecyclerView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_vr, parent, false);

                return new vrRecyclerView(view);
            }
        };


        recyclerView.setAdapter(VRAdapter);
        return v;
    }


    private void downloadFile(final String filename, StorageReference storageReference, final vrRecyclerView holder, final String backgroundImage, final String Credits) {

        Picasso.get().load(backgroundImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_small_noti).fit().centerCrop() .into(holder.panoView, new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                Picasso.get().load(backgroundImage).placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_small_noti).fit().centerCrop() .into(holder.panoView);
            }

        });

        final File localFile = new File(folder, filename + ".jpg");
        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                holder.mainBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), VRView.class);
                        i.putExtra("imageToLoad", folder + "/" + filename + ".jpg");
                        startActivity(i);
                    }
                });

                Toast.makeText(Objects.requireNonNull(getContext()), filename+" is ready to be viewed. Tap on the card to get started", Toast.LENGTH_LONG).show();
                holder.name.setText(filename);
                holder.developerCredits.setText( holder.developerCredits.getText().toString()+Credits);
                holder.developerCredits.setVisibility(View.VISIBLE);

            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                holder.name.setText(((int) progress) + " %downloaded");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                localFile.delete();
            }
        });

    }

    public void replaceText() {
        com.transitionseverywhere.TransitionManager.beginDelayedTransition(transitionsContainer,
                new ChangeText().setChangeBehavior(3));

        if (heading.getText().toString().equals(textWhenMainScreen))
            heading.setText(textWhenVRScreen);
        else
            heading.setText(textWhenMainScreen);

    }

    @Override
    public void onStart() {
        super.onStart();
        VRAdapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        VRAdapter.stopListening();

    }

    public void isStoreagePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

    }

    public class vrRecyclerView extends RecyclerView.ViewHolder {

        TextView name;
        LinearLayout mainBody;
        TextView developerCredits;
        ImageView panoView;
        View verticalLine;


        vrRecyclerView(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mainBody = itemView.findViewById(R.id.body);
            developerCredits = itemView.findViewById(R.id.developerCredit);
            panoView = itemView.findViewById(R.id.pano_view);
            verticalLine = itemView.findViewById(R.id.verticalLine);

        }
    }



}
