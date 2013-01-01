/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Auction;
import Managers.DBManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
public class GeneralServlet extends HttpServlet {

    private DBManager DBManager;
    private static String homepagePattern =  "home";
    private static String searchPattern =  "search";
    private static String auctionDetailsPattern =  "details";
    private static String offersLogPattern = "log";
    
    
    @Override
    public void init() throws ServletException {
            DBManager = (DBManager)super.getServletContext().getAttribute("DbManager");        
        }   
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String op = request.getParameter("op");
        ArrayList category_list = DBManager.queryCategoryList();
        
        if(op == null || op.equals(homepagePattern)) {     
             
            request.setAttribute("category_list", category_list);        
            request.getRequestDispatcher("/Jsp/GeneralPages/Homepage.jsp").forward(request, response);
        }
              
        else if(op.equals(searchPattern))
        {
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            String pattern  = request.getParameter("search_query");
            int order       = Integer.parseInt(request.getParameter("order"));
            int offset      = Integer.parseInt(request.getParameter("offset"));
            int per_page    = Integer.parseInt(request.getParameter("per_page"));
        
            ArrayList result = DBManager.queryAuctionsSearch(category_id, pattern , order , offset , per_page);
            int  records = DBManager.countAuction(category_id, pattern);
            

            String json;
            
            if(records == 0) {
                json = "[{\"elem\" : \""+ records + "\"}";
            }
            else {
                json = "[{\"elem\" : \""+ records + "\"},";
            }
            
            Iterator iter = result.iterator();      
            while(iter.hasNext())
            {
                Auction tmp = (Auction) iter.next();
            json += "{\"id\":\""+ tmp.getProduct_id() +"\", ";
            json += "\"name\":\""+ tmp.getName() +"\", ";
            json += "\"description\":\""+ tmp.getDescription() +"\", ";
            json += "\"expiration\":\""+ tmp.getTimeToExpiration() +"\", ";
            json += "\"image_url\":\""+ tmp.getImage_url() +"\", ";
            json += "\"current_price\":\""+ tmp.getCurrent_price() +"\", ";
            json += "\"shipping_price\":\""+ tmp.getShipping_price() +"\" }";
            if(iter.hasNext()) {
                    json += " , ";
                }
            }
            json += "]";
            
            response.setContentType("application/json");
            response.getWriter().write(json);  
        }
        
        else if(op.equals(auctionDetailsPattern))
        {       
            int auction_id = -1;
            try{
            auction_id = Integer.parseInt(request.getParameter("id"));
   
            }catch(Exception e){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        
            ArrayList result = DBManager.queryAuctionDetails(auction_id);
        
            if(result.isEmpty())
            {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }       
            
            request.setAttribute("auction", result.get(0));
            request.setAttribute("user", result.get(1));
            request.setAttribute("winner", result.get(2));
            request.getRequestDispatcher("/Jsp/GeneralPages/AuctionDetailsPage.jsp").forward(request, response);
        
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
