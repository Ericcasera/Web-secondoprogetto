<%-- 
    Document   : RecoverPasswordPage
    Created on : 22-dic-2012, 17.56.54
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link href="Bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="Bootstrap/css/grafica.css" rel="stylesheet">
        <title>Recupero Password</title>
    </head>
    <body>
        <div class="login well">
            <div class="login-title" >
                <h3> Recupero Password </h3>
            </div>  
            <form action="<c:url value="/GuestController?op=pswCon"/>" method="post" class="form-horizontal">
                  <div class="control-group">
                      <label class="control-label" for="username">Username</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Username" type="text" name="username">
                         </div>
                  </div>   
                  <div class="control-group">
                      <label class="control-label" for="email">Email</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Email" type="text" name="email">
                         </div>
                  </div>              
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Invia richiesta</button>
                             <a class="btn" href="<c:url value="/GuestController?op=loginReq"/>">Annulla</a>
                         </div>
                  </div>                 
            </form>
        </div>
    </body>
</html>
