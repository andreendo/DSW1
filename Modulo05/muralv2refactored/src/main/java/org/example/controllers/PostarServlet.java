package org.example.controllers;

import io.vavr.control.Option;
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
