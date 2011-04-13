<%-- 
    Document   : index
    Created on : Feb 27, 2011, 1:51:00 PM
    Author     : Majid
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="server.entitybean.Customer" %>
<%@ page import="server.entitybean.Product" %>
<%@ page import="java.util.Collection;" %>
<%

// Get customer object from users session data
Customer customer = (Customer) session.getAttribute("customer");

// If customer is null means user is not logged in, so redirect user to the homepage
if(customer == null){
	response.sendRedirect("?msg=To access this page you must login first");
}

// Get selected product
Product item = (Product) request.getAttribute("item");

// if item is null means cannot find product by given id, so redirect user to the homepage
if ( item == null){
    response.sendRedirect("?msg=Product not found");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title><%= item.getName()%></title>
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
      %>
      </h4>
      </div>
    <div id="productDetailsLeft">
      <p class="title"><%= item.getName()%></p>
      <p class="code">item code: <%= item.getId()%></p>
      <img src="products/<%= item.getPicture()%>" alt="<%= item.getName()%>" /> <br />
      <dl class="description">
        <dt>Product Description:</dt>
        <dd> <%= item.getDescription()%> </dd>
      </dl>
    </div>
    <div id="productDetailsRight">
      <dl class="product">
        <dt><%= item.getName()%></dt>
        <dd>
            <form action="Shop" onsubmit="return validateQuantity(this)">
            <div class="product">
                <img src="products/<%= item.getPicture()%>" alt="<%= item.getName()%>" /> <br />
                Price: <span class="price">&pound;<%= item.getPrice()%></span> <br /><br />
                <%
                  if (item.getQuantity() > 0){
                %>
                    <label>Quantity:</label>
                    <input type="hidden" name="action" value="addToBasket" />
                    <input name="qty" type="text" value="1" size="3" maxlength="3" />
                    <span class="gray">(<%= item.getQuantity()%> available)</span>
                    <input name="id" type="hidden" value="<%= item.getId()%>"  />
                    <p class="right">
                        <input type="submit" name="btnAddToCart" value="Add to cart" />
                    </p>
                <%
                  }else{
                    out.println("Out of stock!");
                  }
                %>
            </div>
        </form>
      </dd>
    </dl>
    </div>
  </div>
  <%@include  file="footer.jsp" %>
</div>
</body>
</html>