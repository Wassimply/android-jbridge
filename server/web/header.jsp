<%-- 
    Document   : header
    Created on : Mar 6, 2011, 11:00:49 PM
    Author     : Majid
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="header">
    <img src="images/logo.png" alt="TVU Fruit Shop" />
</div>
<div id="menubar">
    <ul id="menu">
        <li class="first"><a href="index.jsp">Home</a></li>
        <li><a href="Shop">Products</a></li>
	<li class="search">
	  <form id="frmSearch" action="Shop" onsubmit="return validateSearch(this)">
	    <div>
	      <label for="txtKeyword">Search:</label> <input name="txtKeyword" type="text" id="txtKeyword" maxlength="40" /> <input id="searchButton" name="Search" type="submit" value="Search" />
	    </div>
	  </form>
	</li>
        <li class="last"><img class="menu" src="images/cart.png" alt="Shopping Cart" /><a href="Shop?action=basket">Shopping cart</a></li>
    </ul>
</div>
