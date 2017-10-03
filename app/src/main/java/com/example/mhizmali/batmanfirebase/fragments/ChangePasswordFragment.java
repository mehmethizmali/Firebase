package com.example.mhizmali.batmanfirebase.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mhizmali.batmanfirebase.MainActivity;
import com.example.mhizmali.batmanfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private View view;
    EditText inputPassword,inputPasswordAgain;
    Button btnChange;
    ProgressBar progressBar;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_change_password, container, false);
        initialView();
        initialClick();
        return view;
    }



    private void initialView() {
        inputPassword=view.findViewById(R.id.fragment_edittxt_change_password);
        inputPasswordAgain=view.findViewById(R.id.fragment_edittxt_change_password_again);
        btnChange=view.findViewById(R.id.fragment_button_change_password);
        progressBar=view.findViewById(R.id.fragment_change_pass_progress_bar);

    }
    private void initialClick() {
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_button_change_password:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        String password=inputPassword.getText().toString().trim();
        String passwordAgain=inputPasswordAgain.getText().toString().trim();

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),getString(R.string.password_edittxt_message),Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passwordAgain)){
            Toast.makeText(getActivity(),getString(R.string.password_edittxt_message_again),Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(passwordAgain)){
            Toast.makeText(getActivity(),"Şifreler Aynı Değil",Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(passwordAgain)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),getString(R.string.password_update_message),
                                    Toast.LENGTH_SHORT).show();
                            HomepageFragment homepageFragment=new HomepageFragment();
                            FragmentManager manager=getFragmentManager();
                            android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
                            transaction.replace(R.id.content_main_container,homepageFragment);
                            transaction.commit();
                        } else {
                            Toast.makeText(getActivity(), getString(R.string.password_no_update_message),
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
