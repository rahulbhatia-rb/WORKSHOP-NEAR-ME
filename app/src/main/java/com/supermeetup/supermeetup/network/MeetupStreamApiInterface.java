package com.supermeetup.supermeetup.network;

import android.support.annotation.Nullable;

import com.supermeetup.supermeetup.model.OpenEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeetupStreamApiInterface {
    /**
     *
     * @param since_mtime Return events with an mtime greater than the supplied time, in milliseconds since the epoch
     * @return
     */
    @GET("/2/open_events")
    Call<OpenEvent> openEvents(@Nullable @Query("since_mtime") Long since_mtime);
}
