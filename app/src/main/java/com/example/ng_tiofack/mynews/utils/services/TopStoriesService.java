package com.example.ng_tiofack.mynews.utils.services;

import com.example.ng_tiofack.mynews.model.TopStories;

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

public interface TopStoriesService {


    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC));
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build();

    @GET("home.json")
    Observable<TopStories> getApiKey(@Query("api-key") String api_key);
}
