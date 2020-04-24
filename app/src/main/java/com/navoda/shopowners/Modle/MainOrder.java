package com.navoda.shopowners.Modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainOrder {
    @SerializedName("orderID")
    private int orderID;

    @SerializedName("totalValue")
    private Double totalValue;

    @SerializedName("orderList")
    private List<OrderItems> orderList;

    @SerializedName("status")
    private String status;

    public MainOrder(int orderID, Double totalValue, List<OrderItems> orderList, String status) {
        this.orderID = orderID;
        this.totalValue = totalValue;
        this.orderList = orderList;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public List<OrderItems> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderItems> orderList) {
        this.orderList = orderList;
    }
}
