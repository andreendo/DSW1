package com.github.serveletthreads;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadDAO {

    public List<Thread> getAll() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        List<Thread> threads = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/threads1";
        try (Connection conn = DriverManager.getConnection(url, "root", "root")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM threads");
            while (rs.next()) {
                threads.add(new Thread(
                        rs.getInt("id"),
                        rs.getString("label"),
                        rs.getInt("num_messages"),
                        rs.getInt("user_id")
                        ));
            }
        }

        return threads;
    }
}
