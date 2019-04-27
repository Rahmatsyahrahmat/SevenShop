package com.hackjam.sevenshop.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.activity.EditProfileActivity;
import com.hackjam.sevenshop.activity.GantiPasswordActivity;
import com.hackjam.sevenshop.activity.LoginActivity;
import com.hackjam.sevenshop.model.User;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private CircleImageView iv_foto;
    private TextView nama, editProfil, editPassword, syaratKetentuan, keluar;
    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        iv_foto = rootView.findViewById(R.id.iv_profile_foto);
        nama = rootView.findViewById(R.id.tv_profile_nama);
        editProfil = rootView.findViewById(R.id.tv_profile_edit);
        editPassword = rootView.findViewById(R.id.tv_profile_ganti_pass);
        syaratKetentuan = rootView.findViewById(R.id.tv_profile_syarat_dan_ketentuan);
        keluar = rootView.findViewById(R.id.tv_profile_keluar);
        progress = new ProgressDialog(getContext());
        progress.setMessage("Loading");
        progress.show();
        firebaseAuth = FirebaseAuth.getInstance();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("User").child(firebaseAuth.getCurrentUser().getUid()+".jpg");
        try {
            final File localFile = File.createTempFile("Images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    if(localFile!=null)iv_foto.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                    progress.dismiss();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            progress.dismiss();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sNama = dataSnapshot.child("nama").getValue().toString();
                nama.setText(sNama);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editProfil.setOnClickListener(this);
        editPassword.setOnClickListener(this);
        keluar.setOnClickListener(this);
        syaratKetentuan.setOnClickListener(this);

        return rootView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_profile_edit :
                startActivity(new Intent(getContext(), EditProfileActivity.class));
                break;
            case R.id.tv_profile_ganti_pass :
                startActivity(new Intent(getContext(), GantiPasswordActivity.class));
                break;
            case R.id.tv_profile_syarat_dan_ketentuan :
                break;
            case R.id.tv_profile_keluar :
                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }
}
