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
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.streams.MostPopularStreams;
import com.example.ng_tiofack.mynews.model.MostPopular;
import com.example.ng_tiofack.mynews.view.adapters.MostPopularAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class MostPopularFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_most_popular_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    // 1 - Declare the SwipeRefreshLayout
    @BindView(R.id.fragment_most_popular_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    // 2 - Declare list of results (MostPopular) & Adapter
    private List<MostPopular.Result> mostPopularResultsList;
    private MostPopularAdapter adapter;
    MostPopular.Result mostpopularRersponse;

    public MostPopularFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
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
    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.mostPopularResultsList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new MostPopularAdapter(this.mostPopularResultsList, Glide.with(this));
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
                        mostpopularRersponse = adapter.getMostPopularResults(position);
                        Intent myIntent = new Intent(getActivity(), WebViewActivity.class);
                        myIntent.putExtra(getString(R.string.articleUrl), mostpopularRersponse.getUrl());
                        startActivity(myIntent);
                    }
                });
    }
    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit() {
        this.disposable = MostPopularStreams.streamFetchMostPopular(Utils.apiKeyNYT).subscribeWith(new DisposableObserver<MostPopular>() {
            @Override
            public void onNext(MostPopular results) {
                // 6 - Update RecyclerView after getting results from Most Popular API
                updateUI(results.getResults());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("", "une erreur est survenue>" + e);
            }

            @Override
            public void onComplete() {
                Log.e("", "on complete is running");
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<MostPopular.Result> results) {
        // 3 - Stop refreshing and clear actual list of results
        swipeRefreshLayout.setRefreshing(false);
        mostPopularResultsList.clear();
        mostPopularResultsList.addAll(results);
        adapter.notifyDataSetChanged();
    }

}