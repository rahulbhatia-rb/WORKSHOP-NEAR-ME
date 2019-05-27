package com.supermeetup.supermeetup.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected ArrayList<T> mModels = new ArrayList<>();

    public void setModels(@Nullable ArrayList<T> models) {
        mModels = models;
        notifyDataSetChanged();
    }

    public void clear() {
        mModels.clear();
        notifyDataSetChanged();
    }

    public void addModels(@Nullable ArrayList<T> models) {
        mModels.addAll(models);
        notifyDataSetChanged();
    }
}
