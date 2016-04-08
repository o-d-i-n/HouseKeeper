package com.housekeeper.Server;


/**
 * Created by Lenovo on 1/17/2016.
 */
public class ServerMain{

    private int port;
    private Server server;

    public ServerMain(int port) {
        this.port = port;
        server = new Server(port);
    }

}

