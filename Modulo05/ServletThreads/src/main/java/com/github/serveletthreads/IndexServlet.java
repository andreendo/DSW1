package com.github.serveletthreads;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    ThreadDAO threadDAO = new ThreadDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            var threads = threadDAO.getAll();
            req.setAttribute("threads", threads);
            req.getRequestDispatcher("list1.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

/*
// versão 1
@WebServlet("/index")
public class IndexServlet extends HttpServlet {

    ThreadsDAO threadsDAO = new ThreadsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(PrintWriter out = resp.getWriter()) {
            out.println("<h1>Servlet Threads!!!!</h1>");
            var threads = threadsDAO.getAll();
            out.println("<br>Threads: "+threads.size()+"<br>");
            threads.forEach(thread -> {
                out.println(String.format("<p>%s</p>", thread.toString()));
            });
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
*/