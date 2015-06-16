package com.example.cs110.education.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.UserDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QuizResult extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_result);
		
		Intent intent = getIntent();
	    String message = intent.getStringExtra("com.example.cs110.MESSAGE");
	    
		TextView scoreView=(TextView) findViewById(R.id.scoreDisplay);
	    scoreView.setText(message);

	    UserDAO udb = new UserDAO(this);
    	udb.open();
    	Cursor u = udb.getAllUsers();
    	
    	if(u.moveToFirst()) {
    		int newanswered = u.getInt(2) + 10;
    		int newscore = u.getInt(3) + Integer.parseInt(message.toString());
    		udb.updateUser(u.getInt(0), u.getString(1), ""+newanswered, ""+newscore);
    	}
    	udb.close();

	    
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
	      Intent intent=new Intent(this, Quiz.class);
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
	
	public void goEducation(View view){
    	Intent intent=new Intent (this, Education.class);
    	startActivity (intent);
    }

}
