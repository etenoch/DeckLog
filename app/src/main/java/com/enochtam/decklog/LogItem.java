package com.enochtam.decklog;

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

        //sb.append(id);
        //sb.append(" ");
        sb.append(log_id);
        sb.append("-");
        sb.append(date_time);
        sb.append("-");
        sb.append(lat);
        sb.append("-");
        sb.append(longit);
        sb.append("-");
        sb.append(speed);
        sb.append("-");
        sb.append(distance);
        sb.append("-");
        sb.append(ETA);
        sb.append("-");
        sb.append(observation);
        sb.append("-");
        sb.append(remarks);
        //sb.append("\n");

        String out = sb.toString();

        return out;
    }
}
