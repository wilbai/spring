package com.wil.pojo;

/**
 * Created by wil on 2017/12/7.
 */
public class Product {

    private Integer id;
    private String name;
    private String title;
    private Integer price;
    private Integer marketPrice;

    public Product() {}

    public Product(Integer id, String name, String title, Integer price, Integer marketPrice) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.price = price;
        this.marketPrice = marketPrice;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }
}
