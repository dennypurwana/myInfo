package com.app.myinfoapp.activity.register;

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
import android.widget.Toast;

import com.app.myinfoapp.R;
import com.app.myinfoapp.activity.login.LoginActivity;
import com.app.myinfoapp.api.APIRequest;
import com.app.myinfoapp.config.Config;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterDataActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText etNpm,
             etNama,
             etEmail,
             etPassword,
             etConfirmPassword;

    Button btnRegister;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_data);
        initView();
    }


    private void initView(){

        etNpm = findViewById(R.id.editTextNpm);
        etNama = findViewById(R.id.editTextNama);
        etEmail = findViewById(R.id.editTextEmail);
        etPassword = findViewById(R.id.editTextPassword);
        etConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);

        //init listener
        btnRegister.setOnClickListener(this);

        if (getIntent().getStringExtra("npm")!="" && getIntent().getStringExtra("nama") != "" && getIntent().getStringExtra("email") != "" ){
            etNpm.setText(getIntent().getStringExtra("npm"));
            etNama.setText(getIntent().getStringExtra("nama"));
            etEmail.setText(getIntent().getStringExtra("email"));
        }

    }

    private void validate(){
        if(etNpm.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(RegisterDataActivity.this, "NPM tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else if (etNama.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(RegisterDataActivity.this, "Nama tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else if (etEmail.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(RegisterDataActivity.this, "Email tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else if (etPassword.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(RegisterDataActivity.this, "Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else if (etConfirmPassword.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(RegisterDataActivity.this, "Konfirmasi Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
        else if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
            Toast.makeText(RegisterDataActivity.this, "Password tidak sesuai.", Toast.LENGTH_SHORT).show();
        } else{
          register();
        }
    }

    private void register(){
        progressDialog.setMessage("sedang mendaftarkan...");
        progressDialog.show();
        Map<String,String> params = new HashMap<String, String>();
        params.put("npm",etNpm.getText().toString());
        params.put("nama",etNama.getText().toString());
        params.put("email",etEmail.getText().toString());
        params.put("password",etPassword.getText().toString());
        try {

            APIRequest request = new APIRequest(RegisterDataActivity.this);
            request.POST(params, "URL_ENCODED", Config.REGISTER, new APIRequest.VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {
                    progressDialog.dismiss();
                    try {
                        if (result.matches("VOLLEY_NETWORK_ERROR")) {
                            Toast.makeText(RegisterDataActivity.this, "NETWORK PROBLEM", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONObject response  = new JSONObject(result);
                                boolean error = response.getBoolean("error");
                                if(error){

                                    Toast.makeText(getApplicationContext(),""+response.getString("error_msg"),Toast.LENGTH_LONG).show();

                                }else{

                                    Toast.makeText(getApplicationContext(),""+response.getString("message"),Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterDataActivity.this, LoginActivity.class);
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

    private void toLogin(){

        Intent intent = new Intent(RegisterDataActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnRegister:
                validate();
                break;


        }
    }
}
