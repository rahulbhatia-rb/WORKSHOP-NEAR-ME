package com.supermeetup.supermeetup.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.GroupViewHolder;
import com.supermeetup.supermeetup.common.ImageRoundCorners;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ActivityEventBinding;
import com.supermeetup.supermeetup.databinding.ItemGroupBinding;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.EventHost;
import com.supermeetup.supermeetup.model.Group;
import com.supermeetup.supermeetup.model.Venue;
import com.supermeetup.supermeetup.network.MeetupClient;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/21/17.
 */

public class EventDetailActivity extends AppCompatActivity {
    private ActivityEventBinding mBinding;
    private Event mEvent;
    private String mGroupNameUrl;
    private String mEventId;
    private LoadingDialog mLoadingDialog;
    private MeetupClient meetupClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_event);
        mLoadingDialog = new LoadingDialog(this);
        meetupClient = MeetupApp.getRestClient(this);

        Intent intent = getIntent();
        if(intent.hasExtra(Util.EXTRA_EVENT)) {
            mEvent = Parcels.unwrap(intent.getParcelableExtra(Util.EXTRA_EVENT));
            bindData();
        }else if(intent.hasExtra(Util.EXTRA_EVENT_ID) && intent.hasExtra(Util.EXTRA_GROUP_USERNAME)){
            mGroupNameUrl = intent.getStringExtra(Util.EXTRA_GROUP_USERNAME);
            mEventId = intent.getStringExtra(Util.EXTRA_EVENT_ID);
            loadEvent();
        }
    }

    private void loadEvent(){
        mLoadingDialog.setMessage(Util.getString(this, R.string.load_event));
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        meetupClient.getEvent(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                mEvent = response.body();
                mLoadingDialog.dismiss();
                bindData();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("finderror", "Recommended event request error: " + t.toString());
            }
        }, mGroupNameUrl, mEventId, Util.DEFAULT_FIELDS);
    }

    private void bindData(){
        if (mEvent == null) {
            finish();
        }
        mBinding.eventdetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Group group = mEvent.getGroup();
        if(group == null){
            mBinding.eventdetailGrouplayout.setVisibility(View.GONE);
        }else{
            mBinding.eventdetailGrouplayout.setVisibility(View.VISIBLE);
            mBinding.eventdetailGrouplayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(group != null){
                        Intent intent = new Intent(EventDetailActivity.this, GroupActivity.class);
                        intent.putExtra(Util.EXTRA_GROUP_USERNAME, group.getUrlname());
                        startActivity(intent);
                    }
                }
            });
        }
        final String groupImageUrl = Util.getGroupPhotoUrl(mEvent.getGroup());
        if(!TextUtils.isEmpty(groupImageUrl)){
            Picasso.with(this)
                    .load(groupImageUrl)
                    .transform(new ImageRoundCorners())
                    .into(mBinding.eventdetailGroupImage);
        }

        Venue venue = mEvent.getVenue();
        if(venue == null){
            mBinding.eventdetailAddressName.setText(Util.getString(this, R.string.no_location));
            mBinding.eventdetailAddressLocation.setVisibility(View.GONE);
        }else{
            if(venue.isVisible()){
                mBinding.eventdetailAddressName.setText(venue.getName());
                mBinding.eventdetailAddressLocation.setVisibility(View.VISIBLE);
                mBinding.eventdetailAddressLocation.setText(venue.getFullAddress());
            }else{
                mBinding.eventdetailAddressName.setText(Util.getString(this, R.string.private_location));
                mBinding.eventdetailAddressLocation.setVisibility(View.GONE);
            }
        }

        ArrayList<EventHost> hosts = mEvent.getEventHosts();
        if(hosts == null || hosts.size() == 0){
            mBinding.eventdetailHostName.setText(Util.getString(this, R.string.no_host));
            mBinding.eventdetailHostIntro.setVisibility(View.GONE);
        }else{
            EventHost host = hosts.get(0);
            mBinding.eventdetailHostName.setText(host.getName());
            if(TextUtils.isEmpty(host.getIntro())){
                mBinding.eventdetailHostIntro.setVisibility(View.GONE);
            }else {
                mBinding.eventdetailHostIntro.setVisibility(View.VISIBLE);
                mBinding.eventdetailHostIntro.setText(host.getIntro());
            }
        }

        mBinding.setVariable(BR.event, mEvent);
    }
}
