<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Mensagem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Mural V2</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
<h1>Mural</h1>
<h2>Enviar Mensagem</h2>
<form action="postar" method="post">
  <label for="enviadoPor">Enviado por:</label><br>
  <input type="text" name="enviadoPor"><br><br>

  <label for="enviadoPara">Enviado para:</label><br>
  <input type="text" name="enviadoPara"><br><br>

  <label for="texto">Mensagem:</label><br>
  <textarea name="texto" rows="6" cols="40"></textarea><br><br>

  <button type="submit">Enviar</button>
</form>

<br><br><h2>Mensagens no mural:</h2><br>
<%
  List<Mensagem> mensagens = (List<Mensagem>) request.getAttribute("mensagens");
  if (mensagens == null) {
    mensagens = new ArrayList<>();
  }

  for (var msg : mensagens) {
%>
    <div>
      <div><strong>De:</strong> <%= msg.enviadoPor() %> &nbsp; <strong>Para:</strong> <%= msg.enviadoPara()%> (em <%= msg.timestamp()%>)</div>
      <div ><%= msg.texto() %></div>
    </div>
    <br/>
<%
  }
%>
</body>
</html>
