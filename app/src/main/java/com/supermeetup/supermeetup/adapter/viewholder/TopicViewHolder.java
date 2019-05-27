package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.activities.HomeActivity;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemTopicBinding;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.Topic;

/**
 * Created by Irene on 10/21/17.
 */

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private ItemTopicBinding mBinding;

    public TopicViewHolder(ItemTopicBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(final Topic topic){
        mBinding.setVariable(BR.topic, topic);
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                i.putExtra(Util.EXTRA_QUERY, topic.getName());
                v.getContext().startActivity(i);
            }
        });
        mBinding.executePendingBindings();
    }
}
