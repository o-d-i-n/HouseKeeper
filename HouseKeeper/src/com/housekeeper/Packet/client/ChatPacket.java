package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 1/23/2016.
 */
public class ChatPacket extends Packet {
    public String to;
    public String from;

    public ChatPacket(String to,String from,String message) {
        this.to = to;
        this.from = from;
        this.message = message;
        this.type = Type.CHAT;
    }
}
