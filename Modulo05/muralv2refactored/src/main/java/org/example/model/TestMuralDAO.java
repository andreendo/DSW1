package org.example.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMuralDAO {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost:1527/Mural";
        Connection conn = (Connection) DriverManager.getConnection(url, "root", "root");
        MuralDAO muralDAO = new MuralDAO(conn);

        muralDAO.addMensagem("a", "b", "c");
        muralDAO.addMensagem("a1", "b1", "c1");
        muralDAO.addMensagem("a2", "b2", "c2");

        muralDAO.getMensagens().forEach(System.out::println);
    }
}
