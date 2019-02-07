package com.example.ng_tiofack.mynews;


import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.services.TopStoriesService;

import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

/**
 * Created by NG-TIOFACK on 1/5/2019.
 */
public class RequestRetrofitTest {

    @Mock
    Observable<TopStories> mockCall;

    @Test
    public void getTopStories() throws IOException {

       final TopStories results = new TopStories();

        //@Mock
        //MovieAPIService mockMovieAPIService;

        //@Mock ResponseBody responseBody;
        //@Mock MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener;

        //private MovieListModelContract movieListModelContract;
        // Observable<TopStories> topRatedList = TopStoriesStreams.streamFetchTopStories(Utils.apiKeyNYT);
        //Observable<TopStories> movieResponse = topRatedList.doOnNext()

        TopStoriesService topStoriesService = TopStoriesService.retrofit.create(TopStoriesService.class);
       // Observable<TopStories> topRatedList = topStoriesService.getApiKey(Utils.apiKeyNYT).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).timeout(10, TimeUnit.SECONDS);
        //assertEquals(movieResponse.code(), 200);
        // assertEquals(true, movieResponse.isSuccessful());*/

        when(topStoriesService.getApiKey(Utils.apiKeyNYT)).thenReturn(Observable.just(results));

      //  movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);
      // verify(mockPopularMoviesResultsListener, never()).onFailure(anyString());
      //  verify(mockPopularMoviesResultsListener, times(1)).onSuccess(results);


    }

    @Test
    public void getPopularMovies() throws IOException {

          /*  Call<Movie> popularMovies = getTmdbApiClient().movieInterface().getPopularMovies();
            Response<Movie> movieResponse = popularMovies.execute();

            assertEquals(movieResponse.code(), 200);
            assertEquals(true, movieResponse.isSuccessful());*/

    }
}

