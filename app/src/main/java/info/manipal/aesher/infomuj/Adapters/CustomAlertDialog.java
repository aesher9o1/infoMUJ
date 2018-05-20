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
import android.widget.ProgressBar;

import info.manipal.aesher.infomuj.R;

public class CustomAlertDialog extends AlertDialog {
    Context context;
    View layout;
    WebView webView;
    ProgressBar progressBar;
    public CustomAlertDialog(@NonNull Context context, View layout) {
        super(context);
        this.context = context;
        this.setView(layout);
        this.layout = layout;
        webView = layout.findViewById(R.id.webview);
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

    public void ProgressBarVisible(Boolean mode){
        if(mode){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void dismiss() {
        super.dismiss();
        ProgressBarVisible(true);
        SetWebView("");
    }
}
