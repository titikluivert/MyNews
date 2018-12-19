package com.example.ng_tiofack.mynews.model;

/**
 * Created by NG-TIOFACK on 11/7/2018.
 */
public class SavedValues {


    private String queryItem;
    private boolean switchParams;
    private boolean [] categories;

    public SavedValues(boolean switchParams, String queryItem, boolean[] categories) {
        this.switchParams = switchParams;
        this.queryItem = queryItem;
        this.categories = categories;

    }

    public String getqueryItem() {
        return queryItem;
    }

    public boolean getswitchParams() {
        return switchParams;
    }

    public boolean[] getCategories() {
        return categories;
    }


}