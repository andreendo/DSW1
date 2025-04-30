<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<c:forEach var="msg" items="${requestScope.mensagens}">
  <div>
    <div><strong>De:</strong> ${msg.enviadoPor} &nbsp; <strong>Para:</strong> ${msg.enviadoPara} (em ${msg.timestamp})</div>
    <div >${msg.texto}</div>
  </div>
  <br/>
</c:forEach>

</body>
</html>
