package com.navoda.shopowners.Modle;

import com.google.gson.annotations.SerializedName;

public class OrderItems {
    @SerializedName("productName")
    private String productName;

    @SerializedName("quantity")
    private String quantity;

    public OrderItems(String productName, String quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
