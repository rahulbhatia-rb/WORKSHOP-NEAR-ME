package com.supermeetup.supermeetup.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.GroupMemberViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.NearbyCategoryListViewHolder;
import com.supermeetup.supermeetup.common.Util;
import com.supermeetup.supermeetup.databinding.ItemCategorylistBinding;
import com.supermeetup.supermeetup.databinding.ItemGroupmemberBinding;
import com.supermeetup.supermeetup.model.Profile;

import java.util.ArrayList;

/**
 * Created by Irene on 10/26/17.
 */

public class GroupMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Profile> mMembers;
    private Context mContext;

    public GroupMemberAdapter(Context context, ArrayList<Profile> members){
        mMembers = members;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new GroupMemberViewHolder((ItemGroupmemberBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_groupmember, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupMemberViewHolder) holder).bind(mMembers.get(position));
    }

    @Override
    public int getItemCount() {
        int size = mMembers.size();
        int membersCount = Util.getGroupMemberRowCount(mContext);
        return size > membersCount ? membersCount : size;
    }
}
