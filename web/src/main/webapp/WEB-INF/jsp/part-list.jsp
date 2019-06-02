<%--
  Created by IntelliJ IDEA.
  User: Sgty
  Date: 30.05.2019
  Time: 20:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>List parts</title>
</head>
<body>
<%@ page import="com.itacademy.database.entity.Part,java.util.List" %>
<h3> Part List </h3>
<%
    List<Part> parts = (List<Part>) request.getAttribute("partList");
%>
<h4> Total Number of Books are <%= parts.size() %>
</h4>
<table border="1">
    <tr>
        <td>Number</td>
        <td>Description</td>
        <td>Type</td>
        <td>Sort</td>
        <td>Date</td>
        <td>Manufacturer</td>
    </tr>
    <c:forEach var="part" items="${requestScope.partList}">
        <tr>
            <td> ${part.getPartNumber()} </td>
            <td> ${part.getDescription()}</td>
            <td> ${part.getType()}</td>
            <td> ${part.getSort()}</td>
            <td> ${part.getCreatePartDate()}</td>
            <td> ${part.getManufacturer()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>