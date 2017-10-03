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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputEmail,inputPassword;
    private Button btnLogin,btnsignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase aut instance
        auth=FirebaseAuth.getInstance();

        initialView();
        initialClick();
    }

    private void initialView() {
        btnsignUp= (Button) findViewById(R.id.activity_signup_btn_register);
        btnLogin= (Button) findViewById(R.id.activity_signup_btn_login);
        inputEmail= (EditText) findViewById(R.id.activity_signup_edittxt_email);
        inputPassword= (EditText) findViewById(R.id.activity_signup_edittxt_password);
        progressBar= (ProgressBar) findViewById(R.id.activity_signup_progressbar);
    }

    private void initialClick() {
        btnsignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_signup_btn_register:
                //Activity Register
                UserRegister();
                break;

            case R.id.activity_signup_btn_login:
                //Activity Sign İn
                userLogin();
                break;

        }
    }

    public void UserRegister(){
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),getString(R.string.email_edittxt_message),Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),getString(R.string.password_edittxt_message),Toast.LENGTH_LONG).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(getApplicationContext(),getString(R.string.password_edittxt_lenght_message),Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Create User
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
             // Toast.makeText(SignupActivity.this,R.string.register_btn_succses_message,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

                //Kullanıcı girişi başarısız ise mesaj yazdıracak.Başarılı ise MainActivity class'na yönlendirecek.
                if(!task.isSuccessful()){
                    Toast.makeText(SignupActivity.this,getString(R.string.register_btn_un_succses_message),Toast.LENGTH_LONG).show();
                }else{
                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                    finish();
                }
            }
        });


    }


    public void userLogin()
    {
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
