package com.supermeetup.supermeetup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.adapter.EventAdapter;
import com.supermeetup.supermeetup.common.ImageRoundCorners;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.FragmentNewBinding;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.OpenEvent;
import com.supermeetup.supermeetup.model.Venue;
import com.supermeetup.supermeetup.network.MeetupClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/21/17.
 */

public class NewFragment extends Fragment {

    private static NewFragment mFragment;

    public static NewFragment getInstance(){
        if(mFragment == null){
            mFragment = new NewFragment();
        }

        return mFragment;
    }

    private FragmentNewBinding mNewBinding;
    private MeetupClient meetupClient;
    private GoogleMap mGoogleMap;
    private LoadingDialog mLoadingDialog;
    private LinkedList<OpenEvent> mEvents = new LinkedList<>();
    private Handler mHandler = new Handler();
    private Runnable mLoadData = new Runnable() {
        @Override
        public void run() {
            loadOpenEvent();
            mHandler.postDelayed(this, 1000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNewBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_new, container, false);
        View view = mNewBinding.getRoot();
        meetupClient = MeetupApp.getRestClient(getActivity());
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.setMessage(Util.getString(getActivity(), R.string.load_event));
        mNewBinding.newMap.onCreate(savedInstanceState);
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void setWaitingPanel(){
        mNewBinding.newConsoleStatus.setVisibility(View.VISIBLE);
    }

    private void setEventPanel(final OpenEvent event){
        mNewBinding.newConsoleStatus.setVisibility(View.INVISIBLE);
        String url = Util.getGroupPhotoUrl(event.getGroup());
        if(TextUtils.isEmpty(url)){
            mNewBinding.newConsoleImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Picasso.with(getActivity()).load(url).into(mNewBinding.newConsoleImage);
            mNewBinding.newConsoleTitle.setText(event.getName());
            mNewBinding.newConsoleContent.setText(Util.getVenueAddress(getActivity(), event.getVenue()));
        }
        mNewBinding.newConsole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openEventDetail(getActivity(), event.getGroup().getUrlname(), event.getId());
            }
        });
    }

    private void loadOpenEvent(){
        if(!isAdded()){
            return;
        }
        setWaitingPanel();
        meetupClient.streamOpenEvents(new Callback<OpenEvent>() {
            @Override
            public void onResponse(Call<OpenEvent> call, Response<OpenEvent> response) {
                int statusCode = response.code();
                OpenEvent openEvent = response.body();
                if(openEvent != null && openEvent.isVisible()){
                    addEvent(openEvent);
                }
            }

            @Override
            public void onFailure(Call<OpenEvent> call, Throwable t) {
                // Log error here since request failed
                Log.e("finderror", "Find event request error: " + t.toString());
            }
        }, null);
    }

    private void addEvent(OpenEvent event){
        mEvents.add(event);
        if(mEvents.size() == 10){
            mEvents.remove(0);
        }
        if(isAdded()) {
            final Venue venue = event.getVenue();
            if(venue == null || !venue.isVisible()){
                return;
            }
            setEventPanel(event);
            setMap(event);
        }
    }

    private void setMap(final OpenEvent event){
        final Venue venue = event.getVenue();
        mNewBinding.newMap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                mGoogleMap.clear();
                if(mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                // For showing a move to my location button
                mGoogleMap.setMyLocationEnabled(true);

                LatLng markerLocation = new LatLng(venue.getLat(), venue.getLon());
                Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(markerLocation).title(event.getName()).snippet(event.getVenue().getFullAddress()));
                marker.setTag(event.getId() + "," + event.getGroup().getUrlname());
                marker.showInfoWindow();
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(markerLocation)
                        .zoom(4)
                        .build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String s = (String) marker.getTag();
                        String[] eventInfos = s.split(Pattern.quote(","));
                        if(eventInfos != null && eventInfos.length == 2) {
                            Util.openEventDetail(getActivity(), eventInfos[1], eventInfos[0]);
                        }

                    }
                });

            }
        });

    }

    @Override
    public void onResume(){
        mNewBinding.newMap.onResume();
        mHandler.post(mLoadData);
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        super.onResume();
    }

    @Override
    public void onPause(){
        mHandler.removeCallbacks(mLoadData);
        mNewBinding.newMap.onPause();
        super.onPause();
    }
}
