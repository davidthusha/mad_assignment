package com.example.moonbitecafe;

public class CartItem {

    public String foodId;
    public String foodName;
    public String price;
    public String image;
    public int quantity;

    public CartItem(
            String foodId,
            String foodName,
            String price,
            String image)
    {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
        this.image = image;
        this.quantity = 1;
    }
}