package com.example.easyproperty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}
	
	private boolean isNetworkAvailable(){
		ConnectivityManager connMgr = (ConnectivityManager)this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if(networkInfo!=null && networkInfo.isConnected()){
			return true;
		}
		return false;
	}
	public void OpenAppButton(View v){
		if(isNetworkAvailable()){
		Intent e = new Intent(this.getApplicationContext(),GetAreas.class);
		startActivity(e);
		}
		else{
			Toast.makeText(this.getApplicationContext(), "This Application Requires Internet Connectivity. Please connect to the Internet and Try Again", Toast.LENGTH_LONG).show();
		}
	}
	public void LogButton(View v){
		if(isNetworkAvailable()){
		Intent e = new Intent(this.getApplicationContext(),LoginAgent.class);
		startActivity(e);
		}
		else{
			Toast.makeText(this.getApplicationContext(), "This Application Requires Internet Connectivity. Please connect to the Internet and Try Again", Toast.LENGTH_LONG).show();
		}
	}
}
