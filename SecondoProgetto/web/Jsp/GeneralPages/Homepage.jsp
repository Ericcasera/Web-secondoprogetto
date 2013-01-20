
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>Homepage</title>
    </head>

<jsp:include page="/Jsp/Body.jsp" flush="false"/>
    
<h4>Aste in scadenza</h4>
   
<table class="table">
    <thead><th class="span3"></th><th class="span3"></th><th></th></thead>
        <tbody>
            <c:forEach items="${requestScope.auctions_list}" var="auction">
                <tr>
                    <td><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                    <td>
                        <a href="<c:url value="/General/GeneralController?op=details&id=${auction.auction_id}"/>"> ${auction.name} </a><br>
                        <c:if test="${auction.cancelled}">
                            <span class="label label-important">Asta anullata</span>
                        </c:if>
                    <small>
                    <p>
                        <c:set value="${auction.description}" var="desc"/>
                        <c:out value="${fn:substring(desc, 0, 100)}..."/>
                    </p>
                    </small>
                    </td>
                    <td>
                        Prezzo corrente   : <strong><span class="text-error"> ${auction.current_price}</span>$</strong><br></p>
                        Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br></p>        
                    </td>
                    <td>
                        <strong><span class="text-error"> ${auction.timeToExpiration}</span> alla scadenza.</strong>
                    </td>                                      
                    </tr>                    
               </c:forEach>
          </tbody> 
</table> 

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
