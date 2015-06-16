package com.example.cs110.education.controller;

import java.util.HashSet;
import java.util.Random;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class RegionsQuiz extends Activity {
	
	static int number=0;
	public static int score=0;
	private WineDAO db;
	private Cursor c;
	public final static String SCORE_MESSAGE = "com.example.cs110.MESSAGE";
	private HashSet<String> usedRegions = new HashSet<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regions_quiz);
		
		number=0;
		score=0;
		db = new WineDAO(this);
		db.open();
		c = db.getAllWines();

		getRandomWine();
        nextQuestion();        
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
	
	private void getRandomWine()
	{
		Random r=new Random();
		int index=r.nextInt(c.getCount());
		c.moveToPosition(index);
	}
	
	private void getRandomRegion()
	{
		int prevInftLoop = 0;
		int loopMax = c.getCount() - 1;
		while( usedRegions.contains(c.getString(3).toLowerCase()) 
			   && prevInftLoop < loopMax ) {
			getRandomWine();
		}
		usedRegions.add(c.getString(3).toLowerCase());
	}
	// creates 4 unique, randomized database-generated answers
	private void randomizeButtons() {
		
		Button b1=(Button) findViewById(R.id.Button1);
		Button b2=(Button) findViewById(R.id.Button2);
		Button b3=(Button) findViewById(R.id.Button3);
		Button b4=(Button) findViewById(R.id.Button4);
		
		// the button that's set first
		Random r = new Random();
		int ansB = r.nextInt(3);
		
		usedRegions.clear();
		
		// set the buttons & final cursor. last button in each case is the answer
		switch( ansB ){
		
		case 0:
			getRandomRegion();
			b2.setText(c.getString(3));
			getRandomRegion();
			b3.setText(c.getString(3));
			getRandomRegion();
			b4.setText(c.getString(3));
			getRandomRegion();
			b1.setText(c.getString(3));
			break;
		
		case 1:
			getRandomRegion();
			b3.setText(c.getString(3));
			getRandomRegion();
			b4.setText(c.getString(3));
			getRandomRegion();
			b1.setText(c.getString(3));
			getRandomRegion();
			b2.setText(c.getString(3));
			break;	
		
		case 2:
			getRandomRegion();
			b4.setText(c.getString(3));
			getRandomRegion();
			b1.setText(c.getString(3));
			getRandomRegion();
			b2.setText(c.getString(3));
			getRandomRegion();
			b3.setText(c.getString(3));
			break;
			
		case 3:
			
			getRandomRegion();
			b1.setText(c.getString(3));
			getRandomRegion();
			b2.setText(c.getString(3));
			getRandomRegion();
			b3.setText(c.getString(3));
			getRandomRegion();
			b4.setText(c.getString(3));
			break;
			
		}
	}
	
	private void nextQuestion()
	{
		TextView scoreDisplay=(TextView) findViewById(R.id.scoreDisplay);
		TextView wineDisplay=(TextView) findViewById(R.id.wineDisplay);
		TextView questionDisplay=(TextView) findViewById(R.id.questionDisplay);
		
		if (9 < number)
		{
			Intent intent=new Intent (this, QuizResult.class);
	    	String message = "" + score;
	    	intent.putExtra(SCORE_MESSAGE,message);
	    	number = score = 0;
	    	startActivity (intent);
		}
		else
		{
			randomizeButtons();
			String wine = c.getString(1);
			wineDisplay.setText(wine);
			number++;
			questionDisplay.setText("Question " + number + "/10");
		}
	}
	
	/*
	 * Checks if the answer is right, then updates the score display
	 */
	public void checkAnswer(View view)
	{
		Button b = (Button) view;
		String buttonRegion = b.getText().toString();
		String region = c.getString(3);
		boolean isRight = buttonRegion.equalsIgnoreCase(region);
		
		TextView scoreDisplay=(TextView) findViewById(R.id.scoreDisplay);
		
    	if (isRight)
    	{
    		score++;
    		scoreDisplay.setText("Score: "+score);
    	}
    	
		nextQuestion();
	}

}
