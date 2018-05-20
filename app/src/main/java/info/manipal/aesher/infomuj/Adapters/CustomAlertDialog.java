package info.manipal.aesher.infomuj.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import info.manipal.aesher.infomuj.R;

public class CustomAlertDialog extends AlertDialog {
    Context context;
    View layout;
    WebView webView;
    ProgressBar progressBar;
    ImageView image;




    public CustomAlertDialog(@NonNull Context context, View layout) {
        super(context);
        this.context = context;
        this.setView(layout);
        this.layout = layout;
        webView = layout.findViewById(R.id.webview);
        image = layout.findViewById(R.id.waitingImage);
        progressBar = layout.findViewById(R.id.progressBar1);
    }



    public void SetWebView(String str){
        webView.loadData(str, "text/html", null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressBarVisible(false);
            }
        });
    }


    public void removeimage(){
        image.animate().scaleX(0f).scaleY(0f).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                image.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }).start();

    }



    public void ProgressBarVisible(Boolean mode){
        if(mode){
            progressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
            removeimage();
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        ProgressBarVisible(true);
        SetWebView("");
    }
}
