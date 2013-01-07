/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Managers.DBChecker;
import Managers.DBManager;
import Managers.EmailManager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.Marshaller;

/**
 * Web application lifecycle listener.
 *
 * @author Daniel
 */
public class ContextListener implements ServletContextListener {

    DBChecker checker;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String DBurl , DBdriver;
            DBurl = sce.getServletContext().getInitParameter("DBurl");
            DBdriver = sce.getServletContext().getInitParameter("DBdriver");
            sce.getServletContext().setAttribute("DbManager", new DBManager(DBurl , DBdriver , sce.getServletContext()));
            sce.getServletContext().setAttribute("EmailManager", new EmailManager());
            checker = new DBChecker(sce.getServletContext());
            scheduler.scheduleAtFixedRate(checker , 1 , 1 , TimeUnit.MINUTES);
            
        } catch (Exception ex) {
            Logger.getLogger(Marshaller.Listener.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdown();
        DBManager.shutdown();
    }
}
