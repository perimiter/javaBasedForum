<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ex4package.Message"%>
<% Message errmsg = (Message) request.getAttribute("loginError");%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>login</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel-heading">
                        <h2>Please Enter Your E-Mail</h2>
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="loginform" action="Login" method="post">
                            <div class="form-group"><!-- name  field -->
                                <input type="email" class="form-control"  placeholder="Enter email" name="email" required>
                            </div>  
                            <div class="form-group">
                                <% if (errmsg != null) {%>
                                <div class="alert alert-danger"><h2>${loginError.value}</h2></div>
                                        <%}%>
                                <button type="submit" class="btn btn-lg btn-success btn-block" >Log In</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
