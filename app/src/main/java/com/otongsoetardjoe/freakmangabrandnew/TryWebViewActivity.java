package com.otongsoetardjoe.freakmangabrandnew;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.otongsoetardjoe.freakmangabrandnew.databinding.ActivityTryWebViewBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class TryWebViewActivity extends AppCompatActivity {
    private ActivityTryWebViewBinding webViewBinding;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewBinding = ActivityTryWebViewBinding.inflate(getLayoutInflater());
        setContentView(webViewBinding.getRoot());
        webViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        webViewBinding.webView.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        webViewBinding.webView.loadUrl("https://komikcast.com/");
        webViewBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
            }

            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }
                }, 11000);
            }

        });
    }

    public void showToast(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TryWebViewActivity.this, message, Toast.LENGTH_SHORT).show();
                if (!message.isEmpty()) {
                    Intent intent = new Intent(TryWebViewActivity.this, SearchNuclearActivity.class);
                    intent.putExtra("vidLink", message);
                    startActivity(intent);
                } else {
//                    webViewBinding.webView.loadUrl("https://animeindo.fun/yesterday-wo-utatte-episode-09-subtitle-indonesia/");
//                    webViewBinding.webView.reload();
                    Toast.makeText(TryWebViewActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class LoadListener {
        public String content = "";

        @JavascriptInterface
        public void processHTML(String html) {
            Toast.makeText(TryWebViewActivity.this, "Berhasil!\n" + html, Toast.LENGTH_LONG).show();
//            Document doc = Jsoup.parse(html);
//            Elements getVideoEmbedURL = doc.getElementsByClass("playeriframe");
//            String getURLFromElementSrc = getVideoEmbedURL.attr("src");
//            Log.e("result", getURLFromElementSrc);
//            nextToAct(getURLFromElementSrc);
        }

        private void nextToAct(String content) {
            showToast(content);
        }
    }
}

