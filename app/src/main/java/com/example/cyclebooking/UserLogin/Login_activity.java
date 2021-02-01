package com.example.cyclebooking.UserLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cyclebooking.R;
import com.example.cyclebooking.UserLogin.UserHelperClass.Profile;
import com.example.cyclebooking.maindashbaord.MainDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login_activity extends AppCompatActivity {

    TextInputLayout admissionNumber,password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        progressBar=findViewById(R.id.login_progress_bar);
        admissionNumber=findViewById(R.id.admission_number);
        password=findViewById(R.id.password);

    }
    public void callSignIn(View view){
        Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(intent);

    }

    public void callNextloginScreen(View view){
        if(!verifyAdmission()|!verifyPassword()){
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        final String admissionnumber=admissionNumber.getEditText().getText().toString().trim();
        final String passWord=password.getEditText().getText().toString().trim();
        Query checkUser=FirebaseDatabase.getInstance().getReference("Users").orderByChild("admission").equalTo(admissionnumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    admissionNumber.setError(null);
                    admissionNumber.setErrorEnabled(true);
                    String _password=snapshot.child(admissionnumber).child("password").getValue(String.class);
                    if(_password.equals(passWord)){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login_activity.this, "Logged In SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(), MainDashboard .class);
                        //intent.putExtra("currentUser",admissionnumber);
                        sharedStoreData();
                        startActivity(intent);
                        finish();
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login_activity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login_activity.this, "Incorrect Admission number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    private boolean verifyPassword() {
        String val = password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }


    }
    private boolean verifyAdmission() {
        String val = admissionNumber.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            admissionNumber.setError("Field cannot be empty");
            return false;
        } else {
            admissionNumber.setError(null);
            admissionNumber.setErrorEnabled(false);
            return true;
        }



    }

    public void callBackMainDashboard(View view){
        Intent intent=new Intent(getApplicationContext(),MainDashboard.class);
        startActivity(intent);
        finish();
    }

    private void sharedStoreData() {

        SharedPreferences sharedPref=getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("username",admissionNumber.getEditText().getText().toString());
        editor.apply();

    }



}
