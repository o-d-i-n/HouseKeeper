package com.housekeeper.Database.Statements;

import com.housekeeper.Database.Database;
import com.housekeeper.Packet.client.StudentInfo;
import com.housekeeper.Packet.client.StudentRegister;

import java.sql.SQLException;

/**
 * Created by Lenovo on 1/28/2016.
 */
public class Update {

    private Database connection;

    public Update(Database connection) {
        this.connection = connection;
    }

    public void studentInfo(StudentInfo studentInfo, String table) throws SQLException {

        String sql = "UPDATE "+ table +" SET `name`='"+studentInfo.name+"' WHERE `roll_number`='"+studentInfo.roll_number+"'";


        System.out.println(sql);
        connection.sendData(sql);

    }


}
