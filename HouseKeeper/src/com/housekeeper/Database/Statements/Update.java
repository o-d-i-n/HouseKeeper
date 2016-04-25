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
    private String sql;

    public Update(Database connection) {
        this.connection = connection;
    }

    public void studentInfo(StudentInfo studentInfo, String table) throws SQLException {

        System.out.println(studentInfo.roll_number);
        sql = "UPDATE `user` SET `name`='"+studentInfo.name+"', `section`='"+studentInfo.section+"' , `branch`='"+studentInfo.branch +"' , `percentage`='"+studentInfo.percentage+"' WHERE `roll_number`='"+ studentInfo.roll_number+"'";
        connection.sendData(sql);

    }


}
