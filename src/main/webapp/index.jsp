<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/new-match" method="GET">
    <button type="submit">press</button>
</form>

<%
    out.println(request.getContextPath());
%>

<form action="<%=request.getContextPath()%>/new-match" method="POST">
    <input type="text" name="player1">
    <input type="text" name="player2">
    <button type="submit">press</button>
</form>
</body>
</html>
