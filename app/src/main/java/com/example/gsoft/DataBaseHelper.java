package com.example.gsoft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.HashMap;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "commandes";
    private static final String TABLE_Users = "orders";
    private static final String KEY_ID = "id";
    private static final String KEY_CODE = "code";
    private static final String KEY_DESG = "designation";
    private static final String KEY_QTE = "quantite";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PRICET= "pricet";
    private static final String KEY_TOTAL= "total";
    //private static final String KEY_Q= "valeur";
    private static final String KEY_TEST = "TEST";
    private static final String TAG = "DatabaseHelper";

    SQLiteDatabase db;
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CODE + " TEXT,"
                + KEY_DESG + " TEXT,"
                + KEY_QTE + " TEXT,"
                + KEY_PRICE + " TEXT,"
                +KEY_PRICET+  "TEXT,"
                +KEY_TOTAL+   "TEXT"+ ")";
               // +KEY_Q+   "TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    void insertUserDetails(String code, String designation, String quantite, String price) {
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_CODE, code);
        cValues.put(KEY_DESG, designation);
        cValues.put(KEY_QTE, quantite);
        cValues.put(KEY_PRICE, price);
      //  cValues.put(KEY_QTE,valeur);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT code,designation,quantite,price,(quantite*price)as pricet FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("code", cursor.getString(cursor.getColumnIndex(KEY_CODE)));
            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("quantite", cursor.getString(cursor.getColumnIndex(KEY_QTE)));
            user.put("price", cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
            user.put("pricet",cursor.getString(cursor.getColumnIndex(KEY_PRICET))+"DT");
           // user.put("valeur",cursor.getString(cursor.getColumnIndex(KEY_QTE)));
            //user.put("valeur",cursor.getString(cursor.getColumnIndex(KEY_Q)));
            userList.add(user);

        }
        return userList;
    }


    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT code,designation,quantite,price FROM " + TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_CODE, KEY_DESG, KEY_QTE, KEY_PRICE}, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);
        if (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("code", cursor.getString(cursor.getColumnIndex(KEY_CODE)));
            user.put("designation", cursor.getString(cursor.getColumnIndex(KEY_DESG)));
            user.put("quantite", cursor.getString(cursor.getColumnIndex(KEY_QTE)));
            user.put("price", cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
            userList.add(user);
        }
        return userList;
    }
/* public void updateName(String newName, int id, String oldName) {
        SQLiteDatabase db = this.getWritableDatabase();
      String query = "UPDATE " + TABLE_Users + " SET " + KEY_QTE +
              " = '" + newName + "' WHERE " + KEY_ID + " = '" + id + "'" +
              " AND " + KEY_QTE + " = '" + oldName + "'";

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }*/
    public void updateName(String newName, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_Users + " SET " + KEY_QTE +
                " = '" + newName + "' WHERE " + KEY_CODE + "''";//" = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

 /* public static int update(String quantite) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(DataBaseHelper. KEY_QTE, quantite);
      int i =DataBaseHelper.update(DataBaseHelper.TABLE_Users, contentValues, quantite + " = " +quantite, null);
      return i;
  }*/
 //edit data

   /* public void update(String text2,String text3,String text4,String text5) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_Users+" SET code='"+text2+"', designation='"+text3+"', quantite='"+text4+"', price='"+text5+"' WHERE _code=" + text2);
    }*/
    public void update(String text2,String text3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_Users+" SET quantite='"+text2+"' WHERE _code=" + text3);
    }

    public double getTotalPrice() {
        String sql = "select sum(" + KEY_QTE + " * " + KEY_PRICE + ") from "
                +TABLE_Users;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        return 0;
    }
    /**
     * Returns only the ID that matches the name passed in
     * @param a
     * @return
     */
    public Cursor getItemID(String a){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_ID + " FROM " + TABLE_Users +
                " WHERE " + KEY_QTE + " = '" + a + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public boolean update(String newName,int id)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(KEY_QTE,newName);

            int result=db.update(TABLE_Users,cv, KEY_ID + " =?", new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;

    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_Users,"ID = ?",new String[]{id});
    }

}
