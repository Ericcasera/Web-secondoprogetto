<%-- 
    Document   : ErrorPage
    Created on : 22-dic-2012, 18.27.00
    Author     : Daniel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script type="text/javascript">
           var ss = 5;
               function countdown() {
                   ss = ss-1;
                   if (ss==0) {
                       window.location = "<c:url value="${requestScope.redirectURL}"/>";
               }
                   else {
                       document.getElementById("countdown").innerHTML=ss;
                       window.setTimeout("countdown()", 1000);
                   }}
        </script>
        
        
        <title>Errore</title>
    </head>
    <body onload="countdown()">
        <center>
        <h1>Non sei autorizzato ad accedere a questa pagina</h1>
        <h3 >Sarai reindirizzato a breve alla <a href="<c:url value="${requestScope.redirectURL}"/>"><c:out value="${requestScope.redirectMSG}"/></a>(<span id="countdown">5</span>)</h3>
        </center>
    </body>
    </html>
      

