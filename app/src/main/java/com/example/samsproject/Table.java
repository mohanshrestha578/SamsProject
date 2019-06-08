package com.example.samsproject;

public class Table {

    private String tableId;
    private String tableNumber;
    private String numberOfSeats;
    private Boolean bookStatus;

    public Table() {
    }

    public Table(String tableId, String tableNumber, String numberOfSeats, Boolean bookStatus) {
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

    public Boolean getBookStatus(){ return bookStatus;}
}
