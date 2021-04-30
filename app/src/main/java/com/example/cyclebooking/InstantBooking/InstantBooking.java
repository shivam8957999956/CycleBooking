package com.example.cyclebooking.InstantBooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cyclebooking.R;

public class InstantBooking extends AppCompatActivity {
    public static TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_booking);

        result =findViewById(R.id.result);
    }
    public void startNew(View V){
    startActivity(new Intent(getApplicationContext(),ScannerActivity.class));
    }

}