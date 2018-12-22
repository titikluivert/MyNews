package com.example.ng_tiofack.mynews.utils.streams;

import com.example.ng_tiofack.mynews.model.MostPopular;
import com.example.ng_tiofack.mynews.utils.services.MostPopularService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NG-TIOFACK on 10/3/2018.
 */
public class MostPopularStreams {

    public static Observable<MostPopular> streamFetchMostPopular(String api_key) {
        MostPopularService mostPopularService = MostPopularService.retrofit.create(MostPopularService.class);
        return mostPopularService.getApiKeyMostPopular(api_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
