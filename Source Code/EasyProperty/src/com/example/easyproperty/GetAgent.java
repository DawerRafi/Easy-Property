package com.example.easyproperty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class GetAgent extends Activity {
	private static String TAG_ID ="ID";
	JSONParser jParser = new JSONParser();
	private ProgressDialog pDialog;
	private static final String url_Get_Results = "http://easyproperty.tk.hostinghood.com/agent.php";
	private static final String TAG_SUCCESS="success";
	JSONArray Property = null;
	String Agent_ID ;
	String Name;
	String Number;
	String Address;
	String Rating;
	String Email;
	TextView NameView;
	TextView NumberView;
	TextView AddressView;
	RatingBar RatingView;
	TextView EmailView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Intent e = getIntent();
	    Agent_ID=e.getStringExtra(TAG_ID);
	    setContentView(R.layout.agent_profile);
	    NameView = (TextView) findViewById(R.id.textView1);
	    NumberView = (TextView) findViewById(R.id.textView3);
	    AddressView = (TextView) findViewById(R.id.addressView);
	    RatingView = (RatingBar) findViewById(R.id.ratingBar1);
	    EmailView = (TextView) findViewById(R.id.TextView01);																					
	    // TODO Auto-generated method stub
	    new GetData().execute();
	}
	public void View_properties(View v){
		Intent e = new Intent(this.getApplicationContext(),Select_Property.class);
		e.putExtra(TAG_ID, Agent_ID);
		startActivity(e);
	}
	
	public void View(View v){
		Intent e = new Intent(this.getApplicationContext(),VAprop.class);
		e.putExtra(TAG_ID, Agent_ID);
		startActivity(e);
	}
	
	public void Write(View v){
		Intent e = new Intent(this.getApplicationContext(),Review.class);
		e.putExtra(TAG_ID, Agent_ID);
		startActivity(e);
	}
	
	class GetData extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GetAgent.this);
			pDialog.setMessage("Retrieving Agent Info ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		@Override
		
		protected String doInBackground(String... args) {

			// Building Parameters
			Log.d("Given",Agent_ID);
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", Agent_ID));
			// sending modified data through http request
			// Notice that update product url accepts POST method
			
			JSONObject json = jParser.makeHttpRequest(url_Get_Results,
					"GET", params);
			Log.d("TRY", "Before Try");
			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					Log.d("Success", "Inside Success");
					//Property=json.get("property");
					//Property= json.getJSONArray("property");
					//for (int i = 0; i < Property.length(); i++) {
						//JSONObject c = Property.getJSONObject(i);
						JSONObject c = json.getJSONObject("agent");
						// Storing each json item in variable
						Name = c.getString("name");
						Address = c.getString("add");
						Number = c.getString("numb");
						Rating = c.getString("rating");
						Email = c.getString("email");
						// creating new HashMap
						 Log.d("HashMap","Creating Hashmap");
					 Log.d("Values","Entering Values");
						// adding each child node to HashMap key => value
						 Log.d("Shift","Shifting to onPostExecute");
						// adding HashList to ArrayList
					//	PropertyList.add(map);
					//}
				} else {
					// failed to update product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product uupdated
			 Log.d("OnPost","In OnPost");
			pDialog.dismiss();
			NameView.setText(Name);
			AddressView.setText(Address);
			NumberView.setText(Number);
			EmailView.setText(Email);
			float rating = Float.parseFloat(Rating);
			RatingView.setRating(rating);
			 Log.d("End","End Proc");
		}}

}
