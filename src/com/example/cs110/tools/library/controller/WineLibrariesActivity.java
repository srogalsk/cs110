package com.example.cs110.tools.library.controller;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.tools.controller.Tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class WineLibrariesActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_libraries);
		Button add_wine = (Button) findViewById(R.id.digital_cellar_button);
		add_wine.getBackground().setAlpha(200);
		Button search_wine = (Button) findViewById(R.id.search_wines_button);
		search_wine.getBackground().setAlpha(200);
		Button wish_wine = (Button) findViewById(R.id.wishlist_button);
		wish_wine.getBackground().setAlpha(200);
		Button favorites_wine = (Button) findViewById(R.id.favorites_button);
		favorites_wine.getBackground().setAlpha(200);
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
	
	
	public void goWishlist(View view){
    	Intent intent=new Intent (this,WishlistActivity.class);
    	startActivity(intent);
    }
	public void goFavorites(View view){
    	Intent intent=new Intent (this,FavoritesActivity.class);
    	startActivity(intent);
    }
	public void goSearchWines(View view){
    	Intent intent=new Intent (this,SearchWinesActivity.class);
    	startActivity(intent);
    }
	public void goAddWine(View view){
    	Intent intent=new Intent (this,AddWine.class);
    	startActivity(intent);
    }
	
	
}
