package com.housekeeper.Database;

import com.housekeeper.Packet.Packet;

import java.sql.*;

/**
 * Created by Lenovo on 1/28/2016.
 */
public class Database {

    // DataBase Connection Parmeters
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/housekeeper";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    public static Connection connection;
    protected Statement statement;
    public ResultSet resultSet;

    protected String sql;

    public Database(){

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            System.out.println("Connection Successful");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean getData(String sql) throws SQLException {

        resultSet = statement.executeQuery(sql);

        if(resultSet != null) {
            return false;
        }

        return true;

    }

    public void sendData(String sql) throws SQLException {

       System.out.print(statement.executeUpdate(sql));

    }

}
