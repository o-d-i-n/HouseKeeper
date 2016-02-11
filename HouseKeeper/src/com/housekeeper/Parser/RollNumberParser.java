package com.housekeeper.Parser;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Lenovo on 2/11/2016.
 */
public class RollNumberParser {

    String[] features;
    HashMap<String,Integer> Branch = new HashMap<String, Integer>();
    HashMap<Integer,Integer> Section = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> Year = new HashMap<Integer, Integer>();

    private static final int ODD = 43;
    private static final int EVEN = 47;

    public RollNumberParser(String roll_number)
    {
        this.features = roll_number.split("/");

        init();

    }

    public int timeTableCodeGen(int section,int year,String branch)
    {
        return Branch.get(branch)*Section.get(section)*Year.get(year)* (Calendar.getInstance().get(Calendar.MONTH) < 6?EVEN:ODD);

    }

    private void init()
    {
        Section.put(1,2);
        Section.put(2,3);
        Section.put(3,5);

        Branch.put("COE",7);
        Branch.put("IT",11);
        Branch.put("MPAE",13);
        Branch.put("ICE",17);
        Branch.put("ECE",19);
        Branch.put("BT",23);

        Year.put(1,29);
        Year.put(2,31);
        Year.put(3,37);
        Year.put(4,41);




    }
}
