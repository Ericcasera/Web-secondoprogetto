/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Auction;
import Beans.User;
import Managers.DBManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
public class ProductServlet extends HttpServlet {

    private DBManager DBManager;
    private String contextPath;
    private static String addAcutionRequestPattern =  "addAuctionRequest";
    private static String addAcutionConfirmPattern =  "addAuctionConfirm";
    private static String offerRequestPattern =  "requestOffer";
    private static String offerConfirmPattern =  "confirmOffer";
    
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
        
        else if(op.equals(addAcutionRequestPattern))
        {  
        Auction tmp = new Auction();
            try{
            tmp.setDescription(request.getParameter("description"));
            tmp.setIncrement_price(Float.parseFloat(request.getParameter("increment")));
            tmp.setMin_price(Float.parseFloat(request.getParameter("min_price")));
            tmp.setName(request.getParameter("product_name"));
            tmp.setSeller_id(((User)request.getSession().getAttribute("user")).getId());
            tmp.setShipping_price(Float.parseFloat(request.getParameter("shipping_price")));
            tmp.setStarting_price(Float.parseFloat(request.getParameter("starting_price")));
            tmp.setCategory_id(Integer.parseInt(request.getParameter("category")));
            
            String image = request.getParameter("image_name");
            if(image == null || image.equals(""))
                {
                    image = "no_image.jpg";
                }
            tmp.setImage_url(image);
            
            Calendar expiration = Calendar.getInstance();
            expiration.add(Calendar.DAY_OF_YEAR, Integer.parseInt(request.getParameter("day")));
            expiration.add(Calendar.HOUR, Integer.parseInt(request.getParameter("hour")));
            expiration.add(Calendar.MINUTE, Integer.parseInt(request.getParameter("min")));
            expiration.add(Calendar.SECOND, Integer.parseInt(request.getParameter("sec")));
            tmp.setExpiration(expiration.getTimeInMillis());       
            }catch(Exception ex){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
            }
            request.setAttribute("category_list", category_list);
            request.setAttribute("auction", tmp);
            request.getRequestDispatcher("/UserPages/AddProductConfirmPage.jsp").forward(request, response);
            
        }
                
        else if(op.equals(addAcutionConfirmPattern))
        {
        Auction tmp = new Auction();
            try{
            tmp.setDescription(request.getParameter("description"));
            tmp.setIncrement_price(Float.parseFloat(request.getParameter("increment_price")));
            tmp.setMin_price(Float.parseFloat(request.getParameter("min_price")));
            tmp.setName(request.getParameter("name"));
            tmp.setSeller_id(Integer.parseInt(request.getParameter("seller_id"))); 
            tmp.setShipping_price(Float.parseFloat(request.getParameter("shipping_price")));
            tmp.setStarting_price(Float.parseFloat(request.getParameter("starting_price"))); 
            tmp.setImage_url(request.getParameter("image_name"));
            tmp.setExpiration(Long.parseLong(request.getParameter("expiration")));
            tmp.setCategory_id(Integer.parseInt(request.getParameter("category")));
            
            }catch(Exception ex){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
            }
            
            boolean result = DBManager.addNewAuction(tmp);
            if(result == false) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else {
                request.getRequestDispatcher("/UserPages/Homepage.jsp").forward(request, response);
            }
            
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
