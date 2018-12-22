package com.example.ng_tiofack.mynews.model;

import android.content.Context;
import android.widget.Toast;

import com.example.ng_tiofack.mynews.R;

import java.util.List;

/**
 * Created by NG-TIOFACK on 11/13/2018.
 */
public class ParamsOptions {


    public ParamsOptions() {

    }

    public SavedValuesParams checkParamsOptions(Context context, String search_query_item, String dateView, String dateView1, boolean[] categories, List<String> articles) {

        String dateCombinedBegin = null;
        String dateCombinedEnd = null;
        String[] dateBegin;
        String[] dateEnd;
        String articleChecked = "(";

        String[] topicsArray = context.getResources().getStringArray(R.array.topicArray);
        if (!search_query_item.isEmpty()) {

            for (int i = 0; i < categories.length; i++) {
                if (categories[i]) {
                    articles.add(topicsArray[i]);
                    articleChecked += "\"" + topicsArray[i] + "\"" + " ";
                }
            }
            articleChecked = "news_desk:" + articleChecked.trim() + ")";


            if ((!(dateView == null) && !(dateView1 == null)) && (!dateView.isEmpty() && !dateView1.isEmpty())) {

                dateBegin = dateView.split("/");
                dateEnd = dateView1.split("/");

                if (dateBegin[0].length() < 2) {
                    dateBegin[0] = "0" + dateBegin[0];
                }

                if (dateBegin[1].length() < 2) {
                    dateBegin[1] = "0" + dateBegin[1];
                }

                if (dateEnd[0].length() < 2) {
                    dateEnd[0] = "0" + dateEnd[0];
                }

                if (dateEnd[1].length() < 2) {
                    dateEnd[1] = "0" + dateEnd[1];
                }

                dateCombinedBegin = dateBegin[2] + dateBegin[1] + dateBegin[0];
                dateCombinedEnd = dateEnd[2] + dateEnd[1] + dateEnd[0];

                Double dateCombinedBeginD = Double.parseDouble(dateCombinedBegin);
                Double dateCombinedEndD = Double.parseDouble(dateCombinedEnd);

                if (dateCombinedEndD > dateCombinedBeginD) {

                    if (articles.isEmpty()) {
                        search_query_item = "SYMPTHOME_III";
                        Toast.makeText(context, "please you must check a least one box", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    search_query_item = "SYMPTHOME_II";
                    Toast.makeText(context, "the bigin date shall not be greater than the end date", Toast.LENGTH_SHORT).show();
                }

            } else {




                if ((dateView != null) || (dateView1 != null)) {
                    dateCombinedBegin = dateView;
                    dateCombinedEnd = dateView1;

                    if (!dateCombinedBegin.isEmpty()) {
                        dateBegin = dateView.split("/");

                        if (dateBegin[0].length() < 2) {
                            dateBegin[0] = "0" + dateBegin[0];
                        }
                        if (dateBegin[1].length() < 2) {
                            dateBegin[1] = "0" + dateBegin[1];
                        }
                        dateCombinedBegin = dateBegin[2] + dateBegin[1] + dateBegin[0];
                    }
                    else{
                        dateCombinedBegin = null;

                    }
                    if (!dateCombinedEnd.isEmpty()) {
                        dateEnd = dateView1.split("/");

                        if (dateEnd[0].length() < 2) {
                            dateEnd[0] = "0" + dateEnd[0];
                        }
                        if (dateEnd[1].length() < 2) {
                            dateEnd[1] = "0" + dateEnd[1];
                        }
                        dateCombinedEnd = dateEnd[2] + dateEnd[1] + dateEnd[0];
                    }else
                    {
                        dateCombinedEnd = null;
                    }
                }
                if (articles.isEmpty()) {
                    search_query_item = "SYMPTHOME_III";
                    Toast.makeText(context, "please you must check a least one box", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            search_query_item = "SYMPTHOME_I";
            Toast.makeText(context, "you must enter a query item", Toast.LENGTH_SHORT).show();
        }

        return new SavedValuesParams(
                search_query_item,
                articleChecked,
                dateCombinedBegin,
                dateCombinedEnd);
    }

}