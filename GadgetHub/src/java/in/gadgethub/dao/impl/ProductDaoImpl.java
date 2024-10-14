/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.ProductDao;
import in.gadgethub.pojo.ProductPojo;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao{
    
    public String addProduct(ProductPojo product){
        String status ="Product registration Failed";
        if(product.getProdid()==null){
            product.setProdid(IDutil.generateProdId());
}       
        
        
        Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("Insert into products values (?,?,?,?");
              pstmt.setString(1,product.getProdid());
                pstmt.setString(2,product.getProdName());
                  pstmt.setString(3,product.getProdType());
                     pstmt.setString(4,product.getProdInfo());
                        pstmt.setDouble(5,product.getProdPrice());
                           pstmt.setInt(6,product.getProdQuantity());
                            pstmt.setBlob(7,product.getProdImage());
                            int count = pstmt.executeUpdate();
                              
                            if(count == 1){
                                  status = "Resistered Succefully";
                            }
        }
          catch(SQLException e){
              System.out.println("Error in product"+e);
               e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          
          return status;
         
    }
    
    public String updateProduct(ProductPojo prevProduct,ProductPojo updatedProduct){
        String Status = "Updation failed";
        if(!prevProduct.getProdid().equals(updatedProduct.getProdid())){
            Status = "Product ID's Do not match.Updation failed";
            return Status;
            
    }
       Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("Update products set pname=?,ptype = ?,pinfo = ?,pprice=?,pquantity =? , image =? where pid=?");
              pstmt.setString(1,updatedProduct.getProdName());
                pstmt.setString(2,updatedProduct.getProdType());
                  pstmt.setString(3,updatedProduct.getProdInfo());
                    
                        pstmt.setDouble(4,updatedProduct.getProdPrice());
                           pstmt.setInt(5,updatedProduct.getProdQuantity());
                            pstmt.setBlob(6,updatedProduct.getProdImage());
                            int count = pstmt.executeUpdate();
                              
                            if(count == 1){
                                  Status = "Product Updated succesfully";
                            }
        }
          catch(SQLException e){
              System.out.println("Error in Updatedproduct"+e);
               e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          return Status; 
        
        
        
    }
    
    public String updateProductPrice(String prodid,double updatedPrice){
         String Status = " Price Updation failed";
   
       Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("Update products set pprice=? where pid=?");
              pstmt.setDouble(1,updatedPrice);
                pstmt.setString(2,prodid);
               
                            int count = pstmt.executeUpdate();
                              
                            if(count == 1){
                                  Status = "Product Updated succesfully";
                            }
        }
          catch(SQLException e){
              Status = "Error"+e.getMessage();
              System.out.println("Error in product"+e);
               e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          return Status; 
        
    }
    
    public List<ProductPojo> getAllProducts(){
        List<ProductPojo> productList = new ArrayList<>();
        
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs =null;
        try{
            st = conn.createStatement();
            rs = st.executeQuery("Select * from Products");
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdid(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }
        catch(SQLException e){
             
              System.out.println("Error in getAllproduct"+e);
               e.printStackTrace();
        }
        
           DBUtil.closeStatement(st); 
           DBUtil.closeResultSet(rs);
          
        return productList;
        
    }
    
    
    public List<ProductPojo> getAllProductsByType(String ptype){
        List<ProductPojo> productList = new ArrayList<>();
        
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs =null;
        ptype = ptype.toLowerCase();
        try{
            ps = conn.prepareStatement("Seletc * from products where lower(ptype) like ?");
            ps.setString(1,"%"+ptype+"%");
            rs = ps.executeQuery("Select * from Products");
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdid(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }
        catch(SQLException e){
             
              System.out.println("Error in getAllproduct"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(ps); 
           DBUtil.closeResultSet(rs);
          
        return productList;
        
    }
    
    public List<ProductPojo> searchAllProducts(String searchTerm ){
        List<ProductPojo> productList = new ArrayList<>();
        
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs =null;
        try{
            ps = conn.prepareStatement("Seletc * from products where lower(ptype) like ? or lower(pname) like or lower(pinfo) like? ?");
            ps.setString(1,"%"+searchTerm+"%");
            ps.setString(2,"%"+searchTerm+"%");
            ps.setString(3,"%"+searchTerm+"%");
            rs = ps.executeQuery("Select * from Products");
            while(rs.next()){
                ProductPojo product = new ProductPojo();
                product.setProdid(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                productList.add(product);
            }
        }
        catch(SQLException e){
             
              System.out.println("Error in getAllproduct"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(ps); 
           DBUtil.closeResultSet(rs);
          
        return productList;
        
    }
    
    public ProductPojo  getProductDetails(String prodId){
        ProductPojo product = null;
       Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs =null; 
        try{
            ps = conn.prepareStatement("Select * from products where pid =?");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
        
        if(rs.next()){
            product = new ProductPojo();
                product.setProdid(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdType(rs.getString("ptype"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdQuantity(rs.getInt("pquantity"));
                product.setProdImage(rs.getAsciiStream("image"));
                
        }
    }catch(SQLException e){
             
              System.out.println("Error in getProductDetails"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(ps); 
           DBUtil.closeResultSet(rs);
          
        return product;
    
    } 
    
    public int getProductQuantity(String prodId){
        int quantity = 0;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select pquantity from products where pid = ?");
            ps.setString(1,prodId);
            rs = ps.executeQuery();
            if(rs.next()){
                quantity = rs.getInt(1);
                
            }
        }catch(SQLException e){
             
              System.out.println("Error in getProductQuantity"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(ps); 
           DBUtil.closeResultSet(rs);
          
        return quantity;
    
    }
    
    public String updateProductWithoutImage(String prevProduct,ProductPojo updatedProduct){
        String Status = "Updation failed";
        int prevQuantity =0;
        
        if(!prevProduct.equals(updatedProduct.getProdid())){
            Status = "Product ID's Do not match.Updation failed";
            return Status;
            
    }
       Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
       
        
        try{
            prevQuantity = getProductQuantity(prevProduct);
            pstmt = conn.prepareStatement("Update products set pname=?,ptype = ?,pinfo = ?,pprice=?,pquantity =?  where pid=?");
              pstmt.setString(1,updatedProduct.getProdName());
                pstmt.setString(2,updatedProduct.getProdType());
                  pstmt.setString(3,updatedProduct.getProdInfo());
                    
                        pstmt.setDouble(4,updatedProduct.getProdPrice());
                           pstmt.setInt(5,updatedProduct.getProdQuantity());
                            pstmt.setString(6,updatedProduct.getProdid());
                            int count = pstmt.executeUpdate();
                              
                            if(count == 1){
                                  Status = "Product Updated succesfully";
                            }
        }
          catch(SQLException e){
              System.out.println("Error in Updatedproduct"+e);
               e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          return Status; 
        
        
        
    }
     public double getProductPrice(String prodId){
         double price=0.0;
          Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            pstmt= conn.prepareStatement("Select pprice from products where pid =?");
            pstmt.setString(1,prodId);
            rs = pstmt.executeQuery();
            if(rs.next()){
                price = rs.getDouble(1);
            }
        }catch(SQLException e){
             
              System.out.println("Error in getProductPrice"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(pstmt); 
           DBUtil.closeResultSet(rs);
          
        return price;
    
     }
     
     public Boolean sellNProduct(String prodId,int n){
         boolean result = false;
   
       Connection conn = DBUtil.provideConnection();
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement("Update products set pquantity=(pquantitu-?) where pid=?");
            
            pstmt.setInt(1,n);
            pstmt.setString(2,prodId);
               
             int count = pstmt.executeUpdate();
                              
                            if(count == 1){
                                  result = true;
                            }
        }
          catch(SQLException e){
              
              System.out.println("Error in sellNProducts"+e);
               e.printStackTrace();
        }
          DBUtil.closeStatement(pstmt);
          return result; 
        
     }
     
     public List<String> getAllProductsType(){
         List<String> productTypeList = new ArrayList<>();
        
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs =null;
        try{
            st = conn.createStatement();
  
            rs = st.executeQuery("Select distinct ptype from Products");
            while(rs.next()){
                productTypeList.add(rs.getString(1));
               
            }
        }
        catch(SQLException e){
             
              System.out.println("Error in getAllproductstype"+e);
               e.printStackTrace();
        }
           DBUtil.closeStatement(st); 
           DBUtil.closeResultSet(rs);
          
        return productTypeList;
     }
    
     public byte[] getImage(String prodId){
         byte[] arr = null;
         Connection conn = DBUtil.provideConnection();
         PreparedStatement ps = null;
         ResultSet rs = null;
         try{
             ps = conn.prepareStatement("Select image from products where pid =?");
             ps.setString(1,prodId);
             rs = ps.executeQuery();
             if(rs.next()){
                 arr = rs.getBytes(1);
                
             }
         }catch(SQLException ex){
             System.out.println("Errror in getImage:"+ex);
             ex.printStackTrace();
            }
               DBUtil.closeResultSet(rs);
               DBUtil.closeStatement(ps);
               return arr;
    
    }
     
     public String removeProduct(String prodId){
         return "";
     }
     
     
     
}
    
    

