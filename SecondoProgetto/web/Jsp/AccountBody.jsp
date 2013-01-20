
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid" >
        <div class="row-fluid">
            <div class="span2">
                <ul class="nav nav-list" id="menu">
                <li class="nav-header">Il mio account</li>
                <li id="account" ><a href="<c:url value="/User/AccController?op=account"/>">I miei dati</a></li>
                <li class="divider"></li>
                <li class="nav-header">Ordini attivi</li>
                <li id="buys" ><a href="<c:url value="/User/AccController?op=buys"/>">Aste in corso</a></li>
                <li id="sells" ><a href="<c:url value="/User/AccController?op=sells"/>">Prodotti in vendita</a></li>
                <li class="divider"></li>
                <li class="nav-header">Ordini terminati</li>
                <li id="won" ><a href="<c:url value="/User/AccController?op=won"/>">Aste vinte</a></li>
                <li id="lost" ><a href="<c:url value="/User/AccController?op=lost"/>">Aste perse</a></li>
                <li id="sold" ><a href="<c:url value="/User/AccController?op=sold"/>">Le mie vendite</a></li>
                </ul>       
            </div>  
            <div class="span10 container-fluid" >
                <div class="row-fluid">
