package com.supermeetup.supermeetup.adapter.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.BR;
import com.supermeetup.supermeetup.activities.RecommendEventsActivity;
import com.supermeetup.supermeetup.callback.UIActionCallback;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemCategoryBinding;
import com.supermeetup.supermeetup.model.Category;

import org.parceler.Parcels;

import java.util.ArrayList;


/**
 * Created by yuxin on 10/14/17.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private ItemCategoryBinding mBinding;
    private UIActionCallback mCallback;
    private ArrayList<Category> mCategories;

    public CategoryViewHolder(ItemCategoryBinding binding, ArrayList<Category> categories, UIActionCallback callback) {
        super(binding.getRoot());
        mCallback = callback;
        mBinding = binding;
        mCategories = categories;
    }

    public void bind(final Category category){
        mBinding.setVariable(BR.category, category);
        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RecommendEventsActivity.class);
                i.putExtra(Util.EXTRA_CATEGORY, Parcels.wrap(category));
                i.putExtra(Util.EXTRA_CATEGORYLIST, Parcels.wrap(mCategories));
                v.getContext().startActivity(i);
                if(mCallback != null){
                    mCallback.close();
                }
            }
        });
        mBinding.executePendingBindings();
    }
}
