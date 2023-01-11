package edu.rihong.DB;

import java.sql.*;
// import com.mysql.jdbc.*;

public class Database {
    private Statement statement;

    public Database() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");

            // Establish a connection
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/cherryforum", 
                "root", 
                "qrh"
            );
            System.out.println("Database connected");

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean register(String account, String username, String passward) {
        try {
            String queryString = "insert user values ('" +
                account + "', '" + username + "', '" + passward + "')";
            
            int ret = statement.executeUpdate(queryString);
            return ret == 1;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean login(String account, String passward, String[] name) {
        try {
            String queryString = "select name, password " + 
                "from user where uid = " + account;
            
            ResultSet resultSet = statement.executeQuery(queryString);

            if (resultSet.next()) {
                String userName = resultSet.getString(1);
                String passWord = resultSet.getString(2);

                if (name == null) {
                    System.out.println("Program Error, null pointer for Database.login.name");
                    return false;
                }

                name[0] = userName;

                return passward.equals(passWord);
            } else {
                System.out.println("account not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
