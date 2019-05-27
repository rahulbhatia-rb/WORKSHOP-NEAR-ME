package com.supermeetup.supermeetup.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.supermeetup.supermeetup.common.ImageRoundCorners;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemGroupmemberBinding;
import com.supermeetup.supermeetup.model.Photo;
import com.supermeetup.supermeetup.model.Profile;

/**
 * Created by Irene on 10/26/17.
 */

public class GroupMemberViewHolder extends RecyclerView.ViewHolder {
    private ItemGroupmemberBinding mBinding;

    public GroupMemberViewHolder(ItemGroupmemberBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(Profile profile){
        Photo photo = profile.getPhoto();
        if(photo != null && !TextUtils.isEmpty(photo.getPhotoLink())) {
            final Transformation transformation = new ImageRoundCorners();
            Picasso.with(mBinding.getRoot().getContext()).load(photo.getPhotoLink()).transform(transformation).into(mBinding.groupmemberImage);
        }

        mBinding.executePendingBindings();
    }
}
