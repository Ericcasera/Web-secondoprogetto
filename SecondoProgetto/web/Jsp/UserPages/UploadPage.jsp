<%-- 
    Document   : AddProductPage.jsp
    Created on : 24-dic-2012, 19.31.05
    Author     : Daniel
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/Jsp/Header.jsp" flush="false"/>

<link rel="stylesheet" href="<c:url value="/Bootstrap/css/Upload.css"/>">
<link  href="<c:url value="/Bootstrap/css/grafica.css"/>" rel="stylesheet">
<script src="<c:url value="/Bootstrap/js/Upload.js"/>"></script>   
<script>
            $(document).ready(function(){
                $(this).Uploader({
                    url : "<c:url value="/User/Upload"/>"
                });
            });
</script>
        <title>Upload</title>
    </head>
    
<jsp:include page="/Jsp/Body.jsp" flush="false"/>

    <h4>Fai l'upload delle tue immagini</h4>  
        <div class="container pull-left">
            <span id="btn" class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    <span id="toni">Aggiungi file</span>
                    <input id="input_file" type="file" name="files" multiple>
                </span>
                <button id="upload_all" class="btn btn-primary">Upload all </button>
                <button id="remove_all" class="btn btn-warning">Remove all </button>
        </div>

        <div class="container span14">
        <table class="table" id="upload_table">
            <thead>
                <tr>
                    <th class="span5"></th>
                    <th class="span2"></th>
                    <th class="span2"></th>
                    <th class="span3"></th>
                    <th class="span1"></th>
                    <th class="span1"></th>
                </tr>
            </thead>
            <tbody>
            </tbody>    
        </table>
        </div>
    <br><br>
                    
<jsp:include page="/Jsp/Footer.jsp" flush="false"/>