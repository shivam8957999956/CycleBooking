package com.example.cyclebooking.BookANewCycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cyclebooking.BookingHelperClass.BookingHelperClass;
import com.example.cyclebooking.BookingHelperClass.SecurityHelperClass;
import com.example.cyclebooking.R;
import com.example.cyclebooking.UserLogin.Login_activity;
import com.example.cyclebooking.maindashbaord.MainDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class BookANewCycleDashboard extends AppCompatActivity {
    TextView getResult;
    Button getExpectedAmount;
    Button bookingComplete;
    Dialog mDialog;
    String phone;
    int _type;
    String calculate;
    String codeBySystem;
    //type of cycle to book
    Button geared, normal;
    String type = "not";
    EditText pinFromUser;


    //booking details
    TextInputLayout admissionNumber, zone, pickUpTime, numberOfHours;

    //progress bar

    ProgressBar getExpectedAmountProgressBar, bookingProgressBar;


    int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_a_new_cycle_dashboard);
        //hooks for expected amount
        numberOfHours = findViewById(R.id.number_of_hours);
        admissionNumber = findViewById(R.id.addmission_number);
        zone = findViewById(R.id.zone_number);
        pickUpTime = findViewById(R.id.starting_time);
        geared = findViewById(R.id.geared);
        normal = findViewById(R.id.normal);

        getExpectedAmountProgressBar = findViewById(R.id.get_expected_amount_progress_bar);
        bookingProgressBar = findViewById(R.id.booking_progress_bar);

        pinFromUser = findViewById(R.id.pin_from_user);

        getExpectedAmount = findViewById(R.id.get_expected_amount);
        getResult = findViewById(R.id.get_result);

        getExpectedAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verifynumOfhours()) {
                    return;
                }
                getExpectedAmount.setVisibility(View.GONE);
                getExpectedAmountProgressBar.setVisibility(View.VISIBLE);
                calcutate();
                getExpectedAmount.setVisibility(View.VISIBLE);
                getExpectedAmountProgressBar.setVisibility(View.GONE);
            }
        });


        //final step
        bookingComplete = findViewById(R.id.book_cycle);
        bookingComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingComplete.setVisibility(View.GONE);
                bookingProgressBar.setVisibility(View.VISIBLE);
                verify();
            }
        });


    }

    private void verify() {
        if (!verifyAdmission() | !verifyzone() | !verifypickup() | !verifynumOfhours() | !verifyType()) {
            bookingProgressBar.setVisibility(View.GONE);
            bookingComplete.setVisibility(View.VISIBLE);
            return;
        }
        calcutate();

        final String admission = admissionNumber.getEditText().getText().toString().trim();
        final String _zone = zone.getEditText().getText().toString().trim();
        final String _numberOfHours = numberOfHours.getEditText().getText().toString().trim();
        final String _pickUpTime = pickUpTime.getEditText().getText().toString().trim();
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("admission").equalTo(admission);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    admissionNumber.setError(null);
                    admissionNumber.setErrorEnabled(true);
                    phone = snapshot.child(admission).child("phone").getValue(String.class);
                    String status = snapshot.child(admission).child("current booking").child("status").child("status details").getValue(String.class);
                    finalstep();

//                    if (status.equals("Payment Pending")) {
//                        admissionNumber.setError("First pay your due to book new cycle");
//                        bookingProgressBar.setVisibility(View.GONE);
//                        bookingComplete.setVisibility(View.VISIBLE);
//                    } else {
//                        pinFromUser.setVisibility(View.VISIBLE);
//                        bookingProgressBar.setVisibility(View.GONE);
//
//                    }

                } else {
                    admissionNumber.setError("User Not Registered");
                    bookingProgressBar.setVisibility(View.GONE);
                    bookingComplete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private boolean verifyType() {
        if (type.equals("not")) {
            Toast.makeText(this, "Select the type of cycle you", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean verifynumOfhours() {
        String val = numberOfHours.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            numberOfHours.setError("Field cannot be empty");
            return false;
        } else {
            numberOfHours.setError(null);
            numberOfHours.setErrorEnabled(false);
            return true;
        }
    }

    private boolean verifypickup() {
        String val = pickUpTime.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            pickUpTime.setError("Field cannot be empty");
            return false;
        } else {
            pickUpTime.setError(null);
            pickUpTime.setErrorEnabled(false);
            return true;
        }
    }

    private boolean verifyzone() {
        String val = zone.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            zone.setError("Field cannot be empty");
            return false;
        } else {
            zone.setError(null);
            zone.setErrorEnabled(false);
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

    private void finalstep() {
        setuppin(phone);


    }

    private void setuppin(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(BookANewCycleDashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    bookingProgressBar.setVisibility(View.GONE);
                    bookingComplete.setVisibility(View.VISIBLE);
                    pinFromUser.setVisibility(View.GONE);
                    Toast.makeText(BookANewCycleDashboard.this, "You hove Crossed the maximum limit ", Toast.LENGTH_SHORT).show();
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storeData();
                            Toast.makeText(BookANewCycleDashboard.this, "Pickup Pin generated", Toast.LENGTH_SHORT).show();
                            String _pinFromUser = pinFromUser.getText().toString().trim();
                            String _rate = getResult.getText().toString().trim();
                            Intent intent = new Intent(getApplicationContext(), completeBooking.class);
                            intent.putExtra("pin", _pinFromUser);
                            intent.putExtra("rate", _rate);
                            startActivity(intent);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

    private void storeData() {
        final String admission = admissionNumber.getEditText().getText().toString().trim();
        final String _zone = zone.getEditText().getText().toString().trim();
        final String _numberOfHours = numberOfHours.getEditText().getText().toString().trim();
        final String _pickUpTime = pickUpTime.getEditText().getText().toString().trim();
        String pin = pinFromUser.getText().toString().trim();
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference ref = rootnode.getReference("Users");
        DatabaseReference ref2 = rootnode.getReference("Users");
        BookingHelperClass bookingHelperClass = new BookingHelperClass(admission, _zone, _pickUpTime, _numberOfHours, type, pin);
        ref.child(admission).child("current booking").setValue(bookingHelperClass);
        String v1 = " ";
        String v2 = " ";
        String v3 = " ";
        String v4 = " ";
        ref2.child(admission).child("current booking").child("status").child("pin verification").setValue("");
        ref2.child(admission).child("current booking").child("status").child("id verification").setValue("");
        ref2.child(admission).child("current booking").child("status").child("ride end").setValue("");
        ref2.child(admission).child("current booking").child("status").child("ride start").setValue("");
        ref2.child(admission).child("current booking").child("status").child("status details").setValue("ride not started yet");
        ref2.child(admission).child("current booking").child("status").child("end time").setValue("");
        ref2.child(admission).child("current booking").child("status").child("start time").setValue("");
        ref2.child(admission).child("current booking").child("status").child("drop zone").setValue("");
        ref2.child(admission).child("current booking").child("status").child("total travel time").setValue("");
        ref2.child(admission).child("current booking").child("status").child("final amount").setValue("");
    }

    public void changegeared(View view) {

        geared.setBackgroundColor(getColor(R.color.black));
        geared.setTextColor(getColor(R.color.white));
        type = "geared";
        normal.setBackgroundColor(getColor(R.color.white));
        normal.setTextColor(getColor(R.color.black));
    }

    public void changenormal(View view) {

        normal.setBackgroundColor(getColor(R.color.black));
        normal.setTextColor(getColor(R.color.white));
        type = "normal";
        geared.setBackgroundColor(getColor(R.color.white));
        geared.setTextColor(getColor(R.color.black));


    }


    private void calcutate() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Rate");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Rate = snapshot.child(type).getValue(String.class);
                String hours = numberOfHours.getEditText().getText().toString().trim();
                int result = Integer.parseInt(hours) * Integer.parseInt(Rate);


                getResult.setText("Rs. " + String.valueOf(result));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
