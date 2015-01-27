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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

public class Edit_property extends Activity {
String property_ID;
EditText NameView;
EditText AreaView;
EditText DescriptionView;
EditText PriceView;
EditText SizeView;
CheckBox TypeView;
CheckBox TypeView2;

String Name;
String Area;
String Description;
String Type;
String Price;
String Size;
String Bed;
String Bath;
int Beds;
int Baths;

RadioButton b1;
RadioButton b2;
RadioButton b3;
RadioButton b4;
RadioButton b5;
RadioButton c1;
RadioButton c2;
RadioButton c3;
RadioButton c4;
RadioButton c5;



private ProgressDialog pDialog;
public static String TAG_ID="Product_ID";
private String givenID;
JSONParser jParser = new JSONParser();
private static final String url_Get_Results = "http://easyproperty.tk.hostinghood.com/property.php";

private static final String url_update_product = "http://easyproperty.tk.hostinghood.com/property-edit.php";
private static final String TAG_SUCCESS="success";
JSONArray Properties = null;
ArrayList<HashMap<String, String>> PropertyList;

		
		/** Called when the activity is first created. */
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_product);
		
			NameView= (EditText) findViewById(R.id.inputName);
			DescriptionView= (EditText) findViewById(R.id.description);
			SizeView= (EditText) findViewById(R.id.size);
			PriceView= (EditText) findViewById(R.id.inputPrice);
			AreaView= (EditText) findViewById(R.id.area);
			TypeView=(CheckBox) findViewById(R.id.checkBox1);
			TypeView2=(CheckBox) findViewById(R.id.checkBox2);
			
			b1= (RadioButton) findViewById(R.id.RadioButton06);
			b2= (RadioButton) findViewById(R.id.RadioButton05);
			b3= (RadioButton) findViewById(R.id.RadioButton04);
			b4= (RadioButton) findViewById(R.id.RadioButton02);
			b5= (RadioButton) findViewById(R.id.RadioButton03);
			
			c1= (RadioButton) findViewById(R.id.RadioButton10);
			c2= (RadioButton) findViewById(R.id.RadioButton11);
			c3= (RadioButton) findViewById(R.id.RadioButton12);
			c4= (RadioButton) findViewById(R.id.RadioButton13);
			c5= (RadioButton) findViewById(R.id.RadioButton14);
			
		    // TODO Auto-generated method stub
	Intent i = getIntent();
			PropertyList = new ArrayList<HashMap<String,String>>();
			// getting product id (pid) from intent
			givenID = i.getStringExtra(TAG_ID);
			new GetData().execute();
		    
		
	}
		public void btnSave(View v){
			new SaveProductDetails().execute();
		}
		public void onRadioButtonClicked1(View v) {
			Bed = "1";
		}

		public void onRadioButtonClicked2(View v) {
			Bed = "2";
		}

		public void onRadioButtonClicked3(View v) {
			Bed = "3";
		}

		public void onRadioButtonClicked4(View v) {
			Bed = "4";
		}

		public void onRadioButtonClicked5(View v) {
			Bed = "5";
		}
		public void onRadioButtonClicked6(View v) {
			Bath = "1";
		}

		public void onRadioButtonClicked7(View v) {
			Bath = "2";
		}

		public void onRadioButtonClicked8(View v) {
			Bath = "3";
		}

		public void onRadioButtonClicked9(View v) {
			Bath = "4";
		}

		public void onRadioButtonClicked10(View v) {
			Bath = "5";
		}
		public void Rent(View v ){
			Type="1";
		}

		public void Sale(View v ){
			Type="0";
		}



		class GetData extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(Edit_property.this);
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
				//Log.d("Given",Prop_ID);
			
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", givenID));
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
							Beds = Integer.parseInt(c.getString("bed"));
							Name = c.getString("name");
							Description = c.getString("dscrpt");
							Baths = Integer.parseInt(c.getString("bath"));
							Type = c.getString("type");
							Area = c.getString("area");
							Size = c.getString("size");
							Price = c.getString("price");
							
							String Agent_ID = c2.getString("id");
							Log.d("TESTING ID",Agent_ID);
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
				DescriptionView.setText(Description);
				PriceView.setText(Price);
				NameView.setText(Name);
				//Description.setText( map.get(2).toString());
				AreaView.setText(Area);
				//Type.setText(Type.getText()+ " : "+map.get(6).toString());
				SizeView.setText(Size);
				if(Type=="1"){
					TypeView.setChecked(true);
				}else{
					TypeView2.setChecked(true);
				}
				
				 switch (Beds) {
		            case 1:  b1.setChecked(true);
		            		Bed="1";
		                     break;
		            case 2:  b2.setChecked(true);
		            Bed="2";
                    break;
		            case 3:  b3.setChecked(true);
		            Bed="3";
                    break;
		            case 4:  b4.setChecked(true);
		            Bed="4";
		            break;
		            case 5:  b5.setChecked(true);
		            Bed="5";
		            break;
				 }
				 
				 switch (Baths) {
		            case 1:  c1.setChecked(true);
		            		Bath="1";
		                     break;
		            case 2:  c2.setChecked(true);
		            Bath="2";
                 break;
		            case 3:  c3.setChecked(true);
		            Bath="3";
                 break;
		            case 4:  c4.setChecked(true);
		            Bath="4";
                 break;
		            case 5:  c5.setChecked(true);
		            Bath="5";
                 break;
				 }
				
				 Log.d("End","End Proc");
			}}
		
		
		
		//**************************************************************************************
		//**************************************************************************************
		
		class SaveProductDetails extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(Edit_property.this);
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
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", givenID));
				params.add(new BasicNameValuePair("name", NameView.getText().toString()));
				params.add(new BasicNameValuePair("beds", Bed));
				
				params.add(new BasicNameValuePair("baths",Bath));
				params.add(new BasicNameValuePair("desc", DescriptionView.getText().toString()));
				params.add(new BasicNameValuePair("type", Type));
				params.add(new BasicNameValuePair("price", PriceView.getText().toString()));
				params.add(new BasicNameValuePair("area", AreaView.getText().toString()));
				params.add(new BasicNameValuePair("size", SizeView.getText().toString()));
				

				// sending modified data through http request
				// Notice that update product url accepts POST method
				JSONObject json = jParser.makeHttpRequest(url_update_product,
						"POST", params);

				// check json success tag
				try {
					int success = json.getInt(TAG_SUCCESS);
					
					if (success == 1) {
						// successfully updated
						Log.d("Entry","Successful");
					} else {
						Log.d("Entry","Unsuccessful");
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


