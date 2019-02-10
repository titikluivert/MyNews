package com.example.ng_tiofack.mynews.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NG-TIOFACK on 11/13/2018.
 */
public class ParamsOptions {

    private String[] topicsArray;

    public ParamsOptions() {
    }


    public ParamsOptions(String[] topicArray) {
        this.topicsArray = topicArray;
    }

    public SavedValuesParams checkParamsOptions(String search_query_item, String dateBegin, String dateEnd, boolean[] categories) {

        List<String> articles = new ArrayList<>();
        String dateCombinedBegin = null;
        String dateCombinedEnd = null;
        StringBuilder articleChecked = null;

        if (!search_query_item.isEmpty()) {

            boolean b_article = true;

            for (int i = 0; i < categories.length; i++) {
                if (categories[i]) {

                    if (b_article) {
                        articleChecked = new StringBuilder("(");
                        b_article = false;
                    }
                    articles.add(topicsArray[i]);
                    articleChecked.append("\"").append(topicsArray[i]).append("\"").append(" ");
                }
            }
            if (!b_article)
                articleChecked = new StringBuilder("news_desk:" + articleChecked.toString().trim() + ")");

            if ((!(dateBegin == null) && !(dateEnd == null)) && (!dateBegin.isEmpty() && !dateEnd.isEmpty())) {

                String[] values = splitDateAndTurnToTheCorrectFormat(search_query_item, dateBegin, dateEnd, articles);
                dateCombinedBegin = values[0];
                dateCombinedEnd = values[1];
                search_query_item = values[2];

            } else {

                dateCombinedBegin = changeDateToCorrectFormat(dateBegin);
                dateCombinedEnd = changeDateToCorrectFormat(dateEnd);
            }

        } else {
            search_query_item = "SYMPTHOME_I";
        }

        return new SavedValuesParams(
                search_query_item,
                articleChecked != null ? articleChecked.toString() : null,
                dateCombinedBegin,
                dateCombinedEnd);
    }

    public String changeDateToCorrectFormat(String dateView1) {

        String retValue = null;
        if (dateView1 != null && !dateView1.isEmpty()) {

            String[] dateFormat = dateView1.split("/");

            if (dateFormat[0].length() < 2) {
                dateFormat[0] = "0" + dateFormat[0];
            }
            if (dateFormat[1].length() < 2) {
                dateFormat[1] = "0" + dateFormat[1];
            }
            retValue = dateFormat[2] + dateFormat[1] + dateFormat[0];
        }

        return retValue;
    }

    public String[] splitDateAndTurnToTheCorrectFormat(String s0, String s, String s1, List<String> articles) {

        String[] retValue = new String[3];

        String[] dateBegin = s.split("/");
        String[] dateEnd = s1.split("/");

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

        retValue[0] = dateBegin[2] + dateBegin[1] + dateBegin[0];
        retValue[1] = dateEnd[2] + dateEnd[1] + dateEnd[0];

        if (Double.parseDouble(retValue[1]) > Double.parseDouble(retValue[0])) {

            if (articles.isEmpty()) {
                retValue[2] = "SYMPTHOME_III";
            } else {
                retValue[2] = s0;
            }

        } else {
            retValue[2] = "SYMPTHOME_II";
        }

        return retValue;
    }
}