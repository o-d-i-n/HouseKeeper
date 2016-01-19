package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 1/19/2016.
 */
public class StudentRegister extends Packet {

    public String password;
    public StudentRegister(String roll_number,String password) {
        this.type = Type.STUDENT_REGISTER;
        this.roll_number = roll_number;
        this.password = password;
    }
}
