package com.enochtam.decklog;

/**
 * Created by geo on 06/02/16.
 */
public class LogItem {
    //no encapsulation because we nuts

    int _id;
    int log_id;
    int date_time;
    float lat, longit, speed, distance, ETA;
    String observation;
    String remarks;

    public LogItem(int id, int lID, int dateTime, float lati, float longitu, float spd, float dist, float esta, String obs, String re)
    {
        _id=id;
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

}
