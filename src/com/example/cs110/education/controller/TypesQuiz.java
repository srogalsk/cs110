package com.example.cs110.education.controller;

import java.util.Random;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.WineDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TypesQuiz extends Activity {
	
	static int number=0;
	public static int score=0;
	WineDAO db;
	Cursor c;
	public final static String SCORE_MESSAGE = "com.example.cs110.MESSAGE";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_types_quiz);
		
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
	
	private void getRandomWine()
	{
		Random r=new Random();
		int index=r.nextInt(c.getCount());
		c.moveToPosition(index);
		if(c.getString(1).equalsIgnoreCase("rose"))
			getRandomWine();
	}
	
	/*
	 * Sets the cursor to a new wine
	 * Updates the wine display
	 * Updates the question number
	 */
	private void nextQuestion()
	{
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
			getRandomWine();
			String wine=c.getString(1);
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
		String buttonColor = b.getText().toString();
		String color = c.getString(2);
		boolean isRight = buttonColor.equalsIgnoreCase(color);
		
		TextView scoreDisplay=(TextView) findViewById(R.id.scoreDisplay);
		
    	if (isRight)
    	{
    		score++;
    		scoreDisplay.setText("Score: "+score);
    	}
    	
		nextQuestion();
		
		/*switch(view.getId())
    	{
    	case R.id.whiteButton://if white,
    		score++;
    		break;
    	case R.id.redButton://if red,
    		score++;
    		break;
    	default:
    		break;
    	}*/
    	
	}
}
