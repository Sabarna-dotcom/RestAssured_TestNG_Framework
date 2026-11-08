package com.be.pojo;

public class Product {

    private String description;
    private double price;
    private String title;
    private String image;
    private String category;

    public Product(String description, double price, String title, String image, String category) {
        this.description = description;
        this.price = price;
        this.title = title;
        this.image = image;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}
