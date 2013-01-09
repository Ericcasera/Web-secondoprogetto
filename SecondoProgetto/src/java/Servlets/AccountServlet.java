/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Auction;
import Beans.User;
import Managers.DBManager;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
public class AccountServlet extends HttpServlet {

    private DBManager DBManager;
    private static String accountPattern =  "account";
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");        
        }   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String op = request.getParameter("op");   
        request.setAttribute("category_list", DBManager.queryCategoryList()); 
        
        if(op == null) {
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=home");
        }
        
        if(op.equals(accountPattern))
        {
        request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response);
        }
                    /*String json = "[{\"elem\" : \""+ records + "\"},";
            
            Iterator iter = result.iterator();      
            while(iter.hasNext())
            {
                Auction tmp = (Auction) iter.next();
            json += "{\"id\":\""+ tmp.getAuction_id() +"\", ";
            json += "\"name\":\""+ tmp.getName() +"\", ";
            json += "\"description\":\""+ tmp.getDescription() +"\", ";
            json += "\"expiration\":\""+ tmp.getTimeToExpiration() +"\", ";
            json += "\"image_url\":\""+ tmp.getImage_url() +"\", ";
            json += "\"current_price\":\""+ tmp.getCurrent_price() +"\", ";
            json += "\"shipping_price\":\""+ tmp.getShipping_price() +"\"},";
            }
            
            json = json.replaceAll(",$", "]");

            response.setContentType("application/json");
            response.getWriter().write(json);*/

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
