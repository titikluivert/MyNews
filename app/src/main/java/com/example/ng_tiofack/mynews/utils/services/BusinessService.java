package com.example.ng_tiofack.mynews.utils.services;

import com.example.ng_tiofack.mynews.model.Business;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NG-TIOFACK on 9/27/2018.
 */
public interface BusinessService {


    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC));

    @GET("articlesearch.json")
    Observable<Business> getapiKeyBusiness(@Query("api-key") String api_key);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/search/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build();
}