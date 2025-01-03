
package in.gadgethub.dao.impl;

import in.gadgethub.dao.CartDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.demand;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class CartdaoImpl implements CartDao{
    
    @Override
    public String addProductToCart(CartPojo cart){
        String status="Failed to add to cart!";        
         Connection conn=DBUtil.provideConnection();
         PreparedStatement ps1=null;
         ResultSet rs=null;
         try{
             ps1=conn.prepareStatement("Select * from USERCART where prodid=? and useremail=?");
             ps1.setString(1,cart.getProdid());
             ps1.setString(2,cart.getUseremail());
             rs=ps1.executeQuery();
             if(rs.next()){
                 ProductDaoImpl prodDao=new ProductDaoImpl();
                 int stockQty=prodDao.getProductQuantity(cart.getProdid());
                 int newQty=cart.getQuantity()+rs.getInt("quantity");
                 if(stockQty<newQty){
                     cart.setQuantity(stockQty);
                     updateProductInCart(cart);
                     status="Only "+stockQty+" no of items are avaialble in our stock so we are adding "+stockQty+" in your cart";
                     demand demandPojo=new demand();
                     demandPojo.setProdid(cart.getProdid());
                     demandPojo.setUseremail(cart.getUseremail());
                     demandPojo.setDemandQuantity(newQty-stockQty);
                     demandDaoImpl demandDao=new demandDaoImpl();
                     boolean result=demandDao.addProduct(demandPojo);
                     if(result==true){
                         status+="We will mail you when "+(newQty-stockQty)+" no of items will be avaialble";
                     }                 
                     
                 }else{
                     cart.setQuantity(newQty);
                     status=updateProductInCart(cart);
                 }
             }
             
         }catch(SQLException ex){
            status="Addition failed due to exeption";
            System.out.println("Error in addProductToCart:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        return status;
}
    
    
    
    @Override
    public String updateProductInCart(CartPojo cart){
        String status="Failed to add to cart!";        
         Connection conn=DBUtil.provideConnection();
         PreparedStatement ps1=null;
         PreparedStatement ps2=null;
         ResultSet rs=null;
         int ans=0;
         try{
             ps1=conn.prepareStatement("Select * from USERCART where prodid=? and useremail=?");
             ps1.setString(1,cart.getProdid());
             ps1.setString(2,cart.getUseremail());
             rs=ps1.executeQuery();
             if(rs.next()){
                 int qty=cart.getQuantity();
                 if(qty>0){
                     ps2=conn.prepareStatement("Update USERCART set quantity=? where useremail=? and prodid=?");
                     ps2.setInt(1,cart.getQuantity());
                     ps2.setString(2,cart.getUseremail());
                     ps2.setString(3,cart.getProdid());
                     ans=ps2.executeUpdate();
                     if(ans>0){
                         status="Product successfully updated in the cart";
                    }
                }else if(qty==0){
                     ps2=conn.prepareStatement("Delete from USERCART where useremail=? and prodid=?");
                     ps2.setString(1,cart.getUseremail());
                     ps2.setString(2,cart.getProdid());
                     ans=ps2.executeUpdate();
                     if(ans>0){
                         status="Product successfully updated in the cart";
                    }else{
                         status="Could not updated the product";
                    }
                }
                 
                }else{
                 ps2=conn.prepareStatement("Insert into USERCART values(?,?,?)");
                 ps2.setString(1,cart.getUseremail());
                 ps2.setString(2,cart.getProdid());
                 ps2.setInt(3,cart.getQuantity());
                 ans=ps2.executeUpdate();
                 if(ans>0){
                     status="Product successfully added into the cart";
                 }else{
                         status="Could not add the product";
                    }
            }
         }catch(SQLException ex){
            status="Updation failed due to exeption";
            System.out.println("Error in updateProductInCart:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
        
    }
    
    @Override
        public List<CartPojo> getAllCartItems(String userId){
        List<CartPojo> itemList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
         try{
             ps=conn.prepareStatement("Select * from USERCART where useremail=?");
             ps.setString(1,userId);
             rs=ps.executeQuery();
             while(rs.next()){
                 CartPojo cart=new CartPojo();
                 cart.setUseremail(rs.getString("useremail"));
                 cart.setProdid(rs.getString("prodid"));
                 cart.setQuantity(rs.getInt("quantity"));
                 itemList.add(cart);                 
             }
         }catch(SQLException ex){            
            System.out.println("Error in getAllCartItems:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return itemList;
        }

    
    @Override
      public Boolean removeAProduct(String userId, String ProdId){
            boolean status=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("Delete from usercart where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2,ProdId);
            int k=ps.executeUpdate();
            if(k>0){
                status=true;
            }
        }catch(SQLException ex){
            
            System.out.println("Error in removeAProduct:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
            
}


    @Override
    public int getCartItemsCount(String userId, String itemId) {
        if(userId==null||itemId==null){
            return 0;
        }
        int count=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("Select quantity from USERCART where useremail=? and prodid=?");
            ps.setString(1,userId);
            ps.setString(2, itemId);
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt(1);
            }
        }catch(SQLException ex){
            
            System.out.println("Error in getAllCartItemCount:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return count;        
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status="Product removal failed!";        
         Connection conn=DBUtil.provideConnection();
         PreparedStatement ps1=null;
         PreparedStatement ps2=null;
         ResultSet rs=null;
         try{
             ps1=conn.prepareStatement("Select * from USERCART where prodid=? and useremail=?");
             ps1.setString(1,prodId);
             ps1.setString(2,userId);
             rs=ps1.executeQuery();
             if(rs.next()){
                 int prodQuantity=rs.getInt("quantity");
                 prodQuantity-=1;
                 if(prodQuantity>0){
                     ps2=conn.prepareStatement("Update usercart set quantity=? where useremail=? and prodid=?");
                     ps2.setInt(1,prodQuantity);
                     ps2.setString(2,userId);
                     ps2.setString(3,prodId);
                     int k=ps2.executeUpdate();
                     if(k>0){
                         status="Product successfully removed from the Cart";
                     }
                 }else{
                     ps2=conn.prepareStatement("Delete from usercart  where useremail=? and prodid=?");
                     ps2.setString(1,userId);
                     ps2.setString(2,prodId);
                     int k=ps2.executeUpdate();
                     if(k>0){
                         status="Product successfully removed from the Cart";
                     }
                 }
             }
         }catch(SQLException ex){
            status="Removal failed due to exeption";
            System.out.println("Error in removeProductFromCart:"+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
   }

    
   

    
}
