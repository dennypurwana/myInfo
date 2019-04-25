package com.app.myinfoapp.activity.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.myinfoapp.R;
import com.app.myinfoapp.activity.login.LoginActivity;
import com.app.myinfoapp.config.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SplashscreenActivity extends AppCompatActivity {

    Handler handler;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        handler=new Handler();
        sessionManager = new SessionManager(getApplicationContext());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    sessionManager.checkLogin();
            }
        },3000);
    }

}
