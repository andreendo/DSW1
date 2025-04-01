Criar um servlet que mostra páginas HTML em uma dada pasta.

Responder as URLs /page ou /p. Ao enviar http://localhost:8080/StaticPageServer/page?target=index, localizar o arquivo index.html ler ele na memória e mostrar. 

- Adicione logs para seu servlet:

```java
    private static Logger logger = Logger.getLogger(StaticPageServlet.class.getName());

    ...

    logger.info("GET received for target = " + target);
```

- Observe o log do Tomcat em tempo real, faça algumas requisições via browser.

```sh
    tail -f logs/catalina.out #não mostra exceptions
    tail -f logs/localhost.2025-04-01.log
```

- Copie a pasta [Exercicio01/pages](Exercicio01/pages) para a raiz do projeto. 

- Implemente o doGet do Servlet, use os trechos a seguir:

```java
    @WebServlet(urlPatterns = {"/page", "/p"})
    ...

    var target = req.getParameter("target");

    ...

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter pw = resp.getWriter();
    // atualize a URL
    var pageFilePath = "/home/andre/web1/StaticPageServer/pages/" + target + ".html";
    BufferedReader reader = new BufferedReader(new FileReader(pageFilePath));
    reader.lines().forEach(line -> {
        pw.println(line);
    });
    reader.close();
    pw.close();

```

- Faça a lógica para validar quando target for null ou não é nenhuma das páginas existentes, sempre mostre a página page_1.html.

- Implemente a classe a seguir no seu projeto e verifique o tempo de resposta do seu servlet.

```java
package br.ufscar.dc.dsw1.StaticPageServer.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.IntStream;

public class ConcurrentClients {

    private static void sendRequest(HttpClient client, String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        long startTime = System.currentTimeMillis();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    long duration = System.currentTimeMillis() - startTime;
                    System.out.println("Response from " + url + " in " + duration + " ms:\n" + response.body());
                });
    }

    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();

        var repetitionsForEachPage = 1;
        for (var i = 1; i <= repetitionsForEachPage; i++) {
            var baseURL = "http://localhost:8080/StaticPageServer/page?target=";
            IntStream.rangeClosed(1, 10).mapToObj(num -> "page_" + num).parallel().forEach(page -> {
                sendRequest(client, baseURL + page);
            });
        }

        try {
            Thread.sleep(3000); // Adjust based on expected response time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

- DESAFIO: Implemente um novo Servlet chamado FasterStaticPageServlet que opera na rota http://localhost:8080/StaticPageServer/fastpage e tente melhorar o desempenho do Servlet (use o response time para obter evidências da melhoria). Use arquivos HTML maiores na pasta [Exercicio01/pages2](Exercicio01/pages2). 