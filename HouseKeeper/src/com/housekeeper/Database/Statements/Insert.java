package com.housekeeper.Database.Statements;

/**
 * Created by Lenovo on 1/28/2016.
 */

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.Attendance;
import com.housekeeper.Packet.client.StudentRegister;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;


/**
 * Created by Lenovo on 1/28/2016.
 */
public class Insert {

    Database connection;
    String sql;


    public Insert(Database connection) {
        this.connection = connection;
    }



    public void studentInfo(StudentRegister studentInfo, String table) throws SQLException {

        sql = "INSERT INTO "+ table +" (`roll_number`,`name`,`section`,`branch`,`percentage`,`password`)" + "VALUES (";

        sql = sql + "'" + studentInfo.roll_number + "',";
        sql = sql + "'" + "N/A" + "',";
        sql = sql + "'" + "0" + "',";
        sql = sql + "'" + "N/A" + "',";
        sql = sql + "'" + "0" + "',";
        sql = sql + "'" + studentInfo.password + "')";

        connection.sendData(sql);

    }

    public void subjects(Object[] sub,String user_id) throws SQLException {

        sql = "INSERT INTO `studentsubjects`(`user_id`, `subject1`, `subject2`, `subject3`, `subject4`, `subject5`) VALUES (" +
                "'" + user_id + "','"+sub[0]+"','"+sub[1]+"','"+sub[2]+"','"+sub[3]+"','"+sub[4]+"')";

        connection.sendData(sql);
    }

    public void sendAttendance(Attendance aat,Select select,String user_id) throws SQLException {
        sql = "SELECT * FROM `attendance` WHERE `user_id` = '"+user_id+"'";

        select.executeStatement(sql);
        Integer subject1 = 0,subject2=0,subject3=0,subject4=0,subject5=0;
        int flag = 0;
        while(select.connection.resultSet.next()) {
            flag = 1;
            subject1 += Integer.parseInt(select.connection.resultSet.getString("subject1"));
            subject2 += Integer.parseInt(select.connection.resultSet.getString("subject2"));
            subject3 += Integer.parseInt(select.connection.resultSet.getString("subject3"));
            subject4 += Integer.parseInt(select.connection.resultSet.getString("subject4"));
            subject5 += Integer.parseInt(select.connection.resultSet.getString("subject5"));
            System.out.println(subject2);
        }

        //System.out.println(user_id + "yo");
        subject1 += aat.attendance[0];
        subject2 += aat.attendance[1];
        subject3 += aat.attendance[2];
        subject4 += aat.attendance[3];
        subject5 += aat.attendance[4];

        if(flag == 1) {
            sql = "UPDATE `attendance` SET `subject1`='"+subject1+"',`subject2`='"+subject2+"',`subject3`='"+subject3+"',`subject4`='"+subject4+"',`subject5`='"+subject5+"' WHERE `user_id`='"+user_id+"'";
        } else {
            sql = "INSERT INTO `attendance`(`user_id`, `subject1`, `subject2`, `subject3`, `subject4`, `subject5`) VALUES ('"+user_id+"','"+subject1+"','"+subject2+"','"+subject3+"','"+subject4+"','"+subject5+"')";
        }

        connection.sendData(sql);

    }
}