package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.TopicViewHolder;
import com.supermeetup.supermeetup.databinding.ItemTopicBinding;
import com.supermeetup.supermeetup.model.Topic;

import java.util.ArrayList;

/**
 * Created by Irene on 10/21/17.
 */

public class TopicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Topic> mTopics;

    public TopicsAdapter(ArrayList<Topic> topics){
        mTopics = topics;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new TopicViewHolder((ItemTopicBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TopicViewHolder) holder).bind(mTopics.get(position));
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }
}
