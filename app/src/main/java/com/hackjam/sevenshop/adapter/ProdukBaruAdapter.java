package com.hackjam.sevenshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.ArrayList;

public class ProdukBaruAdapter extends RecyclerView.Adapter<ProdukBaruAdapter.ViewHolder> {

    private ArrayList<itemProduk> produks;
    private Context context;

    public ProdukBaruAdapter(Context context, ArrayList<itemProduk> produks){
        this.context = context;
        this.produks = produks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_produk_baru,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        itemProduk produk = produks.get(i);
        viewHolder.judul.setText(produk.getNama());
        viewHolder.harga.setText(produk.getHargaPotongan());
        viewHolder.progres.setText(produk.getJumlahJoin()+"/"+produk.getMaxJoin());
        viewHolder.progressBar.setProgress(produk.getJumlahJoin());
    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, harga, progres;
        private ProgressBar progressBar;
        private ImageView foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_produk_judul);
            harga = itemView.findViewById(R.id.tv_produk_harga);
            progres = itemView.findViewById(R.id.tv_produk_progres);
            progressBar = itemView.findViewById(R.id.p_produk_progres);
            foto = itemView.findViewById(R.id.iv_produk_gambar);
        }
    }
}
