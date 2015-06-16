package com.example.cs110.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDAO 
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_USER = "user";
    public static final String KEY_TOTAL_QUESTIONS = "total";
    public static final String KEY_CORRECT = "correct";    
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "user";
    private static final String DATABASE_TABLE = "users";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table users (_id integer primary key autoincrement, "
        + "user text not null, total text not null, " 
        + "correct text not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public UserDAO(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
        int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }    
    
    //opens the database
    public UserDAO open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //closes the database
    public void close() 
    {
        DBHelper.close();
    }
    
    //insert a wine into the database
    public long insertUser(String user, String total, String correct) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USER, user);
        initialValues.put(KEY_TOTAL_QUESTIONS, total);
        initialValues.put(KEY_CORRECT, correct);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //deletes a particular user
    public boolean deleteUser(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }

    //retrieves all the user
    public Cursor getAllUsers() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_USER,
        		KEY_TOTAL_QUESTIONS,
                KEY_CORRECT}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //retrieves a particular user
    public Cursor getUser(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_USER, 
                		KEY_TOTAL_QUESTIONS,
                		KEY_CORRECT
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //updates a user
    public boolean updateUser(long rowId, String user, 
    String total, String correct) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_USER, user);
        args.put(KEY_TOTAL_QUESTIONS, total);
        args.put(KEY_CORRECT, correct);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
}