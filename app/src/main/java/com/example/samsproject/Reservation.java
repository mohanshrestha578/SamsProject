package com.example.samsproject;

public class Reservation {

    private String reservationName;
    private String reservationTime;
    private String reservationDate;
    private String tableNumber;
    private String reservationContactNo;

    public Reservation(){}

    public Reservation(String reservationName, String reservationTime, String reservationDate, String tableNumber, String reservationContactNo) {
        this.reservationName = reservationName;
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
        this.tableNumber = tableNumber;
        this.reservationContactNo = reservationContactNo;
    }

    public String getReservationName() {
        return reservationName;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getReservationContactNo() {
        return reservationContactNo;
    }
}
