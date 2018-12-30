package com.example.ng_tiofack.mynews;

import com.example.ng_tiofack.mynews.model.ParamsOptions;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NG-TIOFACK on 12/28/2018.
 */
public class TestDateFormat {

    private ParamsOptions mParamsOptions = new ParamsOptions();

    @Test
    public void changeDateToCorrectFormatTEST() {

        String date;
        date = "26/2/2018";
        Assert.assertEquals("20180226", mParamsOptions.changeDateToCorrectFormat(date));

        date = "6/12/2018";
        Assert.assertEquals("20181206", mParamsOptions.changeDateToCorrectFormat(date));

        date = "16/11/2018";
        Assert.assertEquals("20181116", mParamsOptions.changeDateToCorrectFormat(date));

    }

    @Test
    public void splitDateAndTurnToTheCorrectFormatTEST() {

        String queryItem, date, date1;
        List<String> articles = new ArrayList<>();

        date = "26/1/2018";
        date1 = "28/12/2018";
        queryItem = "macron";
        String[] retValue = mParamsOptions.splitDateAndTurnToTheCorrectFormat(queryItem,date, date1, articles);
        Assert.assertEquals("20180126", retValue[0]);
        Assert.assertEquals("20181228", retValue[1]);
        Assert.assertEquals("SYMPTHOME_III", retValue[2]);


        date = "28/2/2018";
        date1 = "26/1/2018";
        queryItem = "trump";
        retValue = mParamsOptions.splitDateAndTurnToTheCorrectFormat(queryItem, date, date1, articles);
        Assert.assertEquals("20180228", retValue[0]);
        Assert.assertEquals("20180126", retValue[1]);
        Assert.assertEquals("SYMPTHOME_II", retValue[2]);


        articles.add("Arts");
        date = "26/1/2018";
        date1 = "28/12/2018";
        queryItem = "maxwell";
        retValue = mParamsOptions.splitDateAndTurnToTheCorrectFormat(queryItem, date, date1, articles);
        Assert.assertEquals("20180126", retValue[0]);
        Assert.assertEquals("20181228", retValue[1]);
        Assert.assertEquals("maxwell",retValue[2]);


    }


}
