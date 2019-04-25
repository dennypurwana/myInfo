package com.app.myinfoapp.activity.jadwal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.myinfoapp.R;
import com.app.myinfoapp.adapter.jadwal.JadwalKuliahAdapter;
import com.app.myinfoapp.api.APIRequest;
import com.app.myinfoapp.config.Config;
import com.app.myinfoapp.config.SessionManager;
import com.app.myinfoapp.model.Jadwal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class JadwalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private JadwalKuliahAdapter adapter;
    private ArrayList<Jadwal> arrayList;
    SessionManager sessionManager;
    String hari ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionManager = new SessionManager(getApplicationContext());
        getJadwal();

    }

    void initView(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new JadwalKuliahAdapter(JadwalActivity.this,arrayList, new JadwalKuliahAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Jadwal jadwal) {

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void getJadwal(){
        try {

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case Calendar.SUNDAY:
                    hari = "minggu";
                    break;

                case Calendar.MONDAY:
                    hari = "senin";
                    break;

                case Calendar.TUESDAY:
                    hari = "selasa";
                    break;

                case Calendar.WEDNESDAY:
                    hari = "rabu";
                    break;

                case Calendar.THURSDAY:
                    hari = "kamis";
                    break;

                case Calendar.FRIDAY:
                    hari = "jumat";
                    break;

                case Calendar.SATURDAY:
                    hari = "sabtu";
                    break;
            }

            APIRequest request = new APIRequest(JadwalActivity.this);
            String url = Config.GET_JADWAL_BY_HARI.replace("_PARAM1_",hari).replace("_PARAM2_",sessionManager.getIdKelas());
            Log.d("url :", url);
            request.GET(url, new APIRequest.VolleyCallback() {
                @Override
                public void onSuccessResponse(String result) {

                    try {
                        if (result.matches("VOLLEY_NETWORK_ERROR")) {
                            Toast.makeText(JadwalActivity.this, "NETWORK PROBLEM", Toast.LENGTH_SHORT).show();
                        } else {
                            try {

                                JSONObject response  = new JSONObject(result);
                                boolean error = response.getBoolean("error");

                                if(error){

                                    Toast.makeText(getApplicationContext(),""+response.getString("error_msg"),Toast.LENGTH_LONG).show();

                                }else{

                                    try {
                                        arrayList=new ArrayList<>();
                                        JSONArray vResponse=response.getJSONArray("jadwal");
                                        if(vResponse.length()>0) {
                                            for (int i = 0; i < vResponse.length(); i++) {
                                                try {

                                                    JSONObject jsonObject = vResponse.getJSONObject(i);
                                                    Jadwal jadwal = new Jadwal();
                                                    jadwal.setMataKuliah(jsonObject.getString("MATA_KULIAH"));
                                                    jadwal.setIdJadwal(jsonObject.getString("ID_JADWAL"));
                                                    jadwal.setProdi(jsonObject.getString("PRODI"));
                                                    jadwal.setRuang(jsonObject.getString("RUANG"));
                                                    jadwal.setHari(jsonObject.getString("HARI"));
                                                    jadwal.setJam(jsonObject.getString("JAM"));
                                                    jadwal.setSemester(jsonObject.getString("SEMESTER"));
                                                    jadwal.setIdKelas(jsonObject.getString("KELAS"));
                                                    jadwal.setSks(jsonObject.getString("SKS"));
                                                    arrayList.add(jadwal);

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            initView();
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            recyclerView.setVisibility(View.GONE);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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


}
