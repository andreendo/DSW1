## DESENVOLVIMENTO DE SOFTWARE PARA A WEB 1

**Prof. André T. Endo (UFSCar)**

**Módulo 08 - SpringMVC, Thymeleaf & Spring Data JPA**

- - -

#### 01 - Criando um mural com as tecnologias Spring MVC, Thymeleaf & JPA


- Criar um novo projeto Spring (https://start.spring.io/)

  - **Project:** Maven Project

  - **Language:** Java

  - **Spring Boot:** 3.4.3

  - **Group:** br.ufscar.dc.dsw

  - **Artifact:** mural

  - **Name:** mural

  - **Description:** mural

  - **Package name:** br.ufscar.dc.dsw

  - **Packaging:** Jar

  - **Java:** 21

    **Dependências:** Spring Web, Spring Boot DevTools, Thymeleaf & Validation

- Baixar o arquivo .zip e descompactar em um diretório (**mural**)

- Crie alguns controllers:

```java
// IndexController.java
package com.example.mural.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String index() {
        logger.info("GET /");
        return "index";
    }
}

// PostarController.java
package com.example.mural.controllers;

import com.example.mural.dto.SendMessageForm;
import com.example.mural.repositories.Message;
import com.example.mural.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostarController {

    private static final Logger logger = LoggerFactory.getLogger(PostarController.class);

    private final MessageRepository messageRepository;

    public PostarController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/postar")
    public String get(Model model) {
        logger.info("Get /postar");
        model.addAttribute("sendMessageForm", new SendMessageForm());
        return "postar";
    }

    @PostMapping("/postar")
    public String post(@ModelAttribute SendMessageForm sendMessageForm, Model model) {
        logger.info("Post /postar - {}", sendMessageForm);

        var message = new Message();
        message.setFrom(sendMessageForm.getFrom());
        message.setTo(sendMessageForm.getTo());
        message.setMessage(sendMessageForm.getMessage());
        messageRepository.save(message);

        model.addAttribute("sendMessageForm", new SendMessageForm());
        return "postar";
    }

}

// VerMensagensController.java
package com.example.mural.controllers;

import com.example.mural.dto.ListedMessage;
import com.example.mural.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class VerMensagensController {

    private final Logger logger = LoggerFactory.getLogger(VerMensagensController.class);

    private MessageRepository messageRepository;

    public VerMensagensController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/mensagens")
    public String verMensagens(Model model) {
        logger.info("GET /mensagens");

        var listedMessages = new ArrayList<ListedMessage>();
        messageRepository.getMessages().forEach(m -> {
            listedMessages.add(new ListedMessage(
                    m.getFrom(),
                    m.getTo(),
                    m.getMessage(),
                    m.getTimestamp()
            ));
        });
        model.addAttribute("messages", listedMessages);
        logger.info("Listed messages: {}", listedMessages);

        return "mensagens";
    }

}


```

- Adicione o arquivo `style.css` em `resources/static/css`:

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

.error-message {
    color: red;
    font-size: 0.9em;
    margin-bottom: 10px;
    padding: 10px;
    background-color: #ffe0e0;
    border: 1px solid #ffb3b3;
    border-radius: 5px;
}

.success-message {
    color: green;
    font-size: 0.9em;
    margin-bottom: 10px;
    padding: 10px;
    background-color: #e0ffe0;
    border: 1px solid #b3ffb3;
    border-radius: 5px;
}
```

- Adicione algumas views:

```html
<!-- index.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Mural</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Mural</h1>
    <ul>
        <li><a th:href="@{/mensagens}">Ver Mensagens</a></li>
        <li><a th:href="@{/postar}">Postar uma nova mensagem</a></li>
    </ul>
</body>
</html>

<!-- postar.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Mural</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Envie uma mensagem para o Mural</h1>
    <ul>
        <li><a th:href="@{/}">Home</a></li>
        <li><a th:href="@{/mensagens}">Ver Mensagens</a></li>
    </ul>

    <form th:action="@{/postar}" th:object="${sendMessageForm}" method="post">
        <label for="from">Enviado por:</label><br>
        <input type="text" th:field="*{from}"><br><br>

        <label for="to">Enviado para:</label><br>
        <input type="text" th:field="*{to}"><br><br>

        <label for="message">Mensagem:</label><br>
        <textarea th:field="*{message}" rows="6" cols="40"></textarea><br><br>

        <button type="submit">Enviar</button>
    </form>

</body>
</html>

<!-- mensagens.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Mural</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Mensagens</h1>
    <ul>
        <li><a th:href="@{/}">Home</a></li>
        <li><a th:href="@{/postar}">Postar uma nova mensagem</a></li>
    </ul>
    <br>

    <div th:if="${#lists.isEmpty(messages)}">
        <p>Sem mensagens.</p>
    </div>
    <div th:unless="${#lists.isEmpty(messages)}">
        <h4 th:text="${'Número de mensagens: ' + messages.size()}"></h4>
        <div th:each="msg : ${messages}">
            <div>
                <strong>De:</strong> <span th:text="${msg.from()}"></span>
                &nbsp;
                <strong>Para:</strong> <span th:text="${msg.to()}"></span>
                (em <span th:text="${msg.timestamp()}"></span>)
            </div>
            <div th:text="${msg.message()}"></div>
        </div>
    </div>

</body>
</html>
```

- Adicionar o pacote dto (Data Transfer Object) - objetos específicos para transferir dados entre as camadas. 

```java
// ListedMessage.java
package com.example.mural.dto;

public record ListedMessage(
        String from,
        String to,
        String message,
        String timestamp
) {}


// SendMessageForm.java
package com.example.mural.dto;


public class SendMessageForm {
    private String from;
    private String to;
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("SendMessageForm[from='%s', to='%s', message='%s']", from, to, message);
    }
}

```

- Adicionar o pacote repositories:

```java
// Message.java
package com.example.mural.repositories;

public class Message {
    private int id;
    private String from;
    private String to;
    private String message;
    private String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return String.format("Message[id='%d', from='%s', to='%s', message='%s', timestamp='%s']", id, from, to, message, timestamp);
    }
}

// MessageRepository.java
package com.example.mural.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MessageRepository {

    private Logger logger = LoggerFactory.getLogger(MessageRepository.class);

    private List<Message> messages;

    public MessageRepository() {
        messages = new ArrayList<>();
        logger.info("MessageRepository instantiated");
    }

    public void save(Message message) {
        message.setId(messages.size() + 1);
        message.setTimestamp((new Date()).toString());
        logger.info("saving message: {}", message);
        messages.add(message);
        logger.info("# messages: {}", messages.size());
    }

    public List<Message> getMessages() {
        return messages;
    }
}

```

#### Adicionar validação de campos

- Refatorar o formulário para validar campos. Inicialmente, incluir anotations no DTO. Em seguida, refatorar o controller para incluir validação.

```java
// SendMessageForm.java
import jakarta.validation.constraints.NotBlank;

public class SendMessageForm {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotBlank
    private String message;
}


// PostarController.java
    @PostMapping("/postar")
    public String post(
            @Valid @ModelAttribute SendMessageForm sendMessageForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        logger.info("Post /postar - {}", sendMessageForm);

        // validação via código
        if (!sendMessageForm.getFrom().trim().equals("") &&
                sendMessageForm.getFrom().equals(sendMessageForm.getTo())) {
            logger.info("From and to equals");
            bindingResult.reject("FromAndToSame", "From and to are the same");
        }

        // se houver erros, volta ao form
        if (bindingResult.hasErrors()) {
            return "postar";
        }

        // c.c., salva a mensagens e redireciona
        var message = new Message();
        message.setFrom(sendMessageForm.getFrom());
        message.setTo(sendMessageForm.getTo());
        message.setMessage(sendMessageForm.getMessage());
        messageRepository.save(message);

        // envio de attr no caso de redirects
        redirectAttributes.addFlashAttribute("success", "Mensagem enviada com sucesso");
        return "redirect:/mensagens";
    }
```

- Acertar as views para mostrar os erros; perceba que há erros por campo e erros globais.

```html
<!-- postar.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Mural</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Envie uma mensagem para o Mural</h1>
    <ul>
        <li><a th:href="@{/}">Home</a></li>
        <li><a th:href="@{/mensagens}">Ver Mensagens</a></li>
    </ul>

    <form th:action="@{/postar}" th:object="${sendMessageForm}" method="post">
        <label for="from">Enviado por:</label><br>
        <input type="text" th:field="*{from}"><br>
        <div th:if="${#fields.hasErrors('from')}" th:errors="*{from}" class="error-message"></div>
        <br>

        <label for="to">Enviado para:</label><br>
        <input type="text" th:field="*{to}"><br>
        <div th:if="${#fields.hasErrors('to')}" th:errors="*{to}" class="error-message"></div>
        <br>

        <label for="message">Mensagem:</label><br>
        <textarea th:field="*{message}" rows="6" cols="40"></textarea><br>
        <div th:if="${#fields.hasErrors('message')}" th:errors="*{message}" class="error-message"></div>
        <br>
        <div th:if="${#fields.hasGlobalErrors()}">
            <div th:each="err : ${#fields.globalErrors()}" class="error-message" th:text="${err}"></div>
        </div>
        <button type="submit">Enviar</button><br>
    </form>

</body>
</html>

<!-- mensagens.html -->
 <!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Mural</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
    <h1>Mensagens</h1>
    <ul>
        <li><a th:href="@{/}">Home</a></li>
        <li><a th:href="@{/postar}">Postar uma nova mensagem</a></li>
        <li><a th:href="@{/mensagens}">Atualizar página</a></li>
    </ul>
    <br>
    <div th:if="${success}" th:text="${success}" class="success-message"></div>
    <div th:if="${#lists.isEmpty(messages)}">
        <p>Sem mensagens.</p>
    </div>
    <div th:unless="${#lists.isEmpty(messages)}">
        <h4 th:text="${'Número de mensagens: ' + messages.size()}"></h4>
        <div th:each="msg : ${messages}">
            <div>
                <strong>De:</strong> <span th:text="${msg.from()}"></span>
                &nbsp;
                <strong>Para:</strong> <span th:text="${msg.to()}"></span>
                (em <span th:text="${msg.timestamp()}"></span>)
            </div>
            <div th:text="${msg.message()}"></div>
        </div>
    </div>

</body>
</html>
```

#### Salvar dados em um banco de dados

- Adicionar os drivers do SBGD no arquivo `pom.xml`: 

```xml
        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.21</version>
			<scope>runtime</scope>
		</dependency>
        <dependency>
            <groupId>org.apache.derby</groupId>
            <artifactId>derbyclient</artifactId>
            <version>10.14.2.0</version>
            <scope>runtime</scope>
        </dependency>
```

- Criar o banco de dados `muralspring` no MySQL ou outro SGBD de sua escolha. 

    - No Apache Derby, abra seu interpretador e execute os seguintes comandos:

```bash
>> java -Dderby.system.home=<DB_HOME> -jar  $DERBY_HOME/lib/derbyrun.jar ij

# No interpretador ij
ij>> connect 'jdbc:derby:muralspring;create=true;user=root;password=root'; 
ij>> disconnect;
ij>> quit;

# inicia o server
>> java -Dderby.system.home=<DB_HOME> -jar  db-derby-10.17.1.0-bin/lib/derbyrun.jar server start
```

- Configure o arquivo `application.properties`.
    - Certifique-se que o SGBD foi inicializado e o BD foi criado. Caso contrário, a aplicação falha na inicialização e o servidor não começa a aceitar requisições. 

```
spring.application.name=mural

# MySQL
spring.datasource.url = jdbc:mysql://localhost:3306/muralspring
spring.datasource.username = root
spring.datasource.password = root

# Derby
# spring.datasource.url=jdbc:derby://localhost:1527/muralspring
# spring.datasource.username = root
# spring.datasource.password = root
# spring.datasource.driver-class-name=org.apache.derby.jdbc.ClientDriver

# JPA
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true
spring.jpa.open-in-view = true
spring.jpa.hibernate.naming.physical-strategy = org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl

# THYMELEAF
spring.thymeleaf.cache = false
```

- Refatorar a classe Message com informações específicas de persistência

```java
// Message.java
package com.example.mural.repositories;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //private int id; // usar tipo Long para ids
    private Long id;
    @Column(name = "mFrom") // from é keyword do SQL, evita erro na criação do BD
    private String from;
    @Column(name = "mTo")
    private String to;
    private String message;
    private String timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return String.format("Message[id='%d', from='%s', to='%s', message='%s', timestamp='%s']", id, from, to, message, timestamp);
    }
}
```

- Crie a interface `IMessageDAO`, Spring Data e JPA fará todo o meio de campo com o SGBD:

```java
package com.example.mural.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IMessageDAO extends CrudRepository<Message, Long> {

    public Message save(Message message);

    public List<Message> findAll();

    @Query("SELECT m FROM Message m ORDER BY m.id DESC")
    public List<Message> findAllOrderedByIdDesc();
}

```

- Refatorar a classe `` é suficiente para que a aplicação agora use a conexão com o SGBD:

```java
package com.example.mural.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MessageRepository {

    private Logger logger = LoggerFactory.getLogger(MessageRepository.class);

//    private List<Message> messages;

    private final IMessageDAO messageDAO;

    public MessageRepository(IMessageDAO messageDAO) {
//        messages = new ArrayList<>();
        this.messageDAO = messageDAO;
        logger.info("MessageRepository instantiated");
    }

    public void save(Message message) {
//        message.setId(messages.size() + 1);
        message.setTimestamp((new Date()).toString());
        var saved = messageDAO.save(message);
        logger.info("saving message: {}", saved);
//        messages.add(message);
//        logger.info("# messages: {}", messages.size());
    }

    public List<Message> getMessages() {
        return messageDAO.findAllOrderedByIdDesc();
//        return messageDAO.findAll();
//        return messages;
    }
}
```