package com.example.ng_tiofack.mynews;

import android.content.Context;
import android.content.res.Resources;


import com.example.ng_tiofack.mynews.controler.activities.SearchActivity;
import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CheckConfigParamsTest {

   // private static final String TEST_STRING = "Enable notifications (once per day)";

    private String[] arrayTopic = {"arts", "business", "entrepreneurs", "politics", "sports", "travel"};


    private ParamsOptions mParamsOptions = new ParamsOptions(arrayTopic);


    @Test
    public void configParamsTest() {

        SavedValuesParams mSavedValuesParams;
        //26/12/2018

        boolean[] categories = {false, false, false, true, false, false};
        String search_query_item = "macron";
        String dateView = "1/2/2018";
        String dateView1 = "26/4/2018";

        //Resources resources = Mockito.mock(Resources.class);

        //Mockito.when(resources.getStringArray(R.array.topicArray)).thenReturn(arrayTopic);
        //Mockito.when(mockContext)
        mSavedValuesParams = mParamsOptions.checkParamsOptions(search_query_item, dateView, dateView1, categories);
        Assert.assertEquals( "macron",mSavedValuesParams.getQueryItem());
        Assert.assertEquals( "20180201", mSavedValuesParams.getDatebegin());
        Assert.assertEquals( "20180426", mSavedValuesParams.getDateend());
        Assert.assertEquals( "news_desk:(\"" + arrayTopic[3] + "\")", mSavedValuesParams.getArticleschecked());

    }

}


