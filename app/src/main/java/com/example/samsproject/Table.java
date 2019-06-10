package com.example.samsproject;

public class Table {
    private String tableId;
    private String tableNumber;
    private String numberOfSeats;
    private String bookStatus;

    public Table() {
    }

    public Table(String tableId, String tableNumber, String numberOfSeats, String bookStatus) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
        this.bookStatus = bookStatus;
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public String getBookStatus(){ return bookStatus;}

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
}
