package com.hackjam.sevenshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.activity.DetailProduk;
import com.hackjam.sevenshop.model.itemProduk;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProdukBaruAdapter extends RecyclerView.Adapter<ProdukBaruAdapter.ViewHolder> implements Filterable {

    private ArrayList<itemProduk> produks, dataSet;
    private Context context;

    public ProdukBaruAdapter(Context context, ArrayList<itemProduk> produks){
        this.context = context;
        this.produks = produks;
        dataSet = produks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_produk_baru,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final itemProduk produk = produks.get(i);
        viewHolder.judul.setText(produk.getNama());
        viewHolder.harga.setText(produk.getHargaPotongan());
        viewHolder.progres.setText(produk.getJumlahJoin()+"/"+produk.getMaxJoin());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Produk").child(produk.getId()+"_1.jpg");
        try {
            final File localFile = File.createTempFile("Images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(localFile!=null)viewHolder.foto.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailProduk.class);
                intent.putExtra("Produk",produk);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return produks.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    produks = dataSet;
                } else {
                    ArrayList<itemProduk> filteredList = new ArrayList<>();
                    for (itemProduk row : dataSet) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    produks = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataSet;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataSet = (ArrayList<itemProduk>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, harga, progres;
        private RoundCornerProgressBar progressBar;
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
