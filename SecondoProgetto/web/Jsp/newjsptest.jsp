        <script>
           $(document).ready(function(){ 
               $('#table_div').hide();
           page = 0;
           category_id = $('#category_id').val();
           pattern = $('#search_query').val();
           order = $('#order_select').val();
           per_page = parseInt($('#row_per_page').val()); 
            
            $.makeHash = function(){
                var my_hash = "#";
                my_hash += "cat="  + $('#category_id').val();
                my_hash += "&pat=" + $('#search_query').val();
                my_hash += "&ord=" + $('#order_select').val();
                my_hash += "&per=" + $('#row_per_page').val();
                my_hash += "&pag=" + page;
                $('h1').html("location hash" + location.hash);
                window.location.hash = my_hash;
                $('h1').append("<br>my_hash" + my_hash);
                $('h1').append("<br>new hash" + location.hash);
                $.query();
            };


           $.query = function(){
                var base_hash = window.location.hash.substring(1);
                    alert("hash " + base_hash);
                    var hash = base_hash.split("&");
                    $.each(hash , function(key , val){
                        var tmp = val.split("=")[1];
                        switch(key)
                        {
                            case 0 : category_id = tmp; break; 
                            case 1 : pattern     = tmp; break;
                            case 2 : order       = tmp; break;
                            case 3 : per_page    = tmp; break;
                            case 4 : page        = tmp; break;
                        }
                    });
                        var url = "<c:url value="/General/GeneralController?op=search"/>";
                        url += "&category_id=" + category_id;
                        url += "&search_query=" + pattern;
                        url += "&order=" + order;
                        url += "&offset=" + per_page * page;
                        url += "&per_page=" + per_page;
                        $('#result_set').html("<span class=\"text-success\">Searching...</span>");
                        $.getJSON( url , function(data) {
                            var elem = 0;   
                            var result = "<table class=\"table\"><tbody>";  
                                 $.each(data , function(key, val) {
                                     if(key == 0)
                                         {
                                             elem = parseInt(val.elem);
                                         }
                                         else
                                         {
                                    result += "<tr><td><img height=\"100px\" width=\"100px\" src=\"<c:url value="/Images"/>/" + val.image_url + "\"/></td>";
                                    result += "<td> " + val.id + "</td>";
                                    result += "<td><a href=\"<c:url value="/General/GeneralController?op=details"/>&id="+val.id+" \">" + val.name + "</a></td>";
                                    result += "<td> " + val.description + "</td>";
                                    result += "<td> " + val.expiration + "</td>";
                                    result += "<td> " + val.current_price + "</td></tr>";
                                        }
                                    });
                            result += "</tbody></table>";
                            if(elem == 0)
                                result = "<h5>Non sono stati trovati elementi corrispondenti ai parametri di ricerca</h5>";

                            var pager = "<div class=\"pagination pagination-centered pagination-small\"><ul id=\"pager\">";

                            var num_page = Math.ceil(elem / per_page); 
                                                        
                            if(page > 0)
                                pager += "<li value=\""+ (page-1) + "\"><a href=\"#\">Prev</a></li>";       
                            
                            if(num_page == 1)
                                pager += "<li class=\"active\" value = \""+ (page) +"\"><a href=\"#\">"+ (page+1) +"</a></li>";
                            else
                            {
                            var start = (page-2 > 0) ? page-2 : 0 ;
                            var end   = ((start+5) > num_page) ? num_page : (start+5);
                            
                            for(start; start < end ; start++)
                                if(page === start)
                                    pager += "<li class=\"active\" value = \""+ (start) +"\"><a href=\"#\">"+ (start+1) +"</a></li>";
                                else
                                    pager += "<li value = \""+ (start) +"\"><a href=\"#\">"+ (start+1) +"</a></li>";
                            
                            if((page+1) < num_page)
                                pager += "<li value=\""+ (page+1) + "\"><a href=\"#\">Next</a></li>";
                            }
                                  
                            pager += "</ul></div>";
                            window.location.hash = base_hash;
                            $('#find_span').html("Trovati " + elem + " elementi");
                            $('#table_div').show();
                            $('#result_set').html(result);
                            $(document.createElement("pager")) 
                                .html(pager)
                                .appendTo("#result_set");
                                $(document.createEvent($('#pager li').click(function(){page = $(this).val(); $.makeHash();})));
                })
                .error(function() { $('#result_set').html("<h5 class=\"text-error\">Si è verificato un errore inatteso. Ci scusiamo per il disagio</h5>"); })
        }
        
       $('#submit_button').click($.makeHash);
       $('#order_select').change($.makeHash);
       $('#row_per_page').change($.makeHash);
    });
 
        </script>