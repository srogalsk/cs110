package com.example.cs110.tools.pairing.controller;


import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.R.id;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;
import com.example.cs110.R.string;
import com.example.cs110.tools.controller.Tools;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class FoodtowinePasta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foodtowine_pasta);
		// Show the Up button in the action bar.
		setupActionBar();
	}
	
	public void pickTomato(View view){
		EditText display = (EditText) findViewById(R.id.Pasta);

  	display.setText(getResources().getString(R.string.tomato));

	}
	public void goHome(View view){
		Intent intent=new Intent (this,MainActivity.class);
    	startActivity(intent);

	}
	public void pickWhite(View view){
		EditText display = (EditText) findViewById(R.id.Pasta);

  	display.setText(getResources().getString(R.string.cream));

	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	 @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_back:
	      Intent intent=new Intent(this, FoodtoWine.class);
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
