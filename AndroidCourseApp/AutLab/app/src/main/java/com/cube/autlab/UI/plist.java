package com.cube.autlab.UI;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/11/26 0026.
 */

public class plist {
    public String n = "";
    public static HashMap<String,String> map = new HashMap<String,String>();
    public static HashMap<String,String> nc = new HashMap<String,String>();
    public static HashMap<String,String> pn = new HashMap<String,String>();

    public static void addcity (String c){
        map.put(c,"city");
    }

    public static void addnation (String n){
        map.put(n,"nation");
    }
    public static void addPeople (String name){
        map.put(name,"people");
    }

    public static void addReNationCity (String nation, String city){
        map.put(city,"/nation/city/");
        nc.put(city,nation);
    }
    public static void addRePersonNation (String name, String nation){
        map.put(name,"people/nationality");
        pn.put(name,nation);
    }

}
