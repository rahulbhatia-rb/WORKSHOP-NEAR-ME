package com.supermeetup.supermeetup.adapter.viewholder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supermeetup.supermeetup.adapter.GroupsAdapter;
import com.supermeetup.supermeetup.databinding.ItemGrouplistBinding;
import com.supermeetup.supermeetup.model.Group;

import java.util.ArrayList;

/**
 * Created by Irene on 10/21/17.
 */

public class GroupListViewHolder extends RecyclerView.ViewHolder {
    private ItemGrouplistBinding mBinding;
    public GroupListViewHolder(ItemGrouplistBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        mBinding.grouplist.setLayoutManager(new LinearLayoutManager(mBinding.getRoot().getContext()));
    }

    public void bind(ArrayList<Group> groups){
        mBinding.grouplist.setAdapter(new GroupsAdapter(groups));
        mBinding.executePendingBindings();
    }
}
