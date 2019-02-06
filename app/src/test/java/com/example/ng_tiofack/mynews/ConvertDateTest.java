package com.example.ng_tiofack.mynews;

import com.example.ng_tiofack.mynews.utils.Utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ConvertDateTest {

    @Test
    public void convert_date ()
    {
        String date = "2018-10-30T11:30:50-04:00";
        Assert.assertEquals("30/10/2018", Utils.getConvertDate(date));

        date = "2018-10-30";
        Assert.assertEquals("30/10/2018", Utils.getConvertDate(date));
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}

