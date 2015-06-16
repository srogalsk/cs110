package com.example.cs110;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cs110.education.controller.Education;
import com.example.cs110.model.data.*;
import com.example.cs110.tools.controller.BAC;
import com.example.cs110.tools.controller.Tools;
import com.example.cs110.tools.controller.UserStats;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE="com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ImageView tbd = (ImageView) findViewById(R.id.tbd);
		tbd.getBackground().setAlpha(180);
		ImageView forwine = (ImageView) findViewById(R.id.forwine);
		forwine.getBackground().setAlpha(120);*/
        WineDAO db = new WineDAO(this);
        UserDAO udb = new UserDAO(this);
        
        //make temp userdb just to store quiz info...
        udb.open();
        udb.insertUser(
                "Guest",
                "0",
                "0");
        
        
        db.open();
        Cursor c = db.getAllWines();
        if(!c.moveToFirst()){
                    db.populateDatabase();
        }
        
        c = db.getAllWines();
        if (c.moveToFirst())  {      
            displayWine(c);
        	c.moveToNext();
        	displayWine(c);
        	c.moveToNext();
        	displayWine(c);
        	c.moveToNext();
        	displayWine(c);
        }
        else
            Toast.makeText(this, "No wine found", 
            		Toast.LENGTH_LONG).show();
        db.close();
        udb.close();
        //db.populateDatabase();
        /*
        db.insertWine("Yes", "Red", "Here", "Hi");

        udb.insertUser(
                "Bob",
                "204",
                "20");
        udb.insertUser(
                "Dude",
                "522",
                "522");
        
       Cursor c = db.getAllWines();
       if (c.moveToFirst())  {      
            displayWine(c);
        	c.moveToNext();
        	c.moveToNext();
        	c.moveToNext();
        	displayWine(c);
        }
        else
            Toast.makeText(this, "No wine found", 
            		Toast.LENGTH_LONG).show();
        db.close();
        udb.close();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
 /*   public void sendMessage (View view)
    {
   // 	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message); 
  //  	startActivity(intent);
    }
    */
    public void goBAC(View view){
    	Intent intent=new Intent (this,BAC.class);
    	startActivity(intent);
    }
    
    public void goEntry(View view){
    	Intent intent=new Intent (this, MainActivity.class);
    	startActivity (intent);
    }
    
    public void goTools(View view){
    	Intent intent=new Intent (this, Tools.class);
    	startActivity (intent);
    }
    /*
    public void goSettings(View view){
    	Intent intent=new Intent (this, Settings.class);
    	startActivity (intent);
    }
    */
    
    public void goUserStats(View view){
    	Intent intent=new Intent (this, UserStats.class);
    	startActivity (intent);
    }
    
    public void goEducation(View view){
    	Intent intent=new Intent (this, Education.class);
    	startActivity (intent);
    }
    
    //displays a wine
    public void displayWine(Cursor c)
    {
        /*Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "WINE NAME: " + c.getString(1) + "\n" +
                "WINE TYPE: " + c.getString(2) + "\n" +
                "WINE ORIGIN:  " + c.getString(3) + "\n" +
                "Description: " + c.getString(4) + "\n" +
                "COUNT: " + c.getCount(),
                Toast.LENGTH_LONG).show();     */   
    } 
    
    //displays a user
    public void displayUser(Cursor c)
    {
        Toast.makeText(this, 
                "id: " + c.getString(0) + "\n" +
                "USER NAME: " + c.getString(1) + "\n" +
                "USER TOTAL QUESTIONS TAKEN: " + c.getInt(2) + "\n" +
                "WINE CORRECT ANSWERS:  " + c.getInt(3),
                Toast.LENGTH_LONG).show();        
    } 
    
}
