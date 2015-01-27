package com.example.easyproperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class VAprop extends ListActivity {
	// Progress Dialog
		private ProgressDialog pDialog;

		// Creating JSON Parser object
		JSONParser jParser = new JSONParser();

		ArrayList<HashMap<String, String>> productsList;

		// url to get all products list
		private static String url_all_products = "http://easyproperty.tk.hostinghood.com/review-fetch.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_PRODUCTS = "reviews";
	
        private static String account_ID="";
		// products JSONArray
		JSONArray products = null;

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
     Intent e= getIntent();
    account_ID= e.getStringExtra("ID").toString();
     
			super.onCreate(savedInstanceState);
			setContentView(R.layout.all_reviews);
			  Log.d("tag", "tag");
			// Hashmap for ListView
			productsList = new ArrayList<HashMap<String, String>>();

			// Loading products in Background Thread
			new LoadAllProducts().execute();

			// Get listview
			ListView lv = getListView();
  Log.d("tag", "tag");
			// on seleting single product
			// launching Edit Product Screen
		

		}

		// Response from Edit Product Activity
		

		/**
		 * Background Async Task to Load all product by making HTTP Request
		 * */
		class LoadAllProducts extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(VAprop.this);
				pDialog.setMessage("Loading reviews. Please wait...");
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
				params.add(new BasicNameValuePair("id", account_ID));
				// getting JSON string from URL
				JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
				
				// Check your log cat for JSON reponse
				Log.d("All Products: ", json.toString());

				try {
					// Checking for SUCCESS TAG
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// products found
						// Getting Array of Products
						products = json.getJSONArray(TAG_PRODUCTS);

						// looping through All Products
						for (int i = 0; i < products.length(); i++) {
							JSONObject c = products.getJSONObject(i);

							// Storing each json item in variable
							String textt = c.getString("text");
							String ratingg = c.getString("rating");
							

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value

							map.put("text", textt);
							map.put("rating",ratingg);
							
							// adding HashList to ArrayList
							productsList.add(map);
						}
					} else {
						
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
				// dismiss the dialog after getting all products
				pDialog.dismiss();
				// updating UI from Background Thread
				runOnUiThread(new Runnable() {
					public void run() {
						/**
						 * Updating parsed JSON data into ListView
						 * */
						ListAdapter adapter = new SimpleAdapter(
								VAprop.this, productsList,
								R.layout.list_itemsss, new String[] { "text",
										"rating"},
								new int[] { R.id.Description, R.id.name });
						// updating listview
						setListAdapter(adapter);
					}
				});

			}

		}}
