package com.example.cs110.tools.library.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.R.id;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;
import com.example.cs110.education.controller.Education;
import com.example.cs110.model.data.WineDAO;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchWinesActivity extends Activity {

	private String search_term;
	private int call_display;
	WineDAO db;
	private ArrayList<Integer> keys_list;
    

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_wines);
		  keys_list = new ArrayList<Integer>();
		  // keys_list.clear();
		    ArrayList<String> list;
		    list = new ArrayList<String>();
		    db = new WineDAO(this);
		    db.open();
		    Cursor c = db.getAllWines();
		    c.moveToFirst();
		    while(c.moveToNext())
		    {
		      list.add(c.getString(1)+" - "+c.getString(2));
		      keys_list.add(c.getInt(0));
		    }
		    if(list.isEmpty()){
		    list.add("List is Empty!");
		    displayList(list);
		    }
		    else{
		    displayList(list);
		    }
		    
		    
		    db.close();
	}
	
public void displayList(final ArrayList<String> list) {
	 final ListView listview = (ListView) findViewById(R.id.listviewMy);

    final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
    listview.setAdapter(adapter);

    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        final String item = (String) parent.getItemAtPosition(position);
             
            	  if((list.contains("List is Empty!"))||(list.contains("No search Results found"))){
            		  list.add("List is still Empty!");
            		  displayList(list);
            	  }
            	  else{
            		  Intent view_wine = new Intent(SearchWinesActivity.this, ViewWine.class);
                      view_wine.putExtra("rowId", keys_list.get(list.indexOf(item))+"");
                      startActivity (view_wine);
            	  }
              
 
      }

    });
  }

  private class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId,
        List<String> objects) {
      super(context, textViewResourceId, objects);
      for (int i = 0; i < objects.size(); ++i) {
        mIdMap.put(objects.get(i), i);
      }
    }

    @Override
    public long getItemId(int position) {
      String item = getItem(position);
      return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }

}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_wines, menu);
		return true;
	}

	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_back:
	      Intent intent=new Intent(this, WineLibrariesActivity.class);
	      startActivity(intent);
	      break;
	    case R.id.action_home:
		  Intent intent2=new Intent(this, MainActivity.class);
		  startActivity(intent2);
	      break;
	    case R.id.action_search_wines:
	    	searchWineDialog();
   		 //Toast.makeText(this, "FART", Toast.LENGTH_LONG).show();
	    	
		    break;

	    default:
	      break;
	    }

	    return true;
	  }
	String search_input;
	private void searchWineDialog() {
		// TODO Auto-generated method stub
		call_display = 0;
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Search for Wines");
		alert.setMessage("Enter varietal, color, or region");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		    
		  search_input = input.getText().toString();
		  displayList(SearchResults1(search_input));
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
				
		  }
		});
		
		alert.show();
		
	}


public ArrayList<String> SearchResults1(String searched){
	keys_list.clear();
	ArrayList<String> search_list = new ArrayList<String>();
	db = new WineDAO(this);
	db.open();
	Cursor c = db.getAllWines();
	c.moveToFirst();
	while(c.moveToNext()) {
		for(int i = 1; i <=3; i++) {
			
			String s2 = c.getString(i).toLowerCase();
			
			if(s2.contains(searched.toLowerCase())) {
				search_list.add(c.getString(1)+"  -  "+c.getString(2));
				keys_list.add(c.getInt(0));
				break;
			}
		}
	}
	if(search_list.isEmpty()){
		search_list.add("No search Results found");
	}
	db.close();
	return search_list;

	}
}