
package in.gadgethub.servlet;

import in.gadgethub.dao.impl.ProductDaoImpl;
import in.gadgethub.pojo.ProductPojo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class UpdateProductByIdServlet extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
                String userName=(String)session.getAttribute("userName");
                String password=(String)session.getAttribute("password");
                String userType=(String)session.getAttribute("userType");
                if(userType==null || !userType.equalsIgnoreCase("admin")){
                    response.sendRedirect("login.jsp?message=Access denied ! Please login as admin");
                }else if(userName==null||password==null){
                    response.sendRedirect("login.jsp?message=Session expired ! Please login again");
                }
                String prodId = request.getParameter("prodId");
                if(prodId.isEmpty()){
                    RequestDispatcher rd=request.getRequestDispatcher("updateProduct.jsp");
                    request.setAttribute("message", "Please input a valid poduct id");
                    rd.forward(request, response);
                    return; 
                }
                
                ProductDaoImpl productDao = new ProductDaoImpl();
                ProductPojo product = productDao.getProductDetails(prodId);
                if(product == null){
                    RequestDispatcher rd=request.getRequestDispatcher("updateProduct.jsp");
                    request.setAttribute("message", "No product of given id found in id");
                    rd.forward(request, response);
                    return;
                }
                RequestDispatcher rd=request.getRequestDispatcher("updateProduct.jsp");
                    request.setAttribute("product", product);
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

//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }

}
