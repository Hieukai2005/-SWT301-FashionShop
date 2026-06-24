package model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import utility.Formatter;

public class Product {

    private int productId;
    private String productName;
    private double price;
    private String description;
    private String image;
    private int categoryId;
    private String gender;
    private boolean isSale;
    private boolean isActive;
    private int discount;
    private LocalDateTime createdAt;
    private int totalStock;

    public Product() {
    }

    private Category category;
    
    public Product(int productId, String productName, double price, String description, String image, int categoryId, String gender, boolean isSale, boolean isActive, int discount, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.image = image;
        this.categoryId = categoryId;
        this.gender = gender;
        this.isSale = isSale;
        this.isActive = isActive;
        this.discount = discount;
        this.createdAt = createdAt;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isIsSale() {
        return isSale;
    }

    public void setIsSale(boolean isSale) {
        this.isSale = isSale;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", price=" + price + ", description=" + description + ", image=" + image + ", categoryId=" + categoryId + ", gender=" + gender + ", isSale=" + isSale + ", isActive=" + isActive + ", discount=" + discount + ", createdAt=" + createdAt + '}';
    }

    /**
     * HoaNK - chuyen doi gia giam theo %
     */
    public double getSalePrice() {
        if (discount <= 0) {
            return price;
        }
        double discountPercent = discount / 100.0;
        double salePrice = price - (price * discountPercent);
        salePrice = Math.round(salePrice * 100.0) / 100.0;
        return salePrice;
    }
}
