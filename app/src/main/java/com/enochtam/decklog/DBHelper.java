package com.enochtam.decklog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by geo and dmitri on 06/02/16.
 */
    public class DBHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "RedwoodDB";

        public static final String LOGS_TABLE_NAME="LOGS";
        public static final String LOGS_ID = "id"; //UNIQUE BACK END SQL ID IDENTIFIER
        public static final String LOGS_NAME ="name";
        public static final String LOGS_VESSEL="vessel";
        public static final String LOGS_NAVIGATOR = "navigator";

        public static final String LOGS_ITEMS_TABLE_NAME="LOGS_ITEMS";
        public static final String LOGS_ITEMS_ID ="id"; //UNIQUE BACK END SQL ID IDENTIFIER
        public static final String LOGS_ITEMS_log_id = "log_id"; //THE ID OF THE LOG THIS LOG ITEM IS ASSOCIATED WITH
        public static final String LOGS_ITEMS_DATE_TIME= "date_time";
        public static final String LOGS_ITEMS_LATITUDE="latitude";
        public static final String LOGS_ITEMS_LONGITUDE="longitude";
        public static final String LOGS_ITEMS_OBSERVATION="observation";
        public static final String LOGS_ITEMS_SPEED="speed";
        public static final String LOGS_ITEMS_DISTANCE="distance";
        public static final String LOGS_ITEMS_ETA="ETA";
        public static final String LOGS_ITEMS_REMARKS="remarks";

        //context to current activity

        /**
         * This method creates a ___
         * @param context
         */
        public DBHelper(Context context){
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        /**
         * This method creates an SQL database and i have no idea what im doing
         */
        public void onCreate(SQLiteDatabase db){
            db.execSQL("PRAGMA foreign_keys = ON");
            db.execSQL(
                    "create table LOGS" +
                            "(id integer primary key, name text, vessel text,navigator text)"
            );

            db.execSQL(
                    "create table LOGS_ITEMS" +
                            "(id integer primary key, log_id int, date_time integer, latitude real, longitude real, " +
                            "observation text, speed real, distance real, ETA real, remarks text, " +
                            "FOREIGN KEY(log_id) REFERENCES LOGS(id) ON DELETE CASCADE)"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("PRAGMA foreign_keys = ON");
            db.execSQL("DROP TABLE IF EXISTS LOGS");
            db.execSQL("DROP TABLE IF EXISTS LOGS_ITEMS");

            //todo: how to check existance of both tables
            onCreate(db);
        }

        //_id?
        public boolean insertLogs(String name, String navigator, String vessel){

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys = ON");
            ContentValues contentValues = new ContentValues();

            contentValues.put("name", name);
            contentValues.put("navigator", navigator);
            contentValues.put("vessel", vessel);
            db.insert("LOGS", null, contentValues);
            return true;
        }

        public boolean insertLogItems(int log_id, int date_time,
                                      float lat, float longit, String Observation, float speed, float distance,
                                      float ETA, String remarks) {
            SQLiteDatabase db = this.getWritableDatabase();//only needed this before writing
            db.execSQL("PRAGMA foreign_keys = ON");
            ContentValues contentValues = new ContentValues();

            contentValues.put("log_id", log_id);
            contentValues.put("date_time", date_time);
            contentValues.put("latitude", lat);
            contentValues.put("longitude", longit);
            contentValues.put("observation", Observation);
            contentValues.put("speed",speed);
            contentValues.put("distance",distance);
            contentValues.put("ETA", ETA);
            contentValues.put("remarks", remarks);
            db.insert("LOGS_ITEMS", null, contentValues);
            return true;
            // todo:DO WE NEED TO DO UNIQUE ITEMS CHECK SO WE CAN INSERT FALSE? ,NOPE
        }

        public Cursor getLogItemData (int id){
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from LOGS_ITEMS where id=" + id + "",null);

            return res;
        }

        public Cursor getLogsData(int id){
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery("select * from LOGS where id=" + id + "",null);

            return res;
        }

        public int numberOfLogRows(){
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("PRAGMA foreign_keys = ON");
            int numRows = (int) DatabaseUtils.queryNumEntries(db, LOGS_TABLE_NAME);
            return numRows;
        }
        public int numberofLogItemRows(){
            SQLiteDatabase db = this.getReadableDatabase();
            int numRows = (int)DatabaseUtils.queryNumEntries(db, LOGS_ITEMS_TABLE_NAME);
            return numRows;
        }

        public boolean updateLogs(int id, String name, String navigator, String vessel){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys = ON");
            ContentValues contentValues = new ContentValues();

            contentValues.put("name", name);
            contentValues.put("navigator", navigator);
            contentValues.put("vessel",vessel);
            db.update("LOGS", contentValues, "id=?", new String[]{Integer.toString(id)});

            return true;
        }

        public boolean updateLogsItems(int id, int log_id, int date_time,
                                       float lat, float longit, String Observation, float speed, float distance,
                                       float ETA, String remarks){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys = ON");
            ContentValues contentValues = new ContentValues();

            contentValues.put("log_id", log_id);
            contentValues.put("date_time", date_time);
            contentValues.put("latitude", lat);
            contentValues.put("longitude", longit);
            contentValues.put("observation", Observation);
            contentValues.put("speed",speed);
            contentValues.put("distance", distance);
            contentValues.put("ETA", ETA);
            contentValues.put("remarks", remarks);
            db.update("LOGS_ITEMS", contentValues, "id=?", new String[]{Integer.toString(id)});
            return true;
        }

        //uncommented this method below, i have no idea why
        public int deleteLogsItem(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("LOGS_ITEMS", "id = ?",new String[] {Integer.toString(id)} );
        }

        public int deleteLog(int id){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys = ON");

            //below is the attempt at a manual delete
            //db.delete("LOGS_ITEMS", "log_id=?", new String[] {Integer.toString(id)}); //WILL IT BLEND AND WORK

            return db.delete("LOGS", "id = ?", new String[] {Integer.toString(id)});
        }

    /**
     * This method should be deprecated for not being proper java OOP paradigm and being silly
     * brute force
     * @return
     */
        public Pair<ArrayList<String>, int[]> getAllLogs(){
            ArrayList<String > a = new ArrayList<>();
            int [] ids=new int[numberOfLogRows()];

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from LOGS", null);
            res.moveToNext();

            int i=0;
            while(res.isAfterLast()==false){
                a.add(res.getString(res.getColumnIndex(LOGS_NAME)));

                //TODO:HOW DO I GET THE ID OF A SPECIFIC row


                ids[i]=(int)res.getLong(res.getColumnIndex(LOGS_ID)); //maybe i got dis bruh
                res.moveToNext();

            }
            return new Pair(a, ids);
        }

    /**
     * USE THIS METHOD INSTEAD
     * @return
     */
    public ArrayList<Log> getLogObjects(){
        ArrayList<Log > a = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =db.rawQuery("select * from LOGS", null);
        res.moveToNext();

        int idd;
        String named, navigatord, vesseld;

        Log addThis;

        while(res.isAfterLast()==false){

            //variable names appended with d for additional clarity and confusion
            idd=(int) res.getLong(res.getColumnIndex(LOGS_ID)); //pray this works now
            named = res.getString(res.getColumnIndex(LOGS_NAME));
            navigatord = res.getString(res.getColumnIndex(LOGS_NAVIGATOR));
            vesseld = res.getString(res.getColumnIndex(LOGS_VESSEL));


            addThis= new Log(idd,named, navigatord, vesseld);

            a.add(addThis);

            res.moveToNext();
        }
        return a;
    }



    /**
     * Returns an arraylist filled with logitems stuffed with the data form the database
     * @return
     */
        public ArrayList<LogItem> getAllLogItems(){
            ArrayList<LogItem > a = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res =db.rawQuery("select * from LOGS_ITEMS", null);
            res.moveToNext();

            int idd, logIdd,dateTimed;

            float latd, longd, speedd, distanced, ETAd;

            String obsd;
            String remarksd;

            LogItem addThis;

            while(res.isAfterLast()==false){

                //variable names appended with d for additional clarity and confusion
                idd=(int) res.getLong(res.getColumnIndex(LOGS_ITEMS_ID));
                logIdd=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_log_id));
                dateTimed=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_DATE_TIME));
                latd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LATITUDE));
                longd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LONGITUDE));
                speedd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_SPEED));
                distanced = res.getFloat(res.getColumnIndex(LOGS_ITEMS_DISTANCE));
                ETAd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_ETA));
                obsd =res.getString(res.getColumnIndex(LOGS_ITEMS_OBSERVATION));
                remarksd=res.getString(res.getColumnIndex(LOGS_ITEMS_REMARKS));

                addThis= new LogItem(idd,logIdd,dateTimed,latd,longd ,speedd,distanced, ETAd,obsd,remarksd);

                a.add(addThis);

                res.moveToNext();
            }
            return a;
        }

    /**
     *
     * This method probably won't work if target ID is not in there
     * @param targetID the target ID for the LogItem to retrieve
     * @return the log item with the given id
     */
    public LogItem getLogItem(int targetID){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM LOGS_ITEMS where id = "+ targetID, null);
        res.moveToNext();

        LogItem out;

        int idd, logIdd,dateTimed;
        float latd, longd, speedd, distanced, ETAd;

        String obsd;
        String remarksd;

        idd=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_ID));
        logIdd=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_log_id));
        dateTimed=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_DATE_TIME));
        latd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LATITUDE));
        longd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LONGITUDE));
        speedd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_SPEED));
        distanced = res.getFloat(res.getColumnIndex(LOGS_ITEMS_DISTANCE));
        ETAd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_ETA));
        obsd =res.getString(res.getColumnIndex(LOGS_ITEMS_OBSERVATION));
        remarksd=res.getString(res.getColumnIndex(LOGS_ITEMS_REMARKS));

        out = new LogItem(idd,logIdd,dateTimed,latd,longd ,speedd,distanced, ETAd,obsd,remarksd);

        return out;
    }

    /**
     * Returns an arraylist filled with logitems WITH THE targetLogID
     * @return
     */
    public ArrayList<LogItem> getAllLogItems(int targetLogID){
        ArrayList<LogItem > a = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =db.rawQuery("SELECT * FROM LOGS_ITEMS where log_id = "+targetLogID, null);
        res.moveToNext();

        int idd, logIdd,dateTimed;

        float latd, longd, speedd, distanced, ETAd;

        String obsd;
        String remarksd;

        LogItem addThis;

        while(res.isAfterLast()==false){

            //variable names appended with d for additional clarity and confusion
            idd=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_ID));
            logIdd=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_log_id));
            dateTimed=(int)res.getLong(res.getColumnIndex(LOGS_ITEMS_DATE_TIME));
            latd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LATITUDE));
            longd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_LONGITUDE));
            speedd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_SPEED));
            distanced = res.getFloat(res.getColumnIndex(LOGS_ITEMS_DISTANCE));
            ETAd = res.getFloat(res.getColumnIndex(LOGS_ITEMS_ETA));
            obsd =res.getString(res.getColumnIndex(LOGS_ITEMS_OBSERVATION));
            remarksd=res.getString(res.getColumnIndex(LOGS_ITEMS_REMARKS));

            addThis= new LogItem(idd,logIdd,dateTimed,latd,longd ,speedd,distanced, ETAd,obsd,remarksd);

            a.add(addThis);

            res.moveToNext();
        }
        return a;
    }
}
