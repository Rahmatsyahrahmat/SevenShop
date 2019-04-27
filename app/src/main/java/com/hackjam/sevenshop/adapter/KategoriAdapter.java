package com.hackjam.sevenshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.Kategori;

import java.util.ArrayList;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Kategori> kategoris;

    public KategoriAdapter(Context context, ArrayList<Kategori> kategoris){
        this.context = context;
        this.kategoris = kategoris;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_kategori,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView.setText(kategoris.get(i).getNama());
        viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(kategoris.get(i).getIcon()));
    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_kategori_gambar);
            textView = itemView.findViewById(R.id.tv_kategori_judul);
        }
    }
}
