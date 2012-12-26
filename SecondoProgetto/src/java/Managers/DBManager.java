/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Category;
import Beans.Product;
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
    
    public boolean addNewProduct(Product product)
        {
            String query = "Insert into active_auctions (seller_id , category_id , starting_price , price_increment , current_price , "
                    + "min_price , image_url , shipping_price , start_date , end_date , product_name , description) values"
                    + "(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, product.getSeller_id());
            stm.setInt(2, product.getCategory_id());
            
            stm.setFloat(3, product.getStarting_price());
            stm.setFloat(4, product.getIncrement_price());
            stm.setFloat(5, product.getStarting_price());
            stm.setFloat(6, product.getMin_price());
            stm.setFloat(8, product.getShipping_price());
            
            stm.setString(7, product.getImage_url());
            stm.setString(11, product.getName());
            stm.setString(12, product.getImage_url());
            
            
            stm.setTimestamp(9, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            stm.setTimestamp(10, new Timestamp(product.getExpiration()));

            int result = stm.executeUpdate();

            if(result == 0 || copyProduct(product) == 0) {
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
    
    
     private int copyProduct(Product product)
        {
            String query = "Insert into ended_auctions (seller_id , category_id , starting_price , price_increment ,"
                    + "min_price , image_url , shipping_price , start_date , end_date , product_name , description) values"
                    + "(? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, product.getSeller_id());
            stm.setInt(2, product.getCategory_id());
            
            stm.setFloat(3, product.getStarting_price());
            stm.setFloat(4, product.getIncrement_price());
            stm.setFloat(5, product.getMin_price());
            stm.setFloat(7, product.getShipping_price());
            
            stm.setString(6, product.getImage_url());
            stm.setString(10, product.getName());
            stm.setString(11, product.getImage_url());
            
            
            stm.setTimestamp(8, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            stm.setTimestamp(9, new Timestamp(product.getExpiration()));


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
    
    
    
}
