<%-- 
    Document   : HeaderBar
    Created on : 4-gen-2013, 20.55.03
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link  href="<c:url value="/Bootstrap/css/bootstrap.css"/>" rel="stylesheet">
        <script src="<c:url value="/Bootstrap/js/jquery-1.8.3.min.js"/>"></script>
        <script src="<c:url value="/Bootstrap/js/bootstrap.min.js"/>"></script>
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
                <form  class="navbar-search pull-left form-inline" action="<c:url value="/General/GeneralController"/>" method="get">
                    <select name="category_id">
                        <option value="-1" selected>Tutte le categorie</option> 
                            <c:forEach items="${requestScope.category_list}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>   
                    </select> 
                    <input type="hidden" name="op" value="search">
                    <input type="hidden" name="page" value="0">
                    <input type="hidden" name="order"  value="1">
                    <input type="hidden" name="per_page" value="10">
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
                                            <li><a href="<c:url value="/Admin/AdminController?op=ended"/>">Aste terminate</a></li>
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

