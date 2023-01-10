package com.example.recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;


public class SQLdbHelper extends SQLiteOpenHelper {
    Context context;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;

    public static final String DBNAME = "users.db";

    public SQLdbHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key,email TEXT, password TEXT,image BLOB,address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(ModelClass modelClass){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Bitmap imagetToStoreBitmap = modelClass.getProfileImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imagetToStoreBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues contentValues= new ContentValues();
        contentValues.put("username", modelClass.getUsername());
        contentValues.put("email",modelClass.getEmail());
        contentValues.put("password", modelClass.getPassword());
        contentValues.put("image",imageInBytes);
        contentValues.put("address",modelClass.getAddress());
        long result = MyDB.insert("users", null, contentValues);
        return result != -1;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        return cursor.getCount() > 0;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        return cursor.getCount() > 0;
    }
    public Cursor ViewData()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from  users",null);
        return  cursor;
    }
    public Cursor getusername(String username)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from  users where username =?",new String[]{username});
        return  cursor;
    }
    public Boolean checkPassword(String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{password});
        return cursor.getCount() > 0;
    }

public Cursor getbyUsername(String username)
{
    String[] args = {username};
    return(getReadableDatabase().rawQuery(
            "SELECT * FROM users WHERE username = ?",args));
}

}