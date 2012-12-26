<%-- 
    Document   : Homepage
    Created on : 23-dic-2012, 21.12.29
    Author     : Daniel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <title>Homepage</title>
    </head>
    <body>
        <h1>Hello , i'm a user home page</h1>
        <h2><c:out value="Ciao sono l'utente : ${sessionScope.user.username} e la mia mail è : ${sessionScope.user.email}"/></h2>
        
        <c:if test="${empty requestScope.category_list}">
            Non ci sono categorie
        </c:if>
                <br> 
            <form action="" method="post" class="form-inline">
                    <select>
                        <option value="-1"> Tutte le categorie </option>
                        <c:forEach items="${requestScope.category_list}" var="category">
                        <option value="${category.id}">${category.name}</option>              
                        </c:forEach>    
                    </select>
                    <input type="text" class="input-medium search-query">
                    <button type="submit" class="btn">Search</button>          
            </form>
                <br><c:out value="Time : ${time}"/>
        
                <br> Home page (Barra di ricerca + ultimi prodotti in vendita)
                <br>+
                <br>Link a
                <br>->Page del mio account con dati , 2 tab (jquery con Aste correnti e relativo link , mie vendite correnti con possibilità di modifica descrizione)
                <br>-> Le mie aste vinte
                <br>-> Le mie aste perse
                <br>-> Aggiungi prodotto in vendita <a href ="/SecondoProgetto/User/UserController?op=addProduct">AddProduct </a>
        
        
        
        
        
        
        
        <a href="/SecondoProgetto/LogoutController?op=logout">Logout</a>
        
    </body>
</html>
