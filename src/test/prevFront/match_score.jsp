<%@ page import="org.example.DTO.MatchForViewDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
</head>
<body>

<% MatchForViewDTO match =  (MatchForViewDTO)request.getAttribute("matchInfo");
    boolean matchFinished = request.getAttribute("matchFinished")==null?false:true;
%>

Игрок1 <%= match.getPlayer1Name()%> Игрок2 <%= match.getPlayer2Name()%> <br>
Игрок1Сеты <%= match.getPlayer1Sets()%> Игрок2Сеты <%= match.getPlayer2Sets()%> <br>
Игрок1Счет <%= match.getPlayer1Score()%> Игрок2Счет <%= match.getPlayer2Score()%> <br>
Игрок1Id <%= match.getPlayer1Id()%> Игрок2Id <%= match.getPlayer2Id()%> <br>

<% if(!matchFinished){%>
<form action="<%=request.getContextPath()%>/match-score?uuid=<%=request.getParameter("uuid")%>" method="POST">
    <input type="hidden" name="playerScored" value="<%=match.getPlayer1Id()%>">
    <button type="submit">+</button>
</form>
<form action="<%=request.getContextPath()%>/match-score?uuid=<%=request.getParameter("uuid")%>" method="POST">
    <input type="hidden" name="playerScored" value="<%=match.getPlayer2Id()%>">
    <button type="submit">+</button>
</form>
<%}%>

</body>
</html>