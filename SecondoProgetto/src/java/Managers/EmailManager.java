/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;
import Beans.Sale;
import Beans.User;
import java.security.Security;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import java.io.IOException;
import java.io.OutputStream;
   
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailManager {
    
    
    public void RecPasswordEmail(String email,String pass){
        try {

        
           //connessione
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.auth", "true");
           props.put( "mail.debug", "true" ); 
           

           final String username = "secondoprogettoweb@gmail.com";
           final String password = "secondopro";
           Session session = Session.getDefaultInstance(props, new Authenticator(){
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                    }   
               }                                        );
            
           Message msg = new MimeMessage(session);
           
           msg.setFrom(new InternetAddress(username + ""));
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(email,false));
           msg.setSubject("Recupero password");
           msg.setText("La tua password è: "+ pass +" non dimenticarla!");
           msg.setSentDate(new Date());
           Transport.send(msg);
       
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void DelAstaEmail(ArrayList buyer_list  ,User seller){
    try {
            
           User buyer;
           Iterator iter = buyer_list.iterator(); 
        
           //connessione
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.auth", "true");
           props.put( "mail.debug", "true" ); 
           
           
           final String username = "secondoprogettoweb@gmail.com";
           final String password = "secondopro";
           Session session = Session.getDefaultInstance(props, new Authenticator(){
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                    }   
               }                                        );
            
           Message msg = new MimeMessage(session);
           
           msg.setFrom(new InternetAddress(username + ""));
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(seller.getEmail(),false));
           msg.setSubject("Asta cancellata");
           msg.setText("L'ASTA è STATA CANCELLATA DAL AMMINISTRATORE");
           msg.setSentDate(new Date());
           Transport.send(msg);
           
           while (iter.hasNext()){
                buyer = (User) iter.next();
                Message mess = new MimeMessage(session);
           
                msg.setFrom(new InternetAddress(username + ""));
                msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(buyer.getEmail(),false));
                msg.setSubject("Asta cancellata");
                msg.setText("L'ASTA è STATA CANCELLATA DAL AMMINISTRATORE");
                msg.setSentDate(new Date());
                Transport.send(msg);
           } 
            
          
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        

        //asta annullata dal admin email a compratori e venditore
    }
    
    public void SoldEmail(User buyer,User seller, Sale sale,String auction_name){
    try {
            //connessione
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.auth", "true");
           props.put( "mail.debug", "true" ); 
           
           
           final String username = "secondoprogettoweb@gmail.com";
           final String password = "secondopro";
           Session session = Session.getDefaultInstance(props, new Authenticator(){
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                    }   
               }                                        );
            
           Message msg = new MimeMessage(session);
           
           msg.setFrom(new InternetAddress(username + ""));
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(buyer.getEmail(),false));
           msg.setSubject("Vincita Asta");
           msg.setText("hai visto l'asta di nome " +auction_name + "di " +seller.getUsername() +" pagando "+sale.getPrice()+" ");
           msg.setSentDate(new Date());
           Transport.send(msg);
            
            
          
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
      
     //asta ok email a  compratore
    }
    
    public void AnnullamentoAstaEmail(User seller){
        try {
            //connessione
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Get a Properties object
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.auth", "true");
           props.put( "mail.debug", "true" ); 
           
           
           final String username = "secondoprogettoweb@gmail.com";
           final String password = "secondopro";
           Session session = Session.getDefaultInstance(props, new Authenticator(){
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                    }   
               }                                        );
            
           Message msg = new MimeMessage(session);
           
           msg.setFrom(new InternetAddress(username + ""));
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(seller.getEmail(),false));
           msg.setSubject("Annullamento Asta");
           msg.setText("ASTA ANNULLATA cazzi tuoi");
           msg.setSentDate(new Date());
           Transport.send(msg);
            
            
          
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    //asta annullata per pochi soldi email a venditore
    }
    
    
     public void GenExcel(ArrayList sale_list, HttpServletResponse response, OutputStream out ){
        try
        {

            Iterator iter = sale_list.iterator();
            Sale sale;
            int i=1;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Commissioni.xls");
            WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
            WritableSheet s = w.createSheet("Commissioni", 0);

            s.addCell(new Label(0, 0, "SellerID"));
            s.addCell(new Label(0, 1, "Sell Date"));
            s.addCell(new Label(0, 2, "Price"));
            s.addCell(new Label(0, 3, "Commission"));
            
            while(iter.hasNext()){
                
                sale = (Sale) iter.next();
                
                s.addCell(new Label(i, 0, Integer.toString(sale.getSeller_id())));
                s.addCell(new Label(i, 1, Long.toString(sale.getSale_date())));
                s.addCell(new Label(i, 2, Float.toString(sale.getPrice())));
                s.addCell(new Label(i, 3, Float.toString(sale.getCommissions())));
                i++; 
            
            } 
            w.write();
            w.close();

       }      
       catch (Exception e)
       {
             try {
                 throw new ServletException("Exception in Excel Sample Servlet", e);
             } catch (ServletException ex) {
                 Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
             }
       }  
       finally
       { 
            if (out != null) {
               try {
                  out.close();
              } catch (IOException ex) {
                  Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
              }
           }
       }
         
    }
    
    
    
}
