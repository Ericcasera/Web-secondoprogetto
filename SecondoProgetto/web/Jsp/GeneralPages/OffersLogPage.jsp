<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/Jsp/Header.jsp" flush="false"/>

<title>Log offerte</title>
<h5>Offerte per asta :#${requestScope.auction.auction_id}</h5>
<table class="table">
    <thead>
        <tr>
            <th>Offerente</th><th>Offerta</th><th>Data offerta</th>
        </tr>
    </thead>
    <tbody>     
    <c:forEach items="${requestScope.log}" var="offer">
       <tr>
           <td><c:out value="${offer.username} " /></td>
           <td><strong><span class="text-error">${offer.price}</span>$</strong></td>
           <td><c:out value="${offer.formatDate} " /></td>
       </tr>
   </c:forEach>     
</table>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
