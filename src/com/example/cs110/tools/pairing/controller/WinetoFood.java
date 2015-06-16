package com.example.cs110.tools.pairing.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class WinetoFood extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wineto_food);
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
	      Intent intent=new Intent(this, Pairings.class);
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
	public void goWhite(View view){
    	Intent intent=new Intent (this,WinetofoodWhite.class);
    	startActivity(intent);
    }
	public void goRed(View view){
    	Intent intent=new Intent (this,WinetofoodRed.class);
    	startActivity(intent);
    }
	public void goOther(View view){
    	Intent intent=new Intent (this,WinetofoodOther.class);
    	startActivity(intent);
    }
}
