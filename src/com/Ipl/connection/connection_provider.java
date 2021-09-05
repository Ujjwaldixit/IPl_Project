package com.Ipl.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection_provider {
    private static Connection connection=null;
    private final static String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
    private final static String username = "root";
    private final static String password = "root";

    public static Connection getConnection() {
        try {
            if (connection == null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url,username,password);
            }
            else{
               System.out.println("Connection not established");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
