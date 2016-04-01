package com.housekeeper.Database.Statements;

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentLogin;
import com.housekeeper.Packet.client.StudentRegister;
import com.housekeeper.Packet.client.TimeTable;
import com.sun.deploy.util.StringUtils;

import javax.management.relation.RoleList;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by Lenovo on 1/28/2016.
 */
public class Select {

    private Database connection;
    private String sql;
    private String[] temp;


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
            String final_monday = connection.resultSet.getString("monday");
            String final_tuesday = connection.resultSet.getString("tuesday");
            String final_wednesday = connection.resultSet.getString("wednesday");
            String final_thursday = connection.resultSet.getString("thursday");
            String final_friday = connection.resultSet.getString("friday");

            final_monday = parseDay(final_monday);
            final_tuesday = parseDay(final_tuesday);
            final_wednesday = parseDay(final_wednesday);
            final_thursday = parseDay(final_thursday);
            final_friday = parseDay(final_friday);

            return new TimeTable(final_monday,final_tuesday,final_wednesday,final_thursday,final_friday);

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

    public String getSubjectInfo(String subject_code) throws SQLException {
        sql = "SELECT * FROM `subjects` WHERE `code`=\""+subject_code + "\"";
        executeStatement(sql);

        while(connection.resultSet.next()) {
            return connection.resultSet.getString("subject_name");
        }

        return null;
    }

    private String parseDay(String day) throws SQLException {

        temp = day.split(";");
        String final_day = "";
        String[] temp2 = temp;

        for(int i=0;i<temp2.length;i++) {

            temp = temp2;
            temp = temp[i].split("!");
            String subject = temp[1];
            subject = getSubjectInfo(subject);
            temp[1] = subject;
            String better ="";

            for(int j = 0;j<temp.length;j++)
            {
                if(better != "")
                    better = better + "!" +temp[j];
                else
                    better = better + temp[j];
            }
            if(final_day != "")
                final_day = final_day + ";" + better;
            else
                final_day = final_day + better;
        }
        return final_day;
    }
    private boolean executeStatement(String sql) throws SQLException {
        return connection.getData(sql);
    }





}
