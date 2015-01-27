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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Review extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText review;
	
	
	public String bath;
	//public String rent;
	public String type;
	public String bed;
	// url to create new product
	private static String url_create_product = "http://easyproperty.tk.hostinghood.com/review-insert.php";
	public String Agent_ID;
	public String Agent_Name;
	private static final String EXTRA_ID="ID";
	private static final String EXTRA_NAME="NAME";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	RatingBar rate;
	 private TextView ratingValue;
	 private Button button;
	 String rating;
	 String Review;
	 private EditText ReviewView;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent e = getIntent();
		Agent_ID = e.getStringExtra(EXTRA_ID).toString();
		Agent_Name = e.getStringExtra(EXTRA_NAME);
		setContentView(R.layout.review_write);
		ReviewView = (EditText) findViewById(R.id.editText30);
		
		 addListenerOnRatingBar();
		 addListenerOnButton();

	}
	public void addListenerOnRatingBar() {
	    rate = (RatingBar) findViewById(R.id.ratingBar1);
		ratingValue = (TextView) findViewById(R.id.textView3);
	    rate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
	        public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
	            ratingValue.setText(String.valueOf(rating));
	            
	            
	}
	    });
	  }
	public void addListenerOnButton() {
	    rate = (RatingBar) findViewById(R.id.ratingBar1);
	    button = (Button) findViewById(R.id.Button30);
	    button.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	 rating=Float.toString(rate.getRating());
	        	 Review = ReviewView.getText().toString();
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
			pDialog = new ProgressDialog(Review.this);
			pDialog.setMessage("Creating Product..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			//String revieww = review.getText().toString();
		
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("id", Agent_ID));
			params.add(new BasicNameValuePair("text", Review));
			params.add(new BasicNameValuePair("rating", rating));
			
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
					//Toast.makeText(getApplicationContext(), "Review Added", Toast.LENGTH_LONG).show();
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
