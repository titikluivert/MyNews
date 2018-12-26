package com.example.ng_tiofack.mynews;

import android.content.Context;
//import android.support.test.espresso.remote.EspressoRemoteMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ng_tiofack.mynews.controler.activities.MainActivity;
import com.example.ng_tiofack.mynews.controler.fragments.TopStoriesFragment;
import com.example.ng_tiofack.mynews.model.MostPopular;
import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.services.MostPopularService;
import com.example.ng_tiofack.mynews.utils.services.SearchService;
import com.example.ng_tiofack.mynews.utils.services.TopStoriesService;
import com.example.ng_tiofack.mynews.utils.streams.MostPopularStreams;
import com.example.ng_tiofack.mynews.utils.streams.TopStoriesStreams;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by NG-TIOFACK on 12/24/2018.
 */
public class ContentTest {

    private TopStoriesService mTopStoriesService = mock(TopStoriesService.class);

   //
public void fecthSignal() {
    TopStories mTopStories = new TopStories();
    TopStoriesFragment storiesFragment = new TopStoriesFragment();

    when(mTopStoriesService.getApiKey(Utils.apiKeyNYT)).thenReturn(Observable.just(mTopStories));

    storiesFragment.executeHttpRequestWithRetrofitTopStories();

    InOrder inOrder = Mockito.inOrder();
    //inOrder.verify(view, times(1)).onFetchDataSuccess(mTopStories);

}
    /*@Mock
    private TopStoriesService mTopStoriesService;

when(charactersDataSource.getCharacters())
                .thenReturn(Observable.just(charactersResponseModel));
        // Create the service Intent.
        //MenuItem
        private static final String TEST_STRING = "HELLO WORLD!";
        //As we don't have access to Context in our JUnit test classes, we need to mock it
        @Mock
        private
        Context mMockContext;

        SearchService mSearchService = mock(SearchService.class);

        @Test
        public void readStringFromContext() {
            //Returns the TEST_STRING when getString(R.string.hello_world) is called
            when(mMockContext.getString(R.string.text_hello_word)).thenReturn(TEST_STRING);
            //Creates an object of the ClassUnderTest with the mock context
            ClassUnderTest objectUnderTest = new ClassUnderTest(mMockContext);
            //Stores the return value of getHelloWorldString() in result
            String result = objectUnderTest.getHelloWorldString();
            //Asserts that result is the value of TEST_STRING
            assertThat(result, is(TEST_STRING));
        }

    @Test
    public void shouldUpgradeAccountIfPaymentIsSuccessful() throws Exception {

        verify((mSearchService.getSearchItems(null, "news_desk:(\"Arts\")",null,null, Utils.apiKeyNYT)));

*/


    }


