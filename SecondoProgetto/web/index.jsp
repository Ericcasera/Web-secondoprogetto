<%-- 
    Document   : index
    Created on : 22-dic-2012, 16.10.49
    Author     : Daniel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link href="Bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="Bootstrap/css/grafica.css" rel="stylesheet">
        <script src="Bootstrap/js/jquery-1.8.2.js"></script>
        <script src="Bootstrap/js/bootstrap.min.js"></script>
        <title>Login</title>
    </head>
    <body>       
        <div class="login well">
            <div class="login-title" >
                <h1> Secondo Progetto </h1>
                <br>
            </div>
            
            <form action="GuestController?op=login" method="post" class="form-horizontal">
                  <div class="control-group">
                      <label class="control-label" for="username">Username</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Username" type="text" id="username" name="username">
                         </div>
                  </div>
                  <div class="control-group">
                      <label class="control-label" for="password">Password</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Password" type="password" id="password" name="password">
                             <p> <small><u><a href="RecoverPasswordPage.jsp">Password dimenticata? Clicca qui.</a></u></small></p>
                         </div>
                  </div>
                
                <c:if test="${requestScope.message != null}">
                    <c:choose>
                       <c:when test="${requestScope.type == -1}">
                          <div align="center" class="control-group">
                                <div class="alert alert-error fade in">
                                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                                    <p algin="center" class="text-error"> <c:out value="${requestScope.message}"/></p>
                                </div>                
                          </div>
                       </c:when>           
                       <c:otherwise> 
                          <div align="center" class="control-group">
                                <div class="alert alert-success fade in">
                                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                                    <p algin="center" class="text-success"> <c:out value="${requestScope.message}"/></p>
                                </div>                
                          </div>
                       </c:otherwise>                       
                    </c:choose>
                </c:if> 
                
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Login</button>
                             <br><p> <small><u><a href="SubscriptionPage.jsp">Nuovo utente? Registrati qui.</a></u></small></p>
                         </div>
                  </div>                  
            </form>
        </div>
    </body>
</html>
