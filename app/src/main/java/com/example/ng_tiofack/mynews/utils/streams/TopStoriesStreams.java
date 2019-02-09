package com.example.ng_tiofack.mynews.utils.streams;

import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.services.TopStoriesService;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NG-TIOFACK on 9/27/2018.
 */
public class TopStoriesStreams {

    public static Observable<TopStories> streamFetchTopStories(String api_key) {
        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);
        return topStoriesService.getApiKey(api_key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}

