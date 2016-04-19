package com.housekeeper.Server;


import com.housekeeper.Client.Client;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class ServerMain{

    private int port;
    public Server server;

    public ServerMain(int port){
        this.port = port;
        server = new Server(port);
    }

    public static void main(String args[]){

       Server server = new Server(9000);

    }

}

