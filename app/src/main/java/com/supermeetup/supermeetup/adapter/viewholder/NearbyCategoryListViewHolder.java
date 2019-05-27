package com.supermeetup.supermeetup.adapter.viewholder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.CategoryAdapter;
import com.supermeetup.supermeetup.databinding.ItemCategorylistBinding;
import com.supermeetup.supermeetup.model.Category;

import java.util.ArrayList;

/**
 * Created by Irene on 10/15/17.
 */

public class NearbyCategoryListViewHolder extends RecyclerView.ViewHolder {
    private ItemCategorylistBinding mBinding;

    public NearbyCategoryListViewHolder(ItemCategorylistBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.catetorylist.setLayoutManager(new GridLayoutManager(mBinding.getRoot().getContext(), 4));
    }

    public void bind(ArrayList<Category> categories){
        mBinding.catetorylist.setAdapter(new CategoryAdapter(mBinding.getRoot().getContext(), categories, 7, null));
    }
}
