/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Managers.DBManager;
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

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String DBurl , DBdriver;
            DBurl = sce.getServletContext().getInitParameter("DBurl");
            DBdriver = sce.getServletContext().getInitParameter("DBdriver");
            sce.getServletContext().setAttribute("DbManager", new DBManager(DBurl , DBdriver , sce.getServletContext()));                  
        } catch (Exception ex) {
            Logger.getLogger(Marshaller.Listener.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBManager.shutdown();
    }
}
