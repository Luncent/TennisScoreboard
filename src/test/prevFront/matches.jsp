<%@ page import="org.example.DTO.MatchesFinishedDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Dima
  Date: 21.10.2024
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String nameFilter = (String)request.getAttribute("nameFilter");
    String contextPath = request.getContextPath();
    int currentPage = (int)request.getAttribute("currentPage");
    int pagesNumber = ((Double)request.getAttribute("pagesNumber")).intValue();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <% for(MatchesFinishedDTO match : (List<MatchesFinishedDTO>)request.getAttribute("matches")){%>
    <%=match.getPlayer1Name()+" "+match.getPlayer2Name()+"  "+match.getWinnerName()%><br>
    <%}%>

    <form action="<%=contextPath%>/matches" method="GET">
        <input type="text" name="filter_by_player_name">
        <button type="submit">Отфильтровать</button>
    </form>

    <%if(currentPage==1 && pagesNumber>=3){%>
    <ul>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>?page=<%=1%>">1</a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>?page=<%=2%>">2</a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>?page=<%=3%>">3</a></li>
        <li><%=pagesNumber+"currentPage==1 && pagesNumber>=3"%></li>
    </ul>
    <%} else if(currentPage==1 && pagesNumber<3){%>
    <ul>
       <% for(int i = 1; i<=pagesNumber;i++){%>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=i%>"><%=i%></a></li>
    <%}%>
        <li><%=pagesNumber+"currentPage==1 && pagesNumber<3"%></li>
    </ul>
    <%} else if(currentPage!=1 && (currentPage+1)<=pagesNumber){%>
    <ul>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage-1%>"><%=currentPage-1%></a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage%>"><%=currentPage%></a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage+1%>"><%=currentPage+1%></a></li>
        <li><%=pagesNumber+"currentPage!=1 && (currentPage+1)<=pagesNumber"%></li>
    </ul>
    <%} else if (currentPage==pagesNumber && pagesNumber>=3) {%>
    <ul>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage-2%>"><%=currentPage-2%></a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage-1%>"><%=currentPage-1%></a></li>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=currentPage%>"><%=currentPage%></a></li>
        <li><%=pagesNumber+"currentPage==pagesNumber && pagesNumber>=3"%></li>
    </ul>
    <%} else if (currentPage==pagesNumber && pagesNumber<3) {%>
    <ul>
        <%for(int i =currentPage; i>0;i--){%>
        <li><a href="<%=contextPath%>/matches?filter_by_player_name=<%=nameFilter%>&page=<%=i%>"><%=i%></a></li>
        <%}%>
        <li><%=pagesNumber+"currentPage==pagesNumber && pagesNumber<3"%></li>
    </ul>
    <%}%>
</body>
</html>
