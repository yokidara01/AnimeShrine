package animeshrine.kortas.com.animeshrine;

/**
 * Created by Aladinne on 08/09/2016.
 */

// This class handles all the database activities

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "animeindex.db";
    public static final String TABLE_ANIMES = "animes";
    public static final String ALL_ANIMES = "allanime";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ANIMESNAME = "animename";
    public static final String imgurl= "imgurl" ;
    public static final String id="id";
    public static final String rank= "rank";
    public static final String userStatus ="userStatus" ;
    public static final String desc ="desc" ;
    public static final String genres ="genres" ;
    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ANIMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                id + " smallint , " +
                rank + " smallint, " +
                imgurl + " TEXT, " +
                COLUMN_ANIMESNAME + " VARCHAR(150)  ," +
                desc + " TEXT ," +
                userStatus+ " VARCHAR(30) "+
                ");";
        db.execSQL(query);
        String ParamTable = "CREATE TABLE " + "Param" + "(" +

                "firstuse" + " VARCHAR(2) " +

                ");";
        db.execSQL(ParamTable);

        String req2 = "INSERT INTO Param (firstuse)  VALUES ('1')";
        db.execSQL(req2);



        String allAnime = "CREATE TABLE " + ALL_ANIMES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                id + " smallint , " +
                rank + " smallint, " +
                imgurl + " TEXT, " +
                COLUMN_ANIMESNAME + "  VARCHAR(150) ," +
                genres + "  VARCHAR(150) ," +
                desc + " TEXT " +

                ");";
        db.execSQL(allAnime);
    }
    //Lesson 51
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIMES);
        db.execSQL("DROP TABLE IF EXISTS " + ALL_ANIMES);


        db.execSQL("VACUUM"); onCreate(db);
    }

    //Add a new row to the database
    public void addAnime(Anime anime){
        ContentValues values = new ContentValues();
        values.put(id, anime.getId());
        values.put(rank, anime.getRank());
        values.put(desc, anime.getDesc());
        values.put(imgurl, anime.getImg());
        values.put(COLUMN_ANIMESNAME, anime.getName());
        values.put(userStatus, anime.getUserStatus());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ANIMES, null, values);
        db.close();
    }

    public void addToAllAnime(List<Anime> animes){
        ContentValues values;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        int i = 1 ;
        for (Anime a : animes) {
            /*values = new ContentValues();
            values.put(id, a.getId());
            values.put(rank, a.getRank());
            values.put(desc, a.getDesc());
            values.put(imgurl, "imgurl TODO");

            values.put(genres, genres);
            values.put(COLUMN_ANIMESNAME, a.getName());
            db.insert(ALL_ANIMES, null, values);
            Log.d("TransAdd","add"+i);
            i++;*/
            String aux ="";

            for (String g :a.getGenres())
            {
                aux=aux+g+" ";
            }

            String dis=a.getDesc();
            dis=dis.replaceAll("'", "''");
            String name=a.getName();
            name=name.replaceAll("'", "''");
            String ROW1 = "INSERT INTO " + ALL_ANIMES +"( "+
                    id+","+rank+","+imgurl+","+COLUMN_ANIMESNAME+","+
                    genres +","+desc +")"+

                    " Values ("
                    + a.getId() + ", " + a.getRank() + ", '"
                    + a.getImg() + "', '" + name + "', '"
                    + aux +"','" + dis + "') ";
            db.execSQL(ROW1);
        }


        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //Delete a product from the database
    public void deleteAnime(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ANIMES + " WHERE " + id + "=" + id + ";");
    }
    public int getAllAnimeSize(){
        int cnt =0 ;
        try {


            String countQuery = "SELECT  * FROM " + ALL_ANIMES;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();
            cursor.close();
        }catch (Exception e){e.printStackTrace(); return 0;}
        return cnt;
    }
    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANIMES + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("animename")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("animename"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }


    public List<Anime> getAllAnime(){
        List<Anime> listAnime = new ArrayList<>();
        Anime a ;
        
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANIMES + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("animename")) != null) {
               // dbString += recordSet.getString(recordSet.getColumnIndex("animename"));
               // dbString += "\n";
                a = new Anime() ;
                a.setName(recordSet.getString(recordSet.getColumnIndex("animename")));
                a.setUserStatus(recordSet.getString(recordSet.getColumnIndex("userStatus")));
                listAnime.add(a);
            }
            recordSet.moveToNext();
        }
        db.close();
        return listAnime;
    }

    public void LeadAllAnimeFromXml() {


    }


    public boolean isFirstUse() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + "Param" + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("firstuse")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("firstuse"));

            }
            recordSet.moveToNext();
        }
        db.close();

        if(!dbString.equals('1'))
            return false;
            else return  true ;
    }

    public void updateNotFirstUse() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE param SET firstuse='1' WHERE firstuse='0'");
    }



    public List<Anime> getAllAnimeFromAllAnime(){
        List<Anime> listAnime = new ArrayList<>();
        Anime a ;

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + ALL_ANIMES + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("animename")) != null) {
                // dbString += recordSet.getString(recordSet.getColumnIndex("animename"));
                // dbString += "\n";
                a = new Anime() ;
                a.setName(recordSet.getString(recordSet.getColumnIndex("animename")));
                a.setId(recordSet.getInt(recordSet.getColumnIndex(id)));
                a.setImg(recordSet.getString(recordSet.getColumnIndex(imgurl)));
                a.setRank(recordSet.getInt(recordSet.getColumnIndex(rank)));
              String aux =recordSet.getString(recordSet.getColumnIndex(genres));

                String[] array = aux.split(" ", -1);

                List<String> grs = new ArrayList<String>();
                for (String g :array)
                {
                    grs.add(g) ;
                }
                a.setGenres(grs);
                a.setDesc(recordSet.getString(recordSet.getColumnIndex(desc)));

                listAnime.add(a);
            }
            recordSet.moveToNext();
        }
        db.close();
        return listAnime;
    }
}