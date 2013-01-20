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
     public void run() { //Medoto schedulato ogni minuto
        
            ArrayList auction_list ;
            User seller , buyer;
            Sale sale;
            Auction auction;
            Timestamp sell_date;
            
            try{

            sell_date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            auction_list = DBManager.queryEndedAuctions(sell_date); //Ottiene tutti le aste terminate
            Iterator iter = auction_list.iterator();

            logger.log(Level.SEVERE, "Start database check at {0}" , new Date().toString());

            while(iter.hasNext())
            {    
            auction = (Auction) iter.next();
            sale = new Sale();

            //Per ogni asta prepara l'oggetto sale con relativi attributi, invia le mail o lo salva su database
            if(auction.isCancelled()){ //Caso asta cancellata
                sale.setAuction_id(auction.getAuction_id());
                sale.setCancelled(true);
                sale.setSeller_id(auction.getSeller().getId());
                DBManager.saveEndedAuction(sale);  
            }
            
            else if(auction.getCurrent_price() <= auction.getMin_price() || auction.getBuyer().getId() == -1){
            //Caso asta che non raggiunge il prezzo minimo oppure che non ha offerte    
                sale.setAuction_id(auction.getAuction_id());
                sale.setRetreat(true);
                sale.setRetreat_commissions((float) 1.23);
                sale.setSeller_id(auction.getSeller().getId());
                
                seller = DBManager.getUser(auction.getSeller().getId());
                
                DBManager.saveEndedAuction(sale);
                Email.AuctionRetreatEmail(seller, auction);
                
            }
            else
            {//Caso asta venduta con successo
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
            //finita l'iterazione cancella tutte le aste finite della tabella delle aste attive
            DBManager.deleteEndedAuctions(sell_date);

            logger.log(Level.SEVERE, "End database check at {0}" , new Date().toString());
            
            }catch(Exception ex){
                logger.log(Level.SEVERE, "End database check at {0}" , new Date().toString());
            }
         }
    
}
