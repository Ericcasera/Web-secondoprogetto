<%-- 
    Document   : AddProductConfirmPage
    Created on : 24-dic-2012, 21.46.00
    Author     : Daniel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:set value="${requestScope.auction}" var="auction"/>
        
       <c:out value="Nome : ${auction.name}"/><br>
       <c:out value="Seller id : ${auction.seller_id}"/><br>
       <c:out value="Prezzo partenza: ${auction.starting_price}"/><br>
       <c:out value="Incremento : ${auction.increment_price}"/><br>
       <c:out value="Prezzo minimo: ${auction.min_price}"/><br>
       <c:out value="Prezzo spedizione : ${auction.shipping_price}"/><br>
       <c:out value="Descrizione : ${auction.description}"/><br>
       <c:out value="Image : ${auction.image_url}"/><br>
       <c:out value="ID categoria : ${auction.category_id}"/><br>
       <c:forEach items="${requestScope.category_list}" var="category">
           <c:if test="${category.id == prod.category_id}">
               <c:out value="Categoria : ${category.name}"/><br>
           </c:if>            
       </c:forEach>        
       <c:out value="Mancano ${auction.timeToExpiration} alla scadenza"/><br>
       <c:out value="L'inserzione scadrà il : ${auction.expirationDate}"/><br>
       
       <form action="<c:url value="/User/UserController?op=AucConfirm"/>" method="post">
           <input type="hidden" value="${auction.name}" name="name">
           <input type="hidden" value="${auction.description}" name="description">
           <input type="hidden" value="${auction.expiration}" name="expiration">
           <input type="hidden" value="${auction.image_url}" name="image_name">
           <input type="hidden" value="${auction.shipping_price}" name="shipping_price">
           <input type="hidden" value="${auction.min_price}" name="min_price">
           <input type="hidden" value="${auction.increment_price}" name="increment_price">
           <input type="hidden" value="${auction.starting_price}" name="starting_price">
           <input type="hidden" value="${auction.seller_id}" name="seller_id">
           <input type="hidden" value="${auction.category_id}" name="category">
           <button type="submit" >Conferma inserzione</button>
       </form>
       
       
    </body>
</html>
