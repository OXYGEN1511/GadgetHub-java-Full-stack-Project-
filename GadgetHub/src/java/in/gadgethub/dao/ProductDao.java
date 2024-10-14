/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;

import in.gadgethub.pojo.ProductPojo;
import java.util.List;

/**
 *
 * @author Asus
 */
public interface ProductDao {
    public String addProduct(ProductPojo product);
    
    
    
    public String updateProduct(ProductPojo prevProduct,double updatePrice);
    public List<ProductPojo> getAllProducts();
    public List<ProductPojo> getAllProductsByType(String type);
    public List<ProductPojo> searchAllProducts(String search);
    public ProductPojo getProdutDetails(String prodId);
    public int getProductQuantity(String prodId);
    public String updateProductWithoutimage(String prevProductId,ProductPojo updateProduct);
    public double getProductPrice(String prodId);
    
    public Boolean sellNProduct(String prodId,int n);
    public List<String> getAllProductsType();
    public byte[] getImage(String prodId);
    public String removeProduct(String prodId);
}
