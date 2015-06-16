/*
 * To Copy-Paste this, Make sure to change the questions under getQuestionText, as well as the variable "String answer" on
 * Line 25ish. also, Change setContentView, and getMenuInflater
 */


package com.example.cs110.education.controller;

import java.util.Random;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.WineDAO;
import com.example.cs110.model.data.UserDAO;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EtiquetteQuiz extends Activity {

	static int number=0;
	public static int score=0;
	String answer="42-45F (6-10C)";//HERE
	WineDAO db;
	Cursor c;
	public final static String SCORE_MESSAGE = "com.example.cs110.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_etiquette_quiz);
		
		db = new WineDAO(this);
		db.open();
		c = db.getAllWines();
        getRandomWine();
        nextQuestion();
        number = 0;
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
	
	private void getRandomWine()
	{
		Random r=new Random();
		int index=r.nextInt(c.getCount());
		c.moveToPosition(index);
	}
	
	/*
	 * Sets the cursor to a new wine
	 * Updates the wine display
	 * Updates the question number
	 */
	private void nextQuestion()
	{
		TextView scoreDisplay=(TextView) findViewById(R.id.scoreDisplay);
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
			getQuestionText(number);
			
			//String wine=c.getString(1);
			//wineDisplay.setText(wine);
			
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
		String buttonText = b.getText().toString();
		//answer = getQuestionText(number);
		boolean isRight = answer.equalsIgnoreCase(buttonText);
		answer = getQuestionText(number);
		TextView scoreDisplay=(TextView) findViewById(R.id.scoreDisplay);
		
    	if (isRight)
    	{
    		score++;
    		scoreDisplay.setText("Score: "+score);
    	}
    	
		nextQuestion();
	}
	
	public String getQuestionText(int number)
	{
		TextView wineDisplay=(TextView) findViewById(R.id.wineDisplay);
		
		Button b1=(Button) findViewById(R.id.Button1);
		b1.setAlpha(200);
		Button b2=(Button) findViewById(R.id.Button2);
		b2.setAlpha(200);
		Button b3=(Button) findViewById(R.id.Button3);
		b3.setAlpha(200);
		Button b4=(Button) findViewById(R.id.Button4);
		b4.setAlpha(200);
		
		String answer;
		switch(number)
		{
		case 0:
			wineDisplay.setText("What's the best temperature for serving sparkling wine?");
			answer="42-45F (6-10C)";
			b1.setText("42-45F (6-10C)");
			b2.setText("48-54F (9-12C)");
			b3.setText("48-58F (9-14C)");
			b4.setText("57-68F (13-20C)");
			break;
		case 1:
			wineDisplay.setText("How long should you let red wine breath?");
			answer="1 hour";
			b1.setText("0 hours");
			b2.setText("1 hour");
			b3.setText("2 hours");
			b4.setText("3 hours");
			break;
		case 2:
			wineDisplay.setText("What's the best temperature for serving red wine?");
			answer="57-68F (13-20C)";
			b1.setText("42-45F (6-10C)");
			b2.setText("48-54F (9-12C)");
			b3.setText("48-58F (9-14C)");
			b4.setText("57-68F (13-20C)");
			break;
		case 3:
			wineDisplay.setText("Which of these is it a bad idea to decant?");
			answer="Young white wine";
			b1.setText("Red wine");
			b2.setText("Young white wine");
			b3.setText("Vintage Champagne");
			b4.setText("Port");
			break;
		case 4:
			wineDisplay.setText("Which of these do you pour down the side of the glass?");
			answer="Sparkling wine";
			b1.setText("Sparkling wine");
			b2.setText("Red wine");
			b3.setText("White wine");
			b4.setText("Port");
			break;
		case 5:
			wineDisplay.setText("What's the best temperature for serving white wine?");
			answer="48-58F (9-14C)";
			b1.setText("42-45F (6-10C)");
			b2.setText("48-54F (9-12C)");
			b3.setText("48-58F (9-14C)");
			b4.setText("57-68F (13-20C)");
			break;
		case 6:
			wineDisplay.setText("Which glass is good for white wine?");
			answer="Narrow, thin tapered glass";
			b1.setText("Pear-shaped glass");
			b2.setText("Narrow, thin tapered glass");
			b3.setVisibility(View.INVISIBLE);
			b4.setVisibility(View.INVISIBLE);
			break;
		case 7:
			wineDisplay.setText("Which glass is good for red wine?");
			answer="Pear-shaped glass";
			b1.setText("Pear-shaped glass");
			b2.setText("Narrow, thin tapered glass");
			break;
		case 8:
			wineDisplay.setText("Which is not one of the \"Four S's\" of wine tasting?");
			answer="Swallow";
			b1.setText("See");
			b2.setText("Smell");
			b3.setVisibility(View.VISIBLE);
			b4.setVisibility(View.VISIBLE);
			b3.setText("Swirl");
			b4.setText("Swallow");
			break;
		default:
			wineDisplay.setText("What's the best temperature for serving dark sherry?");
			answer="57-68F (13-20C)";
			b1.setText("42-45F (6-10C)");
			b2.setText("48-54F (9-12C)");
			b3.setText("48-58F (9-14C)");
			b4.setText("57-68F (13-20C)");
			break;
			
		}
		return answer;
		
	}

}
