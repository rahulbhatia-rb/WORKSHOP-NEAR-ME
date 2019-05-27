package com.supermeetup.supermeetup.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.BaseAdapter;
import com.supermeetup.supermeetup.databinding.FragmentEventListBinding;
import com.supermeetup.supermeetup.listeners.EndlessRecyclerViewScrollListener;
import com.supermeetup.supermeetup.model.Event;

import java.util.ArrayList;

public class BaseModelListFragment<T> extends Fragment {

    public static final String TAG = "BaseModelListFragment";
    protected FragmentEventListBinding mEventListBinding;
    protected BaseAdapter<T> mAdapter;
    protected EndlessRecyclerViewScrollListener mScrollListener;
    protected DataLoadListener mDataLoadListener;

    public BaseModelListFragment() {
        // Required empty public constructor
    }

    public static BaseModelListFragment getInstance(){
        return new BaseModelListFragment();
    }

    public interface EventSelectedListener {
        // handle event selection
        void onEventSelected(@NonNull Event event);
    }

    public interface DataLoadListener<T> {
        // refresh
        void getNewData();
        void getMoreData(int offset);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mEventListBinding != null) return mEventListBinding.getRoot();
        // Inflate the layout for this fragment
        mEventListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false);

        // Setup refresh listener which triggers new data loading
        mEventListBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                mDataLoadListener.getNewData();
            }
        });
        // Configure the refreshing colors
        mEventListBinding.swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.scrollToPosition(0);
        mEventListBinding.rvEvents.setLayoutManager(layoutManager);
        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(mAdapter.getItemCount());
            }
        };

        // Adds the scroll listener to RecyclerView
        mEventListBinding.rvEvents.addOnScrollListener(mScrollListener);

        return mEventListBinding.getRoot();
    }

    // Required to be implemented by derived class
    protected RecyclerView.Adapter createAdapter() {
        throw new UnsupportedOperationException();
    };

    public void setAdapter(@Nullable BaseAdapter adapter) {
        mAdapter = adapter;
        mEventListBinding.rvEvents.setAdapter(adapter);
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    public void setDataListener(@NonNull DataLoadListener dataLoadListener) {
        this.mDataLoadListener = dataLoadListener;
    }

    /**
     * Put this fragment into an fragment slot in derived fragment
     * @param fragmentId
     */
    public void placeModelListFragment(@NonNull FragmentManager fragmentManager, @IdRes int fragmentId) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(fragmentId, this, TAG);
        ft.commit();
    }

    // Reset all views and clear items
    public void reset() {
        mScrollListener.resetState();
        //mAdapter.clear();
        mAdapter.notifyDataSetChanged();
    }

    // Append the next page of data into the adapter
    public void loadNextDataFromApi(int offset) {
        mDataLoadListener.getMoreData(offset);
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }

    public void addModels(@Nullable ArrayList<T> models) {
        mAdapter.addModels(models);
    }

    public void onRefreshingComplete() {
        mEventListBinding.swipeContainer.setRefreshing(false);
    }

    public void disableRefresh() {
        mEventListBinding.swipeContainer.setEnabled(false);
    }
}
