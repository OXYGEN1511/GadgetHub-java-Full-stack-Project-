/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.pojo;

import java.io.InputStream;
import java.sql.Blob;

/**
 *
 * @author Asus
 */
public class ProductPojo {
    
    private String prodid;
    private String prodName;
    private String prodType;
    private String prodInfo;
    private double prodPrice;
    private int prodQuantity;
    private InputStream prodImage;

    public InputStream getProdImage() {
        return prodImage;
    }

    public void setProdImage(InputStream prodImage) {
        this.prodImage = prodImage;
    }

 

    public ProductPojo() {
    }

    public ProductPojo(String prodid, String prodName, String prodType, String prodInfo, double prodPrice, int prodQuantity,InputStream prodImage) {
        this.prodid = prodid;
        this.prodName = prodName;
        this.prodType = prodType;
        this.prodInfo = prodInfo;
        this.prodPrice = prodPrice;
        this.prodQuantity = prodQuantity;
        this.prodImage=prodImage;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType;
    }

    public String getProdInfo() {
        return prodInfo;
    }

    public void setProdInfo(String prodInfo) {
        this.prodInfo = prodInfo;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }
    
    
}


