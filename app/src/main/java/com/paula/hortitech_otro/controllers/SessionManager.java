package com.paula.hortitech_otro.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.paula.hortitech_otro.view.MainActivity;

public class SessionManager {
    private static final String PREF_NAME = "HORTITECH_PREFS";
    private static final String KEY_USER_TOKEN = "USER_TOKEN";
    private static final String KEY_USER_ID = "USER_ID";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_USER_TOKEN, "Bearer " + token);
        editor.apply();
    }

    public String getAuthToken() {
        return prefs.getString(KEY_USER_TOKEN, null);
    }

    public void saveUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
