package com.example.cyclebooking.PreviousBooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cyclebooking.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreviousBooking extends AppCompatActivity {

    DatabaseReference booking;
    ListView listView;
    String admission;
    List<PreviousHelperClass> previousBookingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_booking);
        listView = findViewById(R.id.list_view);
        currentUserData();
        booking = FirebaseDatabase.getInstance().getReference(admission + "previous booking");
        previousBookingList=new ArrayList<>();
        Toast.makeText(this, admission, Toast.LENGTH_SHORT).show();
    }
    private void currentUserData() {
        SharedPreferences sharedPref = getSharedPreferences("userlogindata", Context.MODE_PRIVATE);
        admission = sharedPref.getString("username", "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        booking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                previousBookingList.clear();
                for (DataSnapshot bookingsnapshot : snapshot.getChildren()) {
                    PreviousHelperClass previousHelperClass=bookingsnapshot.getValue(PreviousHelperClass.class);
                    previousBookingList.add(previousHelperClass);
                }
                PreviousBookingAdapter adapter=new PreviousBookingAdapter(PreviousBooking.this,previousBookingList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
