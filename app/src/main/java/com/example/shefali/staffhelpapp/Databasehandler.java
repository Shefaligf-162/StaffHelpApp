package com.example.shefali.staffhelpapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Databasehandler extends SQLiteOpenHelper {

    public Databasehandler(Context context) {
        super(context,"staffhelp.db",null,5);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table registers(name string,contactnumber string primary key,designation string,image blob not null,headquater string)");
        db.execSQL("create table ca(name string,contactnumber string ,email string,type string,description string,image blob ,date string,contact_tte string,PRIMARY KEY(contact_tte,date))");
        db.execSQL("create table so(name string,contactnumber string ,email string,description string,image blob ,date string,contact_tte string,PRIMARY KEY(contact_tte,date))");
        db.execSQL("create table sp(name string,contactnumber string ,email string,description string,image blob ,date string,contact_tte string,PRIMARY KEY(contact_tte,date))");
        db.execSQL("create table mr(name string,contactnumber string ,email string,type string,image blob ,date string,contact_tte string,PRIMARY KEY(contact_tte,date))");
    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists registers");
        db.execSQL("drop table if exists ca");
        db.execSQL("drop table if exists so");
        db.execSQL("drop table if exists sp");
        db.execSQL("drop table if exists mr");
      onCreate(db);
    }
    public boolean Insertimage(String x,String name,String contct,String des,String headqtr){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            FileInputStream fs=new FileInputStream(x);
            byte [] img=new byte[fs.available()];
            fs.read(img);
            ContentValues contentValues=new ContentValues();
            contentValues.put("name",name);
            contentValues.put("contactnumber",contct);
            contentValues.put("designation",des);
            contentValues.put("image",img);
            contentValues.put("headquater",headqtr);
            long p=db.insert("registers",null,contentValues);
            if(p!=0)
                return true;
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean suspiciousobject(String x,String name,String contct,String email,String desc,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            FileInputStream fs=new FileInputStream(x);
            byte [] img=new byte[fs.available()];
            fs.read(img);
            ContentValues contentValues=new ContentValues();
            contentValues.put("name",name);
            contentValues.put("contactnumber",contct);
            contentValues.put("description",desc);
            contentValues.put("contact_tte",contact_tte);
            contentValues.put("email",email);
            contentValues.put("image",img);
            contentValues.put("date",getdatetime());
            long p=db.insert("so",null,contentValues);
            myreports(x,name,contct,email,"Suspicious object",contact_tte);
            if(p!=0)
                return true;
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(String x,String name,String contct,String des,String headqtr,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] img = new byte[fs.available()];
            fs.read(img);
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("contactnumber", contct);
            contentValues.put("designation", des);
            contentValues.put("headquater", headqtr);
            long p = db.update("registers", contentValues, "contactnumber=" + contact_tte, null);
            if(p!=0)
                return true;
            return true;

        }catch(IOException e){
            Log.i("errror","image");
            return false;
        }

    }

    public boolean update_mr(String contact_new,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("contact_tte",contact_new);
        long p=db.update("mr",contentValues,"contact_tte="+contact_tte,null);
        if(p!=0)
            return true;
        return true;

    }

    public Cursor getmyreports(String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from mr where contact_tte=? ",new String[]{contact});
        return cursor;

    }

    public Cursor getdata(String contact){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from registers where contactnumber=? ",new String[]{contact});


    }
    String getdatetime(){
        SimpleDateFormat sp=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault());
        Date d=new Date();
        return  sp.format(d);
    }
/*

    public boolean suspiciousobject(String x,String name,String contct,String email,String desc,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactnumber",contct);
        contentValues.put("description",desc);
        contentValues.put("contact_tte",contact_tte);
        contentValues.put("email",email);
        contentValues.put("date",getdatetime());
        long p=db.insert("so",null,contentValues);
        myreports(x,name,contct,email,"Suspicious object",contact_tte);
        if(p!=0)
            return true;
        return true;
    }


    public boolean suspicious_person(String x,String name,String contct,String email,String desc,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactnumber",contct);
        contentValues.put("description",desc);
        contentValues.put("contact_tte",contact_tte);
        contentValues.put("email",email);
        contentValues.put("date",getdatetime());
        long p=db.insert("sp",null,contentValues);
        myreports(x,name,contct,email,"Suspicious person",contact_tte);
        if(p!=0)
            return true;
        return true;
    }*/
public boolean suspicious_person(String x,String name,String contct,String email,String desc,String contact_tte){
    SQLiteDatabase db=this.getWritableDatabase();
    try{
        FileInputStream fs=new FileInputStream(x);
        byte [] img=new byte[fs.available()];
        fs.read(img);
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactnumber",contct);
        contentValues.put("description",desc);
        contentValues.put("contact_tte",contact_tte);
        contentValues.put("email",email);
        contentValues.put("image",img);
        contentValues.put("date",getdatetime());
        long p=db.insert("sp",null,contentValues);
        myreports(x,name,contct,email,"Suspicious person",contact_tte);
        if(p!=0)
            return true;
        fs.close();
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

    /*public boolean criminalActivity(String x,String name,String contct,String email,String desc,String contact_tte,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactnumber",contct);
        contentValues.put("description",desc);
        contentValues.put("contact_tte",contact_tte);
        contentValues.put("type",type);
        contentValues.put("email",email);
        contentValues.put("date",getdatetime());
        long p=db.insert("ca",null,contentValues);
        myreports(x,name,contct,email,"criminal activity",contact_tte);
        if(p!=0)
            return true;
        return true;
    }*/
    public boolean criminalActivity(String x,String name,String contct,String email,String desc,String contact_tte,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        try{
            FileInputStream fs=new FileInputStream(x);
            byte [] img=new byte[fs.available()];
            fs.read(img);
            ContentValues contentValues=new ContentValues();
            contentValues.put("name",name);
            contentValues.put("contactnumber",contct);
            contentValues.put("description",desc);
            contentValues.put("contact_tte",contact_tte);
            contentValues.put("type",type);
            contentValues.put("image",img);
            contentValues.put("email",email);
            contentValues.put("date",getdatetime());
            long p=db.insert("ca",null,contentValues);
            myreports(x,name,contct,email,"criminal activity",contact_tte);
            if(p!=0)
                return true;
            fs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean myreports(String x,String name,String contct,String email,String type,String contact_tte){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactnumber",contct);
        contentValues.put("contact_tte",contact_tte);
        contentValues.put("type",type);
        contentValues.put("email",email);
        contentValues.put("date",getdatetime());
        long p=db.insert("mr",null,contentValues);
        if(p!=0)
            return true;
        return true;
    }


    public Boolean checkcontact(String username,String contact){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from registers where name=? and contactnumber=? ",new String[]{username,contact});
        if(cursor.getCount()>0){
            return true;
        }else
            return false;

    }
}
