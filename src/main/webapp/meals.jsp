<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <form method="get" action="meals">
       <input type="hidden" name="action" value="changeUser">
        <select name="user">
            <c:forEach items="${users}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
                <option value="${user.id}">${user.name}</option>
            </c:forEach>
        </select>
        <button type="submit">Select user</button>
    </form>
    <br>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="getAllFiltered">
        <table cellpadding="4">
        <tr>
            <td>Begin Date:</td>
            <td><input type="date" name="startDate"></td>
            <td>End Date:</td>
            <td><input type="date" name="endDate"></td>
        </tr>
        <tr>
            <td>Begin Time:</td>
            <td><input type="time" name="startTime"></td>
            <td>End Time:</td>
            <td><input type="time" name="endTime"></td>
        </tr>
        </table>
        <button type="submit">Filter</button>
    </form>
    <br>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>