package com.supermeetup.supermeetup.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Irene on 10/21/17.
 */
@Parcel
public class Membership {
    @SerializedName("member")
    @Expose
    ArrayList<Member> member;

    public ArrayList<Member> getMember() {
        return member;
    }

    public void setMember(ArrayList<Member> member) {
        this.member = member;
    }
}
