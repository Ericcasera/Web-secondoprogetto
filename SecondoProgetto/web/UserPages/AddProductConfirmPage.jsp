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
        <c:set value="${requestScope.product}" var="prod"/>
        
       <c:out value="Nome : ${prod.name}"/><br>
       <c:out value="Seller id : ${prod.seller_id}"/><br>
       <c:out value="Prezzo partenza: ${prod.starting_price}"/><br>
       <c:out value="Incremento : ${prod.increment_price}"/><br>
       <c:out value="Prezzo minimo: ${prod.min_price}"/><br>
       <c:out value="Prezzo spedizione : ${prod.shipping_price}"/><br>
       <c:out value="Descrizione : ${prod.description}"/><br>
       <c:out value="Image : ${prod.image_url}"/><br>
       <c:out value="ID categoria : ${prod.category_id}"/><br>
       <c:forEach items="${requestScope.category_list}" var="category">
           <c:if test="${category.id == prod.category_id}">
               <c:out value="Categoria : ${category.name}"/><br>
           </c:if>            
       </c:forEach>        
       <c:out value="Mancano ${prod.timeToExpiration} alla scadenza"/><br>
       <c:out value="L'inserzione scadrà il : ${prod.expirationDate}"/><br>
       
       <form action="<c:url value="/User/ProductController?op=confirmAddProduct"/>" method="post">
           <input type="hidden" value="${prod.name}" name="name">
           <input type="hidden" value="${prod.description}" name="description">
           <input type="hidden" value="${prod.expiration}" name="expiration">
           <input type="hidden" value="${prod.image_url}" name="image_name">
           <input type="hidden" value="${prod.shipping_price}" name="shipping_price">
           <input type="hidden" value="${prod.min_price}" name="min_price">
           <input type="hidden" value="${prod.increment_price}" name="increment_price">
           <input type="hidden" value="${prod.starting_price}" name="starting_price">
           <input type="hidden" value="${prod.seller_id}" name="seller_id">
           <input type="hidden" value="${prod.category_id}" name="category">
           <button type="submit" >Conferma inserzione</button>
       </form>
       
       
    </body>
</html>
