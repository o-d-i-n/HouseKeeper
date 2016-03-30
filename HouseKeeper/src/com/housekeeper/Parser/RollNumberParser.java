package com.housekeeper.Parser;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lenovo on 2/11/2016.
 */
public class RollNumberParser {

    private static final Map<String,Integer> Branch = Collections.unmodifiableMap(
            new HashMap<String,Integer>() {{
                put("COE",7);
                put("IT",11);
                put("MPAE",13);
                put("ICE",17);
                put("ECE",19);
                put("BT",23);
            }}
    );
    private static final Map<Integer,Integer> Section = Collections.unmodifiableMap(
            new HashMap<Integer,Integer>() {{
                put(1,2);
                put(2,3);
                put(3,5);
            }}
    );
    private static final Map<Integer,Integer> Year = Collections.unmodifiableMap(
            new HashMap<Integer,Integer>() {{
                put(1,29);
                put(2,31);
                put(3,37);
                put(4,41);
            }}
    );
    private static final int ODD = 43;
    private static final int EVEN = 47;

    public static String[] rollNumberParser(String roll_number)
    {
        String[] features = roll_number.split("/");
        features[2] = "20" + features[2];
        return features;
    }

    public static int timeTableCodeGen(int section,int year,String branch)
    {
        return Branch.get(branch)*Section.get(section)*Year.get(year)* (Calendar.getInstance().get(Calendar.MONTH) < 6?EVEN:ODD);
    }

}
