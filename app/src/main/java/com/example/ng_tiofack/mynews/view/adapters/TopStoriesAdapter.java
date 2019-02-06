package com.example.ng_tiofack.mynews.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.view.holders.TopStoriesViewHolder;

import java.util.List;

/**
 * Created by NG-TIOFACK on 9/27/2018.
 */
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    // private TopStories mTopStories;

    private List<TopStories.Result> mtopStoriesResultsList;
    // 1 - Declaring a Glide object
    private RequestManager glide;

    // 2 - Updating our constructor adding a Glide Object
    public TopStoriesAdapter(List<TopStories.Result> mtopStoriesResultsList, RequestManager glide) {
        this.mtopStoriesResultsList = mtopStoriesResultsList;
        this.glide = glide;
    }

    @Override
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_top_stories_item, parent, false);

        return new TopStoriesViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A TOP STORIES
    @Override
    public void onBindViewHolder(TopStoriesViewHolder viewHolder, int position) {
        // - 3 Passing the Glide object to each ViewHolder
        viewHolder.updateWithTopStories(this.mtopStoriesResultsList.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mtopStoriesResultsList.size();
    }

    public TopStories.Result getTopStroiesResults(int position) {
        return this.mtopStoriesResultsList.get(position);
    }
}