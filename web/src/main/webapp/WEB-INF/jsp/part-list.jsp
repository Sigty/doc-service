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
<%@ page import="com.itacademy.database.dto.ViewPartBasicDto,com.itacademy.database.entity.Part" %>
<%@ page import="java.util.List" %>
<h3> Part List </h3>
<%
    List<Part> allParts = (List<Part>) request.getAttribute("allPartList");
    List<ViewPartBasicDto> parts = (List<ViewPartBasicDto>) request.getAttribute("partList");

%>
<h4> Total number of parts are <%= allParts.size() %>
</h4>
<h4> Current number of parts are <%= parts.size() %>
</h4>
<table border="1">
    <tr>
        <form action="${pageContext.request.contextPath}/part-list">
            <td><input type="text" name="partNumber" value=""></td>
            <td></td>
            <td></td>
            <td><input type="text" name="sort" value=""></td>
            <td></td>
            <td><input type="text" name="manufacturer" value=""></td>
            <input type="submit" value="Find"/>
        </form>
    </tr>
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
<%--Next page --%>
<c:if test="${currentPage lt noOfPages}">
    <td><a href="part-list?page=${currentPage + 1}&partNumber=${partNumber}&sort=${sort}
    &manufacturer=${manufacturer}">Next</a></td>
</c:if>

<table border="1">
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="part-list?page=${i}&partNumber=${partNumber}&sort=${sort}
                    &manufacturer=${manufacturer}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<%--Previous page --%>
<c:if test="${currentPage != 1}">
    <td><a href="part-list?page=${currentPage - 1}&partNumber=${partNumber}&sort=${sort}
    &manufacturer=${manufacturer}">Previous</a>
    </td>
</c:if>

</body>
</html>
