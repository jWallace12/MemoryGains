package a2.csci412.wwu.edu.memorygain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by danie on 10/31/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MemoryStats";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "MemoryTable";
    private static final String ID = "Id";
    private static final String typeCol = "Type";
    private static final String PassCol = "Pass";

    public DatabaseManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_NAME + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + typeCol;
        sqlCreate += " text, " + PassCol + " text )" ;
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(Recall data){
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlInsert = "insert into " + TABLE_NAME;
        sqlInsert += " values( null, '" + data.getType();
        sqlInsert += "', '" + data.getPass() + "' )";
        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<Recall> selectByType(String type) {
        String sqlQuery = "select * from " + TABLE_NAME;
        sqlQuery += " where " + typeCol + " = '" + type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Recall> recalls = new ArrayList<Recall>();

        while (cursor.moveToNext()) {
            Recall recall = new Recall(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    (cursor.getString(2)));
            recalls.add(recall);
        }
        return recalls;
    }


    //    public void deleteById( int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlDelete = "delete from " + TABLE_NAME;
//        sqlDelete += " where " + ID + " = " + id;
//        db.execSQL(sqlDelete);
//        db.close();
//    }
//    public void updateById(int id, String type, String pass) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sqlUpdate = "update " + TABLE_NAME;
//        sqlUpdate += " set " + typeCol + " = '" + type + "', ";
//        sqlUpdate += PassCol + " = '" + pass + "'";
//        sqlUpdate += " where " + ID + " = " + id;
//        db.execSQL(sqlUpdate);
//        db.close();
//    }
//    public ArrayList<Task> selectAll(){
//        String sqlQuery = "select * from " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery( sqlQuery, null );
//
//        ArrayList<Task> info = new ArrayList<Task>();
//        while( cursor.moveToNext()){
//            Task currentData = new Task ( Integer.parseInt( cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
//            info.add( currentData );
//        }
//        db.close();
//        return info;
//    }
}
