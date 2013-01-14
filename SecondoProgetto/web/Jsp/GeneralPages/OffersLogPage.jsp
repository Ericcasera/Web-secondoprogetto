<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>Log offerte</title>
</head>

<jsp:include page="/Jsp/Body.jsp" flush="false"/>

<h5>Offerte per asta #${param.id}</h5>
        <c:choose>
            <c:when test="${empty requestScope.log}">
                <h4>Non ci sono offerte per quest'asta</h4>
            </c:when>
            <c:otherwise>
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
                </tbody></table>
            </c:otherwise>
        </c:choose>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
