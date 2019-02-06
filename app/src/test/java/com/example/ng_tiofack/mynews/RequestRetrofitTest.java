package com.example.ng_tiofack.mynews;


import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.services.TopStoriesService;
import com.example.ng_tiofack.mynews.utils.streams.TopStoriesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NG-TIOFACK on 1/5/2019.
 */
public class RequestRetrofitTest {


    @Test
    public void getTopStories() throws IOException {

       // Observable<TopStories> topRatedList = TopStoriesStreams.streamFetchTopStories(Utils.apiKeyNYT);
        //Observable<TopStories> movieResponse = topRatedList.doOnNext()

        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);
        Observable<TopStories> topRatedList = topStoriesService.getApiKey(Utils.apiKeyNYT).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).timeout(10, TimeUnit.SECONDS);
        //assertEquals(movieResponse.code(), 200);
        // assertEquals(true, movieResponse.isSuccessful());*/
        int x = 2;
    }

    @Test
    public void getPopularMovies() throws IOException {

          /*  Call<Movie> popularMovies = getTmdbApiClient().movieInterface().getPopularMovies();
            Response<Movie> movieResponse = popularMovies.execute();

            assertEquals(movieResponse.code(), 200);
            assertEquals(true, movieResponse.isSuccessful());*/

    }
}

