
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>Aste termiate</title>
    </head>

    <jsp:include page="/Jsp/Body.jsp" flush="false"/>
    <h5 style="text-align: center"><a href="<c:url value="/Admin/AdminController?op=excel"/>">Scarica excel della tabella</a></h5>  
<table class="table">
    <thead>
        <tr>
            <th class="span3"></th>
            <th>Dati asta</th>
            <th>Dati venditore</th>
            <th>Dati compratore</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.result}" var="sale">
            <c:set value="${sale.auction}" var="auction"/>
            <tr>
                <td><img height="150px" width="150px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                <td>
                    <strong><c:out value="${auction.name} #${auction.auction_id}"/></strong>
                    <small>[<a href="<c:url value="/General/GeneralController?op=log&id=${auction.auction_id}"/>">Log offerte</a>]</small>
                    <br>
                    <c:choose>
                        <c:when test="${sale.cancelled}">
                            <span class="label label-important">Asta anullata</span><br><br> 
                            Scaduta il <strong>${auction.expirationDate}</strong><br>
                        </c:when>
                        <c:when test="${sale.retreat}">
                            <span class="label label-info">Asta ritirata</span><br><br>
                            Scaduta il <strong>${auction.expirationDate}</strong><br>
                            Commissioni :&ensp;<strong><span class="text-error">${sale.retreat_commissions}</span>$</strong><br>
                            ------------------------------- <br>
                            Prezzo minimo : <strong><span class="text-error"> ${auction.min_price}</span>$</strong><br>
                        </c:when>
                        <c:otherwise>
                            <span class="label label-success">Asta venduta</span><br><br>
                            Scaduta il <strong>${auction.expirationDate}</strong><br>
                            Commissioni :&ensp;<strong><span class="text-error">${sale.commissions}</span>$</strong><br>
                            ------------------------------- <br>
                            Prezzo prodotto   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br>
                            Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br> 
                            -------------------------------- <br>
                            Prezzo Totale : <strong><span class="text-error"> ${sale.price}</span>$</strong><br>                    
                         </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:set value="${auction.seller}" var="seller"/>
                        <c:out value="Venditore :${seller.username}"/><br>
                        <c:out value="Email : ${seller.email}"/><br><br>
                            <address>
                                <strong>Indirizzo</strong><br>
                                <c:out value="${seller.city} (${seller.country})"/><br>
                                <c:out value="${seller.address}"/><br>
                                <c:out value="Telefono :${seller.phone}" default=""/><br>
                            </address>
                 </td>
                 <td>
                     <c:if test="${sale.sold}">
                     <c:set value="${auction.buyer}" var="buyer"/>
                        <c:out value="Compratore :${buyer.username}"/><br>
                        <c:out value="Email : ${buyer.email}"/><br><br>
                            <address>
                                <strong>Indirizzo</strong><br>
                                <c:out value="${buyer.city} (${buyer.country})"/><br>
                                <c:out value="${buyer.address}"/><br>
                                <c:out value="Telefono :${buyer.phone}" default=""/><br>
                            </address>
                     </c:if>
                 </td>                                  
             </tr>                    
        </c:forEach>           
    </tbody>
</table>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
