/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Category;
import Beans.Auction;
import Beans.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Daniel
 */
public class DBManager {
    
    private transient Connection con;
    private String contextPath;
    private ServletContext context;
    
    
    /**
     *  
     * @param dburl url del database
     * @param driver driver per ConnectionManager
     * @param context servletContext della web application (viene salvata come variabile privata. In caso serva)
     */
    public DBManager(String dburl , String driver , ServletContext context){
                try {
                    
                Class.forName(driver, true, getClass().getClassLoader());
                con = DriverManager.getConnection(dburl); 
                contextPath = context.getContextPath();
                this.context = context;
                
                } catch(Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null ,ex);
                }        
    }
    
    
    /**
     * Questo metodo è statico e viene invocato dal listener per chiudere la connessione al database
     */
    public static void shutdown() 
          {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }
            catch (Exception ex){
            }
          } 
    
        /**
     * Esegue l'autenticazione di un utente
     * @param username username dell'utente
     * @param password password dell'utente
     * @return NULL se l'utente non è stato trovato , User se l'utente è stato trovato
     */
    
    public User Autentication(String username , String password)
        {
            String query = "Select * from users where username = ? and password = ? ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if(rs.next())
                {
                    User x = new User();
                    x.setUsername(username);
                    x.setPassword(password);
                    x.setId(rs.getInt("id"));
                    x.setRole(rs.getInt("role"));
                    x.setCity(rs.getString("city"));
                    x.setAddress(rs.getString("address"));
                    x.setEmail(rs.getString("email"));
                    x.setPhone(rs.getString("phone"));
                    x.setCountry(rs.getString("country"));
                    rs.close(); 
                    return x;
                }       
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
        return null;
     
     }
    
    private boolean checkAccoutAvailability(User tmp){
            String query = "Select * from users where username = ?";
            
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setString(1, tmp.getUsername());
            //stm.setString(2, tmp.getEmail());
            rs = stm.executeQuery();
            if(rs.next())
                {
                    return false;
                }       
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }  
        return true;
    }
    
    public boolean newAccountSubscription(User tmp){
        
            if(!this.checkAccoutAvailability(tmp)) {
            return false;
        }
        
            String query = "Insert into users (username , password , role , email , country , city , address , phone , creation_date)" 
                    + "values ( ? , ? , ? , ? , ? , ? , ? , ? , ?) ";
            
            PreparedStatement stm = null ;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setString(1, tmp.getUsername());
            stm.setString(2, tmp.getPassword());
            stm.setInt(3, 2);
            stm.setString(4, tmp.getEmail());
            stm.setString(5, tmp.getCountry());
            stm.setString(6, tmp.getCity());
            stm.setString(7, tmp.getAddress());   
            stm.setString(8, tmp.getPhone()); 
            stm.setTimestamp(9, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
            int result = stm.executeUpdate();
            if(result == 1)
            {
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }  
        return false;
    }
    
    public String recoverPassword(String username , String email){
            String query = "Select * from users where username = ? and email = ?";
            
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, email);
            rs = stm.executeQuery();
            if(rs.next())
                {
                    return rs.getString("password");
                }       
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }  
        return null;
    } 
    
    
    public ArrayList queryCategoryList(){
    
            String query = "Select * from category order by category_name";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Category tmp;
            
            try {     
            stm = con.prepareStatement(query);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Category();
                    tmp.setId(rs.getInt("ID"));
                    tmp.setName(rs.getString("CATEGORY_NAME"));
                    tmp.setDescription(rs.getString("DESCRIPTION"));
                    lista.add(tmp);
                }
            rs.close(); 
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{          
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
        return lista;
    }
    
    public boolean addNewAuction(Auction auction)
        {
            String query = "Insert into active_auctions (seller_id , category_id , starting_price , price_increment , current_price , "
                    + "min_price , image_url , shipping_price , start_date , end_date , product_name , description) values"
                    + "(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, auction.getSeller_id());
            stm.setInt(2, auction.getCategory_id());
            
            stm.setFloat(3, auction.getStarting_price());
            stm.setFloat(4, auction.getIncrement_price());
            stm.setFloat(5, auction.getStarting_price());
            stm.setFloat(6, auction.getMin_price());
            stm.setFloat(8, auction.getShipping_price());
            
            stm.setString(7, auction.getImage_url());
            stm.setString(11, auction.getName());
            stm.setString(12, auction.getImage_url());
            
            
            stm.setTimestamp(9, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            stm.setTimestamp(10, new Timestamp(auction.getExpiration()));

            int result = stm.executeUpdate();

            if(result == 0 || copyAuction(auction) == 0) {
                    return false;
                }
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
            return false;
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                     return false;
                 }
            }
            
        return true;
     }
    
    
     private int copyAuction(Auction auction)
        {
            String query = "Insert into ended_auctions (seller_id , category_id , starting_price , price_increment ,"
                    + "min_price , image_url , shipping_price , start_date , end_date , product_name , description) values"
                    + "(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, auction.getSeller_id());
            stm.setInt(2, auction.getCategory_id());
            
            stm.setFloat(3, auction.getStarting_price());
            stm.setFloat(4, auction.getIncrement_price());
            stm.setFloat(5, auction.getMin_price());
            stm.setFloat(7, auction.getShipping_price());
            
            stm.setString(6, auction.getImage_url());
            stm.setString(10, auction.getName());
            stm.setString(11, auction.getDescription());
            
            
            stm.setTimestamp(8, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            stm.setTimestamp(9, new Timestamp(auction.getExpiration()));


            int result = stm.executeUpdate();

            return result;
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
            return 0;
        }
        finally {
                try{     
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                     return 0;
                 }
            }
     }
     
    public ArrayList queryAuctionsSearch(int category_id , String pattern){
        
            String query;
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Auction tmp;
            
            if(category_id == -1)
            {
            query = "Select id , current_price , image_url , shipping_price , end_date , product_name , description"
                    + " from active_auctions "
                    + " where (product_name like ? OR description like ?) and end_date > ?"
                    + "order by product_name"; 
            }
            else
            {
            query = "Select id , current_price , image_url , shipping_price , end_date , product_name , description"
                    + " from active_auctions "
                    + " where (product_name like ? OR description like ?) and end_date > ? and category_id = ?"
                    + "order by product_name";
            }
              
            try {     
            stm = con.prepareStatement(query);
            String match_pattern = "%" + pattern + "%";
            stm.setString(1, match_pattern);
            stm.setString(2, match_pattern);
            stm.setTimestamp(3, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
            if(category_id != -1)
            {
            stm.setInt(4, category_id);
            }
            
            rs = stm.executeQuery();
            while(rs.next())
                {
                    tmp = new Auction();
                    tmp.setProduct_id(rs.getInt("id"));
                    tmp.setCurrent_price(rs.getFloat("current_price"));
                    tmp.setImage_url(rs.getString("image_url"));
                    tmp.setShipping_price(rs.getFloat("shipping_price"));
                    tmp.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp.setName(rs.getString("product_name"));
                    tmp.setDescription(rs.getString("description"));
                    lista.add(tmp);
                }
            rs.close(); 
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{          
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
        return lista;
    } 
    
    
    public ArrayList queryAuctionDetails(int auction_id){
        
            PreparedStatement stm = null ;
            PreparedStatement stm2 = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            Auction tmp_auction;
            User tmp_user;
            ArrayList list = new ArrayList(10);

            String query = "Select a.ID as id , a.CATEGORY_ID as category_id , a.PRICE_INCREMENT as price_increment , a.CURRENT_PRICE as current_price , "
                    + " a.IMAGE_URL as image_url , a.SHIPPING_PRICE as shipping_price , a.END_DATE as end_date , a.PRODUCT_NAME as product_name , "
                    + " a.DESCRIPTION as description , a.CANCELLED as cancelled , u.USERNAME as username , u.EMAIL as email "
                    + " from active_auctions a join users u on u.id = a.seller_id "
                    + " where a.ID = ? and a.end_date > ? ";
            String query2 = "Select count(*) as offers from auto_increment_offers where product_id = ?";
              
            try {     
            stm = con.prepareStatement(query);
            stm2 = con.prepareStatement(query2);
            
            stm.setInt(1, auction_id);
            stm.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            
            stm2.setInt(1, auction_id);
            
            rs = stm.executeQuery();
            rs2 = stm2.executeQuery();
            if(rs.next())
                {
                    tmp_auction = new Auction();
                    tmp_user = new User();
                    tmp_auction.setProduct_id(rs.getInt("id"));
                    tmp_auction.setCurrent_price(rs.getFloat("current_price"));
                    tmp_auction.setImage_url(rs.getString("image_url"));
                    tmp_auction.setShipping_price(rs.getFloat("shipping_price"));
                    tmp_auction.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp_auction.setName(rs.getString("product_name"));
                    tmp_auction.setDescription(rs.getString("description"));
                    tmp_auction.setCancelled(rs.getBoolean("cancelled"));
                    tmp_auction.setCategory_id(rs.getInt("category_id"));
                    tmp_auction.setIncrement_price(rs.getInt("price_increment"));
                    tmp_auction.setCancelled(rs.getBoolean("cancelled"));
                    
                    tmp_user.setUsername(rs.getString("username"));
                    tmp_user.setEmail(rs.getString("email"));
                    
                    if(rs2.next())
                    {
                    tmp_auction.setCurrent_offers(rs2.getInt("offers"));
                    }
                    list.add(tmp_auction);
                    list.add(tmp_user);
                }
            rs.close(); 
            rs2.close();       
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{          
                   stm.close(); stm2.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
            return list;
    }     
    
    
    
    
    
}
