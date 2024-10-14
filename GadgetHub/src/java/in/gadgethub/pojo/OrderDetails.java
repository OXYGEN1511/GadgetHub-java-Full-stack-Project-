/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

import java.io.InputStream;
import java.sql.Date;

/**
 *
 * @author Asus
 */
public class OrderDetails {
    
    private String orderId;
    private String prodId;
    private String prodName;
    private int qty;
    private double amount;
    private int shipped;
    private Date  time;
    private InputStream image;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String prodId, String prodName, int qty, double amount, int shipped, Date time, InputStream image) {
        this.orderId = orderId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.qty = qty;
        this.amount = amount;
        this.shipped = shipped;
        this.time = time;
        this.image = image;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
    
    
    
}
