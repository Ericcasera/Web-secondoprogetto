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
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.2.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        <script>
           $(function() {
                    $('#submit_button').click(function() {
                        var category_id = $('#category_id').val();
                        var pattern = $('#search_query').val();
                        var url = "<c:url value="/User/UserController?op=search"/>";
                        url += "&category_id=" + category_id;
                        url += "&search_query=" + pattern;
                        $('#result_set').html("<span class=\"text-success\">Searching...</span>");
                        $.getJSON( url , function(data) {
                            var elem = 0;   
                            var result = "<table><tr><th class\"span3\">Immagine</th><th class=\"span1\">ID</th><th class=\"span2\">Nome</th><th class=\"span2\">Descprition</th><th class=\"span2\">Tempo alla scadenza</th><th class=\"span2\">Price</th></tr>";
                                 $.each(data , function(key, val) {
                                    result += "<tr><td><img height=\"100px\" width=\"100px\" src=\"<c:url value="/Images"/>/" + val.image_url + "\"/></td>";
                                    result += "<td> " + val.id + "</td>";
                                    result += "<td><a href=\"<c:url value="/User/UserController?op=details"/>&id="+val.id+" \">" + val.name + "</a></td>";
                                    result += "<td> " + val.description + "</td>";
                                    result += "<td> " + val.expiration + "</td>";
                                    result += "<td> " + val.current_price + "</td></tr>";
                                    elem ++;
                                    });
                            result += "</table>";
                            if(elem == 0)
                                result = "<h5>Non sono stati trovati elementi corrispondenti ai parametri di ricerca</h5>";
                            
                            $('#result_set').html(result);
                })
                .error(function() { $('#result_set').html("<h5 class=\"text-error\">Si è verificato un errore inatteso. Ci scusiamo per il disagio</h5>"); })
                        });
                    });
         
        </script>
        <title>Homepage</title>
    </head>
    <body>
        <h1>Hello , i'm a user home page</h1>
        <h2><c:out value="Ciao sono l'utente : ${sessionScope.user.username} e la mia mail è : ${sessionScope.user.email}"/></h2>
        
        <c:if test="${empty requestScope.category_list}">
            Non ci sono categorie
        </c:if>
                <br> 
            
          <form class="form-inline" action="#" onsubmit="return false;" method="get">
             <select id="category_id">
                <option value="-1" selected>Tutte le categorie</option> 
                  <c:forEach items="${requestScope.category_list}" var="category">
                        <option value="${category.id}">${category.name}</option>
                  </c:forEach>   
             </select>        
             <div class="input-append">
                 <input type="text" class="span4" name="search_query" id="search_query">                  
                 <button type="submit" id="submit_button" class="btn btn-primary">Search</button>  
             </div>
          </form>
                
                
                
    <center>
        <div id="order_div">
            <form class="form-inline" action="#" onsubmit="return false;" method="get">
                <label for="order_select">Ordina per</label>
                     <select id="order_select" name="order_select">
                         <option value="name" selected>Nome </option>
                        <option value="current_price">Prezzo</option>
                        <option value="end_date">Scadenza</option>
                    </select>
            </form>
        </div>
                <div id="result_set">
                    Ciao
                </div>
    </center>    
                <br><br><br>
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
