package com.supermeetup.supermeetup.adapter.viewholder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.adapter.TopicsAdapter;
import com.supermeetup.supermeetup.databinding.ItemTopiclistBinding;
import com.supermeetup.supermeetup.model.Topic;

import java.util.ArrayList;

/**
 * Created by Irene on 10/21/17.
 */

public class TopicListViewHolder extends RecyclerView.ViewHolder {

    ItemTopiclistBinding mBinding;

    public TopicListViewHolder(ItemTopiclistBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.topiclist.setLayoutManager(new GridLayoutManager(binding.getRoot().getContext(), 2));
    }

    public void bind(ArrayList<Topic> topics){
        mBinding.topiclist.setAdapter(new TopicsAdapter(topics));
        mBinding.executePendingBindings();
    }
}
