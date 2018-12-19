package com.example.ng_tiofack.mynews.controler.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.ng_tiofack.mynews.R;
import com.example.ng_tiofack.mynews.controler.activities.WebViewActivity;
import com.example.ng_tiofack.mynews.model.Search;
import com.example.ng_tiofack.mynews.utils.ItemClickSupport;
import com.example.ng_tiofack.mynews.utils.Utils;
import com.example.ng_tiofack.mynews.view.NewAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String result;
   @BindView(R.id.fragment_news_recycler_view)
   RecyclerView recyclerView; // 1 - Declare RecyclerView

    // 1 - Declare the SwipeRefreshLayout
    @BindView(R.id.fragment_news_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    // 2 - Declare list of results (MostPopular) & Adapter
    private List<Search.Response.Doc> myResultsList;
    private NewAdapter adapter;
    private Search.Response.Doc response;

    public NewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param result Parameter 1.
     * @return A new instance of fragment NewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFragment newInstance(String result) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            result = getArguments().getString(ARG_PARAM1);
            updateUI(Utils.getResultfromJson(result));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView(); // - 3 Call during UI creation
        this.configureSwipeRefreshLayout();  // 4 - Configure the SwipeRefreshLayout
        this.configureOnClickRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 2 - Configure the SwipeRefreshLayout
    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

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
    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<Search.Response.Doc> results) {
        // 3 - Stop refreshing and clear actual list of results
        swipeRefreshLayout.setRefreshing(false);
        myResultsList.clear();
        myResultsList.addAll(results);
        adapter.notifyDataSetChanged();
    }
}



