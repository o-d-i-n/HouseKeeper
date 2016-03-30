package com.housekeeper.Database.Statements;

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;
import com.housekeeper.Packet.client.TimeTable;

import javax.management.relation.RoleList;
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

       return executeStatement(sql);


    }

    public boolean login(StudentLogin studentLogin) throws SQLException {
        sql = "SELECT `password` FROM `user` WHERE `roll_number` = '"+ studentLogin.roll_number +"'";

        executeStatement(sql);

        while(connection.resultSet.next()) {

            String password = connection.resultSet.getString("password");

            if(Objects.equals(studentLogin.password,password)) {
                return true;
            }

            return false;
        }
        return false;

    }

    public TimeTable getTimeTable(int id) throws SQLException {
        sql = "SELECT * FROM `timetable` WHERE `id` = 36519";
        executeStatement(sql);

        while(connection.resultSet.next()) {
            String raw_monday = connection.resultSet.getString("monday");
            String raw_tuesday = connection.resultSet.getString("monday");
            String raw_wednesday = connection.resultSet.getString("monday");
            String raw_thursday = connection.resultSet.getString("monday");
            String raw_friday = connection.resultSet.getString("monday");

            return new TimeTable(raw_monday,raw_tuesday,raw_wednesday,raw_thursday,raw_friday);

        }

        return null;

    }



    public StudentInfo getStudentInfo(String roll_numberz) throws SQLException {
        sql = "SELECT * FROM `user` WHERE `roll_number` = \"" + roll_numberz+"\"";
        executeStatement(sql);

        while(connection.resultSet.next()) {

            String user_id = connection.resultSet.getString("user_id");
            String roll_number = connection.resultSet.getString("roll_number");
            String name = connection.resultSet.getString("name");
            String section = connection.resultSet.getString("section");

            return new StudentInfo(user_id,roll_number,section,name);

        }

        return null;

    }

    private boolean executeStatement(String sql) throws SQLException {
        return connection.getData(sql);
    }




}
