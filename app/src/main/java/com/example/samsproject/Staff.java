package com.example.samsproject;

public class Staff {
    private String staffId;
    private String staffName;
    private String staffPhoneNumber;
    private String year;
    private String staffEmailAddress;
    private String staffLoction;

    public Staff(){}

    public Staff(String staffId, String staffName, String staffPhoneNumber, String year, String staffEmailAddress, String staffLoction) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffPhoneNumber = staffPhoneNumber;
        this.year = year;
        this.staffEmailAddress = staffEmailAddress;
        this.staffLoction = staffLoction;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffPhoneNumber() {
        return staffPhoneNumber;
    }

    public String getYear() {
        return year;
    }

    public String getStaffEmailAddress() {
        return staffEmailAddress;
    }

    public String getStaffLoction() {
        return staffLoction;
    }
}
