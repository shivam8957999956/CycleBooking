package com.example.cyclebooking.BookANewCycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cyclebooking.MainActivity2;
import com.example.cyclebooking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ScheduleBooking extends AppCompatActivity {
    //selection parameters

    int bicycle=0,locker=0;
    RelativeLayout biCycle,lockerRelative;
    TextView bicycleText,lockertext;
    //datepicker
    int mDay,mMon,mYear,mMinute,mHour;
    TextView txtdate,txttime;


    EditText editText;
    EditText pinFromUser;
    String codeBySystem;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_booking);
        editText = findViewById(R.id.number);
        pinFromUser = findViewById(R.id.result);
        txtdate=findViewById(R.id.date);
        txttime=findViewById(R.id.time);


        biCycle=findViewById(R.id.relative_bicycle);
        lockerRelative=findViewById(R.id.relative_locker);
        bicycleText=findViewById(R.id.bicycle);
        lockertext=findViewById(R.id.locker_text);



        mAuth = FirebaseAuth.getInstance();

    }

    public void timePicker(View v){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        txttime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void clickBicycle(View v){

        if(bicycle==0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                biCycle.setBackground(getDrawable(R.drawable.orange_solid));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bicycleText.setTextColor(getColor(R.color.white));
                }
            }
            bicycle=1;
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                biCycle.setBackground(getDrawable(R.drawable.orange_hollow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    bicycleText.setTextColor(getColor(R.color.orange));
                }
            }
            bicycle=0;
        }
    }


    public void clickLocker(View v){

        if(locker==0)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lockerRelative.setBackground(getDrawable(R.drawable.orange_solid));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lockertext.setTextColor(getColor(R.color.white));
                }
            }
            locker=1;
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                lockerRelative.setBackground(getDrawable(R.drawable.orange_hollow));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lockertext.setTextColor(getColor(R.color.orange));
                }
            }
            locker=0;
        }
    }




    public void datePicker(View v){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMon = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMon, mDay);
        datePickerDialog.show();
    }


    public void check(View v) {
        String s=editText.getText().toString().trim();
        String phone="+91"+s;
        setuppin(phone);
    }
    private void setuppin(String phone) {
        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mCallbacks)
                        .build());
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
                    Toast.makeText(ScheduleBooking.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    bookingProgressBar.setVisibility(View.GONE);
//                    bookingComplete.setVisibility(View.VISIBLE);
//                    pinFromUser.setVisibility(View.GONE);
//                    Toast.makeText(BookANewCycleDashboard.this, "You hove Crossed the maximum limit ", Toast.LENGTH_SHORT).show();

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
                            // storeData();
                            Toast.makeText(ScheduleBooking.this, "Pickup Pin generated", Toast.LENGTH_SHORT).show();
//                            String _pinFromUser = pinFromUser.getText().toString().trim();
//                            String _rate = getResult.getText().toString().trim();
//                            Intent intent = new Intent(getApplicationContext(), completeBooking.class);
//                            intent.putExtra("pin", _pinFromUser);
//                            intent.putExtra("rate", _rate);
//                            startActivity(intent);
//                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(ScheduleBooking.this, "failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }



}