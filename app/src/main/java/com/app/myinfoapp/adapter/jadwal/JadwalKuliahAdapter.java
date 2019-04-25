package com.app.myinfoapp.adapter.jadwal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.myinfoapp.R;
import com.app.myinfoapp.model.Jadwal;

import java.util.ArrayList;


public class JadwalKuliahAdapter extends RecyclerView.Adapter<JadwalKuliahAdapter.JadwalKuliahViewHolder> {


    private ArrayList<Jadwal> dataList;
    private OnItemClickListener listener;
    Context mContext;

    public JadwalKuliahAdapter(Context context, ArrayList<Jadwal> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener=listener;
        this.mContext = context;
    }

    @Override
    public JadwalKuliahViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_jadwal_kuliah, parent, false);
        return new JadwalKuliahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JadwalKuliahViewHolder holder, final int position) {
        holder.matkulName.setText(dataList.get(position).getMataKuliah());
        holder.jadwal.setText(dataList.get(position).getHari()+","+dataList.get(position).getJam());
        holder.sks.setText(dataList.get(position).getSks()+" SKS");
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class JadwalKuliahViewHolder extends RecyclerView.ViewHolder{
        private TextView matkulName, jadwal, sks;
        private RelativeLayout card;
        public JadwalKuliahViewHolder(View itemView) {
            super(itemView);
            matkulName = (TextView) itemView.findViewById(R.id.matkulName);
            jadwal = (TextView) itemView.findViewById(R.id.jadwal);
            sks = (TextView) itemView.findViewById(R.id.sks);
            card=(RelativeLayout) itemView.findViewById(R.id.card);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Jadwal jadwal);
    }
}
