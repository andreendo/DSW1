## Refatorando a aplicação Mural para usar JSP, JSTL, JDBC e MVC

- Faça uma cópia do projeto [muralv2](muralv2). 

    - Caso queira ver o resultado final, veja o projeto pronto em [muralv2refactored](muralv2refactored).

    - A cada modificação, teste a aplicação para garantir que a funcionalidade permanece. 

- Refactoring 1: Usar JSP para mostrar as páginas.

    - Criar um arquivo chamado `main.jsp`, separando os estilos em style.css

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Mural V1</title>
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
__MENSAGEM__
</body>
</html>
```

```css
body {
    font-family: Arial, sans-serif;
    background-color: #f6f8fa;
    color: #333;
    margin: 30px;
    line-height: 1.6;
}

h1 {
    color: #2c3e50;
    border-bottom: 2px solid #ccc;
    padding-bottom: 10px;
}

h2 {
    color: #34495e;
    margin-top: 30px;
}

form {
    background-color: #ffffff;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    max-width: 500px;
}

label {
    font-weight: bold;
}

input[type="text"],
textarea {
    width: 100%;
    padding: 8px;
    margin-top: 4px;
    margin-bottom: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

button {
    background-color: #2ecc71;
    color: white;
    padding: 10px 18px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
}

button:hover {
    background-color: #27ae60;
}

div > div {
    background-color: #ecf0f1;
    padding: 12px;
    border-radius: 6px;
    margin-bottom: 12px;
}
```

- Modifique o método da classes `Rotas.listar(..)`:

```java
    public static void listar(HttpServletRequest req, HttpServletResponse resp, Mural mural) 
        throws ServletException, IOException {
        req.setAttribute("mensagens", mural.getMensagens());
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }
```

- Modifique o arquivo `main.jsp` para mostrar as mensagens:

```jsp

<%@ page import="java.util.List" %>
<%@ page import="org.example.Mensagem" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Mural V1</title>
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
```


- Refactoring 2 (Apache Derby): Salvar as mensagens em um banco de dados. 

    - Crie o arquivo `mural.sql`. 

```sql
connect 'jdbc:derby:Mural;create=true;user=root;password=root';

create table Mensagem(
    id bigint not null generated always as identity, 
    de varchar(50) not null,
    para varchar(50) not null, 
    texto varchar(1024) not null,
    dataEnvio varchar(50) not null, 
    constraint Mensagem_PK primary key (id)
);

disconnect;

quit;
```

- Alguns comandos para criar no banco de dados no Derby.

```sh
# abre o terminal para executar comandos no Derby
>> java -Dderby.system.home=mural/db -jar  <DERBY_HOME_PATH>/lib/derbyrun.jar ij

# Dentro do ij
>> run 'mural/mural.sql';

## Iniciar o servidor do Apache Derby
>> java -Dderby.system.home=mural/db -jar <DERBY_HOME_PATH>/lib/derbyrun.jar server start
```


- Adicione as dependências:

```xml
<dependency>
     <groupId>org.apache.derby</groupId>
     <artifactId>derbyclient</artifactId>
     <version>10.14.2.0</version>
     <scope>runtime</scope>
 </dependency>
 <dependency>
        <groupId>jakarta.annotation</groupId>
        <artifactId>jakarta.annotation-api</artifactId>
        <version>2.1.1</version>
        <scope>provided</scope>
</dependency>

```

- Refactoring 2 (MySQL): Salvar as mensagens em um banco de dados.

- Inicie o MySQL usando Docker e entre no PhpMyAdmin (http://localhost:8081/). Crie um banco de dados chamado `mural`. Execute o código SQL a seguir.

```sql
CREATE TABLE mensagem (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    de VARCHAR(50) NOT NULL,
    para VARCHAR(50) NOT NULL, 
    texto VARCHAR(1024) NOT NULL,
    dataEnvio VARCHAR(50) NOT NULL, 
    CONSTRAINT Mensagem_PK PRIMARY KEY (id)
);
```

- Adicione as dependências:

```xml
 <dependency>
 	<groupId>mysql</groupId>
 	<artifactId>mysql-connector-java</artifactId>
     <version>8.0.21</version>
 	<scope>runtime</scope>
 </dependency>
 <dependency>
        <groupId>jakarta.annotation</groupId>
        <artifactId>jakarta.annotation-api</artifactId>
        <version>2.1.1</version>
        <scope>provided</scope>
</dependency>

```

- Crie as classes a seguir e teste a conexão com o banco de dados:

```java
// arquivo MuralDAO.java

package org.example;

import io.vavr.control.Try;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MuralDAO {

    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    MuralDAO(Connection conn) {
        this.connection = conn;
    }

    public List<Mensagem> getMensagens() {
        var mensagens = new ArrayList<Mensagem>();
        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM mensagem");
        ) {
            while(rs.next()) {
                var id = rs.getInt("id");
                var de = rs.getString("de");
                var para = rs.getString("para");
                var texto = rs.getString("texto");
                var dataEnvio = rs.getString("dataEnvio");
                mensagens.add(new Mensagem(id, de, para, texto, Try.of(() -> sdf.parse(dataEnvio)).getOrElse(new Date())));
            }
        } catch (SQLException e) {

        }

        return mensagens;
    }

    public void addMensagem(String de, String para, String texto) {
        String sql = "INSERT INTO mensagem (de, para, texto, dataEnvio) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, de);
            ps.setString(2, para);
            ps.setString(3, texto);
            ps.setString(4, sdf.format(new Date()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirMensagem(Integer id) throws SQLException {
        String sql = "DELETE FROM mensagem WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("ID inexistente");
        }
    }

    public void atualizar(Mensagem mensagem) throws SQLException {
        String sql = "UPDATE mensagem SET de = ?, para = ?, texto = ?, dataEnvio = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, mensagem.enviadoPor());
        ps.setString(2, mensagem.enviadoPara());
        ps.setString(3, mensagem.texto());
        ps.setString(4, sdf.format(new Date()));
        ps.setInt(5, mensagem.iid());
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Erro na atualização");
        }
    }

    public Mensagem getMensagem(int id) throws SQLException {
        String sql = "SELECT * FROM mensagem WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                var de = rs.getString("de");
                var para = rs.getString("para");
                var texto = rs.getString("texto");
                var dataEnvio = rs.getString("dataEnvio");
                return new Mensagem(id, de, para, texto, Try.of(() -> sdf.parse(dataEnvio)).getOrElse(new Date()));
            } else {
                throw new SQLException("ID inexistente");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}

// arquivo TesteMuralDAO.java

package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMuralDAO {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Apache Derby
        // Class.forName("org.apache.derby.jdbc.ClientDriver");
        // String url = "jdbc:derby://localhost:1527/Mural";

        // MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mural";
        
        Connection conn = (Connection) DriverManager.getConnection(url, "root", "root");
        MuralDAO muralDAO = new MuralDAO(conn);

        muralDAO.addMensagem("a", "b", "c");
        muralDAO.addMensagem("a1", "b1", "c1");
        muralDAO.addMensagem("a2", "b2", "c2");

        muralDAO.getMensagens().forEach(System.out::println);
    }
}


```

- Adicionar a conexão com o banco ao projeto. No caso, o Tomcat irá gerenciar a criação das conexões com o BD. O Tomcat cria um pool de conexões com o BD, definindo o número máximo de conexões, o máximo de conexões idle, e o tempo máximo de espera para obter uma conexão antes de lançar uma exception (-1 - infinito).

```xml
<!-- adicione o este arquivo em /webapp/META-INF/context.xml -->
<!-- Este arquivo especifica as informações para definir um recurso e o Tomcat gerencia as conexões -->
<Context>
    <!--Derby-->
    <Resource name="jdbc/muralDB_Derby"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="20"
              maxIdle="10"
              maxWaitMillis="-1"
              username="root"
              password="root"
              driverClassName="org.apache.derby.jdbc.ClientDriver"
              url="jdbc:derby://localhost:1527/Mural"/>
    <!--MySQL-->
    <Resource name="jdbc/muralDB"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="20"
              maxIdle="10"
              maxWaitMillis="-1"
              username="root"
              password="root"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/mural"/>
</Context>
```

- No Servlet, faça as alterações a seguir.

```java
package org.example;

import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/listar", "/postar"})
public class MainServlet extends HttpServlet {

    // Mapea o resource da conexao com o objeto dataSource
    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    //private Mural mural = new Mural();

    private static Logger logger = Logger.getLogger(MainServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rota = Option.of(req.getServletPath()).getOrElse("");

        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);

            if (rota.equals("/postar")) {
                logger.info("Rota /postar");
                Rotas.postar(req, resp, muralDAO);
            } else if (rota.equals("/listar")) {
                logger.info("Rota /listar");
                Rotas.listar(req, resp, muralDAO);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar o banco", e);
        }

    }
}
```

- Refactoring 3: Separar as classes e pacotes: um pacote para controllers, um para o model, e as views em JSP. 

```java
package org.example.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/listar"})
public class ListarServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ListarServlet.class.getName());

    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /listar");

        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);
            req.setAttribute("mensagens", muralDAO.getMensagens());
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }
    }

}

// 
package org.example.controllers;

import io.vavr.control.Option;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/postar"})
public class PostarServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(PostarServlet.class.getName());

    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /postar");

        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);

            var enviadoPor = Option.of(req.getParameter("enviadoPor")).getOrElse("");
            if (enviadoPor.equals(""))
                enviadoPor = "Desconhecido";
            var enviadoPara = Option.of(req.getParameter("enviadoPara")).getOrElse("");
            if (enviadoPara.equals(""))
                enviadoPara = "Desconhecido";
            var texto = Option.of(req.getParameter("texto")).getOrElse("");
            if (texto.equals(""))
                texto = "Sem mensagem escrita.";

            muralDAO.addMensagem(enviadoPor, enviadoPara, texto);
            resp.sendRedirect("listar");
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }
    }

}

// Tente mudar o SQL 
// SELECT * FROM mensagem ORDER BY id DESC

```

- Refactoring 4: Edição e remoção das mensagens.

```java
// ListarEdicaoServlet.java
package org.example.controllers;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/listarEdicao"})
public class ListarEdicaoServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ListarEdicaoServlet.class.getName());

    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /listarEdicao");

        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);
            req.setAttribute("mensagens", muralDAO.getMensagens());
            req.getRequestDispatcher("listarEdicao.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }
    }

}

// ExcluirServlet.java
package org.example.controllers;

import io.vavr.control.Try;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/excluir" })
public class ExcluirServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(ExcluirServlet.class.getName());

    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /excluir");
        var id = Try.of(() -> Integer.parseInt(req.getParameter("id"))).getOrElse(-1);
        if (id == -1) {
            throw new ServletException("Invalid id!");
        }

        try {
            var conn = dataSource.getConnection();
            var muralDAO = new MuralDAO(conn);
            muralDAO.excluirMensagem(id);
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }

        resp.sendRedirect("listarEdicao");
    }
}

// AtualizarServlet.java

package org.example.controllers;

import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Mensagem;
import org.example.model.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/atualizar"})
public class AtualizarServlet extends HttpServlet {

    private static Logger logger = Logger.getLogger(AtualizarServlet.class.getName());

    @Resource(name = "jdbc/muralDB")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /atualizar (GET)");

        var id = Try.of(() -> Integer.parseInt(req.getParameter("id"))).getOrElse(-1);
        if (id == -1) {
            throw new ServletException("Invalid id!");
        }

        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);
            req.setAttribute("mensagem", muralDAO.getMensagem(id));
            req.getRequestDispatcher("editar.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Rota /atualizar (POST)");

        var id = Try.of(() -> Integer.parseInt(req.getParameter("id"))).getOrElse(-1);
        if (id == -1) {
            throw new ServletException("Invalid id!");
        }
        var enviadoPor = Option.of(req.getParameter("enviadoPor")).getOrElse("");
        if (enviadoPor.equals(""))
            enviadoPor = "Desconhecido";
        var enviadoPara = Option.of(req.getParameter("enviadoPara")).getOrElse("");
        if (enviadoPara.equals(""))
            enviadoPara = "Desconhecido";
        var texto = Option.of(req.getParameter("texto")).getOrElse("");
        if (texto.equals(""))
            texto = "Sem mensagem escrita.";

        var mensagem = new Mensagem(id, enviadoPor, enviadoPara, texto, new Date());
        try (var conn = dataSource.getConnection()) {
            var muralDAO = new MuralDAO(conn);
            muralDAO.atualizar(mensagem);
            resp.sendRedirect("listarEdicao");
        } catch (SQLException e) {
            logger.info(e.toString());
            throw new ServletException("Erro ao acessar o banco", e);
        }
    }
}

```


```jsp
// listarEdicao.jsp
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

//editar.jsp
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
<form action="editar" method="post">
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

```

- Refactoring 5: Usar tags JSTL e internacionalização.

    - Alterar o `pom.xml`:

```xml
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
	<artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
	<version>3.0.0</version>
</dependency>
<dependency>
	<groupId>org.glassfish.web</groupId>
	<artifactId>jakarta.servlet.jsp.jstl</artifactId>
	<version>3.0.1</version>
</dependency>


<plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>native2ascii-maven-plugin</artifactId>
        <version>1.0-beta-1</version>
        <executions>
          <execution>
            <id>native2ascii-utf8-resources</id>
            <goals>
              <goal>native2ascii</goal>
            </goals>
            <configuration>
              <dest>${project.build.directory}/classes</dest>
              <src>${project.resources[0].directory}</src>
              <encoding>UTF-8</encoding>
            </configuration>
          </execution>
        </executions>
      </plugin>
```

- Criar arquivos de propriedades para internacionalização.

```
// -- main/resources/messages_en.properties
message_board = Message Board
see_messages_in_the_board = See the messages in the board

// -- main/resources/messages_pt.properties
message_board = Mural de Mensagens
see_messages_in_the_board = Veja as mensagens no mural
```

- Exemplo de internacionalização, usando fmt do JSTL:

```jsp
<!-- index.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:bundle basename="messages">
<html>
<head>
    <title><fmt:message key="message_board"/> v2</title>
</head>
<body>
  <h2><fmt:message key="message_board"/></h2>
  <a href="listar"><fmt:message key="see_messages_in_the_board"/></a>
</body>
</html>
</fmt:bundle>
```
- Exemplo de JSTL, usando `<c:forEach>`:

```jsp
<!-- main.jsp -->
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

```