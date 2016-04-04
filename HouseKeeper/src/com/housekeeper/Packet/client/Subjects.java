package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

import java.util.Set;

/**
 * Created by Lenovo on 4/4/2016.
 */
public class Subjects extends Packet {

    public Object[] subjects;

    public Subjects(Set<String> subjectz) {
        this.subjects = subjectz.toArray();
        this.type = Type.SUBJECTS;
    }
}
