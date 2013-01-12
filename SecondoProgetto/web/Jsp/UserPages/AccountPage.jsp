
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>MyAccount</title>
    <script>
        $(document).ready(function(){
            $('#menu li[id*="${param.op}"]').addClass("active");
     });
    
    </script>

    </head>

<jsp:include page="/Jsp/AccountBody.jsp" flush="false"/>

<div id="result_div">
    <c:set value="${param.op}" var="op"/>
    <c:choose>
        <c:when test="${op == 'account'}">
            <c:set value="${sessionScope.user}" var="user"/>
                <h3>Il mio account</h3>
                <strong>Username :&ensp;</strong><c:out value="${user.username}"/><br>
                <strong>Email    :&ensp;</strong><c:out value="${user.email}"/><br><br>
                <address>
                    <strong>Indirizzo</strong><br>
                    <c:out value="${user.city} (${user.country})"/><br>
                    <c:out value="${user.address}"/><br>
                    <c:out value="Telefono :${user.phone}" default=""/><br>
                </address>
        </c:when>
        <c:when test="${op == 'sells'}">
            <h3>Le mie aste attive</h3>
            <jsp:include page="/Jsp/Message.jsp" flush="false"/>
            <table class="table">
                <thead><th class="span3"></th><th class="span3"></th><th></th></thead>
                <tbody>
                <c:forEach items="${requestScope.result}" var="auction">
                    <tr>
                        <td ><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                        <td >
                            <a href="<c:url value="/General/GeneralController?op=details&id=${auction.auction_id}"/>"> ${auction.name} </a>
                            <c:if test="${auction.cancelled}">
                                <span class="label label-important">Asta anullata</span>
                            </c:if>
                            <small>
                                <p>
                                    <c:set value="${auction.description}" var="desc"/>
                                    <c:out value="${fn:substring(desc, 0, 100)}..."/><br><br>
                                </p>
                            </small>
                        </td>
                        <td>
                            <p>
                            Prezzo corrente   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                            Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br>
                            Prezzo minimo di vendita : <strong><span class="text-error"> ${auction.min_price}</span>$</strong><br>
                            Prezzo incremento minimo : <strong><span class="text-error"> ${auction.increment_price}</span>$</strong><br>
                            </p>            
                        </td>
                        <td>
                            <strong><span class="text-error"> ${auction.timeToExpiration}</span> alla scadenza.</strong>
                        </td>                                      
                    </tr>                    
                </c:forEach>
                </tbody> 
            </table>        
        </c:when> 
        <c:when test="${op == 'buys'}">
            <h3>Le mie aste attive</h3>
            <table class="table">
                <thead><th class="span3"></th><th class="span3"></th><th></th></thead>
                <tbody>
                <c:forEach items="${requestScope.result}" var="auction">
                    <tr>
                        <td class="span3"><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                        <td class="span3">
                            <a href="<c:url value="/General/GeneralController?op=details&id=${auction.auction_id}"/>"> ${auction.name} </a>
                            <c:if test="${auction.cancelled}">
                                <span class="label label-important">Asta anullata</span>
                            </c:if>
                            <small>
                                <p>
                                    <c:set value="${auction.description}" var="desc"/>
                                    <c:out value="${fn:substring(desc, 0, 100)}..."/><br><br>
                                </p>
                            </small>
                        </td>
                        <td>
                            <p>
                            Prezzo corrente   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                            Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br>
                            </p>        
                        </td>
                        <td>
                            <strong><span class="text-error"> ${auction.timeToExpiration}</span> alla scadenza.</strong>
                        </td>                                      
                    </tr>                    
                </c:forEach>
                </tbody> 
            </table>        
        </c:when>
        <c:when test="${op == 'won'}">
            <h3>I miei acquisti passati</h3>
            <table class="table">
                <thead><th class="span3"></th><th class="span3"></th><th></th></thead>
                <tbody>
                <c:forEach items="${requestScope.result}" var="sale">
                    <c:set value="${sale.auction}" var="auction"/>
                    <tr>
                        <td class="span3"><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                        <td class="span3">
                            <strong><c:out value="${auction.name}"/></strong><br>
                            <c:out value="Venduto da ${auction.seller.username}"/><br>
                            <small><c:out value="${auction.seller.email}"/></small>
                        </td>
                        <td>
                            <p>
                            Prezzo prodotto   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                            Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br> 
                            -------------------------------- <br>
                            Prezzo Totale : <strong><span class="text-error"> ${sale.price}</span>$</strong><br>
                            </p>  
                        </td>
                        <td>
                            Acquistato in data <br><strong>${auction.expirationDate}</strong>
                        </td>                                      
                    </tr>                                              
                </c:forEach>
                </tbody> 
            </table>        
        </c:when>
        <c:when test="${op == 'sold'}">
            <h3>Le mie vendite</h3>
            <table class="table">
                <thead><th class="span3"></th><th class="span4"></th><th></th></thead>
                <tbody>
                <c:forEach items="${requestScope.result}" var="sale">
                    <c:set value="${sale.auction}" var="auction"/>
                    <tr>
                        <td><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                        <td>
                            <strong><c:out value="${auction.name}"/></strong><br>
                            <c:choose>
                                <c:when test="${sale.cancelled}">
                                    <span class="label label-important">Asta anullata</span>   
                                </c:when>
                                <c:when test="${sale.retreat}">
                                    <span class="label label-info">Asta ritirata</span><br>
                                    Commissioni :&ensp;<strong><span class="text-error">${sale.retreat_commissions}</span>$</strong>       
                                </c:when>
                                <c:otherwise>
                                    <span class="label label-success">Asta venduta</span><br>
                                    <c:set value="${auction.buyer}" var="buyer"/>
                                    <strong>Venduta a</strong><br>
                                    <c:out value="Utente :${buyer.username}"/><br>
                                    <c:out value="Email : ${buyer.email}"/><br>
                                    <address>
                                        <strong>Info spedizione</strong><br>
                                        <c:out value="${buyer.city} (${buyer.country})"/><br>
                                        <c:out value="${buyer.address}"/><br>
                                        <c:out value="Telefono :${buyer.phone}" default=""/><br>
                                    </address>                
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <p>
                            Prezzo prodotto   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                            Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br> 
                            -------------------------------- <br>
                            Prezzo Totale : <strong><span class="text-error"> ${sale.price}</span>$</strong><br>
                            </p>  
                        </td>
                        <td>
                            Asta terminata in data <br><strong>${auction.expirationDate}</strong>
                        </td>                                     
                    </tr>                    
                </c:forEach>
                </tbody> 
            </table>        
        </c:when>
    </c:choose>    
</div>
<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
