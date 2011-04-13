<%-- 
    Document   : index
    Created on : Feb 27, 2011, 1:51:00 PM
    Author     : Majid
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="server.entitybean.Customer" %>
<%@ page import="server.entitybean.Product" %>
<%@ page import="java.util.ArrayList;" %>
<%

// Get customer object from users session data
Customer customer = (Customer) session.getAttribute("customer");

// If customer is null means user is not logged in, redirect them to the homepage
if(customer == null){
	response.sendRedirect("?msg=To access this page you must login first");
}

// Get shopping basket from users session
ArrayList<Product> basket = (ArrayList<Product>) session.getAttribute("basket");

// If basket is null, set it to an empty arraylist
if ( basket == null){
    basket = new ArrayList<Product>();
}

// Default error message
String msg = "";

// If a message attribute is provided, get it it from attributes
if (request.getAttribute("msg") != null){
    msg = (String) request.getAttribute("msg");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<title>Shopping Basket</title>
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
    <div id="basket">
      <h3>Shopping Basket</h3>
      <h4 class="error"><%=msg%></h4>
      <%
        // if there is no item in the basket
        if( basket.size() == 0 )
        {
          out.println("<p>Your shoping basket is empty</p>");
        }else{
      %>
      <form id="form1" action="Shop">
        <table class="basket">
          <tr>
            <th>#</th>
            <th>Picture</th>
            <th>Name</th>
            <th>Qty</th>
            <th>Item Price</th>
            <th>Total</th>
          </tr>
        <%

          // total amount
          double total = 0;

          // get each item from the basket
          for(int i =0; i < basket.size(); i ++){
            Product item = basket.get(i);

    
            // calculate the cost and calculate the total
            double cost = item.getQuantity() * item.getPrice();
            total += cost;


            //  get css class name depending on the even and odd rows
            String className = "odd";
            if( i%2 == 0){
              className = "even";
            }
          %>
            <tr class="<%= className%>">
                <td><%= i+1 %></td>
                <td><img src="products/<%= item.getPicture()%>" alt="<%= item.getName()%>" height="100" /></td>
                <td><%= item.getName()%></td>
                <td><%= item.getQuantity()%></td>
                <td>&pound; <%= item.getPrice()%></td>
                <td>&pound <%= cost%></td>
            </tr>
          <%
            }
          %>
            <tr class="total">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
                <td>SubTotal :</td>
                <td>&pound; <%=total%> </td>
		<td>&nbsp;</td>
            </tr>
            <tr>
		<td colspan="7" class="checkout">
                    <input type="hidden" name="action" value="basket" />
                    <input type="submit" name="btnEmpty" value="Empty Cart" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" name="btnCheckout" value="Checkout" onclick="window.location='Shop?action=checkout'" />
		</td>
            </tr>
	  </table>
      </form>
      <%
        }
      %>
      <br />
    </div>
  </div>
  <%@include  file="footer.jsp" %>
</div>
</body>
</html>
