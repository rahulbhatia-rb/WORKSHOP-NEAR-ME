package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.GroupListViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.ProfileRecommendEventViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.TopicListViewHolder;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemGrouplistBinding;
import com.supermeetup.supermeetup.databinding.ItemProfilerecommendeventBinding;
import com.supermeetup.supermeetup.databinding.ItemTopiclistBinding;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.Profile;

/**
 * Created by Irene on 10/21/17.
 */

public class ShakeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEWTYPE_RECOMMENDEVENT = 0;
    private static final int VIEWTYPE_INTERESTS = 1;
    private static final int VIEWTYPE_MEMBER = 2;

    private Profile mProfile;
    private Event mSelectedEvent;

    public ShakeAdapter(Profile profile, Event selectEvent){
        mSelectedEvent = selectEvent;
        mProfile = profile;
    }

    public int getItemViewType(int position){
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case VIEWTYPE_RECOMMENDEVENT:
                return new ProfileRecommendEventViewHolder(
                        (ItemProfilerecommendeventBinding) DataBindingUtil.inflate(
                                layoutInflater,
                                R.layout.item_profilerecommendevent,
                                parent,
                                false));
            case VIEWTYPE_INTERESTS:
                return new TopicListViewHolder((ItemTopiclistBinding) DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_topiclist,
                        parent,
                        false));
            case VIEWTYPE_MEMBER:
                return new GroupListViewHolder((ItemGrouplistBinding) DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.item_grouplist,
                        parent,
                        false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case VIEWTYPE_RECOMMENDEVENT:
                ((ProfileRecommendEventViewHolder) holder).bind(mSelectedEvent);
                break;
            case VIEWTYPE_INTERESTS:
                ((TopicListViewHolder) holder).bind(mProfile.getTopics());
                break;
            case VIEWTYPE_MEMBER:
                ((GroupListViewHolder) holder).bind(Util.getGroupsFromProfile(mProfile));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
