package com.supermeetup.supermeetup.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.MeetupApp;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.GroupEventsAdapter;
import com.supermeetup.supermeetup.adapter.GroupMemberAdapter;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ActivityGroupBinding;
import com.supermeetup.supermeetup.dialog.LoadingDialog;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.Group;
import com.supermeetup.supermeetup.model.Profile;
import com.supermeetup.supermeetup.network.MeetupClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irene on 10/26/17.
 */

public class GroupActivity extends AppCompatActivity {

    private ActivityGroupBinding mBinding;
    private Group mGroup;
    private String mGroupNameUrl;
    private LoadingDialog mLoadingDialog;
    private MeetupClient meetupClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_group);
        mLoadingDialog = new LoadingDialog(this);
        meetupClient = MeetupApp.getRestClient(this);

        Intent intent = getIntent();
        mGroupNameUrl = intent.getStringExtra(Util.EXTRA_GROUP_USERNAME);
        if(TextUtils.isEmpty(mGroupNameUrl)){
            finish();
        }
        loadGroup();

        mBinding.groupdetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadGroup(){
        mLoadingDialog.setMessage(Util.getString(this, R.string.load_group));
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        meetupClient.getGroup(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                mGroup = response.body();
                bindData();
            }

            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("grouperror", "get group request error: " + t.toString());
            }
        }, mGroupNameUrl, Util.DEFAULT_GROUP_FIELDS);
    }

    private void loadEvents(){
        mLoadingDialog.setMessage(Util.getString(this, R.string.load_event));
        if(!mLoadingDialog.isShowing()){
            mLoadingDialog.show();
        }
        meetupClient.getGroupEvents(new Callback<ArrayList<Event>>() {
            @Override
            public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                ArrayList<Event> events = response.body();
                mLoadingDialog.dismiss();
                mBinding.groupdetailEvents.setAdapter(new GroupEventsAdapter(events));
            }

            @Override
            public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                // Log error here since request failed
                mLoadingDialog.dismiss();
                Log.e("loadevent", "load event request error: " + t.toString());
            }
        }, mGroupNameUrl, null, Util.DEFAULT_FIELDS, null, null, null);
    }

    private void bindData(){
        if(mGroup == null){
            finish();
        }

        String groupImageUrl = Util.getGroupPhotoUrl(mGroup);
        if(!groupImageUrl.isEmpty()){
            Picasso.with(this)
                    .load(groupImageUrl)
                    .into(mBinding.groupdetailImage);
        }

        ArrayList<Profile> members = mGroup.getMemberSample();
        if(members != null && members.size() > 0) {
            mBinding.groupdetailMembers.setLayoutManager(new GridLayoutManager(this, Util.getGroupMemberRowCount(this)));
            mBinding.groupdetailMembers.setAdapter(new GroupMemberAdapter(this, members));
        }

        mBinding.groupdetailWho.setText(String.format(Util.getString(this, R.string.groupwho), mGroup.getMembers(), mGroup.getWho(), mGroup.getLocalizedLocation()));

        mBinding.groupdetailEvents.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
        mBinding.setVariable(BR.group, mGroup);
    }

}
