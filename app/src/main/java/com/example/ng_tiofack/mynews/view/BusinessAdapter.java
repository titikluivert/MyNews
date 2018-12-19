package com.example.ng_tiofack.mynews.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.Business;
import com.example.ng_tiofack.mynews.model.GithubUser;

import java.util.List;

/**
 * Created by NG-TIOFACK on 10/5/2018.
 */
public class BusinessAdapter extends RecyclerView.Adapter<BusinessViewHolder> {

// FOR DATA


    private List<Business.Response.Doc> mostBusinessDocsList;
    // 1 - Declaring a Glide object
    private RequestManager glide;

    // 2 - Updating our constructor adding a Glide Object
    public BusinessAdapter (List<Business.Response.Doc> mostBusinessDocsList, RequestManager glide) {
        this.mostBusinessDocsList = mostBusinessDocsList;
        this.glide = glide;
    }

    @Override
    public BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_business_item, parent, false);

        return new BusinessViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A TOP STORIES
    @Override
    public void onBindViewHolder(BusinessViewHolder viewHolder, int position) {
        // - 3 Passing the Glide object to each ViewHolder
        viewHolder.updateWithBusiness(this.mostBusinessDocsList.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {

        return this.mostBusinessDocsList.size();
    }

    public Business.Response.Doc getBusinessDoc(int position){
        return this.mostBusinessDocsList.get(position);
    }
}
