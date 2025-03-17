<%-- 
    Document   : memberRegister
    Created on : 16 Mar 2025, 12:54:47 am
    Author     : adver
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ebookshop registration</title>
    </head>
    
    <script type="text/javascript">
    function ValidateEmail(email)
{
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    if(email.value.match(mailformat))
    {
        document.getElementById('password').focus();
        return true;
    }
    else
    {
        alert("You have entered an invalid email address!");
        document.getElementById('email').focus();
        return false;
    }
}
</script>
    
    <body>
        <form action="Register" method="post" onclick="ValidateEmail(document.getElementById('email'))">
            <table>
                <tr><td>User Name</td><td><input type="text" name="uname"></td></tr>
                <tr><td>Password</td><td><input type="password" name="password"></td></tr>
                <tr><td>Email</td><td><input type="text" name="email"></td></tr>
                <tr><td>Phone</td><td><input type="text" name="phone"></td></tr>
                <tr><td></td><td><input type="submit" value="register"></td></tr>
            </table>
        </form>
    </body>
</html>
