/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Customer {

    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String createdAt;
    private double totalSpending;
    private int totalOrders;
    private boolean isActive; // true = hoạt động, false = bị khóa


    public Customer() {
    }

    public Customer(int userId, String fullName, String email, String phoneNumber, String address, String createdAt, double totalSpending, int totalOrders) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
        this.totalSpending = totalSpending;
        this.totalOrders = totalOrders;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    //  XÉT HẠNG THẺ Ở ĐÂY
    public String getTier() {
        if (totalSpending >= 3000000) {
            return "Gold";
        } else if (totalSpending >= 2000000) {
            return "Silver";
        } else if (totalSpending >= 1000000) {
            return "Bronze";
        } else {
            return "Green";
        }
    }

    public int getPoints() {
        return (int) (totalSpending / 10000);
    }
}
