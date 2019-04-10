package com.cube;

import java.util.*;

public class Main {
    public static class httpd {
        public static String website = "";
        public int cnt = 0;
    }
    public static int max = 0;
    public static HashMap<String, httpd> map = new HashMap<String, httpd>();
    public static String read = " ";
    public static void main(String[] args) {
	// write your code here
        Scanner scan = new Scanner(System.in);
        String str;

        while(scan.hasNextLine()){
            str = scan.nextLine();
            if(str.length() <= 0)
                break;
            String[] tokens = {" ",};
            if(str != null)
                tokens = str.split(" ");
            if(str.length() > 0 && tokens[6].contains("?")){
                int mark = tokens[6].indexOf("?");
                tokens[6] = tokens[6].substring(0, mark);
            }
            //System.out.println(tokens[6]);
            if(map.get(tokens[6])!=null){
                map.get(tokens[6]).cnt++;
                if(max < map.get(tokens[6]).cnt)
                    max = map.get(tokens[6]).cnt;
            }else {
                httpd http = new httpd();
                http.cnt = 1;
                http.website = tokens[6];
                map.put(tokens[6], http);
            }
        }
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            HashMap.Entry entry = (HashMap.Entry) iterator.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if(map.get(key.toString()).cnt == max)
                System.out.println(map.get(key.toString()).cnt + ":" + key.toString() );
        }
        return;
    }
}
