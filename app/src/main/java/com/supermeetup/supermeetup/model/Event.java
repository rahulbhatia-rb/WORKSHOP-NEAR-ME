package com.supermeetup.supermeetup.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * GET https://api.meetup.com/find/events
 */
@Parcel
public class Event {

    public static final String VISIBILITYTYPE_PUBLIC = "public";
    public static final String VISIBILITYTYPE_PUBLIC_LIMITED = "public_limited";
    public static final String VISIBILITYTYPE_MEMBERS = "members";
    public static final String STATUS_UPCOMING = "upcoming";
    public static final String STATUS_PAST = "past";

    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("time")
    @Expose
    private long time;
    @SerializedName("rsvp_close_offset")
    @Expose
    private String rsvpCloseOffset;
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("utc_offset")
    @Expose
    private long utcOffset;
    @SerializedName("waitlist_count")
    @Expose
    private int waitlistCount;
    @SerializedName("yes_rsvp_count")
    @Expose
    private int yesRsvpCount;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("group")
    @Expose
    private Group group;
    @SerializedName("event_hosts")
    @Expose
    private ArrayList<EventHost> eventHosts;
    @SerializedName("fee")
    @Expose
    private Fee fee;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("how_to_find_us")
    @Expose
    private String howToFindUs;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("plain_text_no_images_description")
    @Expose
    private String plain_text_no_images_description;
    @SerializedName("photo_album")
    @Expose
    private PhotoAlbum photoAlbum;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRsvpCloseOffset() {
        return rsvpCloseOffset;
    }

    public void setRsvpCloseOffset(String rsvpCloseOffset) {
        this.rsvpCloseOffset = rsvpCloseOffset;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(long utcOffset) {
        this.utcOffset = utcOffset;
    }

    public int getWaitlistCount() {
        return waitlistCount;
    }

    public void setWaitlistCount(int waitlistCount) {
        this.waitlistCount = waitlistCount;
    }

    public int getYesRsvpCount() {
        return yesRsvpCount;
    }

    public void setYesRsvpCount(int yesRsvpCount) {
        this.yesRsvpCount = yesRsvpCount;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHowToFindUs() {
        return howToFindUs;
    }

    public void setHowToFindUs(String howToFindUs) {
        this.howToFindUs = howToFindUs;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isPublic(){
        return VISIBILITYTYPE_PUBLIC.equals(visibility);
    }

    public String getPlainTextNoImagesDescription() {
        return plain_text_no_images_description;
    }

    public void setPlainTextNoImagesDescription(String plain_text_no_images_description) {
        this.plain_text_no_images_description = plain_text_no_images_description;
    }

    public ArrayList<EventHost> getEventHosts() {
        return eventHosts;
    }

    public void setEventHosts(ArrayList<EventHost> eventHosts) {
        this.eventHosts = eventHosts;
    }

    public PhotoAlbum getPhotoAlbum() {
        return photoAlbum;
    }

    public void setPhotoAlbum(PhotoAlbum photoAlbum) {
        this.photoAlbum = photoAlbum;
    }

    public String getEventTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
        Date date = new Date(getTime());
        return sdf.format(date);
    }

    public boolean isUpcomingStatus(){
        return STATUS_UPCOMING.equals(status);
    }

    public boolean isPastStatus(){
        return STATUS_PAST.equals(status);
    }

}
