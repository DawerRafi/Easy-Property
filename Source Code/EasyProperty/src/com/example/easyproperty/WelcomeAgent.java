package com.example.easyproperty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class WelcomeAgent extends Activity {
	public String Agent_ID;
	public String Agent_Name;
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent e = getIntent();
		Agent_ID = e.getStringExtra(EXTRA_ID).toString();
		Agent_Name = e.getStringExtra(EXTRA_NAME);
		Log.d("Info ID","Successful");
		setContentView(R.layout.welcome_agent);
	}
	public void EditButton(View v){
		Intent e = new Intent(this.getApplicationContext(),Select_Property.class);
		e.putExtra(EXTRA_ID, Agent_ID);
		e.putExtra(EXTRA_NAME, Agent_Name);
		e.putExtra("Edit",true);
		startActivity(e);
		}
	public void AddButton(View v){
		Intent e = new Intent(this.getApplicationContext(),NewProductActivity.class);
		e.putExtra(EXTRA_ID, Agent_ID);
		e.putExtra(EXTRA_NAME, Agent_Name);
		startActivity(e);
	}
	public void ChangePwButton(View v){
		Intent e = new Intent(this.getApplicationContext(),ChangePassword.class);
		e.putExtra(EXTRA_ID, Agent_ID);
		e.putExtra(EXTRA_NAME, Agent_Name);
		startActivity(e);
	}
	public void SettingsButton(View v){
		Intent e = new Intent(this.getApplicationContext(),UAAcc.class);
		e.putExtra(EXTRA_ID, Agent_ID);
		e.putExtra(EXTRA_NAME, Agent_Name);
		startActivity(e);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
