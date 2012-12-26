/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.User;
import Managers.DBManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class LoginServlet extends HttpServlet {
    
    private DBManager DBManager;
    private String contextPath;
    private static String loginPattern =  "login";
    private static String logoutPattern = "logout";
    private static String passwordRecoverPattern = "passwordRecover";
    private static String subscriptionPattern = "subscription";
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");
            contextPath = this.getServletContext().getContextPath();          
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String op = request.getParameter("op");

       if(op == null) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        
       else if(op.equals(loginPattern))
        {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
                User tmp = null;  
                
                tmp = DBManager.Autentication(username, password);
                //Caso autenticazione fallita
                if(tmp == null) {
                    request.setAttribute("message", "Username o password errati");
                    request.setAttribute("type", -1);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }    
                else
                {   //Login accettato , creo la session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", tmp);
                    
                    if(tmp.getRole() == 2) {
                        response.sendRedirect(contextPath + "/User/UserController?op=home");
                    }
                    else if(tmp.getRole() == 1) {
                        response.sendRedirect(contextPath + "/Admin/AdminController?op=home");
                    }    
                }
        }        
        else if(op.equals(logoutPattern))
        {
                    HttpSession session = request.getSession(false);
                    if(session != null)
                    {
                        session.invalidate();
                    }
                    request.setAttribute("message", "Logout effettuato con successo");
                    request.setAttribute("type", 1);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
        } 
        else if(op.equals(passwordRecoverPattern))
        {
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            
            String password = DBManager.recoverPassword(username, email);
            
            //Send email
            
            if(password != null)
            {
            request.setAttribute("message", "Riceverai a breve una mail con la password: "+ password);
            request.setAttribute("type", 1);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else
            {
            request.setAttribute("message", "I dati che hai inserito non sono validi");
            request.setAttribute("type", -1);
            request.getRequestDispatcher("index.jsp").forward(request, response);    
            }
            
        }
        else if(op.equals(subscriptionPattern))
        {
        
            User tmp = new User();
            tmp.setUsername(request.getParameter("username"));
            tmp.setAddress(request.getParameter("address"));
            tmp.setCity(request.getParameter("city"));
            tmp.setCountry(request.getParameter("country"));
            tmp.setPassword(request.getParameter("password"));
            tmp.setEmail(request.getParameter("email"));
            tmp.setPhone(request.getParameter("phone"));
            
            if(DBManager.newAccountSubscription(tmp))
            {
            request.setAttribute("message", "Account registrato correttamente");
            request.setAttribute("type", 1);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            else
            {
            request.setAttribute("message", "Non è stato possibile registrare l'account. Forse lo username o l'indirizzo email non sono univoci");
            request.setAttribute("type", -1);
            request.getRequestDispatcher("index.jsp").forward(request, response);    
            }           
        }
        else 
        {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);   
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
