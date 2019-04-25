package com.app.myinfoapp.activity.dashbord;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.myinfoapp.R;
import com.app.myinfoapp.activity.jadwal.JadwalActivity;
import com.app.myinfoapp.activity.jadwal.JadwalPerkuliahanActivity;
import com.app.myinfoapp.activity.login.LoginActivity;
import com.app.myinfoapp.activity.register.RegisterActivity;
import com.app.myinfoapp.config.SessionManager;

import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    TextView userNameTxt,npmUserText,prodiText;
    RelativeLayout menuJadwalKuliah , menuJadwalHariIni;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        userNameTxt = findViewById(R.id.userName);
        npmUserText = findViewById(R.id.npmUser);
        prodiText = findViewById(R.id.prodi);
        menuJadwalKuliah = findViewById(R.id.menuJadwalKuliah);
        menuJadwalHariIni = findViewById(R.id.menuJadwal);
        setSupportActionBar(toolbar);

        HashMap<String, String> user = sessionManager.getUserDetails();

        String nama = user.get(SessionManager.NAMA);
        String npm = user.get(SessionManager.NPM);
        String prodi = user.get(SessionManager.PRODI);
        String jenjang = user.get(SessionManager.JENJANG);
        userNameTxt.setText(""+nama);
        npmUserText.setText(""+npm);
        prodiText.setText(""+jenjang+" "+prodi);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        menuJadwalKuliah.setOnClickListener(this);
        menuJadwalHariIni.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.menuJadwalKuliah:
                toJadwalActivity();
                break;

            case R.id.menuJadwal:
                toJadwalHariIniActivity();
                break;


        }


    }


    private void toJadwalActivity(){
        Intent intent = new Intent(DashboardActivity.this, JadwalPerkuliahanActivity.class);
        startActivity(intent);
    }

    private void toJadwalHariIniActivity(){
        Intent intent = new Intent(DashboardActivity.this, JadwalActivity.class);
        startActivity(intent);
    }
}
