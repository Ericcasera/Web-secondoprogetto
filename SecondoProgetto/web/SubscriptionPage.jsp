<%-- 
    Document   : RecoverPasswordPage
    Created on : 22-dic-2012, 17.56.54
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link href="Bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="Bootstrap/css/grafica.css" rel="stylesheet">
        
        <script type="text/javascript">
            function validate(form){
                var username = form.username.value;
                var password = form.password.value;
                var email1 = form.email.value;
                var email2 = form.email_repeat.value;
                var country = form.country.value;
                var address = form.address.value;
                var city = form.city.value;
                var corretto = true;
                if(username == "" || username == null) {document.getElementById("username_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("username_span").innerHTML ="";
                if(password == "" || password == null) {document.getElementById("password_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("password_span").innerHTML ="";
                if(country == "" || country == null) {document.getElementById("country_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("country_span").innerHTML ="";
                if(address == "" || address == null) {document.getElementById("address_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("address_span").innerHTML ="";
                if(city == "" || city == null) {document.getElementById("city_span").innerHTML = "*Obbligatorio"; corretto = false; }
                else document.getElementById("city_span").innerHTML ="";
                if(!verifyEmail(email1 , email2)) corretto = false;            
                
                return corretto;
            }
                
                function verifyEmail(email1 , email2){
                   
                var emailRegEx = /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i;
                
                if (email1.search(emailRegEx) == -1)
                    { document.getElementById("email1_span").innerHTML = "*Indirizzo email non valido"; return false; }
                else
                    document.getElementById("email1_span").innerHTML = ""; 
                if (email1 != email2) 
                    { document.getElementById("email1_span").innerHTML = "*Gli indirizzi devono essere uguali";
                      document.getElementById("email2_span").innerHTML = "*Gli indirizzi devono essere uguali";
                      return false;
                    }
                else                   
                    { document.getElementById("email1_span").innerHTML = "";
                      document.getElementById("email2_span").innerHTML = "";
                    }
                    return true;
                }
 
        </script>
        
        
        <title>Iscrizione</title>
    </head>
    <body>
        <div class="login well">
            <div class="login-title" >
                <h3> Registrazione nuovo account </h3>
            </div>  
            <form action="GuestController?op=subscription" method="post" onSubmit ="return validate(this); " class="form-horizontal">

                  <div class="control-group">
                      <label class="control-label" for="username">*Username</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Username" type="text" name="username">
                             <span class="text-error" id="username_span"></span>
                         </div>
                  </div> 
                  <div class="control-group">
                      <label class="control-label" for="password">*Password</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Password" type="text" name="password">
                             <span class="text-error" id="password_span"></span>
                         </div>
                  </div>
                 <div class="control-group">
                      <label class="control-label" for="email">*Email</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Email" type="text" name="email">
                             <span class="text-error" id="email1_span"></span>
                         </div>
                  </div> 
                 <div class="control-group">
                      <label class="control-label" for="email_repeat">*Verifica email</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Email" type="text" name="email_repeat">
                             <span class="text-error" id="email2_span"></span>
                         </div>
                  </div> 
                
                  <div class="control-group">
                        <div class="controls">
                             <span>Informazioni per le spedizioni</span>
                        </div>
                  </div> 
                
                  <div class="control-group">
                      <label class="control-label" for="country">*Nazione</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Nazione" type="text" name="country">
                             <span class="text-error" id="country_span"></span>
                         </div>
                  </div>   
                  <div class="control-group">
                      <label class="control-label" for="city">*Città</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Città" type="text" name="city">
                             <span class="text-error" id="city_span"></span>
                         </div>
                  </div>   
                 <div class="control-group">
                      <label class="control-label" for="address">*Indirizzo</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Indirizzo" type="text" name="address">
                             <span class="text-error" id="address_span"></span>
                         </div>
                  </div>  
                 <div class="control-group">
                      <label class="control-label" for="phone">Telefono</label>
                         <div class="controls">
                             <input class="input-large" placeholder="Telefono" type="text" name="phone">
                         </div>
                  </div>                 
                
                  <div class="control-group">
                         <div class="controls">
                             <button class="btn" type="submit">Invia richiesta</button>
                             <a class="btn" href="<c:url value="/index.jsp"/> ">Annulla</a>
                         </div>
                  </div>                  
            </form>
        </div>
    </body>
</html>
