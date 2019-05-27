package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.activities.GroupActivity;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemGroupBinding;
import com.supermeetup.supermeetup.model.Group;
import com.supermeetup.supermeetup.model.Photo;

/**
 * Created by Irene on 10/21/17.
 */

public class GroupViewHolder extends RecyclerView.ViewHolder {
    private ItemGroupBinding mBinding;

    public GroupViewHolder(ItemGroupBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(final Group group){
        String url = Util.getGroupPhotoUrl(group);
        if(!TextUtils.isEmpty(url)){
            Picasso.with(mBinding.getRoot().getContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mBinding.groupImage);
        }
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GroupActivity.class);
                intent.putExtra(Util.EXTRA_GROUP_USERNAME, group.getUrlname());
                v.getContext().startActivity(intent);
            }
        });
        mBinding.setVariable(BR.group, group);
        mBinding.executePendingBindings();
    }
}
