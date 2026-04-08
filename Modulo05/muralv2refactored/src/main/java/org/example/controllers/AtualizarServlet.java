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