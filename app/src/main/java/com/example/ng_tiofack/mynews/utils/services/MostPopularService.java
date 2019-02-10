package com.example.ng_tiofack.mynews.utils.services;

import com.example.ng_tiofack.mynews.model.MostPopular;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by NG-TIOFACK on 10/3/2018.
 */
public interface MostPopularService {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BASIC));


    Gson gson = new GsonBuilder()
            .registerTypeAdapter(MostPopular.Result.class, new MostPopular.Result.OptionsDeserilizer())
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/svc/mostpopular/v2/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build();

    @GET("mostviewed/all-sections/1.json")
    Observable<MostPopular> getApiKeyMostPopular(@Query("api-key") String api_key);
}
