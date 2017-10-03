package com.example.mhizmali.batmanfirebase;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mhizmali.batmanfirebase.fragments.ChangeMailFragment;
import com.example.mhizmali.batmanfirebase.fragments.ChangePasswordFragment;
import com.example.mhizmali.batmanfirebase.fragments.GalleryFragment;
import com.example.mhizmali.batmanfirebase.fragments.HomepageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_change_mail;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_change_password;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_delete_accou;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_gallery;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_homepage;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_logout;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_settings;
import static com.example.mhizmali.batmanfirebase.R.id.activity_main_drawer_share;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth auth;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    private FragmentManager manager;
    private android.support.v4.app.FragmentTransaction transaction;
    private HomepageFragment homepageFragment=new HomepageFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialView();

    }



    private void initialView() {
        auth=FirebaseAuth.getInstance();
        frameLayout= (FrameLayout) findViewById(R.id.content_main_container);
        progressBar= (ProgressBar) findViewById(R.id.content_main_progressbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager();
        transaction.add(R.id.content_main_container,homepageFragment);
        transaction.commit();
    }

    private void fragmentManager() {
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id){
            case activity_main_drawer_homepage: {
                //homepage
                fragmentManager();
                transaction.replace(R.id.content_main_container,homepageFragment);
                transaction.commit();
                break;
            }
            case activity_main_drawer_gallery:
                //gallery
                GalleryFragment galleryFragment=new GalleryFragment();
                fragmentManager();
                transaction.replace(R.id.content_main_container, galleryFragment);
                transaction.commit();
                break;

            case activity_main_drawer_share:
                //share
                MessagesFragment messagesFragment=new MessagesFragment();
                fragmentManager();
                transaction.replace(R.id.content_main_container, messagesFragment);
                transaction.commit();
                break;
            case activity_main_drawer_settings:
                //settings
                break;
            case activity_main_drawer_change_mail: {
                //change mail
                ChangeMailFragment changeMailFragment=new ChangeMailFragment();
                fragmentManager();
                transaction.replace(R.id.content_main_container, changeMailFragment);
                transaction.commit();
                break;
            }
            case activity_main_drawer_change_password: {
                //change password
                ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                fragmentManager();
                transaction.replace(R.id.content_main_container, changePasswordFragment);
                transaction.commit();
                break;
            }
            case activity_main_drawer_delete_accou:
                //delete account
                userDelete();
                break;

            case activity_main_drawer_logout:
                //logout
                logout();
                break;

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void userDelete() {
        AlertDialog.Builder userDeleteMessage;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userDeleteMessage = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            userDeleteMessage = new AlertDialog.Builder(this);
        }
        userDeleteMessage.setTitle(getString(R.string.activity_main_alertdialog_account_delete_title))
                .setMessage(getString(R.string.activity_main_alertdialog_account_delelte_message))
                .setPositiveButton(R.string.activity_main_alertdialog_delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.GONE);

                                            if (task.isSuccessful()) {

                                                Toast.makeText(MainActivity.this, getString(R.string.delete_account_message),
                                                        Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, getString(R.string.no_delete_account_message), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                })
                .setNegativeButton(R.string.activity_main_alertdialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        fragmentManager();
                        transaction.replace(R.id.content_main_container,homepageFragment);
                        transaction.commit();

                    }
                })
                .setIcon(R.drawable.ic_menu_delete)
                .show();


    }

    private void logout() {

        AlertDialog.Builder userLogout;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userLogout = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            userLogout = new AlertDialog.Builder(this);
        }
        userLogout.setTitle(getString(R.string.activity_main_alertdialog_logout_title))
                .setMessage(getString(R.string.activity_main_alertdialog_logout_message))
                .setPositiveButton(R.string.activity_main_alertdialog_yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        progressBar.setVisibility(View.VISIBLE);
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                    }
                })
                .setNegativeButton(R.string.activity_main_alertdialog_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        fragmentManager();
                        transaction.replace(R.id.content_main_container,homepageFragment);
                        transaction.commit();
                    }
                })
                .setIcon(R.drawable.ic_menu_logout)
                .show();

    }



}
