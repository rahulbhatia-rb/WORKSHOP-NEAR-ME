package com.supermeetup.supermeetup.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.CategoryAdapter;
import com.supermeetup.supermeetup.adapter.EventAdapter;
import com.supermeetup.supermeetup.adapter.ShakeAdapter;
import com.supermeetup.supermeetup.adapter.viewholder.ProfileRecommendEventViewHolder;
import com.supermeetup.supermeetup.common.ImageRoundCorners;
import com.supermeetup.supermeetup.common.ShakeListener;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.FragmentShakeBinding;
import com.supermeetup.supermeetup.databinding.ItemProfilerecommendeventBinding;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.Photo;
import com.supermeetup.supermeetup.model.Profile;
import com.supermeetup.supermeetup.model.Topic;
import com.supermeetup.supermeetup.network.MeetupClient;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/20/17.
 */

public class ShakeFragment extends Fragment {
    private static ShakeFragment mFragment;
    private Location mLocation;

    public static ShakeFragment getInstance(Location location){
        if(mFragment == null){
            mFragment = new ShakeFragment();
        }
        mFragment.setLocation(location);
        return mFragment;
    }

    public void setLocation(Location location){
        if(location != null) {
            mLocation = location;
        }
    }

    private FragmentShakeBinding mShakeBinding;
    private ShakeListener mShaker;
    private LoadingDialog mLoadingDialog;
    private MeetupClient meetupClient;
    private Profile mProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mShakeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_shake, container, false);
        View view = mShakeBinding.getRoot();
        mShakeBinding.shakeProfileList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mShaker = new ShakeListener(getActivity());
        mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
            public void onShake()
            {
                if(mProfile != null) {
                    selectAnEvent(mProfile.getTopics());
                }
            }
        });

        mLoadingDialog = new LoadingDialog(getActivity());
        meetupClient = MeetupApp.getRestClient(getActivity());
        loadProfile();
        return view;
    }

    private void loadProfile(){
        mLoadingDialog.show();
        meetupClient.getProfile(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                int statusCode = response.code();
                mProfile = response.body();
                fillProfile();
                //mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("finderror", "Find event request error: " + t.toString());
            }
        }, null, Util.DEFAULT_PROFILE_FIELDS);
    }

    private void fillProfile(){
        if(mProfile == null){
            return;
        }
        Photo photo = mProfile.getPhoto();
        if(photo != null && !TextUtils.isEmpty(photo.getPhotoLink())) {
            final Transformation transformation = new ImageRoundCorners();
            Picasso.with(getActivity()).load(photo.getPhotoLink()).transform(transformation).into(mShakeBinding.shakeProfileImage);
        }

        mShakeBinding.shakeProfileName.setText(mProfile.getName());
        mShakeBinding.shakeProfileLocation.setText(String.format(Util.getString(getActivity(), R.string.location), mProfile.getCity(), mProfile.getLocalizedCountryName()));
        mShakeBinding.shakeProfileBio.setText(mProfile.getBio());

        selectAnEvent(mProfile.getTopics());

    }

    private void selectAnEvent(ArrayList<Topic> topics){
        if(topics == null || topics.size() == 0){
            loadRecommendEvents();
        }else{
            Topic selectedTopic = topics.get(new Random().nextInt(topics.size()));
            loadEvents(selectedTopic.getName());
        }
    }

    private void fillRecommendEvent(Event event){
        mShakeBinding.shakeProfileList.setAdapter(new ShakeAdapter(mProfile, event));
        Vibrator vibe = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
    }

    private void loadRecommendEvents(){
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_event));
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        meetupClient.recommendedEvents(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                ArrayList<Event> events = response.body();
                if(events != null){
                    Event selectedEvent = events.get(new Random().nextInt(events.size()));
                    fillRecommendEvent(selectedEvent);
                }
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("finderror", "Recommended event request error: " + t.toString());
            }
        }, Util.DEFAULT_FIELDS, mLocation.getLatitude(), mLocation.getLongitude(), null, null, null);
    }

    private void loadEvents(String query){
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_event));
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        meetupClient.findEvent(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                int statusCode = response.code();
                ArrayList<Event> events = response.body();
                if(events != null){
                    Event selectedEvent = events.get(new Random().nextInt(events.size()));
                    fillRecommendEvent(selectedEvent);
                }
                mLoadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("finderror", "Find event request error: " + t.toString());
            }
        }, Util.DEFAULT_FIELDS, mLocation.getLatitude(), mLocation.getLongitude(), Util.DEFAULT_RADIUS, query);
    }

    @Override
    public void onResume()
    {
        mShaker.resume();
        super.onResume();
    }
    @Override
    public void onPause()
    {
        mShaker.pause();
        super.onPause();
    }

}
