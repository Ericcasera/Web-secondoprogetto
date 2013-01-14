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
public class UserServlet extends HttpServlet {

    private DBManager DBManager;
    private static String addAcutionRequestPattern =  "AucRequest";
    private static String addAcutionConfirmPattern =  "AucConfirm";
    private static String offerRequestPattern =  "offreq";
    private static String offerConfirmPattern =  "offcon";
    private static String addAuctionPageRequestPattern = "addAuction";
    private static String DescriptionModificationPattern = "desc";
    
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
        
        else if(op.equals(offerRequestPattern))           
        {
        int user_id , prod_id;      
        
        try{   
            
        user_id = Integer.parseInt(request.getParameter("buyer_id"));
        prod_id = Integer.parseInt(request.getParameter("id"));
        
        }catch(Exception ex){  
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=home");
         return;
        }
        
        if(user_id == ((User)request.getSession().getAttribute("user")).getId())
        {        
            String message = "Sei già in testa, non puoi fare altre offerte!";
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + prod_id + "&message=" + message + "&type=-1");
            return;
        }
 
        request.getRequestDispatcher("/Jsp/UserPages/OfferConfirmPage.jsp").forward(request, response);

        }
        else if(op.equals(offerConfirmPattern))
        {
        
        int prod_id;
        float increment , offer , base_price;
        
        try{

        prod_id =     Integer.parseInt(request.getParameter("id"));
        increment =   Float.parseFloat(request.getParameter("increment"));
        offer =       Float.parseFloat(request.getParameter("offer"));
        base_price =  Float.parseFloat(request.getParameter("base_price"));
        
        if(!"offReq".equals(request.getParameter("prec_op")))
        {
        response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + prod_id);
        }   
        }catch(Exception ex){  
         response.sendError(HttpServletResponse.SC_BAD_REQUEST);
         return;
        }     
         
        if(DBManager.addNewOffer(prod_id, offer, ((User)request.getSession().getAttribute("user")).getId(), increment , base_price))
            {
            String message = "La tua offerta e' stata aggiunta con successo";
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + prod_id + "&message=" + message + "&type=1");
            }
            else
            {
            String message = "Non è stato possibile aggiungere la tua offerta";
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + prod_id + "&message=" + message + "&type=-1");  
            }
        }
        
        else if(op.equals(addAuctionPageRequestPattern))
        {
        String path = this.getServletContext().getRealPath("Images/"); 
        File folder = new File(path);
        String[] file_list = folder.list();
        
        request.setAttribute("file_list", file_list);           
        request.getRequestDispatcher("/Jsp/UserPages/AddProductPage.jsp").forward(request, response);
        }
        
        else if(op.equals(addAcutionRequestPattern))
        {  
        Auction tmp = new Auction();
            try{
            tmp.setDescription(request.getParameter("description"));
            tmp.setIncrement_price(Float.parseFloat(request.getParameter("increment")));
            tmp.setMin_price(Float.parseFloat(request.getParameter("min_price")));
            tmp.setName(request.getParameter("product_name"));
            User x = new User();
            x.setId(((User)request.getSession().getAttribute("user")).getId());
            tmp.setSeller(x);
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
            
            request.setAttribute("auction", tmp);
            request.getRequestDispatcher("/Jsp/UserPages/AddProductConfirmPage.jsp").forward(request, response);
            
        }
                
        else if(op.equals(addAcutionConfirmPattern))
        {
        Auction tmp = new Auction();
            try{
            tmp.setDescription(request.getParameter("description"));
            tmp.setIncrement_price(Float.parseFloat(request.getParameter("increment_price")));
            tmp.setMin_price(Float.parseFloat(request.getParameter("min_price")));
            tmp.setName(request.getParameter("name"));
            User x = new User();
            x.setId(Integer.parseInt(request.getParameter("seller_id")));
            tmp.setSeller(x);
            tmp.setShipping_price(Float.parseFloat(request.getParameter("shipping_price")));
            tmp.setStarting_price(Float.parseFloat(request.getParameter("starting_price"))); 
            tmp.setImage_url(request.getParameter("image_name"));
            tmp.setExpiration(Long.parseLong(request.getParameter("expiration")));
            tmp.setCategory_id(Integer.parseInt(request.getParameter("category")));
            
            }catch(Exception ex){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
            }
            
            if(DBManager.addNewAuction(tmp)) {
                String message = "La tua asta e' stata aggiunta con successo";
                response.sendRedirect(request.getContextPath() + "/User/AccController?op=sells&message="+message+"&type=0");
            }
            else {
                String message = "Non è stato possibile aggiungere la tua asta";
                response.sendRedirect(request.getContextPath() + "/User/AccController?op=sells&message="+message+"&type=-1");
            }
            
        }
        
        else if(op.equals(DescriptionModificationPattern))
        {
            String desc = request.getParameter("desc");
            int prod_id = Integer.parseInt(request.getParameter("prod_id"));
            
            DBManager.modifyDescription(desc, prod_id);
            
            response.sendRedirect(request.getContextPath() + "/General/GeneralController?op=details&id=" + prod_id);  
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
