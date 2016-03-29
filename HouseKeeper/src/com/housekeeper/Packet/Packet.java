package com.housekeeper.Packet;

import java.io.Serializable;

/**
 * Created by Lenovo on 1/17/2016.
 */
public abstract class Packet implements Serializable {

    protected Packet() {
    }

    public enum Type{
        STUDENT_INFO,
        STUDENT_LOGIN,
        STUDENT_REGISTER,
        CHAT,
        SERVER_RESPONSE,
        CONNECTED_USERS,
        TIMETABLE
    }
    public String auth_code;
    public String roll_number;
    public String message;
    public Type type;

    public Packet(String auth_code) {
        this.auth_code = auth_code;
    }

}
