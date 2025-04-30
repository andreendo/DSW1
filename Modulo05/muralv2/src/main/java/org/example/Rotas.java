package org.example;

import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Rotas {

    private static String PAGES_HOME = "/home/andre/web1/muralv1/pages";

    public static void erro(HttpServletRequest req, HttpServletResponse resp, String rota) throws ServletException, IOException {
        String htmlContent = Files.readString(Path.of(PAGES_HOME  + "/erro.html"));
        htmlContent = htmlContent.replace("__ROTA__", rota);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(htmlContent);
        out.close();
    }

    public static void listar(HttpServletRequest req, HttpServletResponse resp, Mural mural) throws ServletException, IOException {
        String htmlContent = Files.readString(Path.of(PAGES_HOME  + "/principal.html"));

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        var mensagensHTML = new StringBuffer();
        mural.getMensagens().forEach(mensagem -> {
            mensagensHTML.append(String.format("<div><div><strong>De:</strong> %s &nbsp; <strong>Para:</strong> %s (em %s)</div>",
                    mensagem.enviadoPor(),
                    mensagem.enviadoPara(),
                    mensagem.timestamp())
            );
            mensagensHTML.append(String.format("<div >%s</div></div>", mensagem.texto()));
            mensagensHTML.append("<br>");
        });
        htmlContent = htmlContent.replace("__MENSAGEM__", mensagensHTML.toString());

        out.println(htmlContent);
        out.close();
    }

    public static void postar(HttpServletRequest req, HttpServletResponse resp, Mural mural) throws ServletException, IOException {
        var enviadoPor = Option.of(req.getParameter("enviadoPor")).getOrElse("");
        if (enviadoPor.equals(""))
            enviadoPor = "Desconhecido";
        var enviadoPara = Option.of(req.getParameter("enviadoPara")).getOrElse("");
        if (enviadoPara.equals(""))
            enviadoPara = "Desconhecido";
        var texto = Option.of(req.getParameter("texto")).getOrElse("");
        if (texto.equals(""))
            texto = "Sem mensagem escrita.";

        mural.addMensagem(enviadoPor, enviadoPara, texto);
        // redireciona para a rota listar
        resp.sendRedirect("listar");
    }
}
