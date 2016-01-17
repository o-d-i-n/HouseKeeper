package com.housekeeper.Packet;

import java.io.Serializable;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class Packet implements Serializable {

    public enum Type{
        STUDENT_INFO
    }

    public String roll_number;
    public String section;
    public String name;
    public int percentage;
    public Type type;

    public Packet(Type type,String name,String roll_number,String section,int percentage) {
        this.name = name;
        this.roll_number = roll_number;
        this.section = section;
        this.percentage = percentage;
        this.type = type;
    }

}
