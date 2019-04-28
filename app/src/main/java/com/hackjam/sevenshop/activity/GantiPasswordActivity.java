package com.hackjam.sevenshop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackjam.sevenshop.R;

public class GantiPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etPassLama, etPassBaru, etKonfirmasiPassBaru;
    private Button btnSimpan;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);

        mAuth = FirebaseAuth.getInstance();

        etPassLama = findViewById(R.id.et_ganti_password_passlama);
        etPassBaru = findViewById(R.id.et_ganti_password_passbaru);
        etKonfirmasiPassBaru = findViewById(R.id.et_ganti_password_konfirm);
        btnSimpan = findViewById(R.id.btn_ganti_password_simpan);
        btnSimpan.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Password...");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ganti_password_simpan :
                progressDialog.show();
                updatePassword();
                break;
        }
    }
    private void updatePassword(){
        if (inputValidated()){
            final FirebaseUser user = mAuth.getCurrentUser();
            final String passbaru = etPassBaru.getText().toString();
            String passlama = etPassLama.getText().toString();
            String email = mAuth.getCurrentUser().getEmail();
            AuthCredential cred = EmailAuthProvider.getCredential(email,passlama);

            user.reauthenticate(cred).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()){
                        user.updatePassword(passbaru).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(GantiPasswordActivity.this, "password updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(GantiPasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else{
                                    Toast.makeText(GantiPasswordActivity.this, "Error Auth Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else  {
                        Toast.makeText(GantiPasswordActivity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private boolean inputValidated(){
        boolean ret = true;
        if (etPassLama.getText().toString().isEmpty()){
            ret = false;
            etPassLama.setError("this is required");
        }if (etPassBaru.getText().toString().isEmpty()){
            ret = false;
            etPassBaru.setError("this is required");
        }if (etKonfirmasiPassBaru.getText().toString().isEmpty()){
            ret = false;
            etKonfirmasiPassBaru.setError("this is required");
        } else if (!etKonfirmasiPassBaru.getText().toString().equals(etPassBaru.getText().toString())){
            ret = false;
            etKonfirmasiPassBaru.setError("password does not match");
        }
        return ret;
    }
}
