package com.housekeeper.Database.Statements;

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by Lenovo on 1/28/2016.
 */
public class Select {

    private Database connection;
    private String sql;

    public Select(Database connection) {
        this.connection = connection;
    }

    public boolean checkIfUser(StudentRegister studentRegister) throws SQLException {
        sql = "SELECT `user_id` FROM `user` WHERE `roll_number` = '"+ studentRegister.roll_number +"'";

       return connection.getData(sql);


    }

    public boolean login(StudentLogin studentLogin) throws SQLException {
        sql = "SELECT `password` FROM `user` WHERE `roll_number` = '"+ studentLogin.roll_number +"'";

        connection.getData(sql);

        while(connection.resultSet.next()) {

            String password = connection.resultSet.getString("password");
            System.out.println(password);
            if(Objects.equals(studentLogin.password,password)) {
                return true;
            }

            return false;
        }
        return false;

    }
}
