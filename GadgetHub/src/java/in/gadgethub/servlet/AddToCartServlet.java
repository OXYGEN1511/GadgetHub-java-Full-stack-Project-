
package in.gadgethub.servlet; 
 
import in.gadgethub.dao.impl.CartdaoImpl; 
import in.gadgethub.dao.impl.demandDaoImpl; 
import in.gadgethub.dao.impl.ProductDaoImpl; 
import in.gadgethub.pojo.CartPojo; 
import in.gadgethub.pojo.demand; 
import in.gadgethub.pojo.ProductPojo; 
import java.io.IOException; 
import java.io.PrintWriter; 
import javax.servlet.RequestDispatcher; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession; 


public class AddToCartServlet extends HttpServlet { 
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        PrintWriter pw = response.getWriter(); 
        HttpSession session = request.getSession(); 
        String userName = (String) session.getAttribute("userName"); 
        String password = (String) session.getAttribute("password"); 
        String userType = (String) session.getAttribute("userType"); 
        if (userType == null || password == null || userName == null || !userType.equalsIgnoreCase("customer")) { 
            response.sendRedirect("login.jsp?message=Access denied ! Please login first"); 
            return; 
        } 
                
        String userId = userName; 
        String prodId = request.getParameter("pid"); 
        int pQty = Integer.parseInt(request.getParameter("pqty")); 
        String action = request.getParameter("action"); 
        CartdaoImpl cartDao = new CartdaoImpl(); 
        ProductDaoImpl productDao = new ProductDaoImpl(); 
        ProductPojo productPojo = productDao.getProductDetails(prodId); 
        int avQty = productDao.getProductQuantity(prodId); 
        int cartQty = cartDao.getCartItemsCount(userId, prodId); 
        pQty += cartQty; 
        if (action != null) { 
            cartDao.updateProductInCart(new CartPojo(userId, prodId, pQty)); 
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet"); 
            rd.forward(request, response); 
        } else if (pQty == cartQty) { 
            String status = cartDao.removeProductFromCart(userId, prodId); 
            RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
              //pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>"); 
            request.setAttribute("message",status); 
            rd.forward(request, response); 
 
        } else if (avQty < pQty) { 
            String status = null; 
            if (avQty == 0) { 
                status = "Product out of stock!"; 
            } else { 
                cartDao.updateProductInCart(new CartPojo(userId, prodId, avQty)); 
                status = "Only " + avQty + " no of " + productPojo.getProdName() + " are available, so we are adding only " + avQty + " products in your cart"; 
            } 
            demandDaoImpl demand = new demandDaoImpl(); 
            demand demandPojo = new demand(userId, prodId, pQty - avQty); 
            boolean flag = demand.addProduct(demandPojo); 
            if (flag) { 
                status += "<br/>Later we will mail you when " + productPojo.getProdName() + " will be available"; 
            } 
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet"); 
            //pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>"); 
            //rd.include(request, response); 
            request.setAttribute("message",status); 
            rd.forward(request, response); 
        }else{ 
            String status = cartDao.updateProductInCart(new CartPojo(userId, prodId, pQty)); 
            RequestDispatcher rd =
            request.getRequestDispatcher("userHome.jsp"); 
            //pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>"); 
            //rd.include(request, response); 
            request.setAttribute("message",status); 
            rd.forward(request, response); 
        } 
 
    } 
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


