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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputEmail;
    private Button btnReset,btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        initialView();
        initialClick();
    }

    private void initialView() {
        inputEmail= (EditText) findViewById(R.id.activity_reset_password_edttext_email);
        btnReset= (Button) findViewById(R.id.activity_reset_password_btn_reset);
        btnBack= (Button) findViewById(R.id.activity_reset_password_btn_back);
        progressBar= (ProgressBar) findViewById(R.id.activity_reset_password_progressbar);
        auth=FirebaseAuth.getInstance();
    }

    private void initialClick() {
        btnReset.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_reset_password_btn_reset:
                //Password Reset
                sendMail();
                break;
            case R.id.activity_reset_password_btn_back:
                //Back
                backLogin();
                break;
        }
    }



    private void sendMail() {
        String email=inputEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(getApplicationContext(),getString(R.string.email_edittxt_empty),Toast.LENGTH_LONG).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),getString(R.string.email_edittxt_send_message),
                                    Toast.LENGTH_LONG).show();;
                        }else{
                            Toast.makeText(getApplicationContext(),getString(R.string.email_edittxt_no_send_message),
                                    Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void backLogin() {
        startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
