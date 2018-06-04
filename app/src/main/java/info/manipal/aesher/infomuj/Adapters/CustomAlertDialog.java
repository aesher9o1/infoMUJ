package info.manipal.aesher.infomuj.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

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


    public void SetWebView(String str) {


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                ProgressBarVisible(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                super.shouldOverrideUrlLoading(view, request);
                Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                view.getContext().startActivity(intent);
                return true;
            }
        });

        webView.loadData(str, "text/html", null);

    }


    public void removeimage() {
        image.animate().scaleX(0f).scaleY(0f).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                image.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        }).start();

    }


    public void ProgressBarVisible(Boolean mode) {
        if (mode) {
            progressBar.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
        } else {
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
