package com.example.cs110.education.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Education extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_education);
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
	    Intent intent=new Intent(this, MainActivity.class);
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
	
	
	public void goLearn(View view){
    	Intent intent=new Intent (this, Learn.class);
    	startActivity (intent);
    }
	
	public void goQuiz(View view){
		Intent intent = new Intent (this, Quiz.class);
		startActivity (intent);
	}
	
	
}
