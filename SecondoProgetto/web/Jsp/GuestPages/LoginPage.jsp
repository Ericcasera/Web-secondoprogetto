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
        <link  href="<c:url value="/Bootstrap/css/grafica.css"/>" rel="stylesheet">
        <link  href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.3.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        <title>Login</title>
    </head>
    <body>       
        <div class="login well">
            <div class="login-title" >
                <img src="/SecondoProgetto/Images-site/logo.png" alt="logo">
                <br>
                <br>
            </div>
            
            <form action="<c:url value="/GuestController?op=loginCon"/>" method="post" class="form-horizontal">
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
                             <p> <small><u><a href="<c:url value="/GuestController?op=pswReq" />">Password dimenticata? Clicca qui.</a></u></small></p>
                         </div>
                  </div>
                
                  <jsp:include page="/Jsp/Message.jsp" flush="false"/>
                
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Login</button>
                             <a  class="btn" href="<c:url value="/" />">Annulla</a>
                         </div>
                  </div>                  
            </form>
        </div>
    </body>
</html>
