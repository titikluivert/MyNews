package com.example.ng_tiofack.mynews.controler.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.controler.activities.WebViewActivity;
import com.example.ng_tiofack.mynews.utils.ItemClickSupport;
import com.example.ng_tiofack.mynews.utils.TopStoriesStreams;
import com.example.ng_tiofack.mynews.model.TopStories;
import com.example.ng_tiofack.mynews.view.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoriesFragment extends Fragment {


    // FOR DESIGN
    @BindView(R.id.fragment_top_story_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    // 1 - Declare the SwipeRefreshLayout
    @BindView(R.id.fragment_top_story_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    // 2 - Declare list of results (TopStories) & Adapter

    //private TopStories topStories;
    private TopStoriesAdapter adapter;

    private List<TopStories.Result> mtopStoriesResultsList;

    private TopStories.Result topstoriesRersponse;

    public TopStoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); // - 3 Call during UI creation
        this.configureSwipeRefreshLayout();  // 4 - Configure the SwipeRefreshLayout
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit(); // 5 - Execute stream after UI creation
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {

        this.mtopStoriesResultsList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new TopStoriesAdapter(this.mtopStoriesResultsList, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        topstoriesRersponse = adapter.getTopStroiesResults(position);
                        Intent myIntent = new Intent(getActivity(), WebViewActivity.class);
                        myIntent.putExtra(getString(R.string.articleUrl), topstoriesRersponse.getUrl());
                        startActivity(myIntent);
                    }
                });
    }
    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        this.disposable = TopStoriesStreams.streamFetchTopStories("a327efabb73048adbaf8ccb2605f8d1b").subscribeWith(new DisposableObserver<TopStories>() {
            @Override
            public void onNext(TopStories results) {
                // 6 - Update RecyclerView after getting results from Top Stories API
                Log.e("","ready to update");
                updateUI(results.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("","Error "+e);
            }

            @Override
            public void onComplete() {
                Log.e("","Termine avec ou sans succes");
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<TopStories.Result> results) {
        // 3 - Stop refreshing and clear actual list of results
        swipeRefreshLayout.setRefreshing(false);
        mtopStoriesResultsList.clear();
        mtopStoriesResultsList.addAll(results);
        adapter.notifyDataSetChanged();
    }

}
