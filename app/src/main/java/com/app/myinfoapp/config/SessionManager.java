package com.app.myinfoapp.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.app.myinfoapp.activity.dashbord.DashboardActivity;
import com.app.myinfoapp.activity.login.LoginActivity;

import java.util.HashMap;

public class SessionManager {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "MY_INFO";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String NPM = "NPM";
    public static final String NAMA = "NAMA";
    public static final String ID_KELAS = "ID_KELAS";
    public static final String PRODI = "PRODI";
    public static final String JENJANG = "JENJANG";


    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void logoutSession(){
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();

    }

    public void createLoginSession(String nama, String npm, String idKelas, String prodi, String jenjang ){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(NAMA, nama);

        // Storing email in pref
        editor.putString(NPM, npm);

        editor.putString(ID_KELAS, idKelas);

        editor.putString(PRODI, prodi);

        editor.putString(JENJANG, jenjang);

        // commit changes
        editor.commit();
//
//        Intent i = new Intent(mContext, DashboardActivity.class);
//        // Closing all the Activities
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        // Add new Flag to start new Activity
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        // Staring Login Activity
//        mContext.startActivity(i);
    }



    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(NAMA, pref.getString(NAMA, null));

        // user email id
        user.put(NPM, pref.getString(NPM, null));

        user.put(ID_KELAS, pref.getString(ID_KELAS, null));

        user.put(PRODI, pref.getString(PRODI, null));

        user.put(JENJANG, pref.getString(JENJANG, null));

        // return user
        return user;
    }

    public String getIdKelas(){
        return  pref.getString(ID_KELAS, null);
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(mContext, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        mContext.startActivity(i);
    }



    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mContext, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);
        }
        else{

            Intent i = new Intent(mContext, DashboardActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            mContext.startActivity(i);

        }

    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}