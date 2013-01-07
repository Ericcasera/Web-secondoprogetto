<%-- 
    Document   : OfferConfirmPage
    Created on : 4-gen-2013, 16.29.35
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form  method="post" action="<c:url value="/User/UserController?op=offcon&id=${requestScope.prod_id}"/>"> 
        <input type="hidden" name="increment" value="${requestScope.increment}">
        <input type="hidden" name="base_price" value="${requestScope.base_price}">
        <input type="hidden" name="prec_op" value="offReq">
        <div class="input-append ">
            <input class="input-small" type="text" name="offer" value="${requestScope.offer}" readonly="true">
            <span class="add-on">$</span> 
        </div> 
        <button type="submit" class="btn btn-primary">Fai un offerta</button>
        <a href="" class="btn btn-warning">Annulla</a>   
        </form>    

    </body>
</html>
