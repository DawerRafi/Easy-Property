package com.example.easyproperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GetAreas extends ListActivity {

	

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	public static ArrayList<String> Areas;
	public static ArrayList<HashMap<String,String>> Agent;
	// url to get all products list
	private static String url_get_areas = "http://easyproperty.tk.hostinghood.com/areas.php";
	private static String url_agents="http://easyproperty.tk.hostinghood.com/top_agents.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_PRODUCTS = "Property_info";
	private static final String TAG_AREA = "Area";
	private static final String TAG_ID = "ID";
	public static ArrayAdapter<CharSequence> adapter;
	public Spinner spinner;

	// products JSONArray
	JSONArray ArrayOfAreas = null;
	JSONArray ArrayOfAgents = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()   // or .detectAll() for all detectable problems
          .penaltyLog()
          .build());
  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity2);
		Areas = new ArrayList<String>();
		Areas.add("Pechs");
		Areas.add("Bahadurabad");
		Areas.add("Gulshan");
		Areas.add("Gulistan-e-Jauhar");
		Areas.add("Malir");
		Areas.add("North Karachi");
		Areas.add("Clifton");
		Areas.add("Defence");
		 spinner = (Spinner) findViewById(R.id.spinner34);
		// Create an ArrayAdapter using the string array and a default spinner layout
		adapter = new ArrayAdapter(this,
		        android.R.layout.simple_spinner_item,Areas);
		Agent = new ArrayList<HashMap<String,String>>();
		// Hashmap for ListView
		
	/*	 */

		// Loading products in Background Thread
		new LoadAllAreas().execute();

		// Get listview
		//ListView lv = getListView();
		// Response from Edit Product Activity
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String ID = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();
				Log.d("ID",ID);
				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						GetAgent.class);
				// sending pid to next activity
				in.putExtra(TAG_ID, ID);
				
				// starting new activity and expecting some response back
				startActivity(in);
			}
		});

	}

	
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.activity2, menu);
	    return true;
	}
	public void SearchButton(View v){
		Intent s = new Intent(this.getApplicationContext(),DisplayResult.class);
		
					s.putExtra(TAG_AREA, spinner.getSelectedItem().toString());
		startActivity(s);
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.LogIn:
	        // About option clicked.
	    	Intent e = new Intent(this.getApplicationContext(),LoginAgent.class);
	    	startActivity(e);
	        return true;
	    }
	    return false;
	}

	
	public void Update(){
		Log.d("Updating","Updating Spinner");
		
		// Specify the layout to use when the list of choices appears
		for(int i=0;i<Areas.size();i++){
			adapter.add(Areas.get(i));
		}
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);	
	}

	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoadAllAreas extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(GetAreas.this);
			pDialog.setMessage("Loading Your Application. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		@Override
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json2 = jParser.makeHttpRequest(url_agents, "GET", params);
			JSONObject json = jParser.makeHttpRequest(url_get_areas, "GET", params);
			
			// Check your log cat for JSON reponse
			Log.d("All Areas Loaded ","All Areas Done Loading");

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("Sucess","Sucess=1");
					// Areas found
					// Getting Array of Areas
					ArrayOfAreas = json.getJSONArray("areas");

					// looping through All Products
					for (int i = 0; i < ArrayOfAreas.length(); i++) {
						JSONObject c = ArrayOfAreas.getJSONObject(i);

						// Storing each json item in variable
						String area = c.getString(TAG_AREA);

						AddInAreas(area);
					}
				} 
				success = json2.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Sucess","Sucess=1");
					// Areas found
					// Getting Array of Areas
					ArrayOfAgents = json2.getJSONArray("agents");

					// looping through All Products
					for (int i = 0; i < ArrayOfAgents.length(); i++) {
						JSONObject c = ArrayOfAgents.getJSONObject(i);
						Log.d("Agents",c.toString());
						// Storing each json item in variable
						String id = c.getString("id");
						String name = c.getString("name");
						String rating = c.getString("rating");
						String user = c.getString("user");

						AddInAgent(id,name,rating,user);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		public void AddInAreas(String s){
			if(Areas.contains(s)==false){
				Areas.add(s);
				Log.d("Areas","Adding Extra Areas");
			}
		}
		public void AddInAgent(String id,String name,String rating,String user){
			HashMap<String,String> Agents = new HashMap<String,String>();
			Agents.put("id", id);
			Agents.put("name", name);
			Agents.put("user",user);
			Agents.put("rating", "Ratings : "+rating);
			Agent.add(Agents);
			
		}
		
		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			
					/**
					 * Updating parsed JSON data into ListView
					 * */
					Log.d("Updating","Updating Spinner2");
					
					// Specify the layout to use when the list of choices appears
					adapter.notifyDataSetChanged();
					Log.d("Updating","Updating Spinner2");
			spinner.setAdapter(adapter);
			Log.d("Updating","Updating Spinner2");
			
			//ADAPTER SETTINGS FOR TOP 5 AGENTS
			//
			//
			ListAdapter adapter = new SimpleAdapter(
					GetAreas.this, Agent,
					R.layout.image_list, new String[] { "id",
							"name","user","rating"},
					new int[] { R.id.pid, R.id.name,R.id.Description,R.id.rating});
			// updating listview
			setListAdapter(adapter);
				
			

		}

	}
}
