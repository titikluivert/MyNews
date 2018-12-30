package com.example.ng_tiofack.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.example.ng_tiofack.mynews.controler.activities.SearchActivity;
import com.example.ng_tiofack.mynews.model.ParamsOptions;
import com.example.ng_tiofack.mynews.model.SavedValuesParams;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class CheckIfConfigOptionWorksCorrectly {

    private static final String TEST_STRING = "Enable notifications (once per day)";
    private ParamsOptions mParamsOptions = new ParamsOptions();
    private Context appContext = InstrumentationRegistry.getTargetContext();
    private SearchActivity mSearchActivity = new SearchActivity();

    //
    @Test
    public void CheckNotificationConfigParamsTEST() {

        SavedValuesParams mSavedValuesParams;
        //26/12/2018
        EditText dateView = mSearchActivity.findViewById(R.id.bigindateText);
        EditText dateView1 = mSearchActivity.findViewById(R.id.enddateText);
        EditText search_query_item = mSearchActivity.findViewById(R.id.searchqueryitem);

        boolean[] categories = {false, false, false, true, false, false};
        List<String> articles = new ArrayList<>();
        search_query_item.setText("macron");
        dateView.setText("1/2/2018");
        dateView1.setText("26/4/2018");

        mSavedValuesParams = mParamsOptions.checkParamsOptions(appContext, search_query_item.getText().toString(), dateView.getText().toString(), dateView1.getText().toString(), categories, articles);
        Assert.assertEquals(mSavedValuesParams.getQueryItem(), "macron");
        Assert.assertEquals(mSavedValuesParams.getDatebegin(), "20180201");
        Assert.assertEquals(mSavedValuesParams.getDateend(), "20180426");
        Assert.assertEquals(mSavedValuesParams.getArticleschecked(), "(news_desk:" + R.string.arts + ")");

    }

}


