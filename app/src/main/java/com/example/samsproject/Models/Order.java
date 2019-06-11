package com.example.samsproject.Models;

public class Order {

    public Order(){}

    public Order(String id, String user_id, String username, String item_id, String item_name, Integer item_price, Integer quantity, String image, Integer discount, String status, String tableNumber) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.quantity = quantity;
        this.image = image;
        this.discount = discount;
        this.status = status;
        this.tableNumber = tableNumber;
    }

    public Order(String id, String user_id, String username, String item_id, String item_name, Integer item_price, Integer quantity, String image, Integer discount, String status, String tableNumber, Integer staff_order) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_price = item_price;
        this.quantity = quantity;
        this.image = image;
        this.discount = discount;
        this.status = status;
        this.tableNumber = tableNumber;
        this.staff_order = staff_order;
    }

    private String id;
    private String user_id;
    private String username;
    private String item_id;
    private String item_name;
    private Integer item_price;
    private Integer quantity;
    private String image;
    private Integer discount;
    private String status;
    private String tableNumber;
    private Integer staff_order;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getItem_price() {
        return item_price;
    }

    public void setItem_price(Integer item_price) {
        this.item_price = item_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Integer getStaff_order() {
        return staff_order;
    }

    public void setStaff_order(Integer staff_order) {
        this.staff_order = staff_order;
    }
}
