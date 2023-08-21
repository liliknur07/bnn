package com.example.bnn.kediri.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class Sessions {

    public final static String SHARED_KEY = "RukunTetangga-session";
    public final static String TOKEN = "token";

    private final SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Sessions(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE);
    }

    public void clearSession() {
        editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void logout(){
        editor = sharedPreferences.edit();
        editor.remove(TOKEN);
        editor.apply();
    }

    public String getData(String data){
        return sharedPreferences.getString(data, null);
    }

    public String getData(String data, String default_value){
        return sharedPreferences.getString(data, default_value);
    }

    public void setData(String data, String value){
        editor = sharedPreferences.edit();
        editor.putString(data, value);
        editor.apply();
    }

    public void createSession(String token){
        editor = sharedPreferences.edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public boolean isLogin() {
        return (sharedPreferences.getString(TOKEN, null) != null);
    }

}
