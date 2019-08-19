package com.cipher.sharesmilesandroid.utilities;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeShow {

    public static String getTime(long time){

        Date now = new Date();
        String strTime = "";

        long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - time);
        long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - time);
        long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - time);
        long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - time);
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

        if(seconds<60)
        {
            strTime = seconds+" seconds ago";

        }
        else if(minutes<60)
        {
            strTime = minutes+" minutes ago";
        }
        else if(hours<24)
        {
            strTime = hours+" hours ago";
        }
        else
        {
            strTime = days+" days ago";
        }

        return strTime;
    }
}
