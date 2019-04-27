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

import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.itemProduk;

import java.util.List;

public class ProdukBaruAdapter extends RecyclerView.Adapter<ProdukBaruAdapter.ProdukBaruViewHolder>{

    private Context context;
    private List<itemProduk> produkitem;

    public ProdukBaruAdapter(Context context,List<itemProduk> produkitem){
        this.context=context;
        this.produkitem=produkitem;
    }

    @NonNull
    @Override
    public ProdukBaruViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_produk_baru,null);
        ProdukBaruViewHolder holder = new ProdukBaruViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukBaruViewHolder produkBaruViewHolder, int position) {
        itemProduk produk = produkitem.get(position);
        produkBaruViewHolder.hargamurah.setText(produk.getHargamurah());
        produkBaruViewHolder.hargalama.setText(produk.getHargalama());
        produkBaruViewHolder.namaproduk.setText(produk.getNamabarang());
        produkBaruViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(produk.getmImageResource()));
    }

    @Override
    public int getItemCount() {
        return produkitem.size();
    }

    class ProdukBaruViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView namaproduk , hargamurah,hargalama;

        public ProdukBaruViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.img_produk_item_produk_baru);
            namaproduk = view.findViewById(R.id.brg_produk_item_produk_baru);
            hargalama = view.findViewById(R.id.hrglama_produk_item_produk_baru);
            hargamurah = view.findViewById(R.id.hrgbaru_produk__item_produk_baru);
        }

    }
}
