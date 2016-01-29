package com.housekeeper.Database.Statements;

/**
 * Created by Lenovo on 1/28/2016.
 */

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentRegister;

import java.sql.SQLException;


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

        sql = "INSERT INTO "+table+" (`roll_number`,`name`,`section`,`password`)" + "VALUES (";

        sql = sql + "'" + studentInfo.roll_number + "',";
        sql = sql + "'" + "N/A" + "',";
        sql = sql + "'" + "0" + "',";
        sql = sql + "'" + studentInfo.password + "')";

        connection.sendData(sql);

    }
}