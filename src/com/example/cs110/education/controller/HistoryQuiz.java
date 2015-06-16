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

public class HistoryQuiz extends Activity {

	static int number=0;
	public static int score=0;
	String answer="Georgia";//HERE
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
		Button b2=(Button) findViewById(R.id.Button2);
		Button b3=(Button) findViewById(R.id.Button3);
		Button b4=(Button) findViewById(R.id.Button4);
		
		String answer;
		switch(number)
		{
		case 0:
			wineDisplay.setText("Where was the earliest example of wine found?");
			answer="Georgia";
			b1.setText("Georgia");
			b2.setText("America");
			b3.setText("France");
			b4.setText("Japan");
			break;
		case 1:
			wineDisplay.setText("Which country popularlized wine production?");
			answer="Roman Empire";
			b1.setText("Roman Empire");
			b2.setText("France");
			b3.setText("Ottoman Empire");
			b4.setText("Wallachia");
			break;
		case 2:
			wineDisplay.setText("Where was the oldest winery found?");
			answer="Armenia";
			b1.setText("Armenia");
			b2.setText("France");
			b3.setText("Italy");
			b4.setText("Germany");
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
			wineDisplay.setText("What did the Chinese create wine out of?");
			answer="Rice";
			b1.setText("Persimmons");
			b2.setText("Dragonfruit");
			b3.setText("Ponzu");
			b4.setText("Rice");
			break;
		case 5:
			wineDisplay.setText("What is the oldest wine grape?");
			answer="Muscat";
			b1.setText("Diamond");
			b2.setText("Cabernet");
			b3.setText("Concord");
			b4.setText("Muscat");
			break;
		case 6:
			wineDisplay.setText("Wine regions with a long history are \"Old World\"");
			answer="True";
			b1.setText("True");
			b2.setText("False");
			b3.setVisibility(View.INVISIBLE);
			b4.setVisibility(View.INVISIBLE);
			break;
		case 7:
			wineDisplay.setText("Wine is a modern beverage");
			answer="False";
			b1.setText("True");
			b2.setText("False");
			break;
		case 8:
			wineDisplay.setText("Who was the Roman god of wine?");
			answer="Bacchus";
			b1.setText("Dionysius");
			b2.setText("Saturn");
			b3.setVisibility(View.VISIBLE);
			b4.setVisibility(View.VISIBLE);
			b3.setText("Bacchus");
			b4.setText("Diana");
			break;
		default:
			wineDisplay.setText("What did ancient Romans dissolve in wine to improve their health?");
			answer="Pearls";
			b1.setText("Dung");
			b2.setText("Pearls");
			b3.setText("Sulphur");
			b4.setText("Salt");
			break;
			
		}
		return answer;
		
	}

}
