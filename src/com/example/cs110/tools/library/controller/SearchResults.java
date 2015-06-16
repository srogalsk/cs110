package com.example.cs110.tools.library.controller;

import java.util.ArrayList;

import com.example.cs110.MainActivity;
import com.example.cs110.R;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;
import com.example.cs110.model.data.WineDAO;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.Toast;
import android.widget.Toast;
import android.widget.Toast;

public class SearchResults {
  private String s1;
  WineDAO db;
  public SearchResults(String s1, WineDAO db) { 
		
		s1 = s1.toLowerCase();
		this.db = db;

  }
  
  public ArrayList<String> getNarrowedResults() {
	  db.open();
	  ArrayList<String> search_list = new ArrayList<String>();
		Cursor c = db.getAllWines();
		c.moveToFirst();
		while(c.moveToNext()) {
			for(int i = 1; i <=3; i++) {
				
				String s2 = c.getString(i).toLowerCase();
				if(s1.equals(s2)) {
					search_list.add(c.getString(1));
				}
			}
		}
		db.close();
		return search_list;
  }
}