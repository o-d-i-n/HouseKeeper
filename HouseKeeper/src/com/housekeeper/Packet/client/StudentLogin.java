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
    private SecureRandom random = new SecureRandom();

    public StudentLogin(String roll_number,String password){
        this.type = Type.STUDENT_LOGIN;
        this.roll_number = roll_number;
        this.password = password;
    }

    public String ifValid(String correct_password){
        if(Objects.equals(correct_password,this.password)) {
            return new BigInteger(130, random).toString(32);
        }
        return "Nope";
    }
}
