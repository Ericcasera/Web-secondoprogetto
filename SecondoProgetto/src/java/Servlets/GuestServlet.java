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
public class GuestServlet extends HttpServlet {
    
    private DBManager DBManager;
    private String contextPath;
    private static String loginConfirmPattern =  "loginCon";
    private static String loginRequestPattern =  "loginReq";
    private static String logoutPattern = "logout";    
    private static String passwordRequestPattern = "pswReq";
    private static String passwordConfirmPattern = "pswCon";
    private static String subscriptionRequestPattern = "subReq";   
    private static String subscriptionConfirmPattern = "subCon";
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");
            contextPath = this.getServletContext().getContextPath();          
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String op = request.getParameter("op");

       if(op == null) {
            response.sendRedirect(contextPath + "/GuestController?op=loginReq"); 
        }
       
       else if (op.equals(loginRequestPattern))
       {
       request.getRequestDispatcher("/Jsp/GuestPages/LoginPage.jsp").forward(request, response);
       }
        
       else if(op.equals(loginConfirmPattern))
        {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
                User tmp = null;  
                
                tmp = DBManager.Autentication(username, password);
                //Caso autenticazione fallita
                if(tmp == null) {
                    String message = "Username o password errati";
                    response.sendRedirect(contextPath + "/GuestController?op=loginReq&message="+message+"&type=-1");   
                }    
                else
                {   //Login accettato , creo la session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", tmp);   
                    response.sendRedirect(contextPath + "/General/GeneralController?op=home");   
                }
        }        
        else if(op.equals(logoutPattern))
        {
                    HttpSession session = request.getSession(false);
                    if(session != null)
                    {
                        session.invalidate();
                    }
                    response.sendRedirect(contextPath + "/General/GeneralController?op=home");
        }
        
        else if(op.equals(passwordRequestPattern))
        {
        request.getRequestDispatcher("/Jsp/GuestPages/RecoverPasswordPage.jsp").forward(request, response);
        }
        
        else if(op.equals(subscriptionRequestPattern))
        {
        request.getRequestDispatcher("/Jsp/GuestPages/SubscriptionPage.jsp").forward(request, response);
        } 
        
        else if(op.equals(passwordConfirmPattern))
        {
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            
            String password = DBManager.recoverPassword(username, email);
            
            //Send email
            
            if(password != null)
            {
                String message = "Riceverai a breve una mail con la password";
                response.sendRedirect(contextPath + "/GuestController?op=loginReq&message="+message+"&type=0");   
            }
            else
            {
                String message = "I dati che hai inserito non sono validi";
                response.sendRedirect(contextPath + "/GuestController?op=loginReq&message="+message+"&type=-1");     
            }
            
        }
        else if(op.equals(subscriptionConfirmPattern))
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
                String message = "Registrazione avvenuta con successo";
                response.sendRedirect(contextPath + "/GuestController?op=loginReq&message="+message+"&type=0");   
            }
            else
            {
                String message = "Non è stato possibile registrare l'account.";
                response.sendRedirect(contextPath + "/GuestController?op=loginReq&message="+message+"&type=-1");   
            }           
        }
        else 
        {
            response.sendRedirect(contextPath + "/GuestController?op=loginReq");  
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
