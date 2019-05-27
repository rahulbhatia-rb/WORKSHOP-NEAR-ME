package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by yuxin on 10/13/17.
 * Sample:
 * {
 *      "created": 1488936101000,
 *      "name": "Adventures in Common",
 *      "id": 22812516,
 *      "join_mode": "open",
 *      "lat": 37.779998779296875,
 *      "lon": -122.41999816894531,
 *      "urlname": "Adventures-in-Common",
 *      "who": "Adventurers",
 *      "localized_location": "San Francisco, CA",
 *      "region": "en_US",
 *      "category": {
 *          "id": 31,
 *          "name": "Socializing",
 *          "shortname": "socializing",
 *          "sort_name": "Socializing"
 *      },
 *      "photo": {
 *          "id": 461031337,
 *          "highres_link": "https://secure.meetupstatic.com/photos/event/c/8/8/9/highres_461031337.jpeg",
 *          "photo_link": "https://secure.meetupstatic.com/photos/event/c/8/8/9/600_461031337.jpeg",
 *          "thumb_link": "https://secure.meetupstatic.com/photos/event/c/8/8/9/thumb_461031337.jpeg",
 *          "type": "event",
 *          "base_url": "https://secure.meetupstatic.com"
 *      }
 *  }
 */
@Parcel
public class Group {

    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("members")
    @Expose
    private long members;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("join_mode")
    @Expose
    private String joinMode;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("lon")
    @Expose
    private double lon;
    @SerializedName("urlname")
    @Expose
    private String urlname;
    @SerializedName("who")
    @Expose
    private String who;
    @SerializedName("localized_location")
    @Expose
    private String localizedLocation;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("photo")
    @Expose
    private Photo photo;
    @SerializedName("group_photo")
    @Expose
    private Photo group_photo;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("member_sample")
    @Expose
    private ArrayList<Profile> member_sample;

    public ArrayList<Profile> getMemberSample() {
        return member_sample;
    }

    public void setMemberSample(ArrayList<Profile> member_sample) {
        this.member_sample = member_sample;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJoinMode() {
        return joinMode;
    }

    public void setJoinMode(String joinMode) {
        this.joinMode = joinMode;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getLocalizedLocation() {
        return localizedLocation;
    }

    public void setLocalizedLocation(String localizedLocation) {
        this.localizedLocation = localizedLocation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Photo getPhoto(){
        return photo;
    }

    public void setPhoto(Photo photo){
        this.photo = photo;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public long getMembers() {
        return members;
    }

    public void setMembers(long members) {
        this.members = members;
    }

    public Photo getGroupPhoto() {
        return group_photo;
    }

    public void setGroupPhoto(Photo group_photo) {
        this.group_photo = group_photo;
    }
}
