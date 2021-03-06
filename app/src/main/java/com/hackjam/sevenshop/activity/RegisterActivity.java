package com.hackjam.sevenshop.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hackjam.sevenshop.R;
import com.hackjam.sevenshop.model.User;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, IPickResult {

    private ImageView ivFoto;
    private EditText etNama, etEmail, etPassword, etConPassword, etAlamat, etKecamatan, etKota, etProvinsi, etNoTel;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Uri uri;

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ivFoto = findViewById(R.id.iv_register_foto);
        etNama = findViewById(R.id.et_register_nama);
        etEmail = findViewById(R.id.et_register_email);
        etPassword = findViewById(R.id.et_register_password);
        etConPassword = findViewById(R.id.et_register_con_password);
        etAlamat = findViewById(R.id.et_register_alamat);
        etKecamatan = findViewById(R.id.et_register_kecamatan);
        etKota = findViewById(R.id.et_register_kota);
        etProvinsi = findViewById(R.id.et_register_provinsi);
        etNoTel = findViewById(R.id.et_register_no_telp);
        btnRegister = findViewById(R.id.btn_register_register);
        progress = new ProgressDialog(this);
        progress.setMessage("creating account, please wait");

        btnRegister.setOnClickListener(this);
        ivFoto.setOnClickListener(this);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_register :
                progress.show();
                register();
                break;
            case R.id.iv_register_foto :
                pickFoto();
                break;
        }
    }

    private void register(){
        if (inputValidated()){
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        uploadDataToDatabase();
                        Toast.makeText(RegisterActivity.this,"User Created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        progress.dismiss();
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }
            });
        }
    }
    private void uploadDataToDatabase(){
        String uid = mAuth.getCurrentUser().getUid();
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String alamat = etAlamat.getText().toString();
        String kecamatan = etKecamatan.getText().toString();
        String kota = etKota.getText().toString();
        String provinsi = etProvinsi.getText().toString();
        String no_telp = etNoTel.getText().toString();

        User user = new User(uid, nama, email,alamat, kecamatan, kota, provinsi, no_telp);
        mDatabase.child("User").child(uid).setValue(user);
        uploadFoto(uid);
    }
    private void uploadFoto(String uid) {
        StorageReference ref = mStorage.child("User/" + uid + ".jpg");
        ref.putFile(uri);
    }
    private void pickFoto(){
        PickImageDialog.build(new PickSetup()).show(this);
    }

    private boolean inputValidated(){
        boolean res = true;
        if (etNama.getText().toString().isEmpty()){
            res = false;
            etNama.setError("This is required");
        }if (etEmail.getText().toString().isEmpty()){
            res = false;
            etEmail.setError("This is required");
        }if (etPassword.getText().toString().isEmpty()){
            res = false;
            etPassword.setError("This is required");
        }if (etConPassword.getText().toString().isEmpty()){
            res = false;
            etConPassword.setError("This is required");
        } else if (!(etConPassword.getText().toString().equals(etPassword.getText().toString()))){
            res = false;
            etConPassword.setError("Password does not match");
        } if (etAlamat.getText().toString().isEmpty()){
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

    @Override
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
}
