package com.example.cyclebooking.BookingHelperClass;

public class BookingHelperClass {
    String admission;
    String zone;
    String pickupTime;
    String numberOfHour;
    String typeofcycle;
    String pickUpPin;

    public String getAdmission() {
        return admission;
    }

    public String getZone() {
        return zone;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public String getNumberOfHour() {
        return numberOfHour;
    }

    public String getTypeofcycle() {
        return typeofcycle;
    }

    public String getPickUpPin() {
        return pickUpPin;
    }

    public BookingHelperClass(String admission, String zone, String pickupTime, String numberOfHour, String typeofcycle,String pickUpPin) {
        this.admission = admission;
        this.zone = zone;
        this.pickupTime = pickupTime;
        this.numberOfHour = numberOfHour;
        this.typeofcycle = typeofcycle;
        this.pickUpPin = pickUpPin;

    }

    public BookingHelperClass(){

    }
}
