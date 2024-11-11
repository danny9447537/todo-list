<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>All Tasks</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <div class="todo-app">
        <h2>To-Do List<img src="assets/icon.png"> </h2>
        <ul id="list-container">
            <c:forEach var="task" items="${tasks}">
                <li>Task ID: ${task.id}, Description: ${task.description}</li>
            </c:forEach>
        </ul>
        <a href="index.jsp">Back to Home</a>
    </div>
</div>
<script src="js/script.js"></script>
</body>
</html>
