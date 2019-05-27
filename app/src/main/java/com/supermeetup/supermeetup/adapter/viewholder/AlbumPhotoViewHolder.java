package com.supermeetup.supermeetup.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.databinding.ItemAlbumphotoBinding;
import com.supermeetup.supermeetup.model.Photo;

/**
 * Created by Irene on 10/26/17.
 */

public class AlbumPhotoViewHolder extends RecyclerView.ViewHolder{

    private ItemAlbumphotoBinding mBinding;

    public AlbumPhotoViewHolder(ItemAlbumphotoBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(Photo photo){
        Picasso.with(mBinding.getRoot().getContext())
                .load(photo.getPhotoLink())
                .placeholder(R.drawable.logo)
                .into(mBinding.albumphoto);
        mBinding.executePendingBindings();
    }
}
