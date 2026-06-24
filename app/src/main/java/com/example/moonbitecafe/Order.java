package com.example.moonbitecafe;

public class Order {

    String orderId;
    String totalAmount;
    String status;

    public Order(
            String orderId,
            String totalAmount,
            String status)
    {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}