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
        <link  href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.2.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        
        <c:set value="${requestScope.auction}" var="auction"/>
        <c:set value="${requestScope.user}" var="seller"/> 
        
        <script>

        function validate(){
                
                var regExpValue = /^\d+[.]?\d{0,2}/;
                var regExpCheck = /^\d+[.]?\d*$/;
                
                var form =  document.getElementById("offer_form");
                var offer = form.offer.value;
                var corretto = true;
     
                if(offer.search(regExpCheck) == -1) 
                    {
                        document.getElementById("error_span").innerHTML = "*Il formato non è corretto"; 
                        corretto = false;
                    }
                    else
                    {
                        document.getElementById("error_span").innerHTML = "";
                        var prezzo_iniziale = parseFloat(${auction.starting_price});
                        var incremento = parseFloat(${auction.increment_price});
                        var valore = (parseFloat(offer.match(regExpValue))) - prezzo_iniziale;
                        var incrementi = Math.round(valore / incremento);
                        var offerta_finale = ((parseFloat(incrementi)) * incremento) + prezzo_iniziale;
                        
                        form.offer.value = offerta_finale 
                    }
                        
                        return corretto;
        }
                     
                     
                     $(document).ready(function() {
                        $('#reset_btn').hide();
                        $('#submit_btn').hide();
                     
                     $('#control_btn').click(function(){
                        if (validate())
                        {
                            $('#control_btn').hide();
                            $('#reset_btn').show();
                            $('#submit_btn').show();
                            $('#input_offer').attr('readonly', true);
                        }});
                     $('#reset_btn').click(function(){
                            $('#reset_btn').hide();
                            $('#submit_btn').hide();
                            $('#control_btn').show();
                            $('#input_offer').removeAttr('readonly');
                        });
                     });
                     
        </script>
        
        
        <title>JSP Page</title>      
        
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
        <c:choose>
            <c:when test="${requestScope.winner.username == null}">
               <span class="text-success">Non ci sono ancora offerte per questo prodotto<br></span>
            </c:when>
            <c:otherwise>
                <span class="text-success"><c:out value="In testa si trova : ${requestScope.winner.username}." /><br></span> 
            </c:otherwise>
        </c:choose>

              
        <c:choose>
                <c:when test="${sessionScope.user.id == 1}">
                    <a href="<c:url value="/Admin/AdminController?op=cancel&id=${auction.product_id}"/>" class="btn btn-danger">Cancella l'asta</a><br>
                        <p class="text-info"><small>Il testo verrà inviato a tutti gli utenti coinvolti nell'asta</small></p> 
                    <textarea name="message" class="span5" rows="6">Gentili utenti, siamo costretti ad annullare quest'asta a causa di violazione di bla bla. La morte vi prenderà</textarea>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${requestScope.winner.id == sessionScope.user.id}">    
                           <p class="text-info"><small>Non puoi fare offerte dato che sei già in testa</small></p>     
                        </c:when>
                        <c:otherwise>
                            <form class="form-inline" action="<c:url value="/User/UserController?op=offer&id=${auction.product_id}"/>" method="post" id ="offer_form"> 
                                 <input type="hidden" name="increment" value="${auction.increment_price}">
                                 <input type="hidden" name="base_price" value="${auction.current_price}">
                                     <div class="input-append ">
                                         <input class="input-small" type="text" name="offer" value="${auction.current_price + auction.increment_price}" id="input_offer">
                                         <span class="add-on">$</span> 
                                     </div> 
                                 <a id="control_btn" class="btn btn-primary">Fai un offerta</a>
                                 <button id="submit_btn" type="submit" class="btn btn-success">Conferma offerta</button>
                                 <button id="reset_btn" type="reset" class="btn btn-warning">Annulla offerta</button><br>
                                 <p class="text-info"><small>Il prezzo verrà incrementato automaticamente fino al raggiungimento della tua offerta<br>
                                 E verrà arrotondato in modo da corrispondere all'incremento    
                                 </small></p>
                                 <span class="text-error" id="error_span"></span>
                            </form>      
                        </c:otherwise>
                    </c:choose>        
                </c:otherwise>     
        </c:choose>
                           
        <c:if test="${requestScope.message != null}">
                    <c:choose>
                       <c:when test="${requestScope.type == -1}">
                          <div align="center" class="span5">
                                <div class="alert alert-error fade in">
                                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                                    <p algin="center" class="text-error"> <c:out value="${requestScope.message}"/></p>
                                </div>                
                          </div>
                       </c:when>           
                       <c:otherwise> 
                          <div align="center" class="span5">
                                <div class="alert alert-success fade in">
                                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                                    <p algin="center" class="text-success"> <c:out value="${requestScope.message}"/></p>
                                </div>                
                          </div>
                       </c:otherwise>                       
                    </c:choose>
      </c:if> 
                            

        
    
    
    </body>
</html>
