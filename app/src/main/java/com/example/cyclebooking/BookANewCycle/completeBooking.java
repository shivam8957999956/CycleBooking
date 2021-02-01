package com.example.cyclebooking.BookANewCycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cyclebooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class completeBooking extends AppCompatActivity {
    TextView amount;
    Button close;
    TextView pin;
    String numOfHours;
    String rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_booking);
        amount=findViewById(R.id.amount);
        pin=findViewById(R.id.pickup_pin);
        amount.setText(getIntent().getStringExtra("rate"));
        pin.setText(getIntent().getStringExtra("pin"));
        if(pin.equals("")){

            pin.setText("Check Sms for the Pin");

        }

    }


}
