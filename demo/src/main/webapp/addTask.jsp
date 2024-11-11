<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Task</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <div class="todo-app">
        <h2>To-Do List<img src="assets/icon.png"> </h2>
        <div class="row">
            <form action="task?action=add" method="post">
                <input type="text" id="description" name="description" required>
                <button onclick="addTask()" type="submit">Add Task</button>
            </form>
        </div>
        <a href="index.jsp">Back to Home</a>
    </div>
</div>
</body>
</html>
