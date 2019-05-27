package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class OpenEvent {
    public static final String VISIBILITYTYPE_PUBLIC = "public";
    public static final String VISIBILITYTYPE_PUBLIC_LIMITED = "public_limited";
    public static final String VISIBILITYTYPE_MEMBERS = "members";

    @SerializedName("utc_offset")
    @Expose
    private long utcOffset;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("rsvp_limit")
    @Expose
    private long rsvpLimit;
    @SerializedName("venue_visibility")
    @Expose
    private String venueVisibility;
    @SerializedName("visibility")
    @Expose
    private String visibility;
    @SerializedName("maybe_rsvp_count")
    @Expose
    private long maybeRsvpCount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mtime")
    @Expose
    private long mtime;
    @SerializedName("event_url")
    @Expose
    private String eventUrl;
    @SerializedName("yes_rsvp_count")
    @Expose
    private long yesRsvpCount;
    @SerializedName("duration")
    @Expose
    private long duration;
    @SerializedName("payment_required")
    @Expose
    private String paymentRequired;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private long time;
    @SerializedName("group")
    @Expose
    private Group group;
    @SerializedName("status")
    @Expose
    private String status;

    public long getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(long utcOffset) {
        this.utcOffset = utcOffset;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public long getRsvpLimit() {
        return rsvpLimit;
    }

    public void setRsvpLimit(long rsvpLimit) {
        this.rsvpLimit = rsvpLimit;
    }

    public String getVenueVisibility() {
        return venueVisibility;
    }

    public void setVenueVisibility(String venueVisibility) {
        this.venueVisibility = venueVisibility;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public long getMaybeRsvpCount() {
        return maybeRsvpCount;
    }

    public void setMaybeRsvpCount(long maybeRsvpCount) {
        this.maybeRsvpCount = maybeRsvpCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public long getYesRsvpCount() {
        return yesRsvpCount;
    }

    public void setYesRsvpCount(long yesRsvpCount) {
        this.yesRsvpCount = yesRsvpCount;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPaymentRequired() {
        return paymentRequired;
    }

    public void setPaymentRequired(String paymentRequired) {
        this.paymentRequired = paymentRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVisible(){
        return VISIBILITYTYPE_PUBLIC.equals(visibility);
    }
}
