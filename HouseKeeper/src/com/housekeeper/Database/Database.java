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


    private static Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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

    public void getData(String sql) throws SQLException {



        resultSet = statement.executeQuery(sql);

        while(resultSet.next()) {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            System.out.print("ID: " + id);
            System.out.println(", Last: " + name);

        }

    }

    public void sendData(String sql) throws SQLException {



       System.out.print(statement.executeUpdate(sql));


    }

}
