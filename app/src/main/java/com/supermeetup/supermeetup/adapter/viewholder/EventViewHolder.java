package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemEventBinding;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.Group;
import com.supermeetup.supermeetup.model.Photo;
import com.supermeetup.supermeetup.model.Venue;

import org.parceler.Parcels;

/**
 * Created by Irene on 10/15/17.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    private ItemEventBinding mBinding;
    private boolean mShowFirstDivider;

    public EventViewHolder(ItemEventBinding binding) {
        this(binding, true);
    }

    public EventViewHolder(ItemEventBinding binding, boolean showFirstDivider){
        super(binding.getRoot());
        mBinding = binding;
        mShowFirstDivider = showFirstDivider;
    }

    public void bind(final Event event, int position){
        if(position == 0 && !mShowFirstDivider){
            mBinding.eventDivider.setVisibility(View.GONE);
        }else{
            mBinding.eventDivider.setVisibility(View.VISIBLE);
        }
        mBinding.eventName.setText(event.getName());
        Group group = event.getGroup();
        if(group != null) {
            mBinding.eventGroup.setText(group.getName());
            String photoLink = Util.getGroupPhotoUrl(group);
            if (!TextUtils.isEmpty(photoLink)) {
                Picasso.with(mBinding.getRoot().getContext())
                        .load(photoLink)
                        .placeholder(R.drawable.logo)
                        .into(mBinding.eventImage);
            }
        }
        mBinding.eventTime.setText(event.getEventTime());
        Venue venue = event.getVenue();
        if(venue!=null){
            if(venue.isVisible()) {
                mBinding.eventAddress.setText(venue.getFullAddress());
            }else{
                mBinding.eventAddress.setText(Util.getString(mBinding.getRoot().getContext(), R.string.private_location));
            }
        }else{
            mBinding.eventAddress.setText(Util.getString(mBinding.getRoot().getContext(), R.string.no_location));
        }
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventDetailActivity.class);
                i.putExtra(Util.EXTRA_EVENT, Parcels.wrap(event));
                v.getContext().startActivity(i);
            }
        });
        mBinding.executePendingBindings();
    }


}
