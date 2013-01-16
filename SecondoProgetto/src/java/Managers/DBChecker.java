/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Auction;
import Beans.Sale;
import Beans.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Daniel
 */
public class DBChecker implements Runnable {
    
        private DBManager DBManager;
        private EmailManager Email;
        private static final Logger logger = Logger.getLogger("myLogger");
        
       public DBChecker(ServletContext context) {
             DBManager = (DBManager)context.getAttribute("DbManager"); 
             Email = (EmailManager)context.getAttribute("EmailManager");
         }
     
    @Override
     public void run() {
        
            ArrayList auction_list ;
            User seller , buyer;
            Sale sale;
            Auction auction;
            Timestamp sell_date;
            
            try{

            sell_date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            auction_list = DBManager.queryEndedAuctions(sell_date);
            Iterator iter = auction_list.iterator();

            logger.log(Level.SEVERE, "Start database check at {0}" , new Date().toString());

            while(iter.hasNext())
            {    
            auction = (Auction) iter.next();
            sale = new Sale();
            
            logger.log(Level.SEVERE, "---------------------------------------------------------------------");
            logger.log(Level.SEVERE , "Auction ID : {0}" , auction.getAuction_id() );
            logger.log(Level.SEVERE , "Auction NAME : {0}" , auction.getName() );
            logger.log(Level.SEVERE , "buyer_id  : {0}" , auction.getBuyer().getId());
            logger.log(Level.SEVERE , "isCanelled : {0}" , auction.isCancelled() );
            logger.log(Level.SEVERE , "CurrentPrice : {0}" , auction.getCurrent_price());
            logger.log(Level.SEVERE , "Min price  : {0}" , auction.getMin_price());

                      
            if(auction.isCancelled()){
                sale.setAuction_id(auction.getAuction_id());
                sale.setCancelled(true);
                sale.setSeller_id(auction.getSeller().getId());
                DBManager.saveEndedAuction(sale);
                
                
            }
            else if(auction.getCurrent_price() <= auction.getMin_price() || auction.getBuyer().getId() == -1){
                
                sale.setAuction_id(auction.getAuction_id());
                sale.setRetreat(true);
                sale.setRetreat_commissions((float) 1.23);
                sale.setSeller_id(auction.getSeller().getId());
                
                seller = DBManager.getUser(auction.getSeller().getId());
                
                DBManager.saveEndedAuction(sale);
                Email.AnnullamentoAstaEmail(seller, auction);
                
            }
            else
            {
                sale.setAuction_id(auction.getAuction_id());
                sale.setSold(true);
                sale.setSeller_id(auction.getSeller().getId());
                sale.setBuyer_id(auction.getBuyer().getId());
                
                float final_price = auction.getCurrent_price() + auction.getShipping_price();
                float commissions = (float) (final_price * 1.25 / 100);
                float value = Math.round(commissions);
                
                if((commissions - value) <= 0 )
                {
                commissions = value;
                }
                else
                {
                commissions = value + (float) 0.5;
                }
                
                sale.setCommissions(commissions);
                sale.setPrice(final_price);
                sale.setSale_date(auction.getExpiration());
            
                seller = DBManager.getUser(auction.getSeller().getId());
                buyer  = DBManager.getUser(auction.getBuyer().getId());
              
                
                DBManager.saveEndedAuction(sale);
                
                Email.SoldEmail(buyer, seller, sale, auction.getName());
            }
              
            }
            

            DBManager.deleteEndedAuctions(sell_date);

            logger.log(Level.SEVERE, "End database check at {0}" , new Date().toString());
            
            }catch(Exception ex){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null ,ex);
            }
         }
    
}
