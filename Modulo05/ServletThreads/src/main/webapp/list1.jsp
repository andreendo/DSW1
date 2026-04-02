<%@ page import="java.util.List" %>
<%@ page import="com.github.serveletthreads.Thread" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    List<Thread> threads = (List<Thread>) request.getAttribute("threads");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Threads</title>

    <!-- Bootstrap CSS - Estilo incluídos como sugestão do ChatGPT ao arquivo original -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <!-- Header -->
    <div class="text-center mb-4">
        <h1 class="fw-bold">🧵 Servlet Threads</h1>
    </div>

    <!-- Actions -->
    <div class="d-flex justify-content-center gap-3 mb-4">
        <a href="register.jsp" class="btn btn-primary">Register</a>
        <a href="login.jsp" class="btn btn-outline-secondary">Login</a>
    </div>

    <!-- Info -->
    <div class="card shadow-sm mb-4">
        <div class="card-body text-center">
            <%
                if (threads != null && threads.size() > 0) {
            %>
            <h5 class="card-title">
                Number of threads: <span class="badge bg-success"><%= threads.size() %></span>
            </h5>
            <%
            } else {
            %>
            <h5 class="card-title text-muted">No threads available</h5>
            <%
                }
            %>
        </div>
    </div>

    <!-- Threads List -->
    <div class="list-group shadow-sm">
        <%
            if (threads != null) {
                for(Thread t : threads) {
        %>
        <div class="list-group-item d-flex justify-content-between align-items-center">
            <div>
                <strong><%= t.label() %></strong>
            </div>
            <span class="badge bg-primary rounded-pill">
                    <%= t.num_messages() %> msg
                </span>
        </div>
        <%
                }
            }
        %>
    </div>

</div>

</body>
</html>
