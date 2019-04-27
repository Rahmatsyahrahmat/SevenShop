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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackjam.sevenshop.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btnRegister, btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        btnRegister = findViewById(R.id.btn_login_register);
        btnLogin = findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        progress.setMessage("logging in, please wait");

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_register :
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_login :
                progress.show();
                SignIn();
                break;
        }
    }
    private void SignIn(){
        if (inputValidated()){
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else{
                        progress.dismiss();
                        String err = task.getException().getMessage();
                        if (err.contains("password")){
                            etPassword.setError(err);
                        } else {
                            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
    private boolean inputValidated(){
        boolean res = true;
        if (etEmail.getText().toString().isEmpty()){
            res = false;
            etEmail.setError("This is required");
        }if (etPassword.getText().toString().isEmpty()){
            res = false;
            etPassword.setError("This is required");
        }
        return res;
    }
}
