- Renomear index.jsp pata test.jsp
- Criar IndexServlet
- Criar um BD
```
CREATE TABLE threads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    label VARCHAR(255) NOT NULL,
    num_messages INT DEFAULT 0,
    user_id INT
);
```
- Adicionar dependência do MySQL
```
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.28</version>
      <scope>runtime</scope>
    </dependency>
```
- Criar TestDB
- Inserir alguns dados / remover
```
INSERT INTO `threads`(`label`) VALUES ('Qual é o caminho?');
INSERT INTO `threads`(`label`) VALUES ('Problemas Legais');
INSERT INTO `threads`(`label`) VALUES ('One Piece seria o GOAT dos animes?');

DELETE FROM `threads`
```
- Criar Thread and ThreadDAO
- Modificar IndexServlet
- Criar o list1.jsp
- Adicionar register.jsp e login.jsp, e estilos usando Bootstrap
- Criar a tabela de usuário
```
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```
- Criar classe User e UserDAO
- Criar PasswordUtil
```
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>

```