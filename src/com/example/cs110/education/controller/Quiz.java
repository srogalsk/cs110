package com.example.cs110.education.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Quiz extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
	}
	
	
	public void goEtiquetteQuiz(View view)
	{
		Intent intent = new Intent(this, EtiquetteQuiz.class);
		startActivity(intent);
	}
	
	public void goRegionsQuiz(View view){
    	Intent intent=new Intent (this, RegionsQuiz.class);
    	startActivity (intent);
    }
	public void goHistoryQuiz(View view){
    	Intent intent=new Intent (this, HistoryQuiz.class);
    	startActivity (intent);
    }
	public void goTypesQuiz(View view){
    	Intent intent=new Intent (this, TypesQuiz.class);
    	startActivity (intent);
    }
	
	public void goProductionQuiz(View view){
		Intent intent = new Intent (this, ProductionQuiz.class);
		startActivity (intent);
	}
	
	public void goFoodPairingsQuiz(View view) {
		Intent intent = new Intent (this, FoodPairingsQuiz.class);
		startActivity (intent);
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
	      Intent intent=new Intent(this, Education.class);
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
