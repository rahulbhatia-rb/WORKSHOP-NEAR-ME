package com.supermeetup.supermeetup.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.EventViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.NearbyCategoryListViewHolder;
import com.supermeetup.supermeetup.databinding.ItemCategorylistBinding;
import com.supermeetup.supermeetup.databinding.ItemEventBinding;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.model.Event;

import java.util.ArrayList;

/**
 * Created by yuxin on 10/14/17.
 */

public class CategoryAndEventAdapter extends BaseAdapter<Event> {
    private static final int VIEWTYPE_CATEGORYLIST = 0;
    private static final int VIEWTYPE_EVENTITEM = 1;

    private Context mConext;
    private ArrayList<Category> mCategories = new ArrayList<>();
    private boolean mShowFirstEventDivider;

    public CategoryAndEventAdapter(Context context) {
        mConext = context;
    }

    public void setCategories(ArrayList<Category> categories) {
        mCategories = categories;
        notifyDataSetChanged();
    }

    public void setEvents(ArrayList<Event> events, boolean showFirstDivider) {
        setModels(events);
        mShowFirstEventDivider = showFirstDivider;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEWTYPE_CATEGORYLIST;
        } else {
            return VIEWTYPE_EVENTITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEWTYPE_CATEGORYLIST:
                return new NearbyCategoryListViewHolder((ItemCategorylistBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_categorylist, parent, false));
            case VIEWTYPE_EVENTITEM:
                return new EventViewHolder((ItemEventBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_event, parent, false), mShowFirstEventDivider);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        switch (viewType) {
            case VIEWTYPE_CATEGORYLIST:
                ((NearbyCategoryListViewHolder) holder).bind(mCategories);
                break;
            case VIEWTYPE_EVENTITEM:
                ((EventViewHolder) holder).bind((Event) mModels.get(position - 1), position - 1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mModels.size() + 1;
    }

    @Override
    public void clear() {
        mModels.clear();
        mCategories.clear();
        notifyDataSetChanged();
    }
}