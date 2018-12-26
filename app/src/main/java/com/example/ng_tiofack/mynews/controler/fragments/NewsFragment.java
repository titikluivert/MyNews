package com.example.ng_tiofack.mynews.controler.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
import com.example.ng_tiofack.mynews.model.ArticlesNews;
import com.example.ng_tiofack.mynews.utils.ItemClickSupport;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.utils.streams.SearchServiceStreams;
import com.example.ng_tiofack.mynews.view.adapters.NewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;

public class NewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.fragment_news_recycler_view)
    RecyclerView recyclerView; // 1 - Declare RecyclerView

    // 1 - Declare the SwipeRefreshLayout
    @BindView(R.id.fragment_news_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    // 2 - Declare list of results (MostPopular) & Adapter
    private List<ArticlesNews.Response.Doc> myResultsList;
    private NewAdapter adapter;
    private ArticlesNews.Response.Doc response;


    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param result Parameter 1.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String result) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); // - 3 Call during UI creation
       // 4 - Configure the SwipeRefreshLayout
        this.configureOnClickRecyclerView();
        if (getArguments() != null) {
            String result = getArguments().getString(ARG_PARAM1);
            this.configureSwipeRefreshLayout(result);
            this.executeHttpRequestWithRetrofitNews(result);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout(final String result) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofitNews(result);
            }
        });
    }
    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // 3.1 - Reset list
        this.myResultsList = new ArrayList<>();
        // 3.2 - Create adapter passing the list of users
        this.adapter = new NewAdapter(this.myResultsList, Glide.with(this));
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
                        response = adapter.getBusinessDoc(position);
                        Intent myIntent = new Intent(getActivity(), WebViewActivity.class);
                        myIntent.putExtra(getString(R.string.articleUrl), response.getWebUrl());
                        startActivity(myIntent);
                    }
                });
    }


    private void executeHttpRequestWithRetrofitNews(String articles_checked) {
        DisposableObserver<ArticlesNews> disposable = SearchServiceStreams.streamFetchSearchItems(null, articles_checked, null, null, Utils.apiKeyNYT).subscribeWith(new DisposableObserver<ArticlesNews>() {

            @Override
            public void onNext(ArticlesNews results) {
                if (results.getResponse().getDocs().isEmpty()) {


                } else {
                    updateUI(results.getResponse().getDocs());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("", getString(R.string.error_msg_rxjava) + e);
            }

            @Override
            public void onComplete() {
                Log.e("", "on complete is running");
            }
        });
    }

    private void updateUI(List<ArticlesNews.Response.Doc> results) {
        // 3 - Stop refreshing and clear actual list of results
        swipeRefreshLayout.setRefreshing(false);
        myResultsList.clear();
        myResultsList.addAll(results);
        adapter.setDocList(myResultsList);
    }
}


