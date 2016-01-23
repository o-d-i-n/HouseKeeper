package com.housekeeper.Packet.server;

import com.housekeeper.Packet.Packet;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 1/23/2016.
 */
public class ConnectedUsers extends Packet {
    public List<String> Clients = new ArrayList<String>();

    public ConnectedUsers(List<String> Clients) {
        Clients = this.Clients;
    }
}
