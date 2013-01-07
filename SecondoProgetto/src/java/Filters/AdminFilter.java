/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Beans.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class AdminFilter implements Filter {
    
    private String contextPath;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse  res = (HttpServletResponse) response;
      
        HttpSession session = req.getSession(false);
        
        if(session == null || session.getAttribute("user") == null)
        {
           res.sendRedirect(contextPath + "/GuestController?op=loginReq");
        }
        else
        {
            int role = ((User) session.getAttribute("user")).getRole();
            if(role == 1)
            {
             chain.doFilter(request, response);
            }
            else if(role == 2)
            {  
             req.setAttribute("URL","/General/GeneralController?op=home");
             req.setAttribute("Page", "Homepage");
             req.getRequestDispatcher("/Jsp/ErrorPage.jsp").forward(request, response);   
            }
            else
            {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
        
    
    @Override
    public void destroy() {
        
    }
   
    @Override
    public void init(FilterConfig filterConfig) {
        contextPath = filterConfig.getServletContext().getContextPath();
    }
}
