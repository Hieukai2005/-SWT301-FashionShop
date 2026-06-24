package model;

public class DashBoard {
    private int totalOrders;
    private double totalAmount;
    private int newProducts;
    private int newUsers;
    private int pendingOrders;
    private int completedOrders;

    public DashBoard() {
    }

    public DashBoard(int totalOrders, double totalAmount, int newProducts, int newUsers) {
        this.totalOrders = totalOrders;
        this.totalAmount = totalAmount;
        this.newProducts = newProducts;
        this.newUsers = newUsers;
    }

    public int getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(int pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public int getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(int completedOrders) {
        this.completedOrders = completedOrders;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(int newProducts) {
        this.newProducts = newProducts;
    }

    public int getNewUsers() {
        return newUsers;
    }

    public void setNewUsers(int newUsers) {
        this.newUsers = newUsers;
    }
}
