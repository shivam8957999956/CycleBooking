package com.example.cyclebooking.maindashbaord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.cyclebooking.AllDetails.AllDetails;
import com.example.cyclebooking.BookANewCycle.BookANewCycleDashboard;
import com.example.cyclebooking.OnGoingBooking.OnGoingBooking;
import com.example.cyclebooking.PreviousBooking.PreviousBooking;
import com.example.cyclebooking.R;
import com.example.cyclebooking.UserLogin.Login_activity;
import com.example.cyclebooking.UserLogin.SignInActivity;
import com.example.cyclebooking.UserLogin.UserHelperClass.Profile;
import com.google.android.material.navigation.NavigationView;

public class MainDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    LinearLayout contentView;
    ImageView menuIcon;
    String currentUser = "";
    //drawer
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_dashboard);
        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        currentUserData();
        navigationDrawer();
    }

    private void currentUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
        currentUser = sharedPref.getString("username", "");
    }


    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.menu_profile);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.addDrawerListener((new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //scale the view based on current slide off set
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                //Translate the view,accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        }));
    }


    public void callbooknewcycle(View view) {
        Intent intent = new Intent(getApplicationContext(), BookANewCycleDashboard.class);
        startActivity(intent);

    }

    public void callAllDetailsScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AllDetails.class);
        startActivity(intent);
        finish();
    }

    public void callPreviousBooking(View view) {
        if (currentUser.equals("")) {
            Toast.makeText(this, "Login first to Unlock this feature", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(getApplicationContext(), PreviousBooking.class);
            startActivity(intent);
        }


    }

    public void callOngoingBooking(View view) {
        if (currentUser.equals("")) {
            Toast.makeText(this, "Login first to Unlock this feature", Toast.LENGTH_SHORT).show();

        } else {
            Intent intent = new Intent(getApplicationContext(), OnGoingBooking.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_login:
                startActivity(new Intent(getApplicationContext(), Login_activity.class));
                break;
            case R.id.menu_signin:
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                break;
            case R.id.menu_profile:
                if (currentUser.equals("")) {
                    Toast.makeText(this, "Login first", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
                break;
            case R.id.menu_logout:
                SharedPreferences sharedPref=getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPref.edit();
                editor.putString("username","");
                editor.apply();
                Toast.makeText(this, "You Are Successfully Logged Out \n Login to continue our service again", Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
