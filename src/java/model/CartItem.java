package model;

import java.time.LocalDateTime;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int variantId;
    private int quantity;
    private LocalDateTime createdAt;

    public CartItem() {
    }

    public CartItem(int cartItemId, int cartId, int variantId, int quantity, LocalDateTime createdAt) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CartItem{" + "cartItemId=" + cartItemId + ", cartId=" + cartId + ", variantId=" + variantId + ", quantity=" + quantity + ", createdAt=" + createdAt + '}';
    }

}
