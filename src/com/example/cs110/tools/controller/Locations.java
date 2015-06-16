package com.example.cs110.tools.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.R.id;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Locations extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locations);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void goMap(View view){
		EditText address = (EditText) findViewById(R.id.zipcode);
		String zipcode = address.getText().toString();
		String uri = "geo:0,0?q=winery+near"+zipcode;
	    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	    startActivity(i);
    }
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_back:
	      Intent intent=new Intent(this, Tools.class);
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
