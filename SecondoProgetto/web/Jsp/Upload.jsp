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
        <script src="Bootstrap/js/jquery-1.8.2.js"></script>
        <style type="text/css">
        .fileinput-button {
  position: relative;
  overflow: hidden;
  float: left;
  margin-right: 4px;
}
    .fileinput-button input {
  position: absolute;
  top: 0;
  right: 0;
  margin: 0;
  opacity: 0;
  filter: alpha(opacity=0);
  transform: translate(-300px, 0) scale(4);
  font-size: 23px;
  direction: ltr;
  cursor: pointer;
}
            
        </style>
        <script>
            $(document).ready(function(){
            index = 0;
            var form_data = new Array();
            
            $('#input_file').change(function(){
                var file = this.files[0]; 
                
                form_data[index] = new FormData();
                form_data[index].append("file" , file);
                
                var allowed =  /\.(png|jpg|jpeg|gif)$/ ;
                var n = file.name.lastIndexOf(".");
                var file_type = file.name.substring(n, file.name.lenght);
                
                var table_row = "<tr id=\""+index+"\" style=\"display:none;\"><td> " + file.name + "</td><td> " + parseInt(file.size/1024) + "KB</td>";
                if(file_type.match(allowed))
                    {
                    table_row += "<td><span class=\"label label-success\">Corretto</span></td>";
                    table_row += "<td><button class=\"btn btn-primary\" onClick=\"\">Upload</button></td>";   
                    }
                else 
                    {
                    table_row += "<td><span class=\"label label-important\">Non consentito</span></td>";
                    table_row += "<td></td>";             
                    }
                
                table_row +=  "<td><button class=\"btn btn-warning\" onClick=\"$.test($(this));\">remove</button></td></tr>";        
                index++;
                $('table tbody').append(table_row);
                $("tr:hidden:first").fadeIn("slow");
            });
            
            $.test = function(elem){
              
              $(elem).parent().parent().fadeOut("slow" , function(){
                  $('#result_form input[id*=' + $(this).attr('id') + ']').remove();
                  $(this).remove();});
              };
              
            $("#uploadbutton").click(function () {
                
               // var formData = new FormData(document.getElementById("file_upload"));
               //var formData = new FormData();
               //formData.append("file" , document.getElementById("file_upload").files[0]);
                    $.ajax({
                            url: 'Servlet',  //server script to process data
                            type: 'POST',
                            enctype : 'multipart/form-data',
                            xhr: function() {  // custom xhr
                                myXhr = $.ajaxSettings.xhr();
                                if(myXhr.upload){ // check if upload property exists
                                    myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // for handling the progress of the upload
                                }
                            return myXhr;
                            },
                        success:  function(){
                            $('#bar').addClass("bar-success")
                                     .width("100%")
                                     .parent().removeClass("progress-striped active");
                        }, 
                        error:   function (jqXHR, ajaxSettings, thrownError) {
                            $('#bar').addClass("bar-danger")
                                     .width("100%")
                                     .parent().removeClass("progress-striped active");
                         },
                        data: form_data[index-1],
                         cache: false,
                         contentType: false,
                         processData: false
                        });
           });
           
           function progressHandlingFunction(e){ 
               $('#bar').width(e.loaded + "%");
            }
            });
           
            
            </script>
    
    <title>Ciao page</title>
    
    <body>
        <button id="test">test</button>
        <div class="container">
                <span id="btn" class="btn btn-success fileinput-button">
                    <i class="icon-plus icon-white"></i>
                    <span id="toni">Add files...</span>
                    <input id="input_file" type="file" name="files" >
            </span>
        </div>
        <form id="result_form" method="get" action="bepi">
            <button class="btn btn-primary" type="submit">Upload</button>
        </form>
        
        <form enctype="multipart/form-data" >
            <input id="file_upload" name="file" type="file">
        </form> 
        <input id="uploadbutton" type="button" value="Upload" > 
        <div class="progress progress-striped active">
            <div class="bar" id="bar" style="width:0%;"></div>
        </div>
        <div class="container span11">
        <table class="table">
            <thead>
                <tr>
                    <th class="span5"></th>
                    <th class="span2"></th>
                    <th class="span2"></th>
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
