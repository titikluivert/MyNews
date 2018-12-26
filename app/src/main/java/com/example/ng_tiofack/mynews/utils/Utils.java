package com.example.ng_tiofack.mynews.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.SavedValues;
import com.example.ng_tiofack.mynews.model.ArticlesNews;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

/**
 * Created by NG-TIOFACK on 11/5/2018.
 */
public class Utils {

    public static final String apiKeyNYT = "a327efabb73048adbaf8ccb2605f8d1b";

    public static void saveNotificationParam(Context context, boolean notifyParam, String queryItem, boolean[] categories) {

        Gson gson = new Gson();
        String jsonCategoryList = gson.toJson(categories);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.save_notifParams__key), notifyParam);
        editor.putString(context.getString(R.string.save_query_item__key), queryItem);
        editor.putString(context.getString(R.string.save_categories__key), jsonCategoryList);
        editor.apply();
    }

    public static SavedValues getNotificationParam(Context context) {

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPreferences.getString(context.getString(R.string.save_categories__key), null);
        Type type = new TypeToken<boolean[]>() {
        }.getType();
        boolean[] categories = gson.fromJson(json, type);

        return new SavedValues(

                sharedPreferences.getBoolean(context.getString(R.string.save_notifParams__key), false),
                sharedPreferences.getString(context.getString(R.string.save_query_item__key), ""),
                categories);

    }

    public static String getConvertDate(String date) {
        String convertDate;
        String[] date_temp_1;
        String[] date_temp_2;

        if (date == null) {
            convertDate = "01/01/1970";
        } else {
            if (date.contains("T")) {
                date_temp_1 = date.split("T");
                date_temp_2 = date_temp_1[0].split("-");
            } else {
                date_temp_2 = date.split("-");
            }
            convertDate = date_temp_2[2] + "/" + date_temp_2[1] + "/" + date_temp_2[0];
        }
        return convertDate;
    }


    public static String setResulttoJson(List<ArticlesNews.Response.Doc> business_response) {

        Gson gson = new Gson();
        return gson.toJson(business_response);
    }

    public static List<ArticlesNews.Response.Doc> getResultfromJson(String myJsonString) {

        Gson gson = new Gson();
        Type type = new TypeToken<List<ArticlesNews.Response.Doc>>() {
        }.getType();
        return gson.fromJson(myJsonString, type);

    }


}
