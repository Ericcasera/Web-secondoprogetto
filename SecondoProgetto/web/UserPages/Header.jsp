<%-- 
    Document   : Header
    Created on : 23-dic-2012, 21.13.33
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:out value="User :${sessionScope.user.username} , Email : ${sessionScope.user.email}"/>
    