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
