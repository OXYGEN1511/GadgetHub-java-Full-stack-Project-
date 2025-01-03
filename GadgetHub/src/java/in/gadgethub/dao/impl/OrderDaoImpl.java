/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao.impl;

import in.gadgethub.dao.OrderDao;
import in.gadgethub.pojo.CartPojo;
import in.gadgethub.pojo.OrderDetails;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.transaction;
import in.gadgethub.utility.DBUtil;
import in.gadgethub.utility.IDutil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class OrderDaoImpl implements OrderDao {

    @Override
    public boolean addOrder(OrderPojo order) {
           boolean status =false;
           Connection conn = DBUtil.provideConnection();
           PreparedStatement ps = null;
           try{
               ps = conn.prepareStatement("Insert into orders value(?,?,?,?,?)");
               ps.setString(1,order.getOrderId());
               ps.setString(2, order.getProdId());
               ps.setInt(3,order.getQuantity());
               ps.setDouble(4,order.getAmount());
               ps.setInt(5,0);
               int count = ps.executeUpdate();
               status = count>0;
               
           }catch(SQLException ex){
               System.out.println("Error in addOrder: "+ex);
               ex.printStackTrace();
               
               
           }
           DBUtil.closeStatement(ps);
           return status;
           
    }

    @Override
    public boolean addTransaction(transaction transaction) {
         
           boolean status =false;
           Connection conn = DBUtil.provideConnection();
           PreparedStatement ps = null;
           try{
               ps = conn.prepareStatement("Insert into orders value(?,?,?,?,?)");
               ps.setString(1,transaction.getTransactionId());
               ps.setString(2, transaction.getUseremail());
               java.util.Date d1= transaction.getTransTime();
               java.sql.Date d2= new java.sql.Date(d1.getTime());
               ps.setDate(3,d2);
               ps.setDouble(4, transaction.getAmount());
               int count = ps.executeUpdate();
               status = count>0;
               
            }catch(SQLException ex){
               System.out.println("Error in addTransaction: "+ex);
               ex.printStackTrace();
            }
        DBUtil.closeStatement(ps);
        return status;
    }
    

    @Override
    public List<OrderPojo> getAllOrders() {
        Connection conn = DBUtil.provideConnection();
        List<OrderPojo> orderList =new ArrayList<>();
        Statement st = null;
        ResultSet rs =null;
        try{
            st = conn.createStatement();
            rs = st.executeQuery("Select * from ORDERS");
            while(rs.next()){
                OrderPojo order = new OrderPojo();
                order.setOrderId(rs.getString("orderId"));
                order.setProdId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
            
            
            
            
            
        }catch(SQLException ex){
            System.out.println("Error in getAllOrders "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        return orderList;

    }

    @Override
    public List<OrderDetails> getAllOrderDetails(String userEmailId) {
        List<OrderDetails>orderList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement("Select p.pid as prodid,o.orderid as orderid,o.shipped as shipped,p.image as image,p.pname as pname,o.quantity as qty,o.amount as amount,t.transtime as time FROM orders o,products p,transaction t where o.orderid = t.transid and o.pid = p.pid and t.useremail=?");
            ps.setString(1, userEmailId);
            rs = ps.executeQuery();
            while(rs.next()){
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setOrderId(rs.getString("orderid"));
                orderDetail.setImage(rs.getAsciiStream("image"));
                orderDetail.setProdId(rs.getString("prodid"));
                orderDetail.setProdName(rs.getString("pname"));
               orderDetail.setQty(rs.getInt("qty"));
               orderDetail.setAmount(rs.getDouble("amount"));
               orderDetail.setTime(rs.getTimestamp("time"));
               orderDetail.setShipped(0);
                
            }
        }catch(SQLException ex){
            System.out.println("Error in getAllOrderDetails"+ex);
            ex.printStackTrace();
        } 
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return orderList;
    }

    @Override
    public String shipNow(String orderId, String prodId) {
           String status = "Failure";
           Connection conn = DBUtil.provideConnection();
           PreparedStatement ps = null;
           try{
               ps = conn.prepareStatement("Update orders set shipped=1 where orderid=? and prodid=?");
               ps.setString(1, orderId);
               ps.setString(2, prodId);
               int count = ps.executeUpdate();
               if(count>0){
                   status = "Order has been shipped succesfully";
               }
           }catch(SQLException ex){
               
           System.out.println("Error in shipNow "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
                 
    }

    @Override
    public String paymentSuccess(String username, double paidAmount) {
        String status ="Order Placement failed";
        CartdaoImpl cartDao = new CartdaoImpl();
        List<CartPojo> cartList = cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            return status;
        }
        String transactionId = IDutil.generateTransId();
        transaction trPojo = new transaction();
        trPojo.setTransactionId(transactionId);
        trPojo.setUseremail(username);
        trPojo.setAmount(paidAmount);
        trPojo.setTransTime((Date) new java.util.Date());
        boolean result = addTransaction(trPojo);
        if(result==false){
            return status;
        }
        
        boolean ordered = true;
        ProductDaoImpl productdao = new ProductDaoImpl();
        for(CartPojo cartPojo:cartList){
            double amount = productdao.getProductPrice(cartPojo.getProdid())*cartPojo.getQuantity();
            OrderPojo order =new OrderPojo();
            order.setProdId(cartPojo.getProdid());
            order.setOrderId(transactionId);
            order.setQuantity(cartPojo.getQuantity());
            order.setAmount(amount);
            order.setShipped(0);
            ordered=addOrder(order);
            if(!ordered){
                break;
            }
            ordered = cartDao.removeAProduct(cartPojo.getUseremail(), cartPojo.getProdid());
            if(!ordered){
                break;
            }
            ordered = productdao.sellNProduct(cartPojo.getProdid(),cartPojo.getQuantity());
             if(!ordered){
                break;
            }
            
            
            
            
        }
        if(ordered){
            status = "Order places Succesfully";
            
        }
        
        
        
        return status;
        
    }
    
    
}
