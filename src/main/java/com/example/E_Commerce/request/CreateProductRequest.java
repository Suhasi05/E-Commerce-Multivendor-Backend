package com.example.E_Commerce.request;

import java.util.List;

public class CreateProductRequest {
    private String title;
    private String description;
    private int MRPprice;
    private int SellingPrice;
    private String color;
    private List<String> images;
    private String category;
    private String Category2;
    private String Category3;
    private String size;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMRPprice() {
        return MRPprice;
    }

    public void setMRPprice(int MRPprice) {
        this.MRPprice = MRPprice;
    }

    public int getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory2() {
        return Category2;
    }

    public void setCategory2(String category2) {
        Category2 = category2;
    }

    public String getCategory3() {
        return Category3;
    }

    public void setCategory3(String category3) {
        Category3 = category3;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
