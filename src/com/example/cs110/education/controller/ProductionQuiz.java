/*
 * To Copy-Paste this, Make sure to change the questions under getQuestionText, as well as the variable "String answer" on
 * Line 25ish. also, Change setContentView, and getMenuInflater
 */


package com.example.cs110.education.controller;

import java.util.Random;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.model.data.WineDAO;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductionQuiz extends Activity {

	static int number=0;
	public static int score=0;
	String answer="Vinification";//HERE
	WineDAO db;
	Cursor c;
	public final static String SCORE_MESSAGE = "com.example.cs110.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_quiz);
		
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
		Button b2=(Button) findViewById(R.id.Button2);
		Button b3=(Button) findViewById(R.id.Button3);
		Button b4=(Button) findViewById(R.id.Button4);
		
		String answer;
		switch(number)
		{
		case 0:
			wineDisplay.setText("What is winemaking called?");
			answer="Vinification";
			b1.setText("Soaking");
			b2.setText("Brewing");
			b3.setText("Vinification");
			b4.setText("Liquifaction");
			break;
		case 1:
			wineDisplay.setText("The science of winemaking is called:");
			answer="Oenology";
			b1.setText("Oenology");
			b2.setText("Wineology");
			b3.setText("Fermentology");
			b4.setText("Tanninology");
			break;
		case 2:
			wineDisplay.setText("What is a person who makes wine called?");
			answer="Vintner";
			b1.setText("Vintner");
			b2.setText("Oenorphile");
			b3.setText("Fermenter");
			b4.setText("Winer");
			break;
		case 3:
			wineDisplay.setText("What was the first winery in California?");
			answer="Sonoma Valley";
			b1.setText("Sonoma Valley");
			b2.setText("Napa");
			b3.setText("Champagne");
			b4.setText("Santa Cruz");
			break;
		case 4:
			wineDisplay.setText("Which is the catalyst for fermenting");
			answer="Yeast";
			b1.setText("Water");
			b2.setText("Pressing Grapes");
			b3.setText("Oak Barrels");
			b4.setText("Yeast");
			break;
		case 5:
			wineDisplay.setText("What does yeast convert sugar in wine to?");
			answer="Ethanol";
			b1.setText("Ethanol");
			b2.setText("Water");
			b3.setText("More sugar");
			b4.setText("Tannins");
			break;
		case 6:
			wineDisplay.setText("White wines are always made from white grapes");
			answer="False";
			b1.setText("True");
			b2.setText("False");
			b3.setVisibility(View.INVISIBLE);
			b4.setVisibility(View.INVISIBLE);
			break;
		case 7:
			wineDisplay.setText("Wine is only made from grapes");
			answer="False";
			b1.setText("True");
			b2.setText("False");
			break;
		case 8:
			wineDisplay.setText("What kind of wood is wine typically kept in?");
			answer="Oak";
			b1.setText("Oak");
			b2.setText("Cedar");
			b3.setVisibility(View.VISIBLE);
			b4.setVisibility(View.VISIBLE);
			b3.setText("Willow");
			b4.setText("Cherrywood");
			break;
		default:
			wineDisplay.setText("What is added to make wine \"fortified\"");
			answer="Distilled Beverage";
			b1.setText("Water");
			b2.setText("Distilled Beverage");
			b3.setText("Yeast");
			b4.setText("Sugar");
			break;
			
		}
		return answer;
		
	}

}
