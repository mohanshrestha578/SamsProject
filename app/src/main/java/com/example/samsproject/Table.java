package com.example.samsproject;

public class Table {

    private String tableId;
    private String tableNumber;
    private String numberOfSeats;

    public Table() {
    }

    public Table(String tableId, String tableNumber, String numberOfSeats) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.numberOfSeats = numberOfSeats;
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
}
