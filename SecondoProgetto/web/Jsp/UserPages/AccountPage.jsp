
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

    <title>MyAccount</title>
    <script src="<c:url value="/Bootstrap/js/hashChange.js"/>" ></script>
    <script>
        $(document).ready(function(){
        $(window).hashchange( function(){
            var hash = location.hash; 
            $('#menu a').each(function(){ 
                $(this).attr('href') === hash ? $(this).parent().addClass('active') : $(this).parent().removeClass('active');
            });
            "#account" === hash ? $("#menu a:first").parent().addClass('active') : $(this).parent().removeClass('active');
        }); 
        
        
        $(window).hashchange();
        });
    
    </script>
    
    </head>

<jsp:include page="/Jsp/AccountBody.jsp" flush="false"/>

<c:set value="${sessionScope.user}" var="user"/>

<h3>Il mio account</h3>
<strong>Username :&ensp;</strong><c:out value="${user.username}"/><br>
<strong>Email    :&ensp;</strong><c:out value="${user.email}"/><br><br>
<address>
    <strong>Indirizzo</strong><br>
    <c:out value="${user.country}"/><br>
    <c:out value="${user.city}"/><br>
    <c:out value="${user.address}"/><br>
    <c:out value="${user.phone}" default=""/><br>
</address>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
