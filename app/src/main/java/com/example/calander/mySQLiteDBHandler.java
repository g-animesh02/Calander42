package com.example.calander;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class mySQLiteDBHandler extends SQLiteOpenHelper {

    public static final String Table_name = "EventCalenderssss";
    public static final String DataBase_name = "Calenderss.db";

    SQLiteDatabase db;


    public mySQLiteDBHandler(@Nullable Context context) {
        super(context, DataBase_name, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table_name + "(Events TEXT , Date TEXT , Type TEXT , Description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);

    }

    boolean insertData(String eve, String date, String typ ,String des) {
        db=this.getWritableDatabase();

        String SQLiteDataBaseQueryHolder = "INSERT INTO " +Table_name +"(Events,Date,Type,Description) VALUES('"+eve+"', '"+date+"', '"+typ+"' , '"+des+"');";

        db.execSQL(SQLiteDataBaseQueryHolder);

        return true;
    }

    public String GetData(String selectedDatee) {
        String str ="No event Entered" ,typ = " ";

        Cursor cursor , cursor1;

        String query = "SELECT Events FROM "+Table_name+" WHERE Date =" + selectedDatee;
        String query2 = "SELECT Type FROM "+ Table_name +" WHERE Date =" + selectedDatee;

        cursor = db.rawQuery(query,null);
        cursor1 = db.rawQuery(query2,null);


        try {
            if (cursor != null && cursor.moveToFirst()){

                str = cursor.getString(cursor.getColumnIndex("Events"));

            }
            if(cursor1 != null && cursor1.moveToFirst()) {
                cursor1.moveToFirst();
                typ = cursor1.getString(cursor1.getColumnIndex("Type")) + " of";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            str = "No Event Entered";
            typ = "";

        }
        return (str +"," + typ );

    }

    public String GetDescription(String selectedDatee) {
        String str ="No Description Entered" ;

        Cursor cursor ;

        String query = "SELECT Description FROM "+Table_name+" WHERE Date =" + selectedDatee;

        cursor = db.rawQuery(query,null);


        try {
            if (cursor != null && cursor.moveToFirst()){

                str = cursor.getString(cursor.getColumnIndex("Description"));

            }

        }
        catch (Exception e) {
            e.printStackTrace();
            str = "No Description Entered";

        }
        return (str);

    }

}