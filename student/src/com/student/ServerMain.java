package com.student;

/**
 * Created by Lenovo on 1/16/2016.
 */
public class ServerMain {

    private int port;
    private Server server;

    public ServerMain(int port) {
        this.port = port;
        server = new Server(port);
    }

    public static void main(String[] args) {
        int port;
        port = 9000;
        new ServerMain(port);
    }

}
