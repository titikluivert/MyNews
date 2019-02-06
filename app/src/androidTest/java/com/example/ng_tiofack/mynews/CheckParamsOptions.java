package com.example.ng_tiofack.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by NG-TIOFACK on 1/5/2019.
 */
@RunWith(AndroidJUnit4.class)
public class CheckParamsOptions {


    private String[] arrayTopic = {"arts", "business", "entrepreneurs", "politics", "sports", "travel"};
    private ParamsOptions mParamsOptions = new ParamsOptions(arrayTopic);


    @Test
    public void configParamsTest() {

        SavedValuesParams mSavedValuesParams;
        //26/12/2018

        boolean[] categories = {false, false, false, true, false, false};
        String search_query_item = "macron";
        String dateBegin = "1/2/2018";
        String dateEnd = "26/4/2018";

        mSavedValuesParams = mParamsOptions.checkParamsOptions(search_query_item, dateBegin, dateEnd, categories);
        Assert.assertEquals(mSavedValuesParams.getQueryItem(), "macron");
        Assert.assertEquals(mSavedValuesParams.getDatebegin(), "20180201");
        Assert.assertEquals(mSavedValuesParams.getDateend(), "20180426");
        String newsDeskExpected = "news_desk:(\"" + arrayTopic[3] + "\")";
        Assert.assertEquals(mSavedValuesParams.getArticleschecked(), newsDeskExpected);

    }

}
