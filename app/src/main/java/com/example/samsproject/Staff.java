package com.example.samsproject;

public class Staff {
    private String staffId;
    private String staffName;
    private String staffPhoneNumber;
    private String joinYear;
    private String staffEmailAddress;
    private String staffPassword;
    private String StaffConfirmPassword;
    private String staffLocation;

    public Staff(){}

    public Staff(String staffId, String staffName, String staffPhoneNumber, String joinYear, String staffEmailAddress,
                 String staffPassword, String staffConfirmPassword, String staffLocation) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffPhoneNumber = staffPhoneNumber;
        this.joinYear = joinYear;
        this.staffEmailAddress = staffEmailAddress;
        this.staffPassword = staffPassword;
        StaffConfirmPassword = staffConfirmPassword;
        this.staffLocation = staffLocation;
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

    public String getJoinYear() {
        return joinYear;
    }

    public String getStaffEmailAddress() {
        return staffEmailAddress;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public String getStaffConfirmPassword() {
        return StaffConfirmPassword;
    }

    public String getStaffLocation() {
        return staffLocation;
    }
}
