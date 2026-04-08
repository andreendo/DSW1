<%@ page import="org.example.model.Mensagem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%
  var mensagem = (Mensagem) request.getAttribute("mensagem");
%>
<html>
<head>
  <title>Mural V2</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>Mural</h1>
<h2>Editar Mensagem (ID: <%= mensagem.iid() %>)</h2>
<form action="atualizar" method="post">
  <input type="hidden" name="id" value="<%= mensagem.iid() %>" />
  <label for="enviadoPor">Enviado por:</label><br>
  <input type="text" name="enviadoPor" value="<%= mensagem.enviadoPor() %>"><br><br>

  <label for="enviadoPara">Enviado para:</label><br>
  <input type="text" name="enviadoPara" value="<%= mensagem.enviadoPara() %>"><br><br>

  <label for="texto">Mensagem:</label><br>
  <textarea name="texto" rows="6" cols="40"><%= mensagem.texto() %></textarea><br><br>

  <button type="submit">Atualizar</button>
</form>
</body>
</html>
