<%-- 
    Document   : index
    Created on : Feb 27, 2011, 1:51:00 PM
    Author     : Majid
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>TVU Mobile Shop</title>
<meta name="keywords" content="TVU mobile shop, online, mobile, store, market" />
<meta name="description" content="TVU mobiles website is a student Java project about a mobile shop." />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link rel="stylesheet" href="default.css" type="text/css" media="all" title="Default styles" />
<script type="text/javascript" src="global.js"></script>
</head>
<body>
<div id="wrapper">
  <%@include file="header.jsp" %>
  <div id="content">
      <div class="center">
      <h4 class="error">
          <%
            String msg = "";
            if (request.getParameter("msg") != null)
            {
                msg = request.getParameter("msg");
            }
            out.println(msg);
      %></h4>
      </div>
    <div id="contact_form">
      <div class="titlebar"> Login Form </div>
      <form id="login" method="post" action="Registration" onSubmit="return loginvalidate(this)">
        <ul id="forms">
          <li>
            <label for="phone">Username</label>
            <input name="txtUsername" type="text"/>
          </li>
          <li>
            <label for="password">Password</label>
            <input name="txtPassword" type="password"/>
          </li>
          <li class="submit">
            <button type="reset" value="Reset" name="btnReset">Reset</button>
            <button type="submit" value="Login" name="btnLogin">Login</button>
            <input type="hidden" name="action" value="login" />
          </li>
        </ul>
      </form>
    </div>
    <div id="contact_form">
      <div class="titlebar"> Registration Form </div>
      <form id="register" method="post" action="Registration" onSubmit="return registerValidate(this)">
        <ul id="forms">
          <li>
            <label for="phone">Username</label>
            <input name="txtUsername" type="text"/>
          </li>
          <li>
            <label for="password">Password</label>
            <input name="txtPassword" type="password"/>
          </li>
          <li>
            <label for="confirm">Confirm</label>
            <input name="txtConfirm" type="password"/>
          </li>
          <li>
            <label for="name">Full Name</label>
            <input name="txtName" type="text" />
          </li>
          <li>
            <label for="email">Email Address</label>
            <input name="txtEmail" type="text" />
          </li>
          <li class="submit">
            <button type="reset" value="Reset" name="btnReset">Reset</button>
            <button type="submit" value="Register" name="btnRegister">Register</button>
            <input type="hidden" name="action" value="register" />
          </li>
        </ul>
      </form>
    </div>
  </div>
  <%@include  file="footer.jsp" %>
</div>
</body>
</html>
