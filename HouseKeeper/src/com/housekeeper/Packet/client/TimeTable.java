package com.housekeeper.Packet.client;

import com.housekeeper.Packet.Packet;

import java.sql.Time;
import java.util.*;

/**
 * Created by Lenovo on 3/29/2016.
 */
public class TimeTable extends Packet {

    String[] monday;
    String[] tuesday;
    String[] wednesday;
    String[] thursday;
    String[] friday;
    public HashMap<String,Integer> subjects = new HashMap<String, Integer>(); // a map for all the subjects the student has

    public TimeTable(String auth_code) {
        this.type = Type.TIMETABLE;
        this.auth_code = auth_code;
    }

    public TimeTable() {
        this.type = Type.SUBJECTS;
    }

    public TimeTable(String monday,String tuesday,String wednesday,String thursday,String friday) {
        this.monday = monday.split(";");
        this.tuesday = tuesday.split(";");
        this.wednesday = wednesday.split(";");
        this.thursday = thursday.split(";");
        this.friday = friday.split(";");

        this.type = Type.TIMETABLE;
    }

    public Set<String> displayTimeTable() {
        String[] temp;

        System.out.println("MONDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<monday.length;i++) {
            temp = monday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
            subjects.put(temp[1],1); // putting in all the subjects into the map
        }

        System.out.println("TUESDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<tuesday.length;i++) {
            temp = tuesday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
            subjects.put(temp[1],1);
        }

        System.out.println("WEDNESDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<wednesday.length;i++) {
            temp = wednesday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
            subjects.put(temp[1],1);
        }

        System.out.println("THURSDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<thursday.length;i++) {
            temp = thursday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
            subjects.put(temp[1],1);
        }

        System.out.println("FRIDAY");
        System.out.println("TIME  CLASS  ROOM NO.");
        for(int i=0;i<friday.length;i++) {
            temp = friday[i].split("!");
            System.out.println(temp[0] + "  " + temp[1] + "  " + temp[2]);
            subjects.put(temp[1],1);
        }

        System.out.println("The subjects the student has is: ");

        System.out.println(subjects.keySet());
        Set<String> subjectSet = subjects.keySet();
        return subjectSet;
    }
}
