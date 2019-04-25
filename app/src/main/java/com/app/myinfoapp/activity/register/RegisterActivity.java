package com.app.myinfoapp.activity.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.myinfoapp.R;
import com.app.myinfoapp.activity.login.LoginActivity;
import com.app.myinfoapp.api.APIRequest;
import com.app.myinfoapp.config.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    EditText etNpm;
    Button btnCheckNpm;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){

        etNpm = findViewById(R.id.editTextNpm);
        btnCheckNpm = findViewById(R.id.btnCheckNPM);
        progressDialog = new ProgressDialog(this);

        //init event

        btnCheckNpm.setOnClickListener(this);

    }


    private void checkUserExisting(){
        progressDialog.setMessage("sedang mencari data...");
        progressDialog.show();
        Map<String,String> params = new HashMap<String, String>();
        params.put("NPM",etNpm.getText().toString());

        try {

            APIRequest request = new APIRequest(RegisterActivity.this);
            request.POST(params, "URL_ENCODED", Config.CHECK_USER_EXISTING, new APIRequest.VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    progressDialog.dismiss();
                    try {
                        if (result.matches("VOLLEY_NETWORK_ERROR")) {
                            Toast.makeText(RegisterActivity.this, "NETWORK PROBLEM", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject response  = new JSONObject(result);
                                boolean error = response.getBoolean("error");
                                if(error){

                                    Toast.makeText(getApplicationContext(),""+response.getString("error_msg"),Toast.LENGTH_LONG).show();

                                }else{

                                    JSONObject users  = response.getJSONObject("user");
                                    Intent intent = new Intent(RegisterActivity.this, RegisterDataActivity.class);
                                    intent.putExtra("npm",users.getString("NPM"));
                                    intent.putExtra("nama",users.getString("nama"));
                                    intent.putExtra("email",users.getString("email"));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);

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

    private void toRegisterData(){
        Intent intent = new Intent(RegisterActivity.this, RegisterDataActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnCheckNPM:
                checkUserExisting();
                break;
        }

    }
}
