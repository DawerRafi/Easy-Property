package com.example.easyproperty;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewProductActivity extends Activity {

	public String Agent_ID;
	public String Agent_Name;
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputName;
	EditText inputPrice;
	EditText inputDesc;
	EditText inputsize;
	EditText inputaddress;
	RadioGroup rg;
	RadioButton b1;
	RadioButton b2;
	RadioButton b3;
	RadioButton b4;
	RadioButton b5;
	RadioButton b6;
	RadioButton b7;
	RadioButton b8;
	RadioButton b9;
	RadioButton b10;
	public String bath;
	//public String rent;
	public String type;
	public String bed;
	// url to create new product
	private static String url_create_product = "http://easyproperty.tk.hostinghood.com/property-insert.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	public static Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_product);
		Intent e = getIntent();
		Agent_ID=e.getStringExtra(EXTRA_ID);
		Agent_Name=e.getStringExtra(EXTRA_NAME);
		Log.d("CHECKING ID", Agent_ID);
		context = this.getApplicationContext();
		// Edit Text
		inputName = (EditText) findViewById(R.id.widget54);
		inputPrice = (EditText) findViewById(R.id.EditText01);
		inputDesc = (EditText) findViewById(R.id.EditText02);
		inputsize = (EditText) findViewById(R.id.widget57);
		inputaddress = (EditText) findViewById(R.id.widget55);

		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.button1);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
	}

	public void onRadioButtonClicked1(View v) {
		bed = "1";
	}

	public void onRadioButtonClicked2(View v) {
		bed = "2";
	}

	public void onRadioButtonClicked3(View v) {
		bed = "3";
	}

	public void onRadioButtonClicked4(View v) {
		bed = "4";
	}

	public void onRadioButtonClicked5(View v) {
		bed = "5";
	}
	public void onRadioButtonClicked6(View v) {
		bath = "1";
	}

	public void onRadioButtonClicked7(View v) {
		bath = "2";
	}

	public void onRadioButtonClicked8(View v) {
		bath = "3";
	}

	public void onRadioButtonClicked9(View v) {
		bath = "4";
	}

	public void onRadioButtonClicked10(View v) {
		bath = "5";
	}
	public void Rent(View v ){
		type="1";
	}

	public void Sale(View v ){
		type="0";
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
			pDialog = new ProgressDialog(NewProductActivity.this);
			pDialog.setMessage("Creating Product..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String name = inputName.getText().toString();
			String price = inputPrice.getText().toString();
			String description = inputDesc.getText().toString();
			String size = inputsize.getText().toString();
			String address = inputaddress.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", Agent_ID));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("beds", bed));
			params.add(new BasicNameValuePair("baths", bath));
			params.add(new BasicNameValuePair("desc", description));
			params.add(new BasicNameValuePair("type", type));
			params.add(new BasicNameValuePair("price", price));
			params.add(new BasicNameValuePair("area", address));
			params.add(new BasicNameValuePair("size", size));

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
							WelcomeAgent.class);
					i.putExtra(EXTRA_ID,Agent_ID);
					i.putExtra(EXTRA_NAME,Agent_Name);
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
			Toast toast = Toast.makeText(context, "Your Property has been added Successfully!",Toast.LENGTH_LONG);
			toast.show();
		}

	}
}