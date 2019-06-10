package com.example.samsproject;

public class Reservation {

    private String reservationName;
    private String reservationTime;
    private String reservationDate;
    private String tableNumber;
    private String reservationContactNo;
    private String user_name;
    private String user_id;

    public Reservation(){}

    public Reservation(String reservationName, String reservationTime, String reservationDate, String tableNumber, String reservationContactNo, String user_name, String user_id) {
        this.reservationName = reservationName;
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
        this.tableNumber = tableNumber;
        this.reservationContactNo = reservationContactNo;
        this.user_name = user_name;
        this.user_id = user_id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setReservationContactNo(String reservationContactNo) {
        this.reservationContactNo = reservationContactNo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
