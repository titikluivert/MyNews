package com.example.ng_tiofack.mynews.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.MostPopular;
import com.example.ng_tiofack.mynews.view.holders.MostPopularViewHolder;


import java.util.List;

/**
 * Created by NG-TIOFACK on 10/3/2018.
 */
public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {

    // FOR DATA
    // private TopStories mTopStories;

    private List<MostPopular.Result> mostPopularResultsList;
    // 1 - Declaring a Glide object
    private RequestManager glide;

    // 2 - Updating our constructor adding a Glide Object
    public MostPopularAdapter(List<MostPopular.Result> mostPopularResultsList, RequestManager glide) {
        this.mostPopularResultsList = mostPopularResultsList;
        this.glide = glide;
    }

    @Override
    public MostPopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_most_popular_item, parent, false);

        return new MostPopularViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A TOP STORIES
    @Override
    public void onBindViewHolder(MostPopularViewHolder viewHolder, int position) {
        // - 3 Passing the Glide object to each ViewHolder
        viewHolder.updateWithMostPopular(this.mostPopularResultsList.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mostPopularResultsList.size();
    }

    public MostPopular.Result getMostPopularResults(int position){
        return this.mostPopularResultsList.get(position);
    }
}
