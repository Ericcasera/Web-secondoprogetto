<%-- 
    Document   : AuctionDetailsPage
    Created on : 29-dic-2012, 19.57.31
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <title>JSP Page</title>
        <c:set value="${requestScope.auction}" var="auction"/>
        <c:set value="${requestScope.user}" var="seller"/>
        
        
    </head>
    <body>
        <h1>Hello World!</h1>
        
        Dati del prodotto :
        <h3>
        <c:out value="ID : ${auction.product_id} Nome : ${auction.name} Descrizione : ${auction.description}"/><br>
        <c:out value="Current price : ${auction.current_price} Shipping price : ${auction.shipping_price}"/><br>
        <c:out value="Increment price : ${auction.increment_price}  image_url : ${auction.image_url}"/><br>
        <c:out value="Cancelled : ${auction.cancelled}  time to expiration : ${auction.timeToExpiration}"/><br>
        <c:out value="Seller username : ${seller.username} User email : ${seller.email}"/><br>
        <c:out value="Offerte attuali : ${auction.current_offers} "/><br>
        </h3>
        
          <form class="form-inline" action="#" onsubmit="return false;" method="get">        
                           <div class="input-append">
              <input type="text" name="search_query" value="${auction.current_price}">
                                                  <span class="add-on">$</span> 
                           </div>
              
                 <button type="submit" id="submit_button" class="btn btn-primary">Fai un offerta</button><br>
                 <p class="text-info"><small>Il prezzo verrà incrementato automaticamente fino al raggiungimento della tua offerta</small></p>
          </form>
        
    
    
    </body>
</html>
