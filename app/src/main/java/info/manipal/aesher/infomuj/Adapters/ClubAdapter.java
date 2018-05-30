package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;

import butterknife.OnClick;
import info.manipal.aesher.infomuj.Constants.firebaseData;
import info.manipal.aesher.infomuj.Fragment.MainPage;
import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.R;

import static android.content.Context.MODE_PRIVATE;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder>{

    private List<ClubProvider> myProvider;
    DatabaseReference ref;
    private Context myContext;
    CustomAlertDialog dialog;
    String category;
    public ClubAdapter(Context mContext, List<ClubProvider> mProvider, CustomAlertDialog dialog, String category){
        this.myContext= mContext;
        this.myProvider = mProvider;
        this.dialog = dialog;
        this.category = category;
        this.ref = FirebaseDatabase.getInstance().getReference("manipal");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.club_layout,parent,false);
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
            dialog.show();
            ref.child(""+provider.getIndex()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        firebaseData firebaseData = dataSnapshot.getValue(info.manipal.aesher.infomuj.Constants.firebaseData.class);

                        try{
                            if(!firebaseData.getDivId().equals("NaN")){
                                final StringBuilder BranchName = new StringBuilder();
                                BranchName.append("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>");
                                myTask myTask = new myTask();
                                Log.w("Aashis",firebaseData.getLongOverview());
                                Log.w("Aashis",firebaseData.getShortOverview());
                                dialog.SetWebView("<b>Please wait while we fetch the latest data available...</b>");
                                myTask.execute(firebaseData.getLongOverview(),BranchName.toString(),firebaseData.getShortOverview(),firebaseData.getDivId());
                            }
                            else {
                                final StringBuilder LongInfo = new StringBuilder();
                                LongInfo.append("<div style=\"text-align:center;font-size:18px;\"><b>" + firebaseData.getName() + "</b></div><br>");
                                LongInfo.append(firebaseData.getLongOverview());
                                dialog.SetWebView(LongInfo.toString());


                            }

                        }
                        catch (Exception e){
                            Log.w("Firebase Infalting",""+e);
                        }
                    }
                    else {
                        Toast.makeText(myContext,"Invalid QR Code",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    });

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


    class  myTask extends AsyncTask<String, Void, String> {

        String info= "";
        String shortOverview="";

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            shortOverview = strings[2];
            try {
                info+=strings[1];
                Document doc = Jsoup.connect(url).get();
                Log.w("Website","Website to fetch "+strings[3]);
                Log.w("Website",""+doc);
                String CssQuery = "div[id="+strings[3]+"]";
                Element text = doc.select(CssQuery).first();
                Log.w("Website",""+text);
                info += ""+text;
                return info;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(info!=null){
                dialog.SetWebView(info);


            }




        }

    }

}
