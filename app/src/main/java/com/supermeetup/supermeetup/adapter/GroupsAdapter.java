package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.GroupViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.TopicViewHolder;
import com.supermeetup.supermeetup.databinding.ItemGroupBinding;
import com.supermeetup.supermeetup.databinding.ItemTopicBinding;
import com.supermeetup.supermeetup.model.Group;

import java.util.ArrayList;

/**
 * Created by Irene on 10/21/17.
 */

public class GroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Group> mGroups;

    public GroupsAdapter(ArrayList<Group> groups){
        mGroups = groups;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new GroupViewHolder((ItemGroupBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_group, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupViewHolder) holder).bind(mGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }
}
