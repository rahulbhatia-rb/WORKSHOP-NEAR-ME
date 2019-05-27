package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.adapter.AlbumAdapter;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemGroupeventBinding;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.PhotoAlbum;

/**
 * Created by Irene on 10/26/17.
 */

public class GroupEventViewHolder extends RecyclerView.ViewHolder {
    private ItemGroupeventBinding mBinding;

    public GroupEventViewHolder(ItemGroupeventBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.eventPhotos.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    public void bind(final Event event){
        if(event.isUpcomingStatus()){
            mBinding.eventStatus.setTextColor(Util.getColor(mBinding.getRoot().getContext(), R.color.colorPrimary));
        }else{
            mBinding.eventStatus.setTextColor(Util.getColor(mBinding.getRoot().getContext(), R.color.textP));
        }
        mBinding.eventAddress.setText(Util.getVenueAddress(mBinding.getRoot().getContext(), event.getVenue()));
        PhotoAlbum album = event.getPhotoAlbum();
        if(album != null){
            mBinding.eventPhotos.setVisibility(View.VISIBLE);
            mBinding.eventPhotos.setAdapter(new AlbumAdapter(album.getPhotos()));
        }else{
            mBinding.eventPhotos.setVisibility(View.GONE);
        }
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openEventDetail(v.getContext(), event.getGroup().getUrlname(), event.getId());
            }
        });

        mBinding.setVariable(BR.event, event);
        mBinding.executePendingBindings();
    }
}
