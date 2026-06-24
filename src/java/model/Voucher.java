/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dotha
 */
public class Voucher {

    private int voucherId;
    private String code;
    private String discountType;
    private double discountValue;
    private double minOrderValue;
    private double maxDiscount;
    private int quantity;
    private int uesedCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private LocalDateTime createAt;
    
    // 
    private String startDateFormat;
    private String endDateFormat;

    public Voucher() {
    }

    public Voucher(int voucherId, String code, String discountType, double discountValue, double minOrderValue, double maxDiscount, int quantity, int uesedCount, LocalDateTime startDate, LocalDateTime endDate, boolean isActive, LocalDateTime createAt) {
        this.voucherId = voucherId;
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderValue = minOrderValue;
        this.maxDiscount = maxDiscount;
        this.quantity = quantity;
        this.uesedCount = uesedCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.createAt = createAt;
        this.startDateFormat = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.endDateFormat = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUesedCount() {
        return uesedCount;
    }

    public void setUesedCount(int uesedCount) {
        this.uesedCount = uesedCount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getStartDateFormat() {
        return startDateFormat;
    }

    public void setStartDateFormat(String startDateFormat) {
        this.startDateFormat = startDateFormat;
    }

    public String getEndDateFormat() {
        return endDateFormat;
    }

    public void setEndDateFormat(String endDateFormat) {
        this.endDateFormat = endDateFormat;
    }

    @Override
    public String toString() {
        return "Voucher{" + "voucherId=" + voucherId + ", code=" + code + ", discountType=" + discountType + ", discountValue=" + discountValue + ", minOrderValue=" + minOrderValue + ", maxDiscount=" + maxDiscount + ", quantity=" + quantity + ", uesedCount=" + uesedCount + ", startDate=" + startDate + ", endDate=" + endDate + ", isActive=" + isActive + ", createAt=" + createAt + ", startDateFormat=" + startDateFormat + ", endDateFormat=" + endDateFormat + '}';
    }

}
