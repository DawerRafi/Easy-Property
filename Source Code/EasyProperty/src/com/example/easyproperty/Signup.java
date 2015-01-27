package com.example.easyproperty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Signup extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText username;
	EditText email;
	EditText password;
	EditText confpass;
	EditText name;
	EditText number;
	EditText add;
	public String bath;
	//public String rent;
	public String type;
	public String bed;
	// url to create new product
	private static String url_create_product = "http://easyproperty.tk.hostinghood.com/agent-insert.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		// Edit Text
		username = (EditText) findViewById(R.id.NameEdit);
		email = (EditText) findViewById(R.id.AdressEdit);
		password = (EditText) findViewById(R.id.editText3);
		confpass = (EditText) findViewById(R.id.postal);
		name = (EditText) findViewById(R.id.editText1);
		number = (EditText) findViewById(R.id.editText2);
		add = (EditText) findViewById(R.id.editText4);
		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.Button01);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
	}

	
	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Signup.this);
			pDialog.setMessage("Creating Product..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String usernamee = username.getText().toString();
			String emaill = email.getText().toString();
			String passwordd = password.getText().toString();
			String confpasss = confpass.getText().toString();
			String namee = name.getText().toString();
			String numb = number.getText().toString();
		    String addd= add.getText().toString();
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", namee));
			params.add(new BasicNameValuePair("user", usernamee));
			params.add(new BasicNameValuePair("pass1", passwordd));
			params.add(new BasicNameValuePair("pass2", confpasss));
			params.add(new BasicNameValuePair("email", emaill));
			params.add(new BasicNameValuePair("add", addd));
			params.add(new BasicNameValuePair("numb", numb));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);

			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(),
							Signup.class);
					startActivity(i);

					// closing this screen
					finish();
				} else {
					// failed to create product
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
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}