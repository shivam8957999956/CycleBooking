package com.example.cyclebooking.OnGoingBooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyclebooking.Payment.Payment;
import com.example.cyclebooking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OnGoingBooking extends AppCompatActivity {
    TextView admission,pickupTime,type,pickupzone,expectedAmount,rideTime;
    String currentUser;
    LinearLayout pay;
    TextView status,statusDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going_booking);
        admission=findViewById(R.id.admission_number);
        pickupTime=findViewById(R.id.pickup_time);
        pickupzone=findViewById(R.id.pickup_zone);
        expectedAmount=findViewById(R.id.expected_amount);
        type=findViewById(R.id.type_of_cycle);
        status=findViewById(R.id.status);
        statusDetails=findViewById(R.id.status_details);
        rideTime=findViewById(R.id.ride_detail);
        currentUserData();
        admission.setText(currentUser);
        pay=findViewById(R.id.pay_btn);
        storeData();
       // detailsOfStatus();
    }
    private void currentUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
        currentUser = sharedPref.getString("username", "");
    }



//    private void detailsOfStatus() {
//        FirebaseDatabase rootnode=FirebaseDatabase.getInstance();
//        DatabaseReference ref=rootnode.getReference("Users");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String _rideTime=
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//    }

    public void callPaymentactivity(View view){
        Intent intent=new Intent(getApplicationContext(), Payment.class);
        String _addmission=admission.getText().toString().trim();
        intent.putExtra("admission",_addmission);
        startActivity(intent);
    }
    private void storeData() {
        final String _admission=admission.getText().toString().trim();
        FirebaseDatabase rootnode= FirebaseDatabase.getInstance();
        DatabaseReference ref=rootnode.getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pickupTime.setText(snapshot.child(_admission).child("current booking").child("pickupTime").getValue(String.class));
                pickupzone.setText(snapshot.child(_admission).child("current booking").child("zone").getValue(String.class));
                type.setText(snapshot.child(_admission).child("current booking").child("typeofcycle").getValue(String.class));
                pickupTime.setText(snapshot.child(_admission).child("current booking").child("pickupTime").getValue(String.class));
                statusDetails.setText(snapshot.child(_admission).child("current booking").child("status").child("status details").getValue(String.class));
                Calendar calendar=Calendar.getInstance();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss a");
                String dateTime=simpleDateFormat.format(calendar.getTime());
                rideTime.setText("at"+dateTime);
                String _statusDetails=statusDetails.getText().toString().trim();
                if(_statusDetails.equals("Proceed for payment")){
                    pay.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
