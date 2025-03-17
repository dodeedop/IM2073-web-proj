<%-- 
    Document   : welcome
    Created on : 16 Mar 2025, 11:35:33 pm
    Author     : adver
--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
<form action="EShopDisplayServlet" method="post">
Welcome <%=session.getAttribute("uname") %>
<style>
body {font-family: Arial, Helvetica, sans-serif;}
form {border: 3px solid #f1f1f1;}
 
button {
  background-color: #04AA6D;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}
 
button:hover {
  opacity: 0.8;
}
 
.container {
  padding: 16px;
}
 
span.psw {
  float: right;
  padding-top: 16px;
}
 
/* Change styles for span and cancel button
   on extra small screens */
@media screen and (max-width: 300px) {
  span.psw {
     display: block;
     float: none;
  }
}
</style>

<button type="submit">Login</button>
</form>
</body>
</html>
