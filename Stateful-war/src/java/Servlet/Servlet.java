package Servlet;

import ejbs.Cart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = (Cart) InitialContext.doLookup("java:global/Stateful/Stateful-ejb/Cart!ejbs.Cart");
                session.setAttribute("cart", cart);
            }
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Stateful</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
                out.println("<form action=\"Servlet\">");
                out.println("<input type=\"text\" name=\"nombre\">");
                out.println("<input type=\"submit\">");

                if (request.getParameter("nombre").equals((String) "borrar")) {
                    cart.remove();
                    session.setAttribute("cart", null);
                } else {
                    cart.add(request.getParameter("nombre"));
                }
                for (Object shoppingCart : cart.getList())
                    out.println(shoppingCart);
                
                /*for (Iterator it = cart.getList().iterator(); it.hasNext();) {
                    String s = (String) it.next();
                    out.print(s + " ");
                }*/
                out.println("</body>");
                out.println("</html>");
            }
        } catch (NamingException ex) {
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}