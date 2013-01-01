<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link  href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.3.min.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        <script>
           $(document).ready(function(){ 
               $('#table_div').hide();
           
           page = 0;
           
           $.query = function(){
                        var category_id = $('#category_id').val();
                        var pattern = $('#search_query').val();
                        var order = $('#order_select').val();
                        var per_page = parseInt($('#row_per_page').val());   
                        var url = "<c:url value="/General/GeneralController?op=search"/>";
                        url += "&category_id=" + category_id;
                        url += "&search_query=" + pattern;
                        url += "&order=" + order;
                        url += "&offset=" + per_page * page;
                        url += "&per_page=" + per_page;
                        $('#result_set').html("<span class=\"text-success\">Searching...</span>");
                        $.getJSON( url , function(data) {
                            var elem = 0;   
                            var result = "<table><tr><th class\"span3\">Immagine</th><th class=\"span1\">ID</th><th class=\"span2\">Nome</th><th class=\"span2\">Descprition</th><th class=\"span2\">Tempo alla scadenza</th><th class=\"span2\">Price</th></tr>";  
                                 $.each(data , function(key, val) {
                                     if(key == 0)
                                         {
                                             elem = parseInt(val.elem);
                                         }
                                         else
                                         {
                                    result += "<tr><td><img height=\"100px\" width=\"100px\" src=\"<c:url value="/Images"/>/" + val.image_url + "\"/></td>";
                                    result += "<td> " + val.id + "</td>";
                                    result += "<td><a href=\"<c:url value="/General/GeneralController?op=details"/>&id="+val.id+" \">" + val.name + "</a></td>";
                                    result += "<td> " + val.description + "</td>";
                                    result += "<td> " + val.expiration + "</td>";
                                    result += "<td> " + val.current_price + "</td></tr>";
                                        }
                                    });
                            result += "</table>";
                            if(elem == 0)
                                result = "<h5>Non sono stati trovati elementi corrispondenti ai parametri di ricerca</h5>";

                            var pager = "<div class=\"pagination pagination-centered pagination-small\"><ul id=\"pager\">";

                            var num_page = Math.ceil(elem / per_page); 
                                                        
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

                            $('#find_span').html("Trovati " + elem + " elementi");
                            $('#table_div').show();
                            $('#result_set').html(result);
                            $(document.createElement("pager")) 
                                .html(pager)
                                .appendTo("#result_set");
                                $(document.createEvent($('#pager li').click(function(){ page = $(this).val(); $.query(); })));
                })
                .error(function() { $('#result_set').html("<h5 class=\"text-error\">Si è verificato un errore inatteso. Ci scusiamo per il disagio</h5>"); })

   
        }
        
        
       $('#submit_button').click($.query);
       $('#order_select').change($.query);
       $('#row_per_page').change($.query);
       
    });
 
        </script>
        <title>Header Page</title>
    </head>
    <body>
        <div >
            <h1>
                Titolo del sito
            </h1>
            <br><br>
        </div>
        
            <div class="navbar">
              <div class="navbar-inner">
                <div class="container">
                  <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                  </a>
                  <a class="brand" href="#">Nome del sito</a>
                  <div class="nav-collapse collapse navbar-responsive-collapse">
          <form  class="navbar-search pull-left form-inline" action="#" onsubmit="return false;" method="get">
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
                    <li><a href="<c:url value="/GuestController?op=loginRequest"/>" >Accedi</a></li>
                    <li><a href="<c:url value="/GuestController?op=subRequest"/>" >Sign up</a></li>
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
                            <li><a href="/SecondoProgetto/LogoutController?op=logout">Logout</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="#">User options</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li class="divider"></li>
                            <li><a href="/SecondoProgetto/LogoutController?op=logout">Logout</a></li>               
                        </c:otherwise> 
                    </c:choose>
                      </ul>
                      </li>
                    </ul>      
                </c:otherwise>
             </c:choose>
                  </div><!-- /.nav-collapse -->
                </div>
              </div><!-- /navbar-inner -->
            </div><!-- /navbar -->     
  
        <div class="container-fluid" >
            <div class="row-fluid">
                <div class="span1"></div>  
                <div class="span10 container-fluid" >
                    <div class="row-fluid">
                        <div id="table_div">
<div class="navbar">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a> 
      <div class="nav-collapse collapse">
        <small><p id="find_span" class="text-success navbar-text pull-left">CIAO</p></small>
        <small><p class="text-info navbar-text pull-left">&ensp; Risultati per pagina : &ensp; </p>
            <form class="navbar-form pull-left"> 
                                <select class="input-mini" id="row_per_page" name="row_per_page">
                                    <option value="2" selected>2 </option>
                                    <option value="10" >10 </option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                 </select> 
            </form>
    
            <form class="navbar-form pull-right"> 
            <select class="input-medium " id="order_select" name="order_select">
                                    <option value="1" selected>Nome </option>
                                    <option value="2">Prezzo</option>
                                    <option value="3">Scadenza</option>
                                </select>
            </form>
        <p class="navbar-text pull-right text-info" >Ordina per : &ensp;</p>   
      </div>
 
    </div>
  </div>
</div>
                        </div>    
                    </div>
                    <div class="row-fluid">
                        <center>
                        <div id="result_set">