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

// Get collection of all the products in the database
Collection<Product> products = (Collection) request.getAttribute("products");

// Default error message
String msg = "";

// If no product is retreived, display a message
if(products.isEmpty()){
    msg = "Your search did not match any items.";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>TVU Mobile Shop products</title>
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
        <%=msg%>
      </h4>
    </div>
    <div class="products">
    <table class="products" border="0">
    <tr>
    <%
      int counter = 0;
      
      // loop through all the products
      for(Product product: products){
        
        // print product details on the webpage
        out.println("<td>");
        out.println("<a href='Shop?action=displayItem&id=" + product.getId() + "'><img src='products/" + product.getPicture() + "' alt='" + product.getName() + "' height='120' /></a>");
        out.println("<br />");
        out.println("<a href='Shop?action=displayItem&id=" + product.getId() + "'>" + product.getName() + "</a>");
        out.println("<br />");
        out.println("Price: <span class='price'>&pound; " + product.getPrice() + "</span>");
        out.println("</td>");

        // close and open a new row for each 3 products
        if(counter%3 == 2){
          out.println("</tr>");

          // if more products are available, open a new row
	  if (counter + 2 < products.size()){
	    out.println("<tr>");
	  }
        }
        
        // increase the counter
        counter++;
      }
    %>
    </table>
    </div>
  </div>
  <%@include  file="footer.jsp" %>
</div>
</body>
</html>
