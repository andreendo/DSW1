<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Mensagem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
  <title>Mural V2</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>Mural</h1>
<h2>Edição e Remoção das Mensagens</h2>

<%
  var mensagens = (List<Mensagem>) request.getAttribute("mensagens");
  for (var mensagem : mensagens) {
%>
  <div>
    <div>| <a href="excluir?id=<%= mensagem.iid() %>">Excluir</a> | <a href="atualizar?id=<%= mensagem.iid() %>">Atualizar</a> | <strong>De:</strong> <%= mensagem.enviadoPor() %> &nbsp; <strong>Para:</strong> <%= mensagem.enviadoPara() %> (em <%= mensagem.timestamp() %>) - <%= mensagem.texto() %></div>
  </div>
  <br/>
<%
  }
%>

</body>
</html>
