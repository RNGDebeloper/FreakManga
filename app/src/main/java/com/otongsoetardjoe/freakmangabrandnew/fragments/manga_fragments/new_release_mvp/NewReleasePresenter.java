package com.otongsoetardjoe.freakmangabrandnew.fragments.manga_fragments.new_release_mvp;

import android.util.Log;

import com.otongsoetardjoe.freakmangabrandnew.networks.CloudFlare;
import com.otongsoetardjoe.freakmangabrandnew.models.HenModel;
import com.otongsoetardjoe.freakmangabrandnew.activities.search_or_read_mvp.ReadHenInterface;
import com.otongsoetardjoe.freakmangabrandnew.networks.JsoupConfig;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewReleasePresenter {
    private NewReleaseInterface newReleaseInterface;
    private ReadHenInterface readHenInterface;

    public NewReleasePresenter(NewReleaseInterface newReleaseInterface) {
        this.newReleaseInterface = newReleaseInterface;
    }

    public NewReleasePresenter(ReadHenInterface readHenInterface) {
        this.readHenInterface = readHenInterface;
    }

    public void getHenContent(String henUrl, String hitStatus) {
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
                newReleaseInterface.onGetDataFailed();
            }
        });
    }

    public void getHenImage(String henUrl, String hitStatus) {
        Log.e("URL", henUrl);
        CloudFlare cloudflare = new CloudFlare(henUrl);
        cloudflare.setUser_agent("Mozilla/5.0");
        cloudflare.getCookies(new CloudFlare.cfCallback() {
            @Override
            public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {
                Map<String, String> cookies = CloudFlare.List2Map(cookieList);
                if (hasNewUrl) {
                    passToJsoupImage(newUrl, cookies, hitStatus);
                } else {
                    passToJsoupImage(henUrl, cookies, hitStatus);
                }
            }

            @Override
            public void onFail(String message) {
                readHenInterface.onGetDataFailed();
            }
        });
    }

    public void getHenCafeImage(String henUrl, String hitStatus) {
        Log.e("URL", henUrl);
        CloudFlare cloudflare = new CloudFlare(henUrl);
        cloudflare.setUser_agent("Mozilla/5.0");
        cloudflare.getCookies(new CloudFlare.cfCallback() {
            @Override
            public void onSuccess(List<HttpCookie> cookieList, boolean hasNewUrl, String newUrl) {
                Map<String, String> cookies = CloudFlare.List2Map(cookieList);
                if (hasNewUrl) {
                    passToJsoupImage(newUrl, cookies, hitStatus);
                } else {
                    passToJsoupImage(henUrl, cookies, hitStatus);
                }
            }

            @Override
            public void onFail(String message) {
                readHenInterface.onGetDataFailed();
            }
        });
    }

    private void passToJsoupImage(String henUrl, Map<String, String> cookies, String hitStatus) {
        Document document = JsoupConfig.setInitJsoup(henUrl, cookies);
        if (document != null) {
            Elements elements = document.getElementsByClass("gallerythumb");
            List<String> henModelList = new ArrayList<>();
            String test = "";
            String tests = "";
            for (Element el : elements) {
                String images = el.getElementsByTag("img").attr("data-src");
                if (images.contains("t.nhentai.net")) {
                    test = images.replace("t.nhentai.net", "i.nhentai.net");
                    if (images.contains("t.png")) {
                        tests = test.replace("t.png", ".png");
                    }
                    if (images.contains("t.jpg")) {
                        tests = test.replace("t.jpg", ".jpg");
                    }
                }
                henModelList.add(tests);
            }
            readHenInterface.onGetImageSuccess(henModelList);
        } else {
            readHenInterface.onGetDataFailed();
        }
    }

    private void passToJsoupHenCafe(String henUrl, Map<String, String> cookies, String hitStatus) {
        Document document = JsoupConfig.setInitJsoup(henUrl, cookies);
        if (document != null) {
            Elements elements = document.getElementsByClass("gallerythumb");
            List<String> henModelList = new ArrayList<>();
            String test = "";
            String tests = "";
            for (Element el : elements) {
                String images = el.getElementsByTag("img").attr("data-src");
                if (images.contains("t.nhentai.net")) {
                    test = images.replace("t.nhentai.net", "i.nhentai.net");
                    if (images.contains("t.png")) {
                        tests = test.replace("t.png", ".png");
                    }
                    if (images.contains("t.jpg")) {
                        tests = test.replace("t.jpg", ".jpg");
                    }
                }
                henModelList.add(tests);
            }
            readHenInterface.onGetImageSuccess(henModelList);
        } else {
            readHenInterface.onGetDataFailed();
        }
    }

    private void passToJsoup(String newUrl, Map<String, String> cookies, String hitStatus) {
        Document document = JsoupConfig.setInitJsoup(newUrl, cookies);
        if (document != null) {
            Elements elements = document.getElementsByClass("gallery");
            List<HenModel> henModelList = new ArrayList<>();
            for (Element element : elements) {
                String henUrl = element.getElementsByTag("a").attr("href");
                String henThumbURL = element.getElementsByTag("img").attr("data-src");
                String henTitle = element.getElementsByClass("caption").text();
                HenModel henModel = new HenModel();
                henModel.setMangaTitle(henTitle);
                henModel.setMangaURL(henUrl);
                henModel.setMangaThumbURL(henThumbURL);
                henModelList.add(henModel);
            }
            newReleaseInterface.onGetDataSuccess(henModelList, hitStatus);
        } else {
            newReleaseInterface.onGetDataFailed();
        }
    }
}
