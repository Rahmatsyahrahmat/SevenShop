package com.hackjam.sevenshop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.User;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, IPickResult {

    private ImageView ivFoto;
    private EditText etNama, etAlamat, etKecamatan, etKota, etProvinsi, etNoTel;
    private Button btnSimpan;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Uri uri;
    private ProgressDialog progress, progress2;
    private String email;
    private String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ivFoto = findViewById(R.id.iv_editprofile_foto);
        etNama = findViewById(R.id.et_editprofile_nama);
        etAlamat = findViewById(R.id.et_editprofile_alamat);
        etKecamatan = findViewById(R.id.et_editprofile_kecamatan);
        etKota = findViewById(R.id.et_editprofile_kota);
        etProvinsi = findViewById(R.id.et_editprofile_provinsi);
        etNoTel = findViewById(R.id.et_editprofile_no_telp);
        btnSimpan = findViewById(R.id.btn_editprofile_simpan);
        progress = new ProgressDialog(this);
        progress2 = new ProgressDialog(this);
        progress.setMessage("saving changes, please wait");
        progress2.setMessage("fetching data");

        btnSimpan.setOnClickListener(this);
        ivFoto.setOnClickListener(this);
        FirebaseApp.initializeApp(this);
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        fetchData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_editprofile_simpan :
                progress.show();
                update();
                break;
            case R.id.iv_editprofile_foto :
                pickFoto();
                break;
        }
    }
    private void fetchData(){
        progress2.show();
        StorageReference storageReference = mStorage.child("User").child(uid+".jpg");
        try {
            final File localFile = File.createTempFile("Images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    ivFoto.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                    progress2.dismiss();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDatabase.child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                etAlamat.setText(dataSnapshot.child("alamat").getValue().toString());
                etKecamatan.setText(dataSnapshot.child("kecamatan").getValue().toString());
                etKota.setText(dataSnapshot.child("kota").getValue().toString());
                etProvinsi.setText(dataSnapshot.child("provinsi").getValue().toString());
                etNama.setText(dataSnapshot.child("nama").getValue().toString());
                etNoTel.setText(dataSnapshot.child("no_telp").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this,"Failed to read : "+databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void update(){
        String uid = mAuth.getCurrentUser().getUid();
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String kecamatan = etKecamatan.getText().toString();
        String kota = etKota.getText().toString();
        String provinsi = etProvinsi.getText().toString();
        String no_telp = etNoTel.getText().toString();

        User user = new User(uid, nama, email,alamat, kecamatan, kota, provinsi, no_telp);
        mDatabase.child("User").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progress.dismiss();
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        uploadFoto(uid);
    }
    private void uploadFoto(String uid) {
        StorageReference ref = mStorage.child("User/" + uid + ".jpg");
        if(uri!=null) ref.putFile(uri);
    }
    private void pickFoto(){
        PickImageDialog.build(new PickSetup()).show(this);
    }
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            uri = r.getUri();
            ivFoto.setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private boolean inputValidated(){
        boolean res = true;
        if (etNama.getText().toString().isEmpty()){
            res = false;
            etNama.setError("This is required");
        }if (etAlamat.getText().toString().isEmpty()){
            res = false;
            etAlamat.setError("This is required");
        }if (etKecamatan.getText().toString().isEmpty()){
            res = false;
            etKecamatan.setError("This is required");
        }if (etKota.getText().toString().isEmpty()){
            res = false;
            etKota.setError("This is required");
        }if (etProvinsi.getText().toString().isEmpty()){
            res = false;
            etProvinsi.setError("This is required");
        }if (etNoTel.getText().toString().isEmpty()){
            res = false;
            etNoTel.setError("This is required");
        }
        return res;
    }
}
