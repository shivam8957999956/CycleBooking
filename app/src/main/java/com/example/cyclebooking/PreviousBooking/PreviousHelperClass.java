package com.example.cyclebooking.PreviousBooking;

public class PreviousHelperClass {
    String admission;
    String pickUp;
    String dropZone;
    String amount;
    String travelTime;
    String typeOfCycle;
    String bookingFinished;

    public String getBookingFinished() {
        return bookingFinished;
    }

    public void setBookingFinished(String bookingFinished) {
        this.bookingFinished = bookingFinished;
    }

    public PreviousHelperClass(){

    }

    public PreviousHelperClass(String admission, String pickUp, String dropZone, String amount, String travelTime, String typeOfCycle,String bookingFinished) {
        this.admission = admission;
        this.pickUp = pickUp;
        this.dropZone = dropZone;
        this.amount = amount;
        this.travelTime = travelTime;
        this.typeOfCycle = typeOfCycle;
        this.bookingFinished=bookingFinished;
    }

    public String getAdmission() {
        return admission;
    }


    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public String getDropZone() {
        return dropZone;
    }

    public void setDropZone(String dropZone) {
        this.dropZone = dropZone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getTypeOfCycle() {
        return typeOfCycle;
    }

    public void setTypeOfCycle(String typeOfCycle) {
        this.typeOfCycle = typeOfCycle;
    }
}
