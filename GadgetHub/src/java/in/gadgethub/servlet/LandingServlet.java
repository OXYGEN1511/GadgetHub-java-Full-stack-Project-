/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.CartdaoImpl;
import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LandingServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session=request.getSession();
        
        String userName=(String)session.getAttribute("username");
        String userType=(String)session.getAttribute("userType");
        String search=request.getParameter("search");
        String type=request.getParameter("type");
        
        ProductDaoImpl productDao=new ProductDaoImpl();
        CartdaoImpl cartDao=new CartdaoImpl();
        String message="All Products";
        List<ProductPojo>products=new ArrayList<>();
        if(search!=null){
            products=productDao.searchAllProducts(search);
            message="Showing results for '"+search+"'";
            
        }else if(type!=null){
            products=productDao.getAllProductsByType(type);
            message="Showing results for '"+type+"'";
        }else{
            products=productDao.getAllProducts();            
        }
        if(products.isEmpty()){
            products=productDao.getAllProducts();
            message="No items found for '"+(search!=null?search:type)+"'";
        }
        Map<String,Integer>map=new HashMap<>();
        for(ProductPojo product:products){
               int qty=cartDao.getCartItemsCount(userName,product.getProdid());
               map.put(product.getProdid(), qty);
        }
        RequestDispatcher rd=request.getRequestDispatcher("Index.jsp");
        request.setAttribute("userName",userName);
        request.setAttribute("message",message);
        request.setAttribute("products",products);
        request.setAttribute("map",map);
        rd.forward(request, response);              
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
}
			

