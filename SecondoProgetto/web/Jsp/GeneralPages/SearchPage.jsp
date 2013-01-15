<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link  href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.3.min.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        <title>Search</title>
        <script>
            $(document).ready(function(){    
              page = ${param.page};
              
                   $.query = function(){
                        var url = "<c:url value="/General/GeneralController?op=search"/>";
                        url += "&category_id="  + $('#category_id').val();
                        url += "&search_query=" + $('#search_query').val();
                        url += "&order="        + $('#order_select').val();
                        url += "&page="         + page;
                        url += "&per_page="     + $('#row_per_page').val();
                        window.location = url;                    
                    }
                    
                    $(function(){
                        $('#row_per_page option[value='+${param.per_page}+']').attr('selected' , true);
                        $('#order_select option[value='+${param.order}+']').attr('selected' , true);
                        $('#category_id option[value='+${param.category_id}+']').attr('selected' , true);                        
                        $('#search_query').val("${param.search_query}");
                    });
                    
                    $(function(){                            
                            var pager = "<div class=\"pagination pagination-centered pagination-small\"><ul id=\"pager\">";
                            
                            var num_elem = ${requestScope.result};
                            var per_page = ${param.per_page};
                            var num_page = Math.ceil(num_elem / per_page); 
                            
                            if(page > 0)
                                pager += "<li value=\""+ (page-1) + "\"><a href=\"#\">Prev</a></li>"; 
                            
                            if(num_page == 1)
                                pager += "<li class=\"active\" value = \""+ (page) +"\"><a href=\"#\">"+ (page+1) +"</a></li>";
                            else
                            {
                            var start = (page-2 > 0) ? page-2 : 0 ;
                            var end   = ((start+5) > num_page) ? num_page : (start+5);
                            
                            for(start; start < end ; start++)
                                if(page === start)
                                    pager += "<li class=\"active\" value = \""+ (start) +"\"><a href=\"#\">"+ (start+1) +"</a></li>";
                                else
                                    pager += "<li value = \""+ (start) +"\"><a href=\"#\">"+ (start+1) +"</a></li>";
                            
                            if((page+1) < num_page)
                                pager += "<li value=\""+ (page+1) + "\"><a href=\"#\">Next</a></li>";
                            
                        
                            }
                                  
                            pager += "</ul></div>";
                            
                            if(num_elem > 0){
                            $(document.createElement("pager")) 
                                .html(pager)
                                .appendTo("#result_set");
                                $(document.createEvent($('#pager li').click(function(){page = $(this).val(); $.query()})));
                            }
                        });

                          
            $('#submit_button').click($.query);
            $('#order_select').change($.query);
            $('#row_per_page').change(function(){ page= 0 ; $.query();});         
        });
        
        </script>
    </head>
    <div>
    <img src="/SecondoProgetto/Images-site/logo.png" alt="logo">
</div>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="<c:url value="/General/GeneralController?op=home"/>">Homepage</a>
            <div class="nav-collapse collapse navbar-responsive-collapse">
                <form class="navbar-search pull-left form-inline" onsubmit="return false;" method="get">
                    <select id="category_id">
                        <option value="-1" selected>Tutte le categorie</option> 
                            <c:forEach items="${requestScope.category_list}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>   
                    </select>        
                    <div class="input-append">
                        <input type="text" class="span4" name="search_query" id="search_query">                  
                        <button type="submit" id="submit_button" class="btn btn-primary">Search</button>
                    </div>
                </form>
                <ul class="nav pull-right">
                    <c:choose>
                        <c:when test="${sessionScope.user == null}">
                            <li><a href="<c:url value="/GuestController?op=loginReq"/>" >Accedi</a></li>
                            <li><a href="<c:url value="/GuestController?op=subReq"/>" >Sign up</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="Benvenuto ${sessionScope.user.username}"/><b class="caret"></b></a>
                                <ul class="dropdown-menu">  
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == 1}">
                                            <li><a href="#">Admin options</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="<c:url value="/LogoutController?op=logout"/>">Logout</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="<c:url value="/User/UserController?op=addAuction"/>">Crea nuova asta</a></li>
                                            <li><a href="<c:url value="/User/AccController?op=account"/>">Il mio account</a></li>
                                            <li class="divider"></li>
                                            <li><a href="<c:url value="/LogoutController?op=logout"/>">Logout</a></li>            
                                        </c:otherwise> 
                                    </c:choose>
                                </ul>
                            </li>    
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
</div>  
<body>
    <div class="container-fluid" >
        <div class="row-fluid">
            <div class="span12 container-fluid" >
                <div class="row-fluid">
                    <div id="table_div">
                        <div class="navbar">
                            <div class="navbar-inner">
                                <small> 
                                <p class="text-success navbar-text pull-left">
                                    <c:out value="Trovati ${requestScope.result} elementi" /> </p>
                                <p class="text-info navbar-text pull-left">&ensp; Risultati per pagina : &ensp; </p></small>
                                    <form class="navbar-form pull-left"> 
                                        <select class="input-mini" id="row_per_page" name="row_per_page">
                                            <option value="10" >10 </option>
                                            <option value="25" >25</option>
                                            <option value="50" >50</option>
                                        </select> 
                                    </form>
                                    <form class="navbar-form pull-right"> 
                                        <select class="input-medium " id="order_select" name="order_select">
                                            <option value="1" >Nome </option>
                                            <option value="2" >Prezzo</option>
                                            <option value="3" >Scadenza</option>
                                        </select>
                                    </form>
                                    <p class="navbar-text pull-right text-info" >Ordina per : &ensp;</p>   
                            </div>
                        </div>
                    </div>
                    </div>
                        <div class="row-fluid">
                            <center>
                                <div id="result_set">
                                        <c:if test="${empty requestScope.auctions_list}">
                                            <h5>Non sono stati trovati elementi corrispondenti ai parametri di ricerca</h5>
                                        </c:if>               
                                        <table class="table">
                                            <thead><th class="span3"></th><th class="span3"></th><th></th></thead>
                                            <tbody>
                                                <c:forEach items="${requestScope.auctions_list}" var="auction">
                                                    <tr>
                                                        <td><img height="100px" width="100px" src="<c:url value="/Images"/>/${auction.image_url}"/></td>
                                                        <td>
                                                            <a href="<c:url value="/General/GeneralController?op=details&id=${auction.auction_id}"/>"> ${auction.name} </a>
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
                                </div>  
                            </center>
                        </div>     
            </div>                        
        </div>
    </div>
</body>
</html>