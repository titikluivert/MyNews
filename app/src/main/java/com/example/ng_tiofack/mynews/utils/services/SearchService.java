package com.example.ng_tiofack.mynews.utils.services;

import com.example.ng_tiofack.mynews.model.ArticlesNews;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NG-TIOFACK on 10/31/2018.
 */
public interface SearchService {


    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC));

    @GET("articlesearch.json")
    Observable<ArticlesNews> getSearchItems(

            @Query("q") String query_item,
            @Query(value = "fq", encoded = true) String articleChecked,
            @Query("begin_date") String dateCombinedBegin,
            @Query("end_date") String dateCombinedEnd,
            @Query("api_key") String api_key

    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/search/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build();

}
