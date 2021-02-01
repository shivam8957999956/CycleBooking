package com.example.cyclebooking.UserLogin.UserHelperClass;

public class SignInDetail {
    String fullname;
    String admission;
    String phone;
    String password;
    public SignInDetail(){

    }

    public String getFullname() {
        return fullname;
    }

    public String getAdmission() {
        return admission;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public SignInDetail(String fullname, String admission, String phone, String password) {
        this.fullname = fullname;
        this.admission = admission;
        this.phone = phone;
        this.password = password;
    }
}
