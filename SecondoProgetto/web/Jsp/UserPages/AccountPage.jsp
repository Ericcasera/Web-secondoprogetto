
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
            $('h4').html(hash);
        }); 
        
        
        $(window).hashchange();
        });
    </script>
    
    </head>

<jsp:include page="/Jsp/AccountBody.jsp" flush="false"/>
    
    
    <h4>Aste in scadenza</h4>
    <h5>Le pagine sono piu o meno tutte = per cui si puo provare a fare questo in json</h5>

<jsp:include page="/Jsp/Footer.jsp" flush="false"/>
