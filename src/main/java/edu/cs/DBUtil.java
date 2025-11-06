package edu.cs;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/dbtest?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "cs370user";
    private static final String PASS = "SE2020";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

