/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Managers.DBManager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
public class AdminServlet extends HttpServlet {
    
    private DBManager DBManager;
    private static String topUsersPattern =  "top";
    private static String cancelAuctionPattern =  "cancel";
    private static String endedAuctionsPattern =  "ended";   
    private static String excelPattern =  "excel";  
    
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
        
        else if(op.equals(cancelAuctionPattern))     
        {
        String message = request.getParameter("message");
        int id = -1;
        try{
         id = Integer.parseInt(request.getParameter("prod_id"));
        }catch(Exception ex){
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=home"); 
        }
        
        DBManager.queryAdminCancelAuction(id);
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + id); 
        }
        
        else if(op.equals(endedAuctionsPattern))     
        {
        request.setAttribute("result", DBManager.queryAdminEndedAuctions());
        request.getRequestDispatcher("/Jsp/AdminPages/EndedAuctionsPage.jsp").forward(request, response);
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
