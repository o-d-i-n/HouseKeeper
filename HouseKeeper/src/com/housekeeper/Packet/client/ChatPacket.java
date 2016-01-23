package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 1/23/2016.
 */
public class ChatPacket extends Packet {
    public String to;

    public ChatPacket(String to,String message) {
        this.to = to;
        this.message = message;
    }
}
