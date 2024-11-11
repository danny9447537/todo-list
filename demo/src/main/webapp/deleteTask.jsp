<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Task</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
<div class="container">
    <div class="todo-app">
        <h2>To-Do List<img src="assets/icon.png"> </h2>
        <div class="row">
            <form action="task?action=delete" method="post">
                <input type="number" id="id" name="id" required>
                <button type="submit">Delete Task</button>
            </form>
        </div>

        <a href="index.jsp">Back to Home</a>
    </div>
</div>
</body>
</html>
