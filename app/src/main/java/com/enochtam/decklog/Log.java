package com.enochtam.decklog;

/**
 * Created by geo on 06/02/16.
 */
public class Log {
    public int id;
    public String name, navigator, vessel;

    public Log(int i, String n, String navi, String v){
        id =i;
        name=n;
        navigator=navi;
        vessel=v;
    }

    /**
     *
     * @return a string detailing all the attributes of this log, except id
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        //sb.append(id);
        //sb.append(" ");
        sb.append(name);
        sb.append("-");
        sb.append(navigator);
        sb.append("-");
        sb.append(vessel);
        //sb.append("\n");

        String out = sb.toString();

        return out;
    }

}
