package com.example.mhizmali.batmanfirebase.fragments;


import android.app.FragmentTransaction;
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

import com.example.mhizmali.batmanfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMailFragment extends Fragment implements View.OnClickListener {

    private View view;
    EditText inputEmail;
    Button btnChange;
    ProgressBar progressBar;


    public ChangeMailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_change_mail, container, false);
        initialView();
        initalClick();
        return view;
    }



    private void initialView() {
        inputEmail=view.findViewById(R.id.fragment_edittxt_cahnage_email);
        btnChange=view.findViewById(R.id.fragment_button_change_mail);
        progressBar=view.findViewById(R.id.fragment_change_email_progress_bar);
    }
    private void initalClick() {
        btnChange.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_button_change_mail:
                changeEmal();
                break;
        }
    }

    private void changeEmal() {
        String email=inputEmail.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),getString(R.string.email_edittxt_empty),Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), getString(R.string.email_edittxt_update_message),
                                    Toast.LENGTH_LONG).show();
                            HomepageFragment homepageFragment=new HomepageFragment();
                            FragmentManager manager=getFragmentManager();
                            android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
                            transaction.replace(R.id.content_main_container,homepageFragment);
                            transaction.commit();

                        } else {
                            Toast.makeText(getActivity(),getString(R.string.email_edittxt_no_update_message),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }
}
