package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Irene on 10/26/17.
 */
@Parcel
public class PhotoAlbum {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("photo_sample")
    @Expose
    private ArrayList<Photo> photos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
