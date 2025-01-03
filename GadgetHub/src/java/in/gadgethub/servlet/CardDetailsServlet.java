/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gadgethub.servlet;

import in.gadgethub.dao.CartDao;
import in.gadgethub.dao.impl.CartdaoImpl;
import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.CartPojo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
    import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CardDetailsServlet extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
          HttpSession session=request.getSession();
                String userName=(String)session.getAttribute("userName");
                String password=(String)session.getAttribute("password");
                String userType=(String)session.getAttribute("userType");
                if(userType==null || password==null || userName==null){
                    response.sendRedirect("login.jsp?message=Access denied ! Please login first");
                    return;
                }
                String addStr = request.getParameter("add");
                if(addStr!=null){
                    int add = Integer.parseInt("add");
                    String uid = request.getParameter("uid");
                    String pid = request.getParameter("pid");
                    int avail = Integer.parseInt(request.getParameter("avail"));
                    int cartQty = Integer.parseInt(request.getParameter("qty"));
                    CartDao cartDao = new CartdaoImpl();
                    if(add==1){
                        cartQty+=1;
                        CartPojo cartPojo = new CartPojo();
                        cartPojo.setProdid(pid);
                        cartPojo.setQuantity(1);
                        cartPojo.setUseremail(uid);
                        cartDao.addProductToCart(cartPojo);
                        
                    }else if(add==0){
                        CartPojo cartPojo = new CartPojo();
                        cartPojo.setProdid(pid);
                        cartPojo.setQuantity(1);
                        cartPojo.setUseremail(uid);
                        cartDao.addProductToCart(cartPojo);
                        cartDao.updateProductInCart(cartPojo);
                    }
                    
                }
                CartdaoImpl cartDao = new CartdaoImpl();
                ProductDaoImpl productDao = new ProductDaoImpl();
                 List<CartPojo> cartItems = new ArrayList<>();
                 
                  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
