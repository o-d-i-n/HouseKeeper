package com.housekeeper.Packet.server;

import com.housekeeper.Packet.Packet;

/**
 * Created by Lenovo on 1/19/2016.
 */
public class ClientPacket extends Packet {

    public ClientPacket(String message) {
        this.message = message;
        this.type = Type.SERVER_RESPONSE;
    }

    public ClientPacket(String auth_code,String message) {
        this.auth_code = auth_code;
        this.message = message;
        this.type = Type.SERVER_RESPONSE;
    }

}
