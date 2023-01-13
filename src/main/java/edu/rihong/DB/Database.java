package edu.rihong.DB;

import java.sql.*;

import edu.rihong.Model.User;

public class Database {
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "qrh";

    private Statement statement;

    public Database() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[server] Driver loaded");

            // Establish a connection
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/cherryforum", 
                MYSQL_USER, 
                MYSQL_PASSWORD
            );
            System.out.println("[server] Database connected");

            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean register(String account, String username, char gender, int fortune, String passward) {
        if (gender != 'M' && gender != 'F') {
            System.out.println("[server] Program Error, gender should be M or F");
            return false;
        }

        try {
            String queryString = "insert user values ('" +
                account + "', '" + username + "', '" + gender + "', " + fortune  + ", '"  + passward + "')";
            
            int ret = statement.executeUpdate(queryString);
            return ret == 1;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public boolean register(User user) {
        return register(user.getAccount(), user.getName(), user.getGender(), user.getFortune(), user.getPassword());
    }

    public boolean login(String account, String passward, User user) {
        try {
            String queryString = "select name, gender, fortune, password " + 
                "from user where uid = " + account;
            
            ResultSet resultSet = statement.executeQuery(queryString);

            if (resultSet.next()) {
                String userName = resultSet.getString(1);
                String gender = resultSet.getString(2);
                int fortune = resultSet.getInt(3);
                String passWord = resultSet.getString(4);
                // System.out.println("database: " + " " + userName + " " + gender + " ");

                if (user == null) {
                    System.out.println("[server] Program Error, null pointer for Database.login.user");
                    return false;
                }

                if (passward.equals(passWord)) {
                    user.loginState = true;
                    user.setAccount(account);
                    user.setName(userName);
                    user.setGender(gender.equals("M") ? 'M' : 'F');
                    user.setFortune(fortune);
                    user.setPassword(passWord);

                    return true;
                } 
                else {
                    return false;
                }
            } else {
                System.out.println("[server] account not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
