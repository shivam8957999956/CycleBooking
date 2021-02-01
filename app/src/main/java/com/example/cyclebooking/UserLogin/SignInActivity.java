package com.example.cyclebooking.UserLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cyclebooking.R;
import com.example.cyclebooking.UserLogin.UserHelperClass.SignInDetail;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignInActivity extends AppCompatActivity {
    TextInputLayout admissionNumber,phoneNo,password,comfirmPassword,fullname;
    CountryCodePicker countryCodePicker;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        admissionNumber=findViewById(R.id.addmission_number);
        phoneNo=findViewById(R.id.phone_number);
        password=findViewById(R.id.password);
        comfirmPassword=findViewById(R.id.confirm_password);
        fullname=findViewById(R.id.full_name);
        countryCodePicker=findViewById(R.id.countryCodePicker);
        progressBar =findViewById(R.id.progress_bar);


    }

    public void callNextSignIn(View view){
        if(!verifyAdmission()|!verifyName()|!verifyPassword()|!verifyConfirmPassword()|!verifyPhone()){

            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        String admissionnumber=admissionNumber.getEditText().getText().toString().trim();
        String phoneno=phoneNo.getEditText().getText().toString().trim();
        String passWord=password.getEditText().getText().toString().trim();
        String confirmpassword=comfirmPassword.getEditText().getText().toString().trim();
        String fullName=fullname.getEditText().getText().toString().trim();
        String _phoneNo="+"+ countryCodePicker.getFullNumber()+phoneno;
        FirebaseDatabase rootnode=FirebaseDatabase.getInstance();
        DatabaseReference ref=rootnode.getReference("Users");
        SignInDetail signInDetail=new SignInDetail(fullName,admissionnumber,_phoneNo,passWord);
        ref.child(admissionnumber).setValue(signInDetail);
        progressBar.setVisibility(View.GONE);

        Intent intent=new Intent(getApplicationContext(),Login_activity.class);
        startActivity(intent);
        finish();




    }

    private boolean verifyPhone() {
        String val = phoneNo.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            phoneNo.setError("Field cannot be empty");
            return false;
        } else {
            phoneNo.setError(null);
            phoneNo.setErrorEnabled(false);
            return true;
        }

    }

    private boolean verifyConfirmPassword() {
        String val = password.getEditText().getText().toString().trim();
        String valconfirm = comfirmPassword.getEditText().getText().toString().trim();
        if (!val.equals(valconfirm)) {
            comfirmPassword.setError("two password don not match");
            return false;
        } else {
            comfirmPassword.setError(null);
            comfirmPassword.setErrorEnabled(false);
            return true;
        }


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

    private boolean verifyName() {
        String val = fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Field cannot be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
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


    public void callLoginScreen(View view){
        Intent intent=new Intent(getApplicationContext(),Login_activity.class);
        startActivity(intent);
        finish();

    }
}
