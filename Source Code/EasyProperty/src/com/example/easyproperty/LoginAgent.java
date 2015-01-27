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
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginAgent extends Activity {
	// Progress Dialog
	private ProgressDialog pDialog;
	public String user;
	public String pass ;
	public String name;
	public String email;
	public String address;
	public int number;
	public int rating;
	public int id;
	private boolean Auth=false;
	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
    public static Info inf;
	ArrayList<HashMap<String, String>> productsList;

	// url to get all products list
	private static String url_all_products = "http://easyproperty.tk.hostinghood.com/login.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_agent = "agent";
	private static final String TAG_user = "username";
	private static final String TAG_pass = "password";
//************************************************************************
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	// products JSONArray
	JSONArray products = null;
	public void FireIntent(){
		Intent e = new Intent(this.getApplicationContext(),WelcomeAgent.class);
		e.putExtra(EXTRA_ID,id+"");
		e.putExtra(EXTRA_NAME, name);
		startActivity(e);
	}
	public void SignButton(View v){
		Intent e = new Intent(this.getApplicationContext(),Signup.class);
		startActivity(e);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Hashmap for ListView
		productsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		
		}
	public void oc(View v){
		new LoadAllProducts().execute();
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
			pDialog = new ProgressDialog(LoginAgent.this);
			pDialog.setMessage("Checking. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		EditText t;
		EditText tt;
		
		@Override
		
		
		protected String doInBackground(String... args) {
			// Building Parameters
			t=(EditText) findViewById(R.id.email);
			tt=(EditText) findViewById(R.id.password);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("username", t.getText().toString()));
		        params.add(new BasicNameValuePair("password", tt.getText().toString()));
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "POST", params);
			
			// Check your log cat for JSON reponse
			//Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS 
				int success = json.getInt(TAG_SUCCESS);
                
				if (success == 1) {
					Auth=true;
					// products found
					// Getting Array of Products
					Log.d("DEBUG","Before");
					JSONObject c = json.getJSONObject(TAG_agent);
					Log.d("DEBUG","After");
					// looping through All Products
			
					//	JSONObject c = products.getJSONObject(0);

						// Storing each json item in variable
				 user = c.getString("user");
				email = c.getString("email");
				address=c.getString("add");
				rating= c.getInt("rating");
				number =c.getInt("numb");
				name =c.getString("name");
				id =c.getInt("id");
				inf = new Info(id,rating,address,name, email , number,user);
				
			
				
				

					
						
					}
				 else {
					// no products found
					 
					 
						}	}
			 catch (JSONException e) {
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
			if(Auth){
			FireIntent();
			}
			else{
				Toast.makeText(getApplicationContext(), "Could Not Authenticate Your Username Or Password", Toast.LENGTH_LONG).show();
			}
		}

	}}
