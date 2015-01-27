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
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Activity {
	public String Agent_ID;
	public String Agent_Name;
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	
	EditText passs;
	EditText passs1;
	EditText passs2;
	// EditText txtCreatedAt;
	Button btnSave;
	Button btnback;

	String id="12";
	String toazt;

	// Progress Dialog
	private ProgressDialog pDialog;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// url to update product
	private static final String url_update_product = "http://easyproperty.tk.hostinghood.com/agent-password.php";
	// url to delete product
	// private static final String url_delete_product =
	// "http://easyproperty.tk.hostinghood.com/delete_product.php";
      
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	//private static final String TAG_PRODUCT = "product";
	private static final String TAG_id = "id";
	private static final String TAG_oldp = "pass";
	private static final String TAG_newp = "pass1";
	private static final String TAG_confnp = "pass2";
	public void FireIntent(){
		Intent e = new Intent(this.getApplicationContext(),ChangePassword.class);
		startActivity(e);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent e = getIntent();
		Agent_ID = e.getStringExtra(EXTRA_ID).toString();
		Agent_Name = e.getStringExtra(EXTRA_NAME);
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
		setContentView(R.layout.change_password);

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);
       passs = (EditText) findViewById(R.id.inputName);
       passs1 = (EditText) findViewById(R.id.inputPrice);
       passs2 = (EditText) findViewById(R.id.inputDesc);
       
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

	class SaveProductDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ChangePassword.this);
			pDialog.setMessage("Changing Password ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving product
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String pass = passs.getText().toString();
			String pass1 = passs1.getText().toString();
			String pass2 = passs2.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_id, Agent_ID));
			params.add(new BasicNameValuePair(TAG_oldp, pass));
			params.add(new BasicNameValuePair(TAG_newp, pass1));
			params.add(new BasicNameValuePair(TAG_confnp, pass2));

			// sending modified data through http request
			// Notice that update product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_update_product,
					"POST", params);

			// check json success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				toazt = json.getString("message");
				if (success == 1) {
					// successfully updated
					Intent i= getIntent();
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
			//FireIntent();
			Toast.makeText(getApplicationContext(), toazt, Toast.LENGTH_SHORT).show();
		}
	}
}
