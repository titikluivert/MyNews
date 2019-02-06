package com.example.ng_tiofack.mynews;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.streams.TopStoriesStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static org.junit.Assert.assertEquals;

/**
 * Created by NG-TIOFACK on 1/5/2019.
 */
@RunWith(AndroidJUnit4.class)
public class Test2 {

    //FOR DATA
    private Disposable disposable;

    @Test
    public void getTopStories() throws IOException {

        TopStories retval = this.testRequest();
        assertEquals(retval.getResults(), 200);


    }

    public TopStories testRequest() {
        final TopStories[] retVal = new TopStories[1];

        this.disposable = TopStoriesStreams.streamFetchTopStories(Utils.apiKeyNYT).subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories results) {
                // 6 - Update RecyclerView after getting results from Top Stories API
                //updateUI(results.getResults());
                retVal[0] = results;
            }

            @Override
            public void onError(Throwable e) {
                Log.e("", "Error " + e);
            }

            @Override
            public void onComplete() {
                Log.e("", "ends with or without success");
            }
        });

        return retVal[0];
    }
}
