package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Irene on 10/21/17.
 */
@Parcel
public class Member {
    @SerializedName("created")
    @Expose
    private long created;
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("visited")
    @Expose
    private long visited;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("group")
    @Expose
    private Group group;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getVisited() {
        return visited;
    }

    public void setVisited(long visited) {
        this.visited = visited;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
