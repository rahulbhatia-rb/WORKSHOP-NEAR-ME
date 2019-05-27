package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.GroupEventViewHolder;
import com.supermeetup.supermeetup.databinding.ItemGroupeventBinding;
import com.supermeetup.supermeetup.model.Event;

import java.util.ArrayList;

/**
 * Created by Irene on 10/26/17.
 */

public class GroupEventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> mEvents = new ArrayList<>();

    public GroupEventsAdapter(ArrayList<Event> events){
        for(Event event:events){
            if(event.isPastStatus() || event.isUpcomingStatus()){
                mEvents.add(event);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new GroupEventViewHolder((ItemGroupeventBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_groupevent, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GroupEventViewHolder) holder).bind(mEvents.get(position));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
