
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>Homepage</title>
    </head>

<jsp:include page="/Jsp/Body.jsp" flush="false"/>
    
    
    <h4>Aste in scadenza</h4>
    

    
    <table class="table">
        <c:forEach items="${requestScope.auctions_list}" var="auction">
            <tr>
                <td class="span3"><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                <td><a href="<c:url value="/General/GeneralController?op=details&id=${auction.auction_id}"/>"> ${auction.name} </a>
                    <p><small>${auction.description}</small><br>
                        <strong>Prezzo corrente   : <span class="text-error"> ${auction.current_price}</span>$</strong><br></p>
                        <strong>Prezzo spedizione : <span class="text-error"> ${auction.shipping_price}</span>$</strong><br></p>
                </td>
                <td>
                    <strong ><span class="text-error"> ${auction.timeToExpiration}</span> alla scadenza.</strong>
                </td>
            </tr>
        </c:forEach>  
    
    </table>


<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
