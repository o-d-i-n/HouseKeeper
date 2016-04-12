package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class Attendance extends Packet {

    public Integer[] attendance;

    public Attendance(Integer[] attendance) {
        this.attendance = attendance;
        this.type = Type.ATTENDANCE;
    }
}
