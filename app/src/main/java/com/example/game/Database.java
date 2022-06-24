package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {


        public Database(@Nullable Context context) {
            super(context, Constant.cons_sql, null, Constant.cons_sql_version);
        }



        @Override
        public void onCreate(SQLiteDatabase sql) {
            sql.execSQL("create table " + Constant.table_name +
                    " ("+ Constant.user_id + " INTEGER  primary key autoincrement ," +
                    Constant.user_fullname + " text  , " +
                    Constant.user_score +" INTEGER ," +
                    Constant.date + " text  )");

        }


        @Override
        public void onUpgrade(SQLiteDatabase sql, int oldVersion, int newVersion) {

            sql.execSQL(" drop table if exists " +  sql);
            onCreate(sql);
        }


       public boolean deleteData(){

           SQLiteDatabase sql = getWritableDatabase();
           int result =sql.delete(Constant.table_name,null,null);
           return result > 0;
       }


        public boolean insertUser(Users users){
            SQLiteDatabase sql = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constant.user_fullname,users.getFullName());
            cv.put(Constant.user_score,users.getScore());
            cv.put(Constant.date,users.getDate());
            long result = sql.insert(Constant.table_name,null,cv);
            return result != -1;
        }

        public ArrayList<Users> getAllData(){
            ArrayList<Users> users = new ArrayList<>();
            SQLiteDatabase sql = getReadableDatabase();
            Cursor cursor = sql.rawQuery("select * from  "+ Constant.table_name,null);
            if (cursor.moveToFirst() &&
                    cursor != null ){
                do {
                    String fullName = cursor.getString(cursor.getColumnIndexOrThrow(Constant.user_fullname));
                    int  score = cursor.getInt(cursor.getColumnIndexOrThrow(Constant.user_score));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(Constant.date));
                    Users user =new Users(fullName,date,score);
                    users.add(user);
                }
                while (cursor.moveToNext());
                cursor.close();
            }
            return users;
        }





}
