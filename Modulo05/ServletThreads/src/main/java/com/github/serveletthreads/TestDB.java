package com.github.serveletthreads;

public class TestDB {
    public static void main(String[] args) throws Exception {
        /*String url = "jdbc:mysql://localhost:3306/threads1";
        try (Connection conn = DriverManager.getConnection(url, "root", "root")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM threads");
            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("label"));
                System.out.println(rs.getInt("num_messages"));
                System.out.println(rs.getInt("user_id"));
            }
        }*/
        ThreadDAO threadDAO = new ThreadDAO();
        threadDAO.getAll().forEach(thread -> {
            System.out.println(thread);
        });
        UserDAO userDAO = new UserDAO();
        //userDAO.insert("user1", "user1");
        System.out.println(userDAO.login("admin", "admin"));
        System.out.println(userDAO.login("user1", "admin"));
        System.out.println(userDAO.login("user1", "user1"));
    }
}
