package com.app.myinfoapp.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.myinfoapp.R;
import com.app.myinfoapp.activity.dashbord.DashboardActivity;
import com.app.myinfoapp.activity.register.RegisterActivity;
import com.app.myinfoapp.activity.register.RegisterDataActivity;
import com.app.myinfoapp.activity.splashscreen.SplashscreenActivity;
import com.app.myinfoapp.api.APIRequest;
import com.app.myinfoapp.app.MyApplication;
import com.app.myinfoapp.config.Config;
import com.app.myinfoapp.config.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNpm,
             etPassword;
    Button btnLogin;
    RelativeLayout linkToDaftar;
    ProgressDialog progressDialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(getApplicationContext());
        initView();
    }

    private void initView(){

        etNpm = findViewById(R.id.editTextNpm);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        linkToDaftar = findViewById(R.id.linkToDaftar);
        progressDialog = new ProgressDialog(this);
        //init listener
        btnLogin.setOnClickListener(this);
        linkToDaftar.setOnClickListener(this);

    }

    private void validate(){

        if (etNpm.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(LoginActivity.this, "NPM tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(LoginActivity.this, "Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else{
            login();
        }

    }
    private void login() {

                progressDialog.setMessage("login proses...");
                progressDialog.show();
                Map<String,String> params = new HashMap<String, String>();
                params.put("npm",etNpm.getText().toString());
                params.put("password",etPassword.getText().toString());

        try {

            APIRequest request = new APIRequest(LoginActivity.this);
            request.POST(params, "URL_ENCODED", Config.LOGIN, new APIRequest.VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    progressDialog.dismiss();
                    try {
                        if (result.matches("VOLLEY_NETWORK_ERROR")) {
                            Toast.makeText(LoginActivity.this, "NETWORK PROBLEM", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                JSONObject response  = new JSONObject(result);
                                boolean error = response.getBoolean("error");

                                if(error){

                                    Toast.makeText(getApplicationContext(),""+response.getString("error_msg"),Toast.LENGTH_LONG).show();

                                }else{

                                    JSONObject userObj = response.getJSONObject("user");
                                    String npm = userObj.getString("NPM");
                                    String nama = userObj.getString("NAMA");
                                    String prodi =  userObj.getString("PRODI");
                                    String idKelas = userObj.getString("ID_KELAS");
                                    String jenjang = userObj.getString("JENJANG");
                                    sessionManager.createLoginSession(nama,npm,idKelas,prodi,jenjang);
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();


                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void toRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                validate();
                break;
            case R.id.linkToDaftar:
                toRegister();
                break;

        }


    }
}
