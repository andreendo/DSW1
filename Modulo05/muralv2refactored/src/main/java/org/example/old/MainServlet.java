package org.example.old;

import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.MuralDAO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

//@WebServlet(urlPatterns = {"/listar", "/postar"})
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
