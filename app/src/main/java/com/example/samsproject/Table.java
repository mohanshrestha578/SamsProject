package com.example.samsproject;

public class Table {

    private String tableNumber;
    private String numberOfSeats;
    private Integer bookStatus;

    public Table() {
    }

    public Table(String tableNumber, String numberOfSeats, Integer bookStatus) {
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.bookStatus = bookStatus;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public Integer getBookStatus(){ return bookStatus;}

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setBookStatus(Integer bookStatus) {
        this.bookStatus = bookStatus;
    }
}
