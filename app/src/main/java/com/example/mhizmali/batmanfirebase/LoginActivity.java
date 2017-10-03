package com.example.mhizmali.batmanfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail,inputPassword;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private Button btnSignup,btnLogin,btnResetPass;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Gökhan Hocaya sor
      if (auth.getCurrentUser()!=null) {
           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
        }


        setContentView(R.layout.activity_login);


        initialView();
        initialClick();
    }
    private void initialView() {

        inputEmail= (EditText) findViewById(R.id.activity_login_edittxt_email);
        inputPassword= (EditText) findViewById(R.id.activity_login_edittxt_password);
        btnLogin= (Button) findViewById(R.id.activity_login_btn_login);
        btnResetPass= (Button) findViewById(R.id.activity_login_btn_reset_password);
        btnSignup= (Button) findViewById(R.id.activity_login_btn_sign_up);
        progressBar= (ProgressBar) findViewById(R.id.activity_login_progress_bar);
        auth=FirebaseAuth.getInstance();


    }

    private void initialClick() {
        btnLogin.setOnClickListener(this);
        btnResetPass.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_login_btn_login:
                //Login
                userLogin();
                break;
            case R.id.activity_login_btn_reset_password:
                //Reset Password
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                break;
            case R.id.activity_login_btn_sign_up:
                //Sign up
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
                break;
        }

    }

    private void userLogin() {
        String email=inputEmail.getText().toString();
        final String password=inputPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),getString(R.string.email_edittxt_message),Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),getString(R.string.password_edittxt_message),Toast.LENGTH_LONG).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        //authenticate user //Kuulanıcıyı Doğrulama
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(!task.isSuccessful()){
                    if(password.length()<6){
                        inputPassword.setError(getString(R.string.password_edittxt_lenght_message));
                    }else{
                        Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                    }
                }else{
                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
