package com.housekeeper.Database.Statements;

/**
 * Created by Lenovo on 1/28/2016.
 */

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentInfo;

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



    public void studentInfo(StudentInfo studentInfo,String table) throws SQLException {

        sql = "INSERT INTO "+table+"VALUES (NULL,";

        sql = sql + "'" + studentInfo.roll_number + "',";
        sql = sql + "'" + studentInfo.name + "',";
        sql = sql + "'" + studentInfo.section + "')";

        connection.sendData(sql);

    }
}