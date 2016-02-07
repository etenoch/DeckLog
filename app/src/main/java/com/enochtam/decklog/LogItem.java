package com.enochtam.decklog;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by geo on 06/02/16.
 */
public class LogItem {
    //no encapsulation because we nuts

    public int id;
    public int log_id;
    public int date_time;
    public float lat, longit, speed, distance, ETA;
    public String observation;
    public String remarks;

    public LogItem(int id, int lID, int dateTime, float lati, float longitu, float spd, float dist, float esta, String obs, String re)
    {
        this.id=id;
        log_id=lID;
        date_time=dateTime;
        lat=lati;
        longit = longitu;
        speed = spd;
        distance = dist;
        ETA = esta;
        observation = obs;
        remarks = re;
    }

    //TODO: PROPER OBJECTS, ADDITOINAL EQUALS, TO STRING ETC COMPARE METHODS

    /**
     * toString method that returns a string of everything but id, separated by hyphens
     * as bax instructed
     * @return
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        //Date date = new Date(date_time*1000L);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(date_time * 1000L);


        String formattedDate = gc.get(Calendar.YEAR) +"-"+ (gc.get(Calendar.MONTH)+1) + "-" + gc.get(Calendar.DAY_OF_MONTH)+  " " + gc.get(Calendar.HOUR) + ":" +gc.get(Calendar.MINUTE);
        //sb.append(id);
        //sb.append(" ");
        sb.append(formattedDate);
        sb.append("-");
        sb.append(lat);
        sb.append("-");
        sb.append(longit);
        //sb.append("\n");

        String out = sb.toString();

        return out;
    }
}
