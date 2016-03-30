package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 1/21/2016.
 */
public class StudentInfo extends Packet {

    public String name;
    public int percentage;
    public String section;
    public String branch;
    public String user_id = "NA";

    public StudentInfo(String user_id,String roll_number,String section,String name)
    {
        this.user_id = user_id;
        this.section = section;
        this.name = name;
        this.roll_number = roll_number;
    }

    public StudentInfo(Type type, String name, String branch, int percentage, int section, String auth_code, String roll_number) {
        this.auth_code = auth_code;
        this.roll_number = roll_number;
        this.name = name;
        this.branch =branch;
        this.percentage = percentage;
        this.section = Integer.toString(section);
        this.type = type;
    }

}
