package org.example;

import io.vavr.control.Option;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(urlPatterns = {"/listar", "/postar"})
public class MainServlet extends HttpServlet {

    private Mural mural = new Mural();

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

        if (rota.equals("/postar")) {
            logger.info("Rota /postar");
            Rotas.postar(req, resp, mural);
        } else if (rota.equals("/listar")) {
            logger.info("Rota /listar");
            Rotas.listar(req, resp, mural);
        } else {
            logger.info("Rota não definida: " + rota);
            Rotas.erro(req, resp, rota);
        }
    }
}
