package com.github.serveletthreads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserDAO {
    public void insert(String username, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/threads1";
        try(Connection conn = DriverManager.getConnection(url,"root","root");) {
            Statement stmt = conn.createStatement();
            var hashedPassword = PasswordUtil.hash(password);
            stmt.execute("INSERT INTO users(username, password) VALUES ('"+username+"','"+hashedPassword+"')");
        }
    }

    public String login(String username, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/threads1";
        try(Connection conn = DriverManager.getConnection(url,"root","root");) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT password FROM users WHERE username = '"+username+"'");
            if (rs.next()) {
                String dbPassword = rs.getString("password");
                if (PasswordUtil.check(password, dbPassword)) {
                    return "SUCCESS";
                } else {
                    return "WRONG_PASSWORD";
                }
            } else {
                return "NO_USERNAME";
            }
        }
    }
}
