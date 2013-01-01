<%-- 
    Document   : AddProductPage.jsp
    Created on : 24-dic-2012, 19.31.05
    Author     : Daniel
--%>
<%@page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.2.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <script type="text/javascript">
            function validate(form){
                
                var regExpValue = /^\d+[.]?\d{0,2}/;
                var regExpCheck = /^\d+[.]?\d*$/;
                
                var name = form.product_name.value;
                var starting_price = form.starting_price.value;
                var increment = form.increment.value;
                var min_price = form.min_price.value;
                var shipping_price = form.shipping_price.value;
                var corretto = true;
                
                if(name == "" || name == null) {document.getElementById("product_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("product_span").innerHTML ="";
                
                if(starting_price.search(regExpCheck) == -1) 
                    {document.getElementById("starting_price_span").innerHTML = "*Il formato non è corretto"; corretto = false;}
                    else{document.getElementById("starting_price_span").innerHTML = "";     
                         form.starting_price.value = starting_price.match(regExpValue);}
                     
                if(increment.search(regExpCheck) == -1) 
                    {document.getElementById("increment_span").innerHTML = "*Il formato non è corretto"; corretto = false;}
                    else{document.getElementById("increment_span").innerHTML = "";
                        form.increment.value = increment.match(regExpValue);}
                
                if(min_price.search(regExpCheck) == -1) 
                    {document.getElementById("increment_span").innerHTML = "*Il formato non è corretto"; corretto = false;}
                    else{document.getElementById("min_price_span").innerHTML = "";
                        form.min_price.value = min_price.match(regExpValue);}

                if(shipping_price.search(regExpCheck) == -1) 
                    {document.getElementById("shipping_price_span").innerHTML = "*Il formato non è corretto"; corretto = false;}
                    else{document.getElementById("shipping_price_span").innerHTML = "";
                        form.shipping_price.value = shipping_price.match(regExpValue);}

                return corretto;
            }
                $(function() {
                    $( "#select_image" ).click(function() {
                            $( "#form_div" ).hide();
                            $( "#image_div").show();
                    return false;});
                    });
                
                function changeImage(image_name){
                    document.getElementById("image_preview").src = "<c:url value="/Images"/>/" + image_name; 
                    document.getElementById("image_name").value = image_name;
                         $(function() {
                             $("#image_div").hide();
                             $( "#form_div" ).show();
                         });
                         return false };
        </script>
        
        
        
        <title>Nuovo prodotto</title>
    </head>
    <body>
        <h1>Inserisci dati prodotto</h1>
        <div id="form_div">
        <form action="<c:url value="/User/UserController?op=AucRequest"/>" method="post" onsubmit="return validate(this);" class="form-horizontal">
            <input type="hidden" name="image_name" id="image_name">
                 <div class="control-group">
                        <div class="controls">
                            <h4>Informazioni sul prodotto</h4>
                        </div>
                  </div>  
                  <div class="control-group">
                         <div class="controls">
                             <a href="#" id="select_image" onClick="return false;">
                             <img id="image_preview" src="<c:url value="/Images-site/immagine_vuota.jpg"/>" width="150px;" height="150px;" class="img-polaroid" >
                             </a>
                         </div>
                  </div> 
                  <div class="control-group">
                      <label class="control-label" for="product_name">*Nome prodotto</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Nome prodotto" type="text" name="product_name">
                             <span class="text-error" id="product_span"></span>
                         </div>
                  </div> 
                  <div class="control-group">
                      <label class="control-label" for="description">*Descrizione</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Descrizione" type="text" name="description">
                             <br><span class="text-info">Potrai cambiere la descrizione in qualsiasi momento</span>
                         </div>
                  </div>
                  <div class="control-group">
                      <label class="control-label" for="category">*Categoria</label>
                         <div class="controls">
                              <select name="category">
                                 <c:forEach items="${requestScope.category_list}" var="category">
                                     <option value="${category.id}">${category.name}</option>              
                                 </c:forEach>    
                              </select>
                         </div>
                  </div>                    
                  <div class="control-group">
                        <div class="controls">
                             <h4>Informazioni per la vendita</h4>
                        </div>
                  </div> 
                  <div class="control-group">
                      <label class="control-label" for="starting_price">*Prezzo di partenza</label>
                         <div class="controls">
                             <div class="input-append ">
                                <input class="input-small" type="text" name="starting_price" value="0.0">
                                <span class="add-on">$</span> 
                             </div>  
                             <span class="text-error" id="starting_price_span"></span>
                             <br><span class="text-info">Prezzo di partenza dell'asta</span>
                         </div>
                  </div>   
                  <div class="control-group">
                      <label class="control-label" for="increment">*Incremento prezzo</label>
                         <div class="controls">
                             <div class="input-append ">
                                <input class="input-small" type="text" name="increment" value="1.0">
                                <span class="add-on">$</span> 
                             </div>
                             <span class="text-error" id="increment_span"></span>
                             <br><span class="text-info">Incremento minimo del prezzo</span>
                         </div>
                  </div>   
                 <div class="control-group">
                      <label class="control-label" for="min_price">*Prezzo minimo</label>
                         <div class="controls">
                             <div class="input-append ">
                                <input class="input-small" type="text" name="min_price" value="0.0">
                                <span class="add-on">$</span> 
                             </div>  
                             <span class="text-error" id="min_price_span"></span>
                             <br><span class="text-info">Se al termine dell'asta il prezzo minimo non è stato raggiunto , l'asta verrà annullata</span>
                         </div>
                  </div>  
                  <div class="control-group">
                      <label class="control-label" for="shipping_price">*Prezzo di spedizione</label>
                         <div class="controls">
                                <div class="input-append ">
                                    <input class="input-small" type="text" name="shipping_price" value="0.0">
                                    <span class="add-on">$</span> 
                                </div>
                             <span class="text-error" id="shipping_price_span"></span>
                             <br><span class="text-info">Se al termine dell'asta il prezzo minimo non è stato raggiunto , l'asta verrà annullata</span>
                         </div>
                  </div> 
                 <div class="control-group">
                      <label class="control-label" for="day">Durata dell'asta:</label>
                         <div class="controls">
                             Giorni:<input class="input-mini"  type="number" name="day" value="0" min="0" max="30">&ensp;
                             Ore:<input class="input-mini"  type="number" name="hour" value="0" min="0" max="23">&ensp;
                             Minuti:<input class="input-mini"  type="number" name="min" value="2" min="0" max="59">&ensp;
                             Secondi:<input class="input-mini"  type="number" name="sec" value="0" min="0" max="59">
                             <br><span class="text-info">Massimo consentito 30/23:59:59</span>
                         </div>
                  </div>                 
                
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Invia richiesta</button>
                             <a class="btn" href="<c:url value="/User/UserController?op=home"/>">Annulla</a>
                         </div>
                  </div>                  
            </form>
        </div>
                         
                         <%-- Inizio secondo div --%>
                         
    <div  id="image_div" class="hide">
        <h4>Seleziona un'immagine</h4>
        <h6>Oppure <a href="#">upload</a> una nuova immagine</h6>
        <form class="form-inline">
            <input type="file" >
            <button type="submit">Upload</button>
        </form>
         <c:forEach items="${requestScope.file_list}" var="file">
             <a href="#" onclick="return changeImage('${file}')">
                 <img src="<c:url value="/Images/${file}"/>" width="150" height="150">
             </a>         
         </c:forEach> 
         
    </div>
                         
                         
                         
           </body>
         </html>
                         
                         
    </body>
</html>
