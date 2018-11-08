<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.HashMap"%>
<%@page import="ex4package.UserData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String user = (String) session.getAttribute("user");
    //check if user logged in
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/Login");
    }
    
    ServletContext context = getServletContext();
    HashMap<String, UserData> usersList = (HashMap<String, UserData>) context.getAttribute("usersList");
    Long timeStamp = (Long) context.getAttribute("usersStamp");

   
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forum</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script>
            var ts = <% out.println(timeStamp); %>;

            function getupdate() {
                $.ajax({
                    type: 'POST',
                    cache: false,
                    url: 'UpdateView',
                    dataType: "json",
                    data: {timestamp: ts},
                    success: function (responseJson) {
                        if (responseJson.changed) {
                            ts = responseJson.timestamp.toString();
                            $(".userslist").empty();
                            $(".userslist").append("<thead>\n\
                                           <tr>            \n\
                                        <th>Email</th> \n\
                                        <th>status</th>\n\
                                         <th>Connection</th>\n\
                                           </tr>\n\
                                        </thead>");

                            var str = $().text();
                            str += "<tbody>";
                            $.each(responseJson.map, function (index, product) {

                                str += "<tr>";
                                str += "<td>" + product.email + "</td>";
                                str += "<td>" + product.status + "</td>";
                                if (product.connectionState === true) {
                                    str += "<td><div style=\"color:green\">online</div></td>";
                                } else {
                                    str += "<td><div style=\"color:red\">offline</div></td>";
                                }
                                str += "</tr>";
                            });
                            str += "</tbody>";
                            $(".table").append(str);

                        }
                    },
                    error: function (x, y, z) {
                        console.log("error:" + x.status + " / " + z.thrownError);
                        $(".updateError").empty();
                        $(".updateError").addClass("alert alert-danger");
                        $(".updateError").append("error displaying data");
                    }
                });
            }
            $(document).ready(function () {

                $(".button").click(function () {
                    $.ajax({
                        type: 'POST',
                        cache: false,
                        url: 'UpdateStatus',
                        data: {stat: $(".status").val()},
                        success: function () {
                            $('.status').val('');
                        }
                    });
                });
                setInterval(getupdate, 5000);
            });
        </script>
    </head>
    <body>
        
        <div class="container">
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <div class="panel-heading">
                        <h1 style="font-size:4vw;">Welcome ${user}</h1>
                    </div>
                    <div class="panel-body">

                        <div class="form-group"><!-- name  field -->
                            <input type="input" class="form-control status"  placeholder="What's on your mind?" name="status">
                        </div>  
                        <div class="form-group">
                            <button type="button" class="btn btn-lg btn-success btn-block button" name="button" >share your mind</button>
                        </div>

                    </div>
                    <a href="Logout"><h2>Logout</h2></a>
                    <h1 style="font-size:4vw; text-align: center;">Forum</h1>
                    <br>
                    <!-- place holder for server request error-->
                    <div class="updateError "></div> 

                    <table class="table userslist table-sriped">
                        <thead>
                            <tr>
                                <th>Email</th>
                                <th>status</th>
                                <th>Connection</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (UserData elem : usersList.values()) {
                            %>
                            <tr>
                                <td>
                                    <%=elem.getEmail()%>
                                </td>
                                <td>
                                    <%
                                        if (elem.getStatus() != null) {
                                            out.print(elem.getStatus());
                                        }
                                    %>
                                </td>
                                <td> 
                                    <%
                                        if (elem.getConnectionState()) {
                                            out.print("<div style=\"color:green\">online</div>");
                                        } else {
                                            out.print("<div style=\"color:red\">offline</div>");
                                        }
                                    %>
                                </td>    
                            </tr>
                            <% }%>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
    </body>
</html>
