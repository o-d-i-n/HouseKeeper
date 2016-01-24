package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 1/24/2016.
 */
public class ConnectedUsers extends Packet {

    public List<String> connectedUsers = new ArrayList<String>();

    public ConnectedUsers(List<String> connectedUsers) {
        this.type = Type.CONNECTED_USERS;
        this.connectedUsers = connectedUsers;
        if(this.connectedUsers.isEmpty()) {
            System.out.println("You're sending an empty packet");
        }
    }
}
