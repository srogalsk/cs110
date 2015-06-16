package com.example.cs110.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ListAdapterDAO
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ORIGIN = "origin";  
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_FAVORITES = "favorites";
    public static final String KEY_WISH = "wish";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "list";
    private static final String DATABASE_TABLE = "lists";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table lists (_id integer primary key autoincrement, "
        + "name text not null, type text not null, " 
        + "origin text not null, description text not null, "
        + "favorites integer not null, wish integer not null);";
        
    private final Context context; 
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public ListAdapterDAO(Context ctx) 
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
            db.execSQL("DROP TABLE IF EXISTS lists");
            onCreate(db);
        }
    }    
    
    //opens the database
    public ListAdapterDAO open() throws SQLException 
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
    public long insertWine(String name, String type, String origin, String description, int favorites, int wish) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TYPE, type);
        initialValues.put(KEY_ORIGIN, origin);
        initialValues.put(KEY_DESCRIPTION, description);
        initialValues.put(KEY_FAVORITES, favorites);
        initialValues.put(KEY_WISH, wish);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //deletes a particular wine
    public boolean deleteWine(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }
    
    //deletes entire database
    public int deleteAllWines()
    {
    	return db.delete(DATABASE_TABLE, null, null);
    }

    //retrieves all the wines
    public Cursor getAllWines() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_NAME,
        		KEY_TYPE,
                KEY_ORIGIN,
                KEY_DESCRIPTION,
                KEY_FAVORITES,
                KEY_WISH}, 
                null, 
                null, 
                null, 
                null, 
                KEY_NAME + " ASC");
    }

    //retrieves a particular wine
    public Cursor getWine(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME, 
                		KEY_TYPE,
                		KEY_ORIGIN,
                		KEY_DESCRIPTION,
                		KEY_FAVORITES,
                		KEY_WISH
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

    //updates a wine
    public boolean updateWine(long rowId, String name, 
    String type, String origin, String desc, int favorites, int wish) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_TYPE, type);
        args.put(KEY_ORIGIN, origin);
        args.put(KEY_DESCRIPTION, desc);
        args.put(KEY_FAVORITES, favorites);
        args.put(KEY_WISH, wish);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    //Add current wine to favorites
    public long addToList(Cursor f, int favorites, int myWish){
    	ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, f.getString(1));
        initialValues.put(KEY_TYPE, f.getString(2));
        initialValues.put(KEY_ORIGIN, f.getString(3));
        initialValues.put(KEY_DESCRIPTION, f.getString(4));
        initialValues.put(KEY_FAVORITES, favorites);
        initialValues.put(KEY_WISH, myWish);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
}