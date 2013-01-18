/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.User;
import Managers.DBManager;
import java.io.IOException;
import java.util.ArrayList;
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
    private static String activeSellsPattern =  "sells";
    private static String activeBuysPattern =  "buys";
    private static String soldPattern =  "sold";
    private static String wonPattern =  "won";
    private static String lostPattern =  "lost";
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");        
        }   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String op = request.getParameter("op"); 
        int user_id = ((User)request.getSession().getAttribute("user")).getId();
        request.setAttribute("category_list", DBManager.queryCategoryList()); 
        
        if(op == null) {
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=home");
            return;
        }
        
        if(op.equals(accountPattern))
        {
        request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response);
        }
        else if(op.equals(activeSellsPattern)){
            request.setAttribute("result", DBManager.queryUserActiveSells(user_id));
            request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response);
        }        
        else if(op.equals(activeBuysPattern)){
            request.setAttribute("result", DBManager.queryUserActiveBuys(user_id));
            request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response); 
        }
        else if(op.equals(soldPattern)){
            request.setAttribute("result", DBManager.queryUserEndedAuctions(user_id));
            request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response); 
        }
        else if(op.equals(wonPattern)){
            request.setAttribute("result", DBManager.queryUserWonAuctions(user_id));
            request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response); 
        }
        else if(op.equals(lostPattern)){
            request.setAttribute("result", DBManager.queryUserLostAuctions(user_id));
            request.getRequestDispatcher("/Jsp/UserPages/AccountPage.jsp").forward(request, response); 
        }
        else
        {
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=home");
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
