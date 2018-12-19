package com.example.ng_tiofack.mynews.utils;

import com.example.ng_tiofack.mynews.model.Business;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NG-TIOFACK on 10/3/2018.
 */
public class BusinessStreams {

   public static Observable<Business> streamFetchBusiness(String apikey) {
        BusinessService businessService = BusinessService.retrofit.create(BusinessService.class);
        return businessService.getapiKeyBusiness(apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);


                }
}