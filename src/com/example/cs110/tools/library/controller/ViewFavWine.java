package com.example.cs110.tools.library.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.WineDAO;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewFavWine extends Activity {
private int rowId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_wine);
		View inWish = findViewById(R.id.buttonAddWish);
		View inFav = findViewById(R.id.buttonAddFav);
		View notinWish = findViewById(R.id.ButtonRemoveWish);
		View notinFav = findViewById(R.id.ButtonRemoveFav);
		WineDAO db;
		db = new WineDAO(this);
		Intent intent = getIntent();
	    db.open();
	    String id = intent.getStringExtra("rowId");
	    rowId = Integer.parseInt(id);
	    Cursor c = db.getWine(rowId);
		
		EditText name = (EditText) findViewById(R.id.winename);
		EditText description = (EditText) findViewById(R.id.winedsc);
		Typeface font = Typeface.createFromAsset(getAssets(), "bless.otf");
		name.setTypeface(font);
		
        name.setText(c.getString(1));
        description.setText(c.getString(4));
       
        if((c.getInt(6)==1)){
        	inWish.setVisibility(View.GONE);
        	notinWish.setVisibility(View.VISIBLE);
        }
        else
        {
        	inWish.setVisibility(View.VISIBLE);
        	notinWish.setVisibility(View.GONE);
        }
        if(!(c.getInt(5)==1)){
        	inFav.setVisibility(View.VISIBLE);
        	notinFav.setVisibility(View.GONE);
        }
        else
        {
        	inFav.setVisibility(View.GONE);
        	notinFav.setVisibility(View.VISIBLE);
        }
        db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void addtoWish(View view){
		View inWish = findViewById(R.id.buttonAddWish);
		View notinWish = findViewById(R.id.ButtonRemoveWish);
		inWish.setVisibility(View.GONE);
    	notinWish.setVisibility(View.VISIBLE);
    	WineDAO db;
		db = new WineDAO(this);
	    db.open();
	    Cursor c = db.getWine(rowId);
	    db.updateWine(rowId, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), 1);
	    Toast.makeText(this, "Added to Wishlist!", 
        		Toast.LENGTH_LONG).show();
	    db.close();
    }
	public void addtoFav(View view){
		View inFav = findViewById(R.id.buttonAddFav);
		View notinFav = findViewById(R.id.ButtonRemoveFav);
		inFav.setVisibility(View.GONE);
    	notinFav.setVisibility(View.VISIBLE);
    	WineDAO db;
		db = new WineDAO(this);
	    db.open();
	    Cursor c = db.getWine(rowId);
	    db.updateWine(rowId, c.getString(1), c.getString(2), c.getString(3), c.getString(4), 1, c.getInt(6));
	    Toast.makeText(this, "Added to Favorites!", 
        		Toast.LENGTH_LONG).show();
	    db.close();
    }
	public void removeFav(View view){
		View inFav = findViewById(R.id.buttonAddFav);
		View notinFav = findViewById(R.id.ButtonRemoveFav);
		inFav.setVisibility(View.VISIBLE);
    	notinFav.setVisibility(View.GONE);
    	WineDAO db;
		db = new WineDAO(this);
	    db.open();
	    Cursor c = db.getWine(rowId);
	    db.updateWine(rowId, c.getString(1), c.getString(2), c.getString(3), c.getString(4), 0, c.getInt(6));
	    Toast.makeText(this, "Removed from Favorites!", 
        		Toast.LENGTH_LONG).show();
	    db.close();
    }
	public void removeWish(View view){
		View inWish = findViewById(R.id.buttonAddWish);
		View notinWish = findViewById(R.id.ButtonRemoveWish);
		inWish.setVisibility(View.VISIBLE);
    	notinWish.setVisibility(View.GONE);
    	WineDAO db;
		db = new WineDAO(this);
	    db.open();
	    Cursor c = db.getWine(rowId);
	    db.updateWine(rowId, c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), 0);
	    Toast.makeText(this, "Removed from Wishlist!", 
        		Toast.LENGTH_LONG).show();
	    db.close();
    }

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_back:
	      Intent intent=new Intent(this, FavoritesActivity.class);
	      startActivity(intent);
	      break;
	    case R.id.action_home:
		  Intent intent2=new Intent(this, MainActivity.class);
		  startActivity(intent2);
	      break;

	    default:
	      break;
	    }
	    return true;
}
}
