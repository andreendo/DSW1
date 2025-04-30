package org.example.old;

import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.MuralDAO;

import java.io.IOException;

public class Rotas {

    public static void listar(HttpServletRequest req, HttpServletResponse resp, MuralDAO muralDAO) throws ServletException, IOException {
        req.setAttribute("mensagens", muralDAO.getMensagens());
        req.getRequestDispatcher("main.jsp").forward(req, resp);
    }

    public static void postar(HttpServletRequest req, HttpServletResponse resp, MuralDAO muralDAO) throws ServletException, IOException {
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
        // redireciona para a rota listar
        resp.sendRedirect("listar");
    }
}
