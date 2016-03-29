package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

import java.sql.Time;

/**
 * Created by Lenovo on 3/29/2016.
 */
public class TimeTable extends Packet {

    String[] monday;
    String[] tuesday;
    String[] wednesday;
    String[] thursday;
    String[] friday;

    public TimeTable() {
        this.type = Type.TIMETABLE;
    }

    public TimeTable(String monday,String tuesday,String wednesday,String thursday,String friday) {
        this.monday = monday.split(";");
        this.tuesday = tuesday.split(";");
        this.wednesday = wednesday.split(";");
        this.thursday = thursday.split(";");
        this.friday = friday.split(";");

        this.type = Type.TIMETABLE;
    }

    public void displayTimeTable() {
        String[] temp;

        System.out.println("MONDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<monday.length;i++) {
            temp = monday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);

        }

        System.out.println("TUESDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<tuesday.length;i++) {
            temp = tuesday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);

        }

        System.out.println("WEDNESDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<wednesday.length;i++) {
            temp = wednesday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);

        }

        System.out.println("THURSDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<thursday.length;i++) {
            temp = thursday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);

        }

        System.out.println("FRIDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<friday.length;i++) {
            temp = friday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);

        }
    }
}
