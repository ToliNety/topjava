<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tolikswx
  Date: 12.12.2016
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<c:set var="mealsList" value="${requestScope.get('mealsList')}"/>
<c:if test="${empty mealsList}">
    <p>no data</p>
</c:if>

<c:if test="${!empty mealsList}">
    <br/>
    <table>
        <tr>
            <th>ID</th>
            <th>Date time</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <tbody>
        <c:forEach items="${mealsList}" var="meal">
        <c:if test="${meal.exceed}">
        <tr bgcolor="red"></c:if>
            <c:if test="${!meal.exceed}">
        <tr bgcolor="green"></c:if>
            <td>${meal.id}</td>
            <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="<c:url value='meal?action=edit&id=${meal.id}'/>">Edit</a></td>
            <td><a href="<c:url value='meal?action=delete&id=${meal.id}'/>">Delete</a></td>
        </tr>
        </c:forEach>
        <tbody>
    </table>
    <br/>
    <p>ADD NEW MEAL</p>
    <form action="meal" method="post">
        <p>Description: <input type="text" name="description"></p>
        <p>Calories: <input type="text" name="calories"></p>
        <input type="submit" value="Add meal">
    </form>
</c:if>
</body>
</html>
