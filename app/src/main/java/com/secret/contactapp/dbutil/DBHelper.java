package com.secret.contactapp.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.secret.contactapp.model.ModelContact;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactdb";
    private SQLiteDatabase db;

    public DBHelper(Context c) {
        super(c, DATABASE_NAME, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                "contact(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(255)," +
                "number VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(sqLiteDatabase);
    }

    public void insertDataToDB(ModelContact modelContact) {
//        SQLiteDatabase db = getWritableDatabase();
        //content values to insert row at once
        ContentValues cv = new ContentValues();
        cv.put("name", modelContact.getName());
        cv.put("num", modelContact.getNumber());
        db.insert("contact", null, cv);


    }

//    public ArrayList<ModelContact> retrieveData() {
//        ArrayList<ModelContact> data = new ArrayList<>();
//        String query = "Select * from contact";
//        //pointer to database table is cursor
//        Cursor cursor = db.rawQuery(query, null);
//        //cursor ko first value null chaina bhane moveToFirst()
//        if (cursor.moveToFirst()) {
//            do {
//              ModelContact modelContact=new ModelContact();
//              modelContact.setName(cursor.getString(0));
//              modelContact.setNumber(cursor.getString(1));
//              data.add(modelContact);
//            }
//            while (cursor.moveToNext());
//        }
//        return data;
//    }
//
//    public void deleteRecord(int number) {
//
//        db.execSQL("DELETE FROM contact where num=" +number );
//    }
//


}
