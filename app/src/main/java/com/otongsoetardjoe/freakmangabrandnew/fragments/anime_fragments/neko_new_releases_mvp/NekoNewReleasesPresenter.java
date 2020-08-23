package com.otongsoetardjoe.freakmangabrandnew.fragments.anime_fragments.neko_new_releases_mvp;

import android.util.Log;

import com.otongsoetardjoe.freakmangabrandnew.models.HenModel;
import com.otongsoetardjoe.freakmangabrandnew.models.NekoModel;
import com.otongsoetardjoe.freakmangabrandnew.networks.CloudFlare;
import com.otongsoetardjoe.freakmangabrandnew.networks.JsoupConfig;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NekoNewReleasesPresenter {
    private NekoNewReleasesInterface nekoNewReleasesInterface;
    private NekoWatchInterface watchInterface;

    public NekoNewReleasesPresenter(NekoWatchInterface watchInterface) {
        this.watchInterface = watchInterface;
    }

    public NekoNewReleasesPresenter(NekoNewReleasesInterface nekoNewReleasesInterface) {
        this.nekoNewReleasesInterface = nekoNewReleasesInterface;
    }

    public void getNekoContent(String henUrl, String hitStatus) {
        Log.e("nekoURL", henUrl);
        CloudFlare cloudflare = new CloudFlare(henUrl);
        cloudflare.setUser_agent("Mozilla/5.0");
        cloudflare.getCookies(new CloudFlare.cfCallback() {
            @Override
            public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {
                Map<String, String> cookies = CloudFlare.List2Map(cookieList);
                if (hasNewUrl) {
                    passToJsoup(newUrl, cookies, hitStatus);
                } else {
                    passToJsoup(henUrl, cookies, hitStatus);
                }
            }

            @Override
            public void onFail(String message) {
                nekoNewReleasesInterface.onGetDataFailed();
            }
        });
    }

    public void getNekoWatch(String henUrl) {
        Log.e("nekoURL", henUrl);
        CloudFlare cloudflare = new CloudFlare(henUrl);
        cloudflare.setUser_agent("Mozilla/5.0");
        cloudflare.getCookies(new CloudFlare.cfCallback() {
            @Override
            public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {
                Map<String, String> cookies = CloudFlare.List2Map(cookieList);
                if (hasNewUrl) {
                    getVideoURL(newUrl, cookies);
                } else {
                    getVideoURL(henUrl, cookies);
                }
            }

            @Override
            public void onFail(String message) {
                nekoNewReleasesInterface.onGetDataFailed();
            }
        });
    }

    private void passToJsoup(String newUrl, Map<String, String> cookies, String hitStatus) {
        Document document = JsoupConfig.setInitJsoup(newUrl, cookies);
        if (document != null) {
            Elements elements = document.getElementsByClass("top");
            List<NekoModel> henModelList = new ArrayList<>();
            for (Element element : elements) {
                String henUrl = "https://nekopoi.care" + element.getElementsByTag("a").attr("href");
                String henThumbURL = "https://nekopoi.care" + element.getElementsByTag("img").attr("src");
                String henTitle = element.getElementsByTag("a").text();
                NekoModel henModel = new NekoModel();
                henModel.setNekoTitle(henTitle);
                henModel.setNekoURL(henUrl);
                henModel.setNekoThumbURL(henThumbURL);
                henModelList.add(henModel);
            }
            nekoNewReleasesInterface.onGetDataSuccess(henModelList, hitStatus);
        } else {
            nekoNewReleasesInterface.onGetDataFailed();
        }
    }

    private void getVideoURL(String newUrl, Map<String, String> cookies) {
        Document document = JsoupConfig.setInitJsoup(newUrl, cookies);
        if (document != null) {
            String videoURL = document.getElementById("stream2").getElementsByTag("iframe").attr("src");
            Log.e("ambilVideURL?", videoURL);
            watchInterface.onGetDataSuccess(videoURL);
        } else {
            Log.e("ambilVideURL?", "Gagal bro");
            watchInterface.onGetDataFailed();
        }
    }
}
