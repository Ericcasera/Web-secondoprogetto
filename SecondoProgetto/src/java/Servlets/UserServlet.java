/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Managers.DBManager;
import java.io.File;
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
public class UserServlet extends HttpServlet {

    private DBManager DBManager;
    private String contextPath;
    private static String HomepagePattern =  "home";
    private static String ProductsPattern =  "products";
    private static String AddProductPattern =  "addProduct";
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");
            contextPath = this.getServletContext().getContextPath();          
        }   
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String op = request.getParameter("op");
        ArrayList category_list = DBManager.queryCategoryList();
        
        if(op == null) {
        
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        else if(op.equals(HomepagePattern))
        {  
        request.setAttribute("category_list", category_list);        
        request.getRequestDispatcher("/UserPages/Homepage.jsp").forward(request, response);
        }
        
        else if(op.equals(AddProductPattern))
        {  
          String path = this.getServletContext().getRealPath("Images/"); 
          File folder = new File(path);
          String[] file_list = folder.list();
          
          request.setAttribute("category_list", category_list);
          request.setAttribute("file_list", file_list); 
          request.getRequestDispatcher("/UserPages/AddProductPage.jsp").forward(request, response);
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
