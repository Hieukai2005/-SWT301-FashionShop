/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dotha
 */
public class CartItemView {

    int cartItemId;
    String image;
    String gender;
    String productName;
    double price;
    int discount;
    int productId;
    String color;
    String size;
    int quantity;
    int colorId;
    int sizeId;

    public CartItemView() {
    }

    public CartItemView(int cartItemId, String image, String gender, String productName, double price, int discount, int productId, String color, String size, int quantity, int colorId, int sizeId) {
        this.cartItemId = cartItemId;
        this.image = image;
        this.gender = gender;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.colorId = colorId;
        this.sizeId = sizeId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }
    
    

    public int getCartItemId() {
        return cartItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CartItemView{" + "cartItemId=" + cartItemId + ", image=" + image + ", gender=" + gender + ", productName=" + productName + ", price=" + price + ", discount=" + discount + ", productId=" + productId + ", color=" + color + ", size=" + size + '}';
    }

}
