package com.example.bahroel.sigapbencana.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;

import com.example.bahroel.sigapbencana.activity.LoginActivity;

public class PrefUtils {
    // Sharedpref file name
    private static final String PREF_NAME = "sigapbencana";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    // Email address (make variable public to access from outside)
    public static final String KEY_USERID = "userId";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_API_TOKEN = "api_token";
    public static final String KEY_PLACE = "place";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_ADDRESS = "address";
    public static final String IS_DATA = "IsDataIn";


    public PrefUtils() {

    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_API_TOKEN, apiKey);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString(KEY_API_TOKEN, null);
    }

    public static void setLoginSession(Context context, String userId, String email, String password, String nama){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERID, userId);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_NAME, nama);
        editor.commit();
    }

    public static void setNewPassword(Context context, String newPassword){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_PASSWORD, newPassword);
        editor.commit();
    }

    public static String getNamaUser(Context context){
        return getSharedPreferences(context).getString(KEY_NAME, null);
    }

    public static String getUserId(Context context){
        return getSharedPreferences(context).getString(KEY_USERID, null);
    }

    public static String getPasswordUser(Context context){
        return getSharedPreferences(context).getString(KEY_PASSWORD, null);
    }

    public static void setDataBencana(Context context, String place, String latitude, String longitude, String address){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_PLACE, place);
        editor.putString(KEY_LATITUDE, latitude);
        editor.putString(KEY_LONGITUDE, longitude);
        editor.putString(KEY_ADDRESS, address);
        editor.commit();
    }

    public static void setLatLang(Context context, String latitude, String longitude){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_LATITUDE, latitude);
        editor.putString(KEY_LONGITUDE, longitude);
        editor.commit();
    }

    public static String getPlaceBencana(Context context){
        return getSharedPreferences(context).getString(KEY_PLACE, null);
    }

    public static String getLatitudeBencana(Context context){
        return getSharedPreferences(context).getString(KEY_LATITUDE, null);
    }

    public static String getLongitudeBencana(Context context){
       return getSharedPreferences(context).getString(KEY_LONGITUDE, null);

    }

    public static String getAddressBencana(Context context){
       return getSharedPreferences(context).getString(KEY_ADDRESS, null);

    }

    public static void setCheckLogin(Context context){
        // Check login status
        if(!isLoggedIn(context)){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(i);
        }

    }

    public static void logoutUser(Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After menu redirect user to Loing Activity
        Intent i = new Intent(context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        context.startActivity(i);
    }

    // Get Login State
    public static boolean isLoggedIn(Context context){
        return getSharedPreferences(context).getBoolean(IS_LOGIN, false);
    }

    public static boolean isDataIn(Context context){
        return getSharedPreferences(context).getBoolean(IS_DATA, false);
    }

}
