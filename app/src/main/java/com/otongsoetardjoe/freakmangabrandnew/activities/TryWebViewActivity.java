package com.otongsoetardjoe.freakmangabrandnew.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.otongsoetardjoe.freakmangabrandnew.activities.search_or_read_mvp.SearchNuclearActivity;
import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityTryWebViewBinding;
import com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp.NekoNewReleasesInterface;
import com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp.NekoNewReleasesPresenter;
import com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp.NekoWatchInterface;

public class TryWebViewActivity extends AppCompatActivity implements NekoWatchInterface {
    private ActivityTryWebViewBinding webViewBinding;
    private ProgressDialog progressDialog;
    private NekoNewReleasesPresenter newReleasePresenter = new NekoNewReleasesPresenter(this);

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBinding = ActivityTryWebViewBinding.inflate(getLayoutInflater());
        setContentView(webViewBinding.getRoot());
        initUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Be patient please onii-chan, it just take less than a minute :3");
        webViewBinding.webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; rv:13.0) Gecko/20100101 Firefox/12");
        webViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        webViewBinding.webView.getSettings().setLoadWithOverviewMode(true);
        webViewBinding.webView.getSettings().setUseWideViewPort(false);
        String episodeURL = getIntent().getStringExtra("watchNekoURL");
        String episodeTitle = getIntent().getStringExtra("watchNekoTitle");
        Toast.makeText(this, episodeTitle, Toast.LENGTH_LONG).show();
        getNekoVideo(episodeURL);
    }

    private void getNekoVideo(String episodeURL) {
        progressDialog.show();
        newReleasePresenter.getNekoWatch(episodeURL);
    }

    @Override
    public void onGetDataSuccess(String nekoURL) {
        runOnUiThread(() -> {
            progressDialog.dismiss();
            if (nekoURL != null) {
                String imageURLModify = "<html><body style=\"margin: 0; padding: 0\"><iframe width=\"100%\" height=\"100%\" src=\"" + nekoURL + "\" allowfullscreen=\"allowfullscreen\"></iframe></body></html>";
                webViewBinding.webView.loadData(imageURLModify, "text/html", "UTF-8");
            }
        });
    }

    @Override
    public void onGetDataFailed() {
        runOnUiThread(() -> {
            progressDialog.dismiss();
            Toast.makeText(TryWebViewActivity.this, "Gagal!!", Toast.LENGTH_SHORT).show();
        });
    }
}

