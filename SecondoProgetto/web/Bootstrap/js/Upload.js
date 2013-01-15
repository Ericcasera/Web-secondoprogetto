           
            jQuery.fn.Uploader = function(){
                
            queue = new Array();
            args = arguments[0] || {}; 
            maxFileSize = args.maxFileSize || 10*1024*1024;
            maxUpload = args.maxUpload || -1;
            table = args.table        || "#upload_table";
            input_button = args.input || "#input_file";
            upload_all = args.upload  || "#upload_all";
            remove_all = args.remove  || "#remove_all";
            url        = args.url     || "";
            allowed =  /\.(png|jpg|jpeg|gif)$/ ; 
            
                $(input_button).change(function(){
                if(queue.length == maxUpload)
                    {
                    alert("MaxUpload : " + maxUpload)
                    return;
                    }
                    
                $.each(this.files , function(key , val){
                var file = val;
                var form_data = new FormData();
                form_data.append("file" , file);
                queue.push(form_data);

                var table_row = "<tr style=\"display:none;\"><td> " + file.name + "</td><td> " + parseInt(file.size/1024) + "KB</td>";
                if(file.name.match(allowed) && file.size < maxFileSize)
                    {
                    table_row += "<td><span class=\"label label-success\">Corretto</span></td>";
                    table_row += "<td><div class=\"progress progress-striped active\"><div class=\"bar\" style=\"width:0%;\"></div></div></td>";
                    table_row += "<td><button class=\"btn btn-primary\" onClick=\"$.single_upload($(this))\">Upload</button></td>";   
                    }
                else 
                    {
                        if(file.size < maxFileSize)
                            table_row += "<td><span class=\"label label-important\">Formato non consentito</span></td>";
                        else
                            table_row += "<td><span class=\"label label-important\">File troppo grande</span></td>";
                    table_row += "<td><div class=\"progress progress-striped active\"><div class=\"bar\" style=\"width:0%;\"></div></div></td>";
                    table_row += "<td></td>";             
                    }
                table_row +=  "<td><button class=\"btn btn-warning\" onClick=\"$.single_remove($(this));\">remove</button></td></tr>";        
                $(table+' tbody').append(table_row);
                $("tr:hidden:first").fadeIn("slow");
            });
            });
            
            $.single_remove = function(elem){
              $(elem).closest('tr').fadeOut("slow" , function(){
                  queue.splice($(this).index(),1);
                  $(this).remove();
              });
            };
            
            $.single_upload = function(elem){
                $.upload($(elem).closest('tr'));
            };
            
            $(upload_all).click(function(){
                $(table + ' tbody tr').each(function(){
                if($(this).find('.bar').width() == 0 && $(this).find('.label-success').length >0)
                    $.upload($(this).closest('tr'));   
                });
            });
            
            $(remove_all).click(function(){
               queue.splice(0, queue.length);
               $(table + ' tbody').fadeOut("slow", function(){
                   $(this).html("");
                   $(this).fadeIn();
                   });
               });
     
            $.upload = function (row) {
                var row_index = row.index();
                var bar = $(row).find('.bar');
                    $.ajax({
                            url: url,
                            type: 'POST',
                            enctype : 'multipart/form-data',
                            xhr: function() {  
                                myXhr = $.ajaxSettings.xhr();
                                if(myXhr.upload){ 
                                    myXhr.upload.addEventListener('progress', function(e){
                                      $(bar).width((e.loaded/e.total)*100 + "%");  
                                    }, false); 
                                }
                            return myXhr;
                            },
                            beforeSend: function() {
                                /*$(row).find('.btn-primary')
                                            .attr("onSubmit" , "null")
                                            .removeClass('btn-primary')
                                            .addClass('btn-danger')
                                            .text("Cancella");*/
                                $(row).find('.btn-primary').hide();              
                                $(row).find('.btn-waring').hide();
                            },         
                            success:  function(){
                            $(bar).addClass("bar-success")
                                     .width("100%")
                                     .parent().removeClass("progress-striped active");
                            $(row).find('.btn-danger').hide();
                            $(row).find('.btn-warning').show();        
                            $(row).find('.label')
                                  .html("Upload completato");
                            }, 
                            error:   function () {
                            $(bar).addClass("bar-danger")
                                     .width("100%")
                                     .parent().removeClass("progress-striped active");
                            $(row).find('.btn-danger').hide();
                            $(row).find('.btn-warning').show();  
                            $(row).find('.label')
                                  .html("Upload fallito")
                                  .removeClass("label-success")
                                  .addClass("label-important");
                         },
                         data: queue[row_index],
                         cache: false,
                         contentType: false,
                         processData: false
                        });
           };
};