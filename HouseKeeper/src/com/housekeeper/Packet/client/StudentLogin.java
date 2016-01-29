package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Created by Lenovo on 1/19/2016.
 */
public class StudentLogin extends Packet {

    public String password;


    public StudentLogin(String roll_number,String password){
        this.type = Type.STUDENT_LOGIN;
        this.roll_number = roll_number;
        this.password = password;
    }


}
