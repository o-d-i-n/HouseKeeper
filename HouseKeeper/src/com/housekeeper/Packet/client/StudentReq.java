package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 4/25/2016.
 */
public class StudentReq extends Packet {

    public StudentReq(String roll_number) {
        this.roll_number = roll_number;
        this.type = Type.STUDENT_REQ;
    }
}
