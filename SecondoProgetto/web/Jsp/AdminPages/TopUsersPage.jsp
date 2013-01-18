
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>Top users</title>
    </head>

<jsp:include page="/Jsp/Body.jsp" flush="false"/>

<table class="table ">
    <thead>
        <tr>
            <th style="width:10%">Posizione</th>
            <th style="width:45%">Compratori migliori</th>
            <th style="width:45%">Venditori migliori</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="index" begin="0" end="9" step="1">
            <tr>
            <td>
                <strong>${index+1}°</strong>
            </td>
                <td>
                    <c:if test="${requestScope.buyers[index] != null}">
                        <c:set var="buyer" value="${requestScope.buyers[index]}" />
                            <strong>${buyer.username}</strong>&ensp;[<strong>${buyer.email}</strong>]<br>
                            Ha vinto <strong><span class="text-error"> ${buyer.auctions_number}</span></strong> aste
                            per un totale di <strong><span class="text-error"> ${buyer.total_price}</span>$</strong>
                    </c:if>
                </td>    
                <td>
                    <c:if test="${requestScope.sellers[index] != null}">
                        <c:set var="seller" value="${requestScope.sellers[index]}" />
                            <strong>${seller.username}</strong>&ensp;[<strong>${seller.email}</strong>]<br>
                            Ha venduto <strong><span class="text-error"> ${seller.auctions_number}</span></strong> aste
                            per un totale di <strong><span class="text-error"> ${seller.total_price}</span>$</strong>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
