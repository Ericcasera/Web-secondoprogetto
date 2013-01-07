<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${param.message != null}">
    <c:choose>
        <c:when test="${param.type == -1}">
            <div align="center" class="control-group">
                <div class="alert alert-error fade in">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    <p algin="center" class="text-error"> <c:out value="${param.message}"/></p>
                </div>                
            </div>
        </c:when>           
        <c:otherwise> 
            <div align="center" class="control-group">
                <div class="alert alert-success fade in">
                    <a class="close" data-dismiss="alert" href="#">&times;</a>
                    <p algin="center" class="text-success"> <c:out value="${param.message}"/></p>
                </div>                
            </div>
        </c:otherwise>                       
    </c:choose>
</c:if> 