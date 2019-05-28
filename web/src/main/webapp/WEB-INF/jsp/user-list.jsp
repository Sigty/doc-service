<%--
  Created by IntelliJ IDEA.
  User: Sgty
  Date: 25.05.2019
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <c:forEach var="user" items="${requestScope.userList}">
        <li>${user}</li>
    </c:forEach>
</table>
</body>
</html>
