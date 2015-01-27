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
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UAAcc extends Activity {
	public String Agent_ID;
	public String Agent_Name;
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	EditText add;
	EditText name;
	EditText numb;
	EditText txtCreatedAt;
	Button btnSave;
	Button btnDelete;

	String id="15";

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_product_detials = "http://easyproperty.tk.hostinghood.com/agent.php";

	// url to update product
	private static final String url_update_product = "http://easyproperty.tk.hostinghood.com/agent-edit.php";
	
	// url to delete product
	//private static final String url_delete_product = "http://easyproperty.tk.hostinghood.com/delete_product.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
//	private static final String TAG_PRODUCT = "product";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_add = "add";
	private static final String TAG_numb = "numb";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent e = getIntent();
		Agent_ID=e.getStringExtra(EXTRA_ID);
		Agent_Name=e.getStringExtra(EXTRA_NAME);
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
		setContentView(R.layout.activity_uaacc);

		// save button
		btnSave = (Button) findViewById(R.id.Button01);
		//btnDelete = (Button) findViewById(R.id.btnDelete);

		// getting product details from intent
		//Intent i = getIntent();
		
		// getting product id (pid) from intent
		//id = i.getStringExtra(TAG_ID);

		// Getting complete product details in background thread
		new GetProductDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update product
				new SaveProductDetails().execute();
			}
		});

	
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UAAcc.this);
			pDialog.setMessage("Loading agent details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
			Log.d("diag", "agetns");
		}

		/**
		 * Getting product details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("id", Agent_ID));

						// getting product details by making HTTP request
						// Note that product details url will use GET request
						JSONObject json = jsonParser.makeHttpRequest(
								url_product_detials, "GET", params);

						// check your log for json response
						Log.d("Single Product Details", json.toString());
						
						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received product details
							
							
							// get first product object from JSON Array
							JSONObject product = json.getJSONObject("agent");

							// product with this pid found
							// Edit Text
							add = (EditText) findViewById(R.id.editText10);
							name = (EditText) findViewById(R.id.editText11);
							numb = (EditText) findViewById(R.id.editText12);

							// display product data in EditText
							add.setText(product.getString(TAG_add));
							name.setText(product.getString(TAG_NAME));
							numb.setText(product.getString(TAG_numb));

						}else{
							// product with pid not found
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});

			return null;
		}


		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to  Save product Details
	 * */
	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UAAcc.this);
			pDialog.setMessage("Updating Info ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String addd = add.getText().toString();
			String namee = name.getText().toString();
			String numbb = numb.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_ID, Agent_ID));
			params.add(new BasicNameValuePair(TAG_add, addd));
			params.add(new BasicNameValuePair(TAG_NAME, namee));
			
			params.add(new BasicNameValuePair(TAG_numb, numbb));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_product,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about product update
					setResult(100, i);
					finish();
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
			pDialog.dismiss();
		}
	}

	
	
}

