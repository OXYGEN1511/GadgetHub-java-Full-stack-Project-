/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.dao;
import in.gadgethub.pojo.OrderDetails;
import in.gadgethub.pojo.OrderPojo;
import in.gadgethub.pojo.transaction;
import java.util.List;


/**
 *
 * @author Asus
 */
public interface OrderDao {
    public boolean addOrder(OrderPojo order);
    public boolean addTransaction(transaction transaction);
    public List<OrderPojo> getAllOrders();
    public List<OrderDetails> getAllOrderDetails(String userEmailId);
    public String shipNow(String orderId,String prodId);
    public String paymentSuccess(String username,double paidAmount);
    
    
    
}
