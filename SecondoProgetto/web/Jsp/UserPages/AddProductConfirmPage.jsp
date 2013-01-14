<%-- 
    Document   : AddProductConfirmPage
    Created on : 24-dic-2012, 21.46.00
    Author     : Daniel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>
        
        <title>Conferma aggiunta asta</title>
    </head>
<jsp:include page="/Jsp/Body.jsp" flush="false"/>
        <c:set value="${requestScope.auction}" var="auction"/>

<table>
    <tr>
        <td class="span4">
            <img src="<c:url value="/Images/${auction.image_url}"/>" width="250px" height="250px"/>
        </td>
        <td class="span7">
            <table>
            <tr>
                <td>
                    <strong><c:out value="${auction.name}"/></strong> 
                        <c:forEach items="${requestScope.category_list}" var="category">
                            <c:if test="${category.id == auction.category_id}">
                                <c:out value=" [${category.name}]"/><br><br>
                            </c:if>            
                        </c:forEach>  
                </td>
            </tr>
            <tr>
            <td>
            <strong>L'asta scadrà fra <span class="text-error">${auction.timeToExpiration}</span></strong><br>
                Prezzo iniziale : <strong><span class="text-error"> ${auction.starting_price}</span>$</strong><br>
                Prezzo spedizione : <strong><span class="text-error"> ${auction.shipping_price}</span>$</strong><br> 
                Incremento minimo : <strong><span class="text-error"> ${auction.increment_price}</span>$</strong><br>
                Prezzo minimo : <strong><span class="text-error"> ${auction.min_price}</span>$</strong><br><br>
            </td>
            </tr>
            <tr>
            <td>
                <form action="<c:url value="/User/UserController?op=AucConfirm"/>" method="post">
                    <input type="hidden" value="${auction.name}" name="name">
                    <input type="hidden" value="${auction.description}" name="description">
                    <input type="hidden" value="${auction.expiration}" name="expiration">
                    <input type="hidden" value="${auction.image_url}" name="image_name">
                    <input type="hidden" value="${auction.shipping_price}" name="shipping_price">
                    <input type="hidden" value="${auction.min_price}" name="min_price">
                    <input type="hidden" value="${auction.increment_price}" name="increment_price">
                    <input type="hidden" value="${auction.starting_price}" name="starting_price">
                    <input type="hidden" value="${auction.seller.id}" name="seller_id">
                    <input type="hidden" value="${auction.category_id}" name="category">
                    <button class="btn btn-primary "type="submit" >Conferma inserzione</button>
                    <a href="<c:url value="/General/GeneralController?op=home"/>" class="btn btn-warning">Annulla</a><br>
                </form>
            </td>
            </tr>
            </table>
       </td>
    </tr>
        </tr>
 </table>
            <br><strong>Descrizione</strong><br>
       <c:out value="${auction.description}"/><br>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
