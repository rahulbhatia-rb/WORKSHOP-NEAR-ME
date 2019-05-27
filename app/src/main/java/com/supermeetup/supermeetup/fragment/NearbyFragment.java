package com.supermeetup.supermeetup.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.HomeActivity;
import com.supermeetup.supermeetup.adapter.CategoryAndEventAdapter;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.FragmentNearbyBinding;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.network.MeetupClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/17/17.
 */

public class NearbyFragment extends Fragment implements BaseModelListFragment.DataLoadListener<Event> {

    private static NearbyFragment mFragment;
    private Location mLocation;
    private String mQuery = "";
    private BaseModelListFragment mBaseModelListFragment;
    private CategoryAndEventAdapter categoryAndEventAdapter;

    public NearbyFragment() {
    }

    public static NearbyFragment getInstance(Location location){
        if(mFragment == null){
            mFragment = new NearbyFragment();
        }
        mFragment.setLocation(location);
        return mFragment;
    }

    private FragmentNearbyBinding mNearbyBinding;
    private LoadingDialog mLoadingDialog;

    private MeetupClient meetupClient;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mNearbyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nearby, container, false);
        View view = mNearbyBinding.getRoot();

        mBaseModelListFragment = BaseModelListFragment.getInstance();
        mBaseModelListFragment.placeModelListFragment(getFragmentManager(), R.id.nearby_listview);
        mBaseModelListFragment.setDataListener(this);

        mLoadingDialog = new LoadingDialog(getActivity());
        mNearbyBinding.nearbySearchlayout.searchview.post(new Runnable() {
            @Override
            public void run() {
                mNearbyBinding.nearbySearchlayout.searchview.setQuery(mQuery, false);
            }
        });
        mNearbyBinding.nearbySearchlayout.searchview.clearFocus();
        mNearbyBinding.nearbySearchlayout.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String sQuery = mNearbyBinding.nearbySearchlayout.searchview.getQuery().toString();
                Intent i = new Intent(getActivity(), HomeActivity.class);
                i.putExtra(Util.EXTRA_QUERY, sQuery);
                getActivity().startActivity(i);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        meetupClient = MeetupApp.getRestClient(getActivity());
        loadCategories();
        return view;
    }

    public void setLocation(Location location){
        if(location != null) {
            mLocation = location;
        }
    }

    public void setQuery(String query){
        mQuery = query;
    }

    private void loadCategories(){
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_category));
        mLoadingDialog.show();

        meetupClient.findTopicCategories(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Category> categories = response.body();
                    if(categories != null){
                        NearbyFragment.this.setCategoryList(categories);
                    }
                    loadRecommendEvents(false);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                // Log error here since request failed
                Log.e("nearbyerror", "Find topic categories request error: " + t.toString());
            }
        }, null, null, null, null);

    }

    private void loadRecommendEvents(final boolean isRefresh){
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_event));
        if (isRefresh) {
            mLoadingDialog.show();
        }
        meetupClient.recommendedEvents(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    meetupClient.saveNextUrlForRecommendedEvents(response);
                    ArrayList<Event> events = response.body();
                    if(events != null){
                        setEventList(events);
                    }
                }
                mLoadingDialog.dismiss();
                if (isRefresh) {
                    mBaseModelListFragment.onRefreshingComplete();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                if (isRefresh) {
                    mBaseModelListFragment.onRefreshingComplete();
                }
                Log.e("nearbyerror", "Recommended event request error: " + t.toString());
            }
        }, Util.DEFAULT_FIELDS, mLocation.getLatitude(), mLocation.getLongitude(), null, null, null);
    }


    private void setCategoryList(ArrayList<Category> categories){
        if (categoryAndEventAdapter == null) {
            categoryAndEventAdapter = new CategoryAndEventAdapter(getActivity());
        }
        mBaseModelListFragment.setAdapter(categoryAndEventAdapter);
        ((CategoryAndEventAdapter) mBaseModelListFragment.getAdapter()).setCategories(categories);
    }

    private void setEventList(ArrayList<Event> events){
        if (categoryAndEventAdapter == null) {
            categoryAndEventAdapter = new CategoryAndEventAdapter(getActivity());

        }
        mBaseModelListFragment.setAdapter(categoryAndEventAdapter);
        ((CategoryAndEventAdapter) mBaseModelListFragment.getAdapter()).setEvents(events, true);
    }

    @Override
    public void getNewData() {
        mBaseModelListFragment.reset();
        loadRecommendEvents(true);
    }

    @Override
    public void getMoreData(int offset) {
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_data));
        mLoadingDialog.show();
        meetupClient.getNextUrlForListEvents(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Event> events = response.body();
                    if(events != null){
                        mBaseModelListFragment.addModels(events);
                    }
                    meetupClient.saveNextUrlForRecommendedEvents(response);
                }
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("nearerror", "Recommended event request error: " + t.toString());
            }
        } );
    }
}
