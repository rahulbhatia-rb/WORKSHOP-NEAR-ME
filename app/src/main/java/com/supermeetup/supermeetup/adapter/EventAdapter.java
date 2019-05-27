package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.EventViewHolder;
import com.supermeetup.supermeetup.databinding.ItemEventBinding;
import com.supermeetup.supermeetup.model.Event;

import java.util.ArrayList;

/**
 * Created by Irene on 10/19/17.
 */

public class EventAdapter extends BaseAdapter<Event> {

    private boolean mShowFirstEventDivider;
    private EventAdapterListener eventAdapterListener;

    public EventAdapter(@NonNull EventAdapterListener eventAdapterListener){
        this.eventAdapterListener = eventAdapterListener;
    }

    // define an interface required by the ViewHolder
    public interface EventAdapterListener {
        void onItemSeleted(View view, int position);
    }

    public void setEvents(ArrayList<Event> events, boolean showFirstDivider){
        mModels = events;
        mShowFirstEventDivider = showFirstDivider;
        notifyDataSetChanged();
    }

    public ArrayList<Event> getEvents(){
        return mModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new EventViewHolder((ItemEventBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_event, parent, false), mShowFirstEventDivider);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((EventViewHolder) holder).bind(mModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }
}
