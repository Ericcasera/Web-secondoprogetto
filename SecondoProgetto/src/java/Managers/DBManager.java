/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Auction;
import Beans.Category;
import Beans.Offer;
import Beans.Sale;
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
    private ServletContext context;
 
    // <editor-fold defaultstate="collapsed" desc="DbManager methods. Click on the + sign on the left to edit the code.">    
    public DBManager(String dburl , String driver , ServletContext context){
                try {
                    
                Class.forName(driver, true, getClass().getClassLoader());
                con = DriverManager.getConnection(dburl); 
                this.context = context;
                
                } catch(Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null ,ex);
                }        
    }

    public static void shutdown(){
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }
            catch (Exception ex){
            }
          } 
    
    public User Autentication(String username , String password){
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
                    x.setId(rs.getInt("user_id"));
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
    
    public boolean newAccountSubscription(User tmp){
        
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
                    tmp.setId(rs.getInt("category_id"));
                    tmp.setName(rs.getString("CATEGORY_NAME"));
                    tmp.setDescription(rs.getString("category_DESCRIPTION"));
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
    
    public boolean addNewAuction(Auction auction){
            String query = "Insert into active_auctions (seller_id , category_id , starting_price , price_increment , current_price , "
                    + "min_price , image_url , shipping_price , start_date , end_date , auction_name , description) values"
                    + "(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, auction.getSeller().getId());
            stm.setInt(2, auction.getCategory_id());
            
            stm.setFloat(3, auction.getStarting_price());
            stm.setFloat(4, auction.getIncrement_price());
            stm.setFloat(5, auction.getStarting_price());
            stm.setFloat(6, auction.getMin_price());
            stm.setFloat(8, auction.getShipping_price());
            
            stm.setString(7, auction.getImage_url());
            stm.setString(11, auction.getName());
            stm.setString(12, auction.getDescription());
            
            
            stm.setTimestamp(9, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            stm.setTimestamp(10, new Timestamp(auction.getExpiration()));

            int result = stm.executeUpdate();

            if(result == 0) {
                    return false;
                }
            
            stm.close();
            
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
     
    public int countAuction(int category_id , String pattern ){
            String query;
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            if(category_id == -1)
            {
            query = "Select count(*) as records "
                    + " from active_auctions "
                    + " where (auction_name like ? OR description like ?) and end_date > ? "; 
            }
            else
            {
            query = "Select count(*) as records "
                    + " from active_auctions "
                    + " where (auction_name like ? OR description like ?) and end_date > ? and category_id = ? ";
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
            
            rs.next();
            
            return rs.getInt("records");
 
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{          
                   stm.close(); rs.close();  
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
            return 0;
      
    } 
     
    public ArrayList queryAuctionsSearch(int category_id , String pattern , int order , int offset , int per_page){
        
            String query;
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Auction tmp;
            
            String order_by = " order by ";
            
            switch (order)
            {
                case 1: order_by  += "auction_name";  break; //1 = auction_name  
                case 2: order_by  += "current_price"; break; //2 = current_price  
                case 3: order_by  += "end_date";      break; //3 = end_date  
            }
            
            order_by += " OFFSET " + offset + " ROWS FETCH FIRST "+ per_page +" ROWS ONLY";
            
            if(category_id == -1)
            {
            query = "Select auction_id , current_price , image_url , shipping_price , end_date , auction_name , description , cancelled "
                    + " from active_auctions "
                    + " where (auction_name like ? OR description like ?) and end_date > ? "
                    + order_by;
            }
            else
            {
            query = "Select auction_id , current_price , image_url , shipping_price , end_date , auction_name , description , cancelled "
                    + " from active_auctions "
                    + " where (auction_name like ? OR description like ?) and end_date > ? and category_id = ? "
                    + order_by;
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
                    tmp.setAuction_id(rs.getInt("auction_id"));
                    tmp.setCurrent_price(rs.getFloat("current_price"));
                    tmp.setImage_url(rs.getString("image_url"));
                    tmp.setShipping_price(rs.getFloat("shipping_price"));
                    tmp.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp.setName(rs.getString("auction_name"));
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
      
    public Auction queryAuctionDetails(int auction_id){
        
            PreparedStatement stm = null ;
            PreparedStatement stm2 = null;
            PreparedStatement stm3 = null;
            ResultSet rs = null;
            ResultSet rs2 = null;
            ResultSet rs3 = null;
            Auction tmp_auction = null;
            User seller = null , buyer = null;
            float max_price = 0;
            

            String query = "Select * "
                    + " from active_auctions join users on user_id = seller_id "
                    + " where auction_id = ? and end_date > ? ";
            
            String query2 = "Select count(*) as offers  , max(price) as max_price from auto_increment_offers where auction_id = ?";
            
            String query3 = "Select username , user_id from auto_increment_offers natural join users "
                    + " where price = ? and auction_id = ? ";
              
            try {     
            stm = con.prepareStatement(query);
      
            stm.setInt(1, auction_id);
            stm.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));

            rs = stm.executeQuery();

            if(rs.next()) //Il prodotto è tra gli active , di conseguenza trovo il seller e il numero delle offerte 
                {
                    tmp_auction = new Auction();
                    seller = new User();

                    tmp_auction.setAuction_id(rs.getInt("auction_id"));
                    tmp_auction.setCurrent_price(rs.getFloat("current_price"));
                    tmp_auction.setImage_url(rs.getString("image_url"));
                    tmp_auction.setShipping_price(rs.getFloat("shipping_price"));
                    tmp_auction.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp_auction.setName(rs.getString("auction_name"));
                    tmp_auction.setDescription(rs.getString("description"));
                    tmp_auction.setCancelled(rs.getBoolean("cancelled"));
                    tmp_auction.setCategory_id(rs.getInt("category_id"));
                    tmp_auction.setIncrement_price(rs.getInt("price_increment"));
                    tmp_auction.setStarting_price(rs.getFloat("starting_price"));
                    
                    seller.setUsername(rs.getString("username")); //Dati del seller
                    seller.setEmail(rs.getString("email"));
                   
                    tmp_auction.setSeller(seller); 
                    
                    stm2 = con.prepareStatement(query2);
                    stm2.setInt(1, auction_id);
                    rs2 = stm2.executeQuery(); 
                                     
                    if(rs2.next())
                    {
                        tmp_auction.setCurrent_offers(rs2.getInt("offers"));
                        max_price = rs2.getFloat("max_price");
                    
                        stm2.close(); rs2.close();            

                        stm3 = con.prepareStatement(query3);
                        stm3.setInt(2, auction_id);
                        stm3.setFloat(1, max_price);   
                        rs3 = stm3.executeQuery();
                        buyer = new User();
                    
                        if(rs3.next())
                            {
                            buyer.setUsername(rs3.getString("username")); 
                            buyer.setId(rs3.getInt("user_id"));
                            tmp_auction.setBuyer(buyer);
                            }      
                        stm3.close();
                        rs3.close();
                    }
                    rs.close(); 
            
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
            
            return tmp_auction;
    } 
    
    public synchronized boolean addNewOffer(int auction_id , float offer , int user_id , float increment , float base_price){
            if(!checkAuctionDate(auction_id))
            {
            return false;
            }
            
            String insertQuery = "Insert into users_offers (auction_id , user_id , max_price , offer_date)"
                    + "values (? , ? , ? , ?)";
            String checkQuery  = "Select current_price from active_auctions where auction_id = ?";
            PreparedStatement insertStm = null ;
            PreparedStatement checkStm = null ;
            ResultSet rs;
            
            
            try {     
                
            checkStm  = con.prepareStatement(checkQuery);
            insertStm = con.prepareStatement(insertQuery); 
            
            checkStm.setInt(1, auction_id);
            rs = checkStm.executeQuery();
            float max_price = -1;
            
            if(rs.next()){
                max_price =  rs.getFloat("current_price");       
            }
            
            rs.close();
            
            if(offer <= max_price){
                return false;
            }
    
            insertStm.setInt(1, auction_id);
            insertStm.setInt(2, user_id);
            insertStm.setFloat(3, offer);     
            insertStm.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));

            int result = insertStm.executeUpdate();

            if(result == 0) {
                    return false;
                }
            
            updateOffers(auction_id , increment , base_price);
            
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
            return false;
        }
        finally {
                try{     
                   insertStm.close(); checkStm.close();  
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                     return false;
                 }
            }
            
        return true;
     }
    
    private synchronized void updateOffers(int auction_id , float increment , float base_price){
         
            String checkQuery = "Select user_id , max_price from users_offers where auction_id = ? order by max_price desc";
            
            String insertQuery = "Insert into auto_increment_offers (auction_id , user_id , price , offer_date)"
                    + "values (? , ? , ? , ?)";
  
            String insertQuery2 = "Update active_auctions set current_price = ? where auction_id = ? ";
            
            PreparedStatement insertStm = null ;
            PreparedStatement checkStm = null ;
            PreparedStatement insertStm2 = null ;
            ResultSet rs;
            
             try {     
                
            checkStm  = con.prepareStatement(checkQuery);
            insertStm = con.prepareStatement(insertQuery);
            insertStm2 = con.prepareStatement(insertQuery2);
            
            checkStm.setInt(1, auction_id);
            rs = checkStm.executeQuery();
            
            float first_price = -1;
            float second_price = -1;
            float sum = -1;
            int first_id = -1 , second_id = -1;
                      
            rs.next();
            
            first_price = rs.getFloat("max_price");
            first_id = rs.getInt("user_id");
            
            if(rs.next()){
            second_price = rs.getFloat("max_price");
            second_id = rs.getInt("user_id");
            }
            
            rs.close();
            
            if(second_price == -1 || second_price == base_price)
            {
            sum = base_price + increment;
            insertStm.setInt(1, auction_id);
            insertStm.setInt(2, first_id);
            insertStm.setFloat(3, sum);     
            insertStm.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));  
            insertStm.executeUpdate();
            }
            else
            {
                sum = second_price + increment;
                
                if(second_price == first_price)
                {
                sum = first_price;
                }
                
                insertStm.setInt(1, auction_id);
                
                if(sum != first_price)
                {
                    insertStm.setInt(2, second_id);
                    insertStm.setFloat(3, second_price);     
                    insertStm.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));     
                    insertStm.executeUpdate();
                }
            
            insertStm.setInt(2, first_id);
            insertStm.setFloat(3, sum);
            insertStm.setTimestamp(4, new Timestamp(Calendar.getInstance().getTimeInMillis()));  
            
            insertStm.executeUpdate(); 
            }
  
            insertStm2.setFloat(1, sum);
            insertStm2.setInt(2, auction_id);
            
            insertStm2.executeUpdate();
                    

        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{     
                   insertStm.close(); checkStm.close(); insertStm2.close(); 
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            } 
  
    
     }
     
    public boolean checkAuctionDate(int auction_id){
    
            String query = "Select auction_id from active_auctions where auction_id = ? and end_date > ? ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, auction_id);
            stm.setTimestamp(2, new Timestamp(Calendar.getInstance().getTimeInMillis()));  
            
            rs = stm.executeQuery();
            
            if(rs.next())
                {
                   return true;
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
            
        return false;
    }
    
    public ArrayList queryEndedAuctions(Timestamp sell_date){
        
            PreparedStatement stm = null ;
            ResultSet rs = null;
            Auction tmp;
            ArrayList list = new ArrayList(40);
            
            String query = "Select a.auction_id as auction_id , seller_id , current_price , min_price , shipping_price , end_date , "
                    + " auction_name , description , cancelled , user_id "
                    + " from active_auctions a left join auto_increment_offers o on (CURRENT_PRICE = PRICE and a.auction_ID = o.auction_ID)"
                    + " where end_date <= ? ";
              
            try {     
            stm = con.prepareStatement(query);
            
            stm.setTimestamp(1, sell_date);
            
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Auction();
                    tmp.setAuction_id(rs.getInt("auction_id"));
                    tmp.setCurrent_price(rs.getFloat("current_price"));
                    tmp.setShipping_price(rs.getFloat("shipping_price"));
                    tmp.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp.setName(rs.getString("auction_name"));
                    tmp.setMin_price(rs.getFloat("min_price"));
                    tmp.setDescription(rs.getString("description"));
                    tmp.setCancelled(rs.getBoolean("cancelled"));
                    User seller = new User();
                    seller.setId(rs.getInt("seller_id"));
                    tmp.setSeller(seller);
                    
                    int x = -1;
                    String buyer_id = rs.getString("user_id");
                    try{
                    x = Integer.parseInt(buyer_id);
                    }catch(Exception e){
                    x = -1;
                    }
                    User buyer = new User();
                    buyer.setId(x);
                    tmp.setBuyer(buyer);
                    list.add(tmp);
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
            
            return list;
    }
    
    public int deleteEndedAuctions(Timestamp sell_date){
        
            PreparedStatement stm = null ;

            String query = "Delete from active_auctions where end_date <= ? ";
              
            try {     
            stm = con.prepareStatement(query);
            
            stm.setTimestamp(1, sell_date);
            
            return stm.executeUpdate();
             

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
            
        return 0;
    }
 
    public User getUser(int user_id){
        
            PreparedStatement stm = null ;
            ResultSet rs = null;
            User tmp = null;
            
            String query = "Select * from users where user_id = ?";
              
            try {     
            stm = con.prepareStatement(query);
            
            stm.setInt(1, user_id);
            
            rs = stm.executeQuery();
            
            if(rs.next())
                {
                    tmp = new User();
                    tmp.setAddress(rs.getString("address"));
                    tmp.setCity(rs.getString("city"));
                    tmp.setEmail(rs.getString("email"));
                    tmp.setId(user_id);
                    tmp.setPhone(rs.getString("phone"));
                    tmp.setUsername(rs.getString("username"));
                    
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
            
            return tmp;
    }
    
    public boolean saveEndedAuction(Sale sale){
        
            PreparedStatement auction_stm = null ;
            PreparedStatement sale_stm = null ;
            
            String auction_query = null;
            
            String sale_query = "Insert into sales (auction_id , buyer_id , seller_id , price , commissions , sale_date) values "
                                + "(? , ? , ? , ? , ? , ?)";
              
            if(sale.isCancelled()) {
            auction_query = "Update ended_auctions set CANCELLED = true where auction_id = ?";
            }
            else if(sale.isRetreat()){
            auction_query = "Update ended_auctions set RETREAT = true , RETREAT_COMMISSIONS = ? where auction_id = ?";
            }
            else{
            auction_query = "Update ended_auctions set SOLD = true  where auction_id = ?";
            }

            try {     
            auction_stm = con.prepareStatement(auction_query);
            
            if(sale.isRetreat())
            {
            auction_stm.setFloat(1, sale.getRetreat_commissions());
            auction_stm.setInt(2, sale.getAuction_id());    
            }
            else{
            auction_stm.setInt(1, sale.getAuction_id());    
            }
            
            if(auction_stm.executeUpdate() == 0){
            return false;
            }
            
            if(!sale.isSold()){
            return true;
            }                
            
            sale_stm = con.prepareStatement(sale_query);
            sale_stm.setInt(1, sale.getAuction_id());
            sale_stm.setInt(2, sale.getBuyer_id());
            sale_stm.setInt(3, sale.getSeller_id());
            sale_stm.setFloat(4, sale.getPrice());
            sale_stm.setFloat(5, sale.getCommissions());
            sale_stm.setTimestamp(6, new Timestamp(sale.getSale_date()));
            
            sale_stm.executeUpdate();
            
            sale_stm.close();
   
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{          
                   auction_stm.close(); 
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
            return true;
    } 
    
    public ArrayList queryOffersLog(int auction_id){
        
            PreparedStatement stm = null ;
            ResultSet rs = null;
            Offer tmp;
            ArrayList list = new ArrayList(60);
            

            String query = "Select username , user_id , price , offer_date "
                         + " from AUTO_INCREMENT_OFFERS natural join users "
                         + " where auction_id = ?"
                         + " order by offer_date" ;
              
            try {     
            stm = con.prepareStatement(query);
            
            stm.setInt(1, auction_id);
            
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Offer();
                    tmp.setDate(rs.getTimestamp("offer_date").getTime());
                    tmp.setPrice(rs.getFloat("price"));
                    tmp.setUser_id(rs.getInt("user_id"));
                    tmp.setUsername(rs.getString("username"));
                    list.add(tmp);
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
            
            return list;
    }
    // </editor-fold>  
    
    public ArrayList queryUserActiveSells(int user_id){
        
            PreparedStatement stm = null ;
            ResultSet rs = null;
            Auction tmp;
            ArrayList list = new ArrayList(20);

            String query = "Select * from active_auctions where seller_id = ?";
              
            try {     
            stm = con.prepareStatement(query);
            
            stm.setInt(1, user_id);
            
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Auction();
                    tmp.setAuction_id(rs.getInt("auction_id"));
                    tmp.setCurrent_price(rs.getFloat("current_price"));
                    tmp.setImage_url(rs.getString("image_url"));
                    tmp.setShipping_price(rs.getFloat("shipping_price"));
                    tmp.setExpiration(rs.getTimestamp("end_date").getTime());
                    tmp.setMin_price(rs.getFloat("min_price"));            
                    tmp.setName(rs.getString("auction_name"));
                    tmp.setDescription(rs.getString("description"));
                    list.add(tmp);
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
            
            return list;
    }
    
}
