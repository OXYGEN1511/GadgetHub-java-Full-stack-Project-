
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(maxFileSize=161777215)
// to fetch the file from the website we use this annotation or accepting file uploaded from jspor Html;


public class AddProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
        String password = (String)session.getAttribute("password");
        String userType =(String)session.getAttribute("userType");
        
        
         if(userType==null || !userType.equalsIgnoreCase("admin")){
             response.sendRedirect("login.jsp?message=Acess denied ! Please login as Admin");
        }
         if(userName==null || password ==null){
             response.sendRedirect("login.jsp?message=Acess denied ! Please login again");
        }
         
         String status ="Product Registration Failed";
         String prodName = request.getParameter("name");
         String prodType = request.getParameter("type");
         String prodInfo = request.getParameter("info");
         double prodPrice = 0.0;
         int prodQuantity = 0;
          RequestDispatcher rd = null;
         String priceParam = request.getParameter("price");
         if(priceParam!= null){
             try{
                 prodPrice = Double.parseDouble(priceParam);
             }catch(NumberFormatException ex){
                 status = "Invalid unit price";
                 request.setAttribute("message",status);
                 rd = request.getRequestDispatcher("addProduct.jsp");
                 rd.forward(request,response);
             }
         }else{
             status = "Price cannot be left blank";
             request.setAttribute("message",status);
             rd = request.getRequestDispatcher("addProduct.jsp");
             rd.forward(request,response);
         }
          String qtyParam = request.getParameter("quantity");
         if(qtyParam!= null){
             try{
                 prodQuantity = Integer.parseInt(qtyParam);
             }catch(NumberFormatException ex){
                 status = "Invalid Quantity";
                 request.setAttribute("message",status);
                 rd = request.getRequestDispatcher("addProduct.jsp");
                 rd.forward(request,response);
             }
         }else{
             status = "Quantity cannot be left blank";
             request.setAttribute("message",status);
             rd = request.getRequestDispatcher("addProduct.jsp");
             rd.forward(request,response);
         }
//         fetching file
         
         Part part = request.getPart("image");
         InputStream img = part.getInputStream();
//         Sent the product to DB;
          ProductDaoImpl productDao = new ProductDaoImpl();
          ProductPojo product = new ProductPojo(null,prodName,prodType,prodInfo,prodPrice,prodQuantity,img);
          
          status = productDao.addProduct(product);
          request.setAttribute("message",status);
          rd = request.getRequestDispatcher("addProduct.jsp");
          rd.forward(request,response);
          
          
         
       
         
         
    }
       @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); 
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp); 
    }
      
    
    

}
