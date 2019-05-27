package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemProfilerecommendeventBinding;
import com.supermeetup.supermeetup.model.Event;

import org.parceler.Parcels;

/**
 * Created by Irene on 10/21/17.
 */

public class ProfileRecommendEventViewHolder extends RecyclerView.ViewHolder {

    private ItemProfilerecommendeventBinding mBinding;

    public ProfileRecommendEventViewHolder(ItemProfilerecommendeventBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
    public void bind(final Event event){
        mBinding.setVariable(BR.event, event);

        String url = Util.getGroupPhotoUrl(event.getGroup());
        if(TextUtils.isEmpty(url)){
            mBinding.shakeRecommendeventImage.setVisibility(View.GONE);
        }else{
            mBinding.shakeRecommendeventImage.setVisibility(View.VISIBLE);
            Picasso.with(mBinding.getRoot().getContext())
                    .load(url)
                    .placeholder(R.drawable.logo)
                    .into(mBinding.shakeRecommendeventImage);
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
