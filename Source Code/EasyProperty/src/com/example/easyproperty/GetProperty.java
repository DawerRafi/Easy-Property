package com.example.easyproperty;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.widget.TextView;

public class GetProperty extends Activity {
	String Prop_ID;
	String Agent_ID;
	
	JSONParser jParser = new JSONParser();
	private ProgressDialog pDialog;
	private static final String url_Get_Results = "http://easyproperty.tk.hostinghood.com/property.php";
	private static final String TAG_SUCCESS="success";
	JSONArray Property = null;
	TextView Name;
	TextView Description;
	TextView Type;
	TextView Area;
	TextView Size;
	TextView Bedrooms;
	TextView Bathrooms;
	ArrayList<HashMap<String, String>> PropertyList;
	HashMap<Integer, String> map;
	
	public void Pictures(View v){
		Intent e = new Intent(this.getApplicationContext(),MyActivityGrid.class);
		startActivity(e);
	}
	public void ViewAgent(View v){
		Intent e = new Intent(this.getApplicationContext(),GetAgent.class);
		e.putExtra("ID", Agent_ID);
		startActivity(e);
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

		 map = new HashMap<Integer, String>();
	    Intent e= getIntent();
	    Prop_ID = e.getStringExtra("Product_ID");

		Log.d("Given2",Prop_ID);
	    setContentView(R.layout.view_property);
	    Name = (TextView) findViewById(R.id.Name);
	    Description = (TextView) findViewById(R.id.Description);
	    Type = (TextView) findViewById(R.id.Type);
	    Area = (TextView) findViewById(R.id.Area);
	    Size = (TextView) findViewById(R.id.Size);
	    Bedrooms = (TextView) findViewById(R.id.Bed);
	    Bathrooms = (TextView) findViewById(R.id.Bath);
	    Log.d("OnCreate","Loaded");
	    new GetData().execute();
	    // TODO Auto-generated method stub
	}
	class GetData extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GetProperty.this);
			pDialog.setMessage("Retrieving Property ...");
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
			Log.d("Given",Prop_ID);
		
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", Prop_ID));
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
						JSONObject c = json.getJSONObject("property");
						JSONObject c2 = json.getJSONObject("agent");
				
						Log.d("TESTING OBJECT",c2.toString());
						// Storing each json item in variable
						String bed = c.getString("bed");
						String name = c.getString("name");
						String description = c.getString("dscrpt");
						String bath = c.getString("bath");
						String type = c.getString("type");
						String area = c.getString("area");
						String size = c.getString("size");
						if(type=="1"){
							type="Rent";
						}
						else { type="Sale";}
						
						Agent_ID= c2.getString("id");
						Log.d("TESTING ID",Agent_ID);
						// creating new HashMap
						 Log.d("HashMap","Creating Hashmap");
					 Log.d("Values","Entering Values");
						// adding each child node to HashMap key => value
						map.put(3, bed);
						map.put(1, name);
						map.put(2, description);
						map.put(4, bath);
						map.put(5, area);
						map.put(6, type);
						map.put(8, size);
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
			String d = map.get(1).toString();
			Name.setText(d);
			Description.setText( map.get(2).toString());
			Area.setText(Area.getText()+ " : "+map.get(5).toString());
			Bedrooms.setText(Bedrooms.getText()+ " : "+map.get(3).toString());
			Bathrooms.setText(Bathrooms.getText()+ " : "+map.get(4).toString());
			Type.setText(Type.getText()+ " : "+map.get(6).toString());
			Size.setText(Size.getText()+ " : "+map.get(8).toString());
			 Log.d("End","End Proc");
		}}
}
