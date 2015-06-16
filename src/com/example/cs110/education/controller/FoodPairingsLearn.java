package com.example.cs110.education.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.tools.pairing.controller.Pairings;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class FoodPairingsLearn extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_pairings_learn);
	}
	
	public void goToQuiz(View view) {
		Intent intent = new Intent(this, Quiz.class);
		startActivity(intent);
	}
	
	public void goToEducation(View view) {
		Intent intent = new Intent(this, Education.class);
		startActivity(intent);
	}
	
	public void PairingsList(View view) {
		Intent intent = new Intent(this, Pairings.class);
		startActivity(intent);
	}
	
	public void loadUrl(View view)
	{
		WebView webview = new WebView(this);
		webview.loadUrl("https://en.wikipedia.org/wiki/Food_%26_Wine");
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
	      Intent intent=new Intent(this, Learn.class);
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
