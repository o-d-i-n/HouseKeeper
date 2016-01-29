package com.housekeeper.Database.Statements;

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentRegister;

import java.sql.SQLException;

/**
 * Created by Lenovo on 1/28/2016.
 */
public class Update extends Database{

    private Database connection;
    private String sql;

    public Update(Database connection) {
        this.connection = connection;
    }

    public void studentInfo(StudentInfo studentInfo, String table) throws SQLException {

        sql = "UPDATE "+ table +" SET `name`='"+studentInfo.name+"' WHERE `roll_number`='"+studentInfo.roll_number+"'";

        connection.sendData(sql);

    }


}
