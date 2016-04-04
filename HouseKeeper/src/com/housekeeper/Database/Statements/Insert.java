package com.housekeeper.Database.Statements;

/**
 * Created by Lenovo on 1/28/2016.
 */

import com.housekeeper.Database.Database;
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

        sql = "INSERT INTO "+ table +" (`roll_number`,`name`,`section`,`password`)" + "VALUES (";

        sql = sql + "'" + studentInfo.roll_number + "',";
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

}