package info.manipal.aesher.infomuj.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

import butterknife.OnClick;
import info.manipal.aesher.infomuj.MainActivity;
import info.manipal.aesher.infomuj.R;

import static android.content.Context.MODE_PRIVATE;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.MyViewHolder>{

    private List<ClubProvider> myProvider;
    private Context myContext;
    CustomAlertDialog dialog;
    String category;
    public ClubAdapter(Context mContext, List<ClubProvider> mProvider, CustomAlertDialog dialog, String category){
        this.myContext= mContext;
        this.myProvider = mProvider;
        this.dialog = dialog;
        this.category = category;
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
            if(category.equals("BRANCH")){
                int index = provider.getIndex();
                SharedPreferences prefs = myContext.getSharedPreferences("com.manipal.infomuj", MODE_PRIVATE);
                String branches = prefs.getString("branches",null);
                String branchArray[] = branches.split("#");
                StringBuilder infoFill = new StringBuilder();
                infoFill.append("<b>"+branchArray[((index-1)*6)+1]+"</b><br>");
                infoFill.append(branchArray[((index-1)*6)+2]);
                dialog.SetWebView(infoFill.toString());
//                dialog.ProgressBarVisible(false);

            }
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
}
