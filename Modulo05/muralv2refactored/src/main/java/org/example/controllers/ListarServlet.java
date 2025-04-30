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