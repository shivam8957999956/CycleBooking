package com.example.cyclebooking.UserLogin.UserHelperClass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cyclebooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBBlackValueNode;

public class Profile extends AppCompatActivity {
    TextView fullname,admissionNumber,phone,password;
    String currentUser;
    ProgressBar progressBar;
    LinearLayout content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullname=findViewById(R.id.full_name);
        admissionNumber=findViewById(R.id.admission_number);
        phone=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress_bar);
        content=findViewById(R.id.content);
        progressBar.setVisibility(View.VISIBLE);
        currentUserData();
        storeData();
    }

    private void currentUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
        currentUser = sharedPref.getString("username", "");
    }

    private void storeData() {
        Query checkUser= FirebaseDatabase.getInstance().getReference("Users").orderByChild("admission").equalTo(currentUser);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    fullname.setText(snapshot.child(currentUser).child("fullname").getValue(String.class));
                    admissionNumber.setText(snapshot.child(currentUser).child("admission").getValue(String.class));
                    phone.setText(snapshot.child(currentUser).child("phone").getValue(String.class));
                    password.setText(snapshot.child(currentUser).child("password").getValue(String.class));
                    progressBar.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
