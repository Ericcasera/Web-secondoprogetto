<%-- 
    Document   : index
    Created on : 28-dic-2012, 14.27.57
    Author     : Daniel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="Bootstrap/css/bootstrap.css">
        <link rel="stylesheet" href="Bootstrap/css/Upload.css">
        <script src="Bootstrap/js/jquery-1.8.3.min.js"></script>
        <script src="Bootstrap/js/Upload.js"></script>
        <script>
            $(document).ready(function(){
                $(this).Uploader();
            });
        </script>
    
    <title>Ciao page</title>
    
    <body>
        <div class="container">
                <span id="btn" class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    <span id="toni">Add files...</span>
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
    </body>
</html>
