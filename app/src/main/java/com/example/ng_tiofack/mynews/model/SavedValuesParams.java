package com.example.ng_tiofack.mynews.model;

/**
 * Created by NG-TIOFACK on 12/5/2018.
 */
public class SavedValuesParams {


    private String queryItem;
    private String articleschecked;

    public SavedValuesParams() {
    }

    public SavedValuesParams(String queryItem, String articleschecked, String datebegin, String dateend) {
        this.queryItem = queryItem;
        this.articleschecked = articleschecked;
        this.datebegin = datebegin;
        this.dateend = dateend;

    }

    public String getqueryItem() {
        return queryItem;
    }

    public String getQueryItem() {
        return queryItem;
    }

    public void setQueryItem(String queryItem) {
        this.queryItem = queryItem;
    }

    public String getArticleschecked() {
        return articleschecked;
    }

    public void setArticleschecked(String articleschecked) {
        this.articleschecked = articleschecked;
    }

    public String getDatebegin() {
        return datebegin;
    }

    public void setDatebegin(String datebegin) {
        this.datebegin = datebegin;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }

    private String datebegin, dateend;


}
