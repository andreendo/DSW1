<%@ page import="java.util.List" %>
<%@ page import="com.github.serveletthreads.Thread" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    List<Thread> threads = (List<Thread>) request.getAttribute("threads");
%>
<html>
<body>
<h1>Servlet Threads!!!!</h1>
<%
    if (threads.size() > 0) {
%>
        <h2>Number of threads: <%= threads.size() %></h2>
<%
    } else {

%>
        <h2>No thread</h2>
<%
    }
%>
<br/>
<a href="register.jsp">Register</a> | <a href="login.jsp">Login</a>
<br/>
<%
    for(Thread t : threads) {
%>
<p>* <b><%= t.label() %></b> | <%= t.num_messages()%> message(s)</p>
<%
    }
%>
</body>
</html>
