package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Irene on 10/21/17.
 */
@Parcel
public class Topic {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("urlkey")
    @Expose
    private String urlkey;
    @SerializedName("lang")
    @Expose
    private String lang;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getUrlkey() {
        return urlkey;
    }

    public void setUrlkey(String urlkey) {
        this.urlkey = urlkey;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

}
