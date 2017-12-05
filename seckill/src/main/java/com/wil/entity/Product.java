package com.wil.entity;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class Product implements Serializable {
    private Integer id;

    private String productName;

    private String productTitle;

    private Integer productInventory;

    private BigDecimal productPrice;

    private BigDecimal productMarketPrice;

    private String productImage;

    private Date startTime;

    private Date endTime;

    private String productDesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductMarketPrice() {
        return productMarketPrice;
    }

    public void setProductMarketPrice(BigDecimal productMarketPrice) {
        this.productMarketPrice = productMarketPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Long getStartTimeTs() {
        return getStartTime().getTime();
    }

    /**
     * 当前时间在start之后，返回true,开始
     * @return
     */
    public boolean isStart() {
        DateTime dateTime = new DateTime(getStartTime().getTime());
        return dateTime.isBeforeNow();
    }

    /**
     * 当前时间在end之后，返回true,结束
     * @return
     */
    public boolean isEnd() {
        DateTime dateTime = new DateTime(getEndTime().getTime());
        return dateTime.isBeforeNow();
    }
}