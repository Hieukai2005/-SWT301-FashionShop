package model;

import dal.ProductDAO;
import java.time.LocalDateTime;
import utility.Formatter;

public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int productVariantId;
    private int quantity;
    private double price;
    private LocalDateTime createdAt;
    private Order order;
    private ProductVariant productVariant;

    public OrderItem() {
    }

    public OrderItem(int orderItemId, int orderId, int productVariantId, int quantity, double price, LocalDateTime createdAt) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
    }
    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(int productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public String getProductName() {
        return new ProductDAO().getProductNameByProductId(productVariant.getProductId());
    }

    public double getProductDiscountPrice() {
        return new ProductDAO().getProductSalePriceByProductId(productVariant.getProductId());
    }

    // dinh dang tien viet nam 3.000.000 đ
    public double getTotalAmount() {
        return this.price * this.quantity;
    }
//
//    public String getPriceFormat() {
//        return new Formatter().getMoneyVnFormat(this.price);
//    }
}
