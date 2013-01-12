<%-- 
    Document   : AuctionDetailsPage
    Created on : 29-dic-2012, 19.57.31
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

<script>

        function validate(){
                
                var regExpValue = /^\d+[.]?\d{0,2}/;
                var regExpCheck = /^\d+[.]?\d*$/;
                
                var form =  document.getElementById("offer_form");
                var offer = form.offer.value;
                var corretto = true;
                var prezzo_corrente = parseFloat(${auction.current_price});
     
                if(offer.search(regExpCheck) == -1) 
                    {
                        document.getElementById("error_span").innerHTML = "*Il formato non è corretto"; 
                        corretto = false;
                    }
                else if(parseFloat(offer.match(regExpValue)) < prezzo_corrente+parseFloat(${auction.increment_price}))
                    {
                        document.getElementById("error_span").innerHTML = "*L'offerta deve essere maggiore del prezzo corrente"; 
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

</script>

    <title>Dettagli</title>
    <c:set value="${requestScope.auction}" var="auction"/>

</head>

<jsp:include page="/Jsp/Body.jsp" flush="false"/>
<table>
    <tr>
        <td class="span4">
        <img src="<c:url value="/Images/${auction.image_url}"/>" width="250px" height="250px"/>
        </td>
        <td class="span7">
            <table>
            <tr>
                <td>
                    <h4><c:out value="${auction.name}"/></h4>         
                </td>
            </tr>
            <tr>
            <td>
                <c:out value="Venduta da : ${auction.seller.username}"/><br>
                <c:out value="Email: ${auction.seller.email}"/><br><br>
            </td>
            </tr>
            <tr>
            <td>
            <strong><span class="text-error"> ${auction.timeToExpiration}</span> alla scadenza.</strong><br>
                Prezzo corrente : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br> 
                Incremento minimo : <strong><span class="text-error"> ${auction.increment_price}</span>$</strong><br><br> 
                <c:choose>
                    <c:when test="${auction.cancelled} ">
                        <span class="label label-important">Asta anullata</span>     
                    </c:when>   
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${auction.buyer == null}">
                                <span class="text-info">Non ci sono ancora offerte per questo prodotto<br><br></span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-info"><c:out value="Offerte attuali : ${auction.current_offers} "/></span>                        
                                [<a href="<c:url value="/General/GeneralController?op=log&id=${auction.auction_id}"/>">Log offerte</a>]<br>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${sessionScope.user.role == 1}">
                                <a href="<c:url value="/Admin/AdminController?op=cancel&id=${auction.auction_id}"/>" class="btn btn-danger">Cancella l'asta</a><br>
                                <p class="text-info"><small>Il testo verrà inviato a tutti gli utenti coinvolti nell'asta</small></p> 
                                <textarea name="message" class="span5" rows="6">Gentili utenti, siamo costretti ad annullare quest'asta a causa di violazione di bla bla. La morte vi prenderà</textarea>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${auction.buyer.id == sessionScope.user.id && sessionScope.user.id != null}">  
                                        <p class="text-warning"><small>Non puoi fare offerte dato che sei già in testa</small></p>     
                                    </c:when>
                                    <c:otherwise>
                                        <form class="form-inline" action="<c:url value="/User/UserController?op=offreq&id=${auction.auction_id}"/>"  onsubmit="return validate();" method="post" id ="offer_form"> 
                                            <input type="hidden" name="buyer_id" value="<c:out value="${auction.buyer.id}" default="-1"/>" > 
                                            <input type="hidden" name="increment" value="${auction.increment_price}">
                                            <input type="hidden" name="base_price" value="${auction.current_price}">
                                            <div class="input-append ">
                                                <input class="input-small" type="text" name="offer" value="${auction.current_price + auction.increment_price}" id="input_offer">
                                                <span class="add-on">$</span> 
                                            </div> 
                                            <button type="submit" class="btn btn-primary">Fai un offerta</button>
                                            <p class="text-info"><small>Il prezzo verrà incrementato automaticamente fino al raggiungimento della tua offerta</small></p><br>
                                            <span class="text-error" id="error_span"></span>
                                        </form>      
                                    </c:otherwise>
                                </c:choose>        
                            </c:otherwise>     
                        </c:choose>               
                    </c:otherwise>
                </c:choose>      
            </td>
            </tr>
            </table>
       </td>
    </tr>
    <tr>
        <td>
            <c:if test="${auction.seller.id == sessionScope.user.id && sessionScope.user.id != null}">
                <a href="#" class="btn btn-success">Modifica descrizione</a><br>
            </c:if>
        <c:out value="${auction.description} "/>
        </td>
    </tr>
</table>
        <jsp:include page="/Jsp/Message.jsp" flush="false"/>                  
<jsp:include page="/Jsp/Footer.jsp" flush="false"/>