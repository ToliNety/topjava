<%--
  Created by IntelliJ IDEA.
  User: tolikswx
  Date: 13.12.2016
  Time: 13:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>EDIT MEAL</p>
<form action="meal" method="post">
    <input type="hidden" name="id" value="${meal.id}"/>
    <input type="hidden" name="dateTime" value="${meal.dateTime}"/>
    <p>Description: <input type="text" name="description" value="${meal.description}"></p>
    <p>Calories: <input type="text" name="calories" value="${meal.calories}"></p>
    <input type="submit" value="Edit meal">
</form>
</body>
</html>
