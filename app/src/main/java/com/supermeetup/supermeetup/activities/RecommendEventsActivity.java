package com.supermeetup.supermeetup.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.EventAdapter;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ActivityRecommendeventsBinding;
import com.supermeetup.supermeetup.dialog.CategoryDialog;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.fragment.BaseModelListFragment;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.network.MeetupClient;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/17/17.
 */

public class RecommendEventsActivity extends AppCompatActivity implements BaseModelListFragment.DataLoadListener<Event> {

    private ActivityRecommendeventsBinding mBinding;
    private Category mCurrentCategory;
    private ArrayList<Category> mCategories;
    private LoadingDialog mLoadingDialog;
    private MeetupClient meetupClient;
    private Location mLocation;
    private BaseModelListFragment mBaseModelListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetupClient = MeetupApp.getRestClient(this);
        mLocation = Util.readLocation(this, Util.KEY_LOCATION);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recommendevents);
        mLoadingDialog = new LoadingDialog(this);
        mBaseModelListFragment = BaseModelListFragment.getInstance();
        mBaseModelListFragment.placeModelListFragment(getSupportFragmentManager(), R.id.recommendevents_list);
        mBaseModelListFragment.setDataListener(this);
        updateUI(getIntent());
        mBinding.recommendeventsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.recommendeventsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog categoryDialog = new CategoryDialog(RecommendEventsActivity.this, mCategories);
                categoryDialog.show();
            }
        });

    }

    private void updateUI(Intent intent){
        mCurrentCategory = Parcels.unwrap(intent.getParcelableExtra(Util.EXTRA_CATEGORY));
        mCategories = Parcels.unwrap(intent.getParcelableExtra(Util.EXTRA_CATEGORYLIST));
        if(mCurrentCategory == null || mCategories == null || mCategories.size() == 0){
            finish();
        }
        mBinding.recommendeventsCategoryIcon.setImageResource(Util.getCategoryIcon(this, mCurrentCategory.getId()));
        mBinding.recommendeventsCategoryName.setText(mCurrentCategory.getName());
        loadRecommendEvents(false);
    }

    private void setEventList(ArrayList<Event> events){
        if (mBaseModelListFragment.getAdapter() == null) {
            mBaseModelListFragment.setAdapter(new EventAdapter(null));
        }

        ((EventAdapter) mBaseModelListFragment.getAdapter()).setEvents(events, false);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        updateUI(intent);
    }

    private void loadRecommendEvents(final boolean isRefresh){
        mLoadingDialog.setMessage(Util.getString(this, R.string.load_event));
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
                    mLoadingDialog.dismiss();
                    if (isRefresh) {
                        mBaseModelListFragment.onRefreshingComplete();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("recommenderror", "Recommended event request error: " + t.toString());
            }
        }, Util.DEFAULT_FIELDS, mLocation.getLatitude(), mLocation.getLongitude(), null, null, (int)mCurrentCategory.getId());
    }

    @Override
    public void getNewData() {
        mBaseModelListFragment.reset();
        loadRecommendEvents(true);
    }

    @Override
    public void getMoreData(int offset) {
        mLoadingDialog.setMessage(Util.getString(this, R.string.load_data));
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
