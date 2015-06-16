package com.example.cs110.tools.controller;

import com.example.cs110.R;
import com.example.cs110.R.layout;
import com.example.cs110.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LibraryAddWine extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_library_add_wine);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.library_add_wine, menu);
		return true;
	}

}
