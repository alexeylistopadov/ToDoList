<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>ToDo List</title>
</head>
<body>
<h1>ToDo List</h1>
<form action="/" method="post">
  <input type="text" name="point">
  <input type="submit" value="submit">
</form>
<ol>
  <c:forEach items="${points}" var="point">
    <li>${point}</li>
  </c:forEach>
</ol>
</body>
</html>