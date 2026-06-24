package com.example.moonbitecafe;

public class Food {

    String food_id;
    String food_name;
    String price;
    String image;
    String description;

    public Food(String food_id,
                String food_name,
                String price,
                String image,
                String description)
    {
        this.food_id = food_id;
        this.food_name = food_name;
        this.price = price;
        this.image = image;
        this.description = description;
    }
}