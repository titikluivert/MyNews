package com.example.ng_tiofack.mynews.utils.streams;

import com.example.ng_tiofack.mynews.model.ArticlesNews;
import com.example.ng_tiofack.mynews.utils.services.SearchService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NG-TIOFACK on 10/31/2018.
 */
public class SearchServiceStreams {


    public static Observable<ArticlesNews> streamFetchSearchItems(String query_item, String articlescheked, String begin_date, String end_date, String key) {
        SearchService searchService = SearchService.retrofit.create(SearchService.class);
        return searchService.getSearchItems(query_item, articlescheked, begin_date, end_date, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);


    }
}
