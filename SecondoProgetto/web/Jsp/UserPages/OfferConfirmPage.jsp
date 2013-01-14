<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/Jsp/Header.jsp" flush="false"/>

        <title>Conferma offerta</title>
    </head>
    <jsp:include page="/Jsp/Body.jsp" flush="false"/>
    
        <form  method="post" action="<c:url value="/User/UserController?op=offcon&id=${param.id}"/>" class="form-inline"> 
        <input type="hidden" name="increment" value="${param.increment}">
        <input type="hidden" name="base_price" value="${param.base_price}">        
        <input type="hidden" name="prec_op" value="offReq">
        <p><strong>Conferma la tua offerta massima
        <br>Prezzo attuale dell'asta : <span class="text-error">${param.base_price}</span> , 
        Incremento : <span class="text-error">${param.increment}</span>$</strong>       
        </p>
        <div class="input-append ">
            <input class="input-small" type="text" name="offer" value="${param.offer}" readonly="true">
            <span class="add-on">$</span> 
        </div> 
        <button type="submit" class="btn btn-primary">Conferma offerta</button>
        <a href="<c:url value="/General/GeneralController?op=details&id=${param.id}"/>" class="btn btn-warning">Annulla</a><br>
        <p class="text-info"><small>Il prezzo verrà incrementato fino al raggiungimento della tua offerta massima</small></p>
        </form>    

    <jsp:include page="/Jsp/Footer.jsp" flush="false"/>
