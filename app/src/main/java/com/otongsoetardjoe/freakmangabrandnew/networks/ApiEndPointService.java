package com.otongsoetardjoe.freakmangabrandnew.networks;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiEndPointService {
    @GET
    Observable<String> getHTMLPageData(
            @Url String pageURL
    );

}
