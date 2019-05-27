package com.supermeetup.supermeetup.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.adapter.viewholder.AlbumPhotoViewHolder;
import com.supermeetup.supermeetup.adapter.viewholder.GroupEventViewHolder;
import com.supermeetup.supermeetup.databinding.ItemAlbumphotoBinding;
import com.supermeetup.supermeetup.databinding.ItemGroupeventBinding;
import com.supermeetup.supermeetup.model.Photo;

import java.util.ArrayList;

/**
 * Created by Irene on 10/26/17.
 */

public class AlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Photo> mPhotos;

    public AlbumAdapter(ArrayList<Photo> photos){
        mPhotos = photos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new AlbumPhotoViewHolder((ItemAlbumphotoBinding) DataBindingUtil.inflate(layoutInflater, R.layout.item_albumphoto, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AlbumPhotoViewHolder) holder).bind(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        int size = mPhotos.size();
        return (size > 5 ? 5 : size);
    }
}
