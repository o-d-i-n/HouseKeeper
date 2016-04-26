package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 4/12/2016.
 */
public class Attendance extends Packet {

    public Integer[] attendance = new Integer[5];

    public Attendance(Integer[] attendance) {
        this.attendance = attendance;
        this.type = Type.ATTENDANCE;
    }

    public Attendance(int one,int two,int three,int four,int five) {
        attendance[0] = one;
        attendance[1] = two;
        attendance[2] = three;
        attendance[3] = four;
        attendance[4] = five;
        this.type = Type.GET_ATTEDANCE;
    }

    public Attendance() {
        this.type = Type.GET_ATTEDANCE;
    }
}
