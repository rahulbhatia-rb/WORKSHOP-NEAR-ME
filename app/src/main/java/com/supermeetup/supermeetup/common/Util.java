package com.supermeetup.supermeetup.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.location.Location;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.supermeetup.supermeetup.R;
import com.supermeetup.supermeetup.activities.EventDetailActivity;
import com.supermeetup.supermeetup.model.Category;
import com.supermeetup.supermeetup.model.Event;
import com.supermeetup.supermeetup.model.EventHost;
import com.supermeetup.supermeetup.model.Group;
import com.supermeetup.supermeetup.model.Member;
import com.supermeetup.supermeetup.model.Membership;
import com.supermeetup.supermeetup.model.Photo;
import com.supermeetup.supermeetup.model.Profile;
import com.supermeetup.supermeetup.model.Topic;
import com.supermeetup.supermeetup.model.Venue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    public static final int     PERMISSIONREQUEST_ACCESS_LOCATION = 0;

    public static final String  SHAREDPREFERENCE = "supermeetupsharedpreferences";

    public static final String  KEY_ATTEMPTINGLOGIN = "attempinglogin";
    public static final String  KEY_LOCATION = "location";

    public static final String  DEFAULT_FIELDS = "event_hosts, plain_text_no_images_description, group_category, group_photo, photo_album";
    public static final String  DEFAULT_PROFILE_FIELDS = "topics, memberships";
    public static final String  DEFAULT_GROUP_FIELDS = "member_sample";
    public static final float   DEFAULT_RADIUS = 30.0f;
    public static final int     DEFAULT_ZOOM = 10;

    public static final String  EXTRA_CATEGORY = "category";
    public static final String  EXTRA_CATEGORYLIST = "categorylist";
    public static final String  EXTRA_QUERY = "query";
    public static final String  EXTRA_EVENT = "event";
    public static final String  EXTRA_EVENT_ID = "eventid";
    public static final String  EXTRA_GROUP_USERNAME = "groupusername";
    public static final String  EXTRA_GROUP = "group";

    public static void disableBottomNavigationViewShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static String getString(Context context, int resId){
        if(context != null && context.getResources() != null) {
            return context.getResources().getString(resId);
        }
        return "";
    }

    public static int getColor(Context context, int resId){
        return context.getResources().getColor(resId);
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename)
    {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getMipMapResourceId(Context context, String variableName){
        return getResourceId(context, variableName, "mipmap");
    }

    public static int getCategoryIcon(Context context, long categoryId){
        int id = getMipMapResourceId(context, "ic_c" + categoryId);
        if(id == -1){
            id = getMipMapResourceId(context, "ic_c");
        }
        return id;
    }

    public static void writeBoolean(Context context, String key, boolean value){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean readBoolean(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static void writeLocation(Context context, String key, Location location){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key+"_lat", location.getLatitude()+"");
        editor.putString(key+"_lot", location.getLatitude()+"");
        editor.commit();
    }

    public static Location readLocation(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPREFERENCE, Context.MODE_PRIVATE);
        Location location = new Location("");
        location.setLatitude(Double.parseDouble(preferences.getString(key+"_lat", "0.0")));
        location.setLatitude(Double.parseDouble(preferences.getString(key+"_lot", "0.0")));
        return location;
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static LatLng getVenueLatLng(Venue venue){
        if(venue.isVisible()){
            return new LatLng(venue.getLat(), venue.getLon());
        }

        return null;
    }

    public static LatLng getVenueLatLng(Event event){
        if(event.getVenue() != null){
            return getVenueLatLng(event.getVenue());
        }
        return null;
    }

    public static ArrayList<Event> sortLocations(ArrayList<Event> events, final LatLng target) {
        Comparator comp = new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                float[] result1 = new float[3];
                LatLng location1 = getVenueLatLng(e1);
                Location.distanceBetween(target.latitude, target.longitude, location1.latitude, location1.longitude, result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                LatLng location2 = getVenueLatLng(e2);
                android.location.Location.distanceBetween(target.latitude, target.longitude, location2.latitude, location2.longitude, result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };


        Collections.sort(events, comp);
        return events;
    }

    public static ArrayList<Group> getGroupsFromProfile(Profile profile){
        ArrayList<Group> res = new ArrayList<>();
        Membership membership = profile.getMemberships();
        if(membership != null){
            ArrayList<Member> members = membership.getMember();
            if(members != null && members.size() > 0){
                for(Member member : members){
                    res.add(member.getGroup());
                }
            }
        }
        return res;
    }

    public static String getGroupPhotoUrl(Group group){
        String url = "";
        if(group != null) {
            Photo photo = group.getPhoto();
            if (photo == null) {
                photo = group.getGroupPhoto();
            }
            if (photo != null) {
                url = photo.getPhotoLink();
            }
        }
        return url;
    }

    public static String getVenueAddress(Context context, Venue venue){
        String res;
        if(venue == null){
            res = getString(context, R.string.no_location);
        }else{
            if(venue.isVisible()){
                res = venue.getFullAddress();
            }else{
                res = getString(context, R.string.private_location);
            }
        }
        return res;
    }

    public static void openEventDetail(Context context, String groupUserName, String eventId){
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra(Util.EXTRA_GROUP_USERNAME, groupUserName);
        intent.putExtra(Util.EXTRA_EVENT_ID, eventId);
        context.startActivity(intent);
    }

    public static int getGroupMemberRowCount(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) dpWidth/36 -1;
    }

    @BindingAdapter({"iconid"})
    public static void setIconId(ImageView view, long iconid) {
        view.setImageResource(Util.getCategoryIcon(view.getContext(), iconid));
    }

}
