/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;
import Beans.Auction;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



public class EmailManager {
    final boolean Gmail = true;   
    final String username = "secondoprogettoweb@gmail.com";
    final String password = "secondopro";
    
    public Session ConnectEmail(){
       /*if(Gmail)
       {
           //connessione*/
           Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
           final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
           Properties props = System.getProperties();
           props.setProperty("mail.smtp.host", "smtp.gmail.com");
           props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
           props.setProperty("mail.smtp.socketFactory.fallback", "false");
           props.setProperty("mail.smtp.port", "465");
           props.setProperty("mail.smtp.socketFactory.port", "465");
           props.put("mail.smtp.auth", "true");
           props.put( "mail.debug", "true" ); 

           Session session = Session.getDefaultInstance(props, new Authenticator(){
               @Override
               protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                    }});
         return session;
       /*}
     
       else
       {
          java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
          Properties props = new Properties();
          props.put("mail.smtp.host", "mail.unitn.it");
          props.put("mail.smtp.port","587");
          props.put("mail.debug", "true");
          props.put("mail.smtp.debug", "true");
          props.put("mail.smtp.starttls.enable", "true");
          props.put("mail.transport.protocol", "smtp");
          props.put("mail.smtp.socketFactory.port", "587");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.socketFactory.fallback", "false");
          Authenticator auth = new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication(){
                  return new PasswordAuthentication("myUsername", "myPassword");
              }
          };
         Session session = Session.getInstance(props,auth);
         return session;
       }*/
    }
   
    public void RecPasswordEmail(String email,String pass){
        try {

           Message msg = new MimeMessage(ConnectEmail());
           InternetAddress from = new InternetAddress(username + "");
           msg.setFrom(from);
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(email,false));
           msg.setSubject("Recupero password");
           msg.setText("La tua password è: "+ pass +" \n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
           msg.setSentDate(new Date());
           Transport.send(msg);
       
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void AuctionCancelMail(ArrayList buyer_list , String message, Auction auction){
    try {
            
           User buyer;
           Iterator iter = buyer_list.iterator(); 

           Session session = ConnectEmail(); 
           Message msg = new MimeMessage(session);
           
           InternetAddress from = new InternetAddress(username + "");
           msg.setFrom(from);
           msg.setRecipients(Message.RecipientType.TO,
           InternetAddress.parse(auction.getSeller().getEmail(),false));
           msg.setSubject("Asta cancellata");
           msg.setText("L'asta "+ auction.getName() +" è stata annullata \n\n"+ message +".\n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
           msg.setSentDate(new Date());
           Transport.send(msg);
           
           while (iter.hasNext()){
                msg = new MimeMessage(session);
                buyer = (User) iter.next();
                msg.setFrom(from);
                msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(buyer.getEmail(),false));
                msg.setSubject("Asta cancellata");
                msg.setText("L'asta "+ auction.getName() +" è stata annullata \n\n"+ message +".\n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
                msg.setSentDate(new Date());
                Transport.send(msg);
           } 
            
          
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    
    public void SoldEmail(User buyer, User seller, Sale sale,String auction_name){
    try {

           Session session =  ConnectEmail(); 
           Message msg = new MimeMessage(session);
           InternetAddress from = new InternetAddress(username + "");
           msg.setFrom(from);
           msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(buyer.getEmail(),false));
           msg.setSubject("Aggiudicazione Asta");
           msg.setText("Ti sei aggiudicato l'asta contenente " +auction_name + ", venduta da " +seller.getUsername() +"\nA breve l'importo di "+sale.getPrice()+"$ verrà prelevato dal tuo conto corrente\n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
           msg.setSentDate(new Date());
           Transport.send(msg);
           
           msg.setFrom(from);
           msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(seller.getEmail(),false));
           msg.setSubject("Asta Terminata");
           msg.setText("La sua asta composta da " +auction_name + ", si è conclusa con la vincita di " +buyer.getUsername() +".\nA breve le sarà corrisposto sul suo conto un importo pari a "+sale.getPrice()+"\n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
           msg.setSentDate(new Date());
           Transport.send(msg); 
    
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    
    public void AuctionRetreatEmail(User seller, Auction auction){
        try {        
           Message msg = new MimeMessage(ConnectEmail());
           
           InternetAddress from = new InternetAddress(username + "");
           msg.setFrom(from);
           msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(seller.getEmail(),false));
           msg.setSubject("Annullamento Asta");
           msg.setText("Asta "+ auction.getName() +" ritirata perchè il prezzo ("+ auction.getCurrent_price() +"$) non ha raggiunto il prezzo minimo ("+ auction.getMin_price() +"$)\n\n\nCordiali Saluti.\nTrento Aste SpA.,\nLeader nella distibuzione agroalimentare bellica contrattuale nel mondo\n\n\nIl messaggio è stato generato automaticamente");
           msg.setSentDate(new Date());
           Transport.send(msg);
     
        } catch (MessagingException ex) {
            Logger.getLogger(EmailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    //asta annullata per pochi soldi email a venditore
    }
    
    
     public void GenExcel(ArrayList sale_list, HttpServletResponse response){
        try
        {

            Iterator iter = sale_list.iterator();
            Sale sale;
            int i=1;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Commissioni.xls");
            WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
            WritableSheet s = w.createSheet("Commissioni", 0);
            
            WritableFont formato = new WritableFont(WritableFont.ARIAL , 10 , WritableFont.BOLD);
            WritableFont formato1 = new WritableFont(WritableFont.ARIAL , 10);
            WritableCellFormat titolo = new WritableCellFormat (formato);
            WritableCellFormat corpo = new WritableCellFormat (formato1);
            
            titolo.setBackground(Colour.AQUA);
            titolo.setAlignment(Alignment.CENTRE);
            titolo.setBorder(jxl.format.Border.ALL, BorderLineStyle.MEDIUM);
            
            corpo.setAlignment(Alignment.CENTRE);
            corpo.setBorder(jxl.format.Border.ALL, BorderLineStyle.THICK);
            
            for(int x=0;x<=7;x++){
                CellView cell=s.getColumnView(x);
                cell.setAutosize(true);
                s.setColumnView(x, cell);
            }
            
            s.addCell(new Label(0, i, "ID", titolo));  
            s.addCell(new Label(1, i, "Prodotto", titolo));
            s.addCell(new Label(2, i, "Nome venditore",titolo));
            s.addCell(new Label(3, i, "Stato",titolo));  
            s.addCell(new Label(4, i, "Data di vendita ",titolo));
            s.addCell(new Label(5, i, "Prezzo di vendita",titolo));
            s.addCell(new Label(6, i, "Commissioni",titolo));
            s.addCell(new Label(7, i, "Nome compratore",titolo));

            Auction auc;
            i++;
            
            while(iter.hasNext()){
                
                sale = (Sale) iter.next();
                auc = sale.getAuction();
                s.addCell(new Label(0, i, "" + auc.getAuction_id(),corpo));     
                s.addCell(new Label(1, i, "" + auc.getName(),corpo));
                s.addCell(new Label(2, i, "" + auc.getSeller().getUsername(),corpo));
                s.addCell(new Label(4, i, "" + auc.getExpirationDate(),corpo));

                if(sale.isCancelled()){
                    s.addCell(new Label(3, i, "Annullata",corpo));
                    s.addCell(new Label(5, i, "-",corpo));
                    s.addCell(new Label(6, i, "-",corpo));
                    s.addCell(new Label(7, i, "-",corpo));
                }
                
                if(sale.isRetreat()){
                    s.addCell(new Label(3, i, "Ritirata",corpo));
                    s.addCell(new Label(5, i, "-",corpo));
                    s.addCell(new Label(6, i, "" + sale.getRetreat_commissions()+"$",corpo)); 
                    s.addCell(new Label(7, i, "-",corpo));
                }
                
                if(sale.isSold()){
                    s.addCell(new Label(3, i, "Venduta",corpo));
                    s.addCell(new Label(5, i, "" + sale.getPrice()+"$",corpo));
                    s.addCell(new Label(6, i, "" + sale.getCommissions()+"$",corpo));
                    s.addCell(new Label(7, i, "" + auc.getBuyer().getUsername(),corpo));
                }
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
         
    }
    
    
    
}
