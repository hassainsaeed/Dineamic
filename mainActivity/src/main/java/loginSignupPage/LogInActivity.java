



package loginSignupPage;

import android.content.Context;
import android.os.Bundle;

import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import bookingTable.AddToWaitList;
import globalVariables.GlobalVariable;
import homePage.HomePageMainActivity;

public class LogInActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();	//JSONParser jsonParser = new JSONParser();
	EditText username;
	EditText pass;
	public static int success=0;
	

	// url to create new product
	private static String url_signInUsers = "http://52.11.144.56/signInUsers.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	public static  String customerName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	      
	        setContentView(R.layout.start_page_log_in_screen);



	       Button btnLogIn = (Button) findViewById(R.id.btnLogIn);
	        
	        
		

		// Edit Text
		username = (EditText) findViewById(R.id.etUsername);
		pass = (EditText) findViewById(R.id.etPass);
	

	

		// button click event
		btnLogIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new LogInUser().execute();
			}
		});
	}

	
	class LogInUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(LogInActivity.this);
			pDialog.setMessage("Logging In");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String userName = username.getText().toString();
			userName=userName.toLowerCase();

			String userPassword = pass.getText().toString();
			
			Log.e("OK ",userName+" "+userPassword);
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Username", userName));
			params.add(new BasicNameValuePair("Password", userPassword));
			

			// getting JSON Object
			// Note that create product url accepts POST method
			if (!Utilities.CheckInternetConnection(LogInActivity.this)){
				success=2;
				return "";
			}
			JSONObject json = jsonParser.makeHttpRequest(url_signInUsers,
					"GET", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt(TAG_SUCCESS);
				System.out.println("OK "+success);
				if (success == 1) {
					// successfully created product
					//Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
					//startActivity(i);
					Log.d("Success","");
					// closing this screen
					System.out.println("OK");
					//final GlobalVariable globalUserName = (GlobalVariable)getApplicationContext();
					GlobalVariable.setCustomerUserName(userName);
					String FILENAME = "authentication";
					String string = GlobalVariable.getCustomerUserName();

					FileOutputStream fos = null;
					try {
						fos = openFileOutput(FILENAME, MODE_PRIVATE);
						fos.write(string.getBytes());
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					//Log.e("OK ", 	GlobalVariable.getCustomerUserName());
					Intent i=new Intent(LogInActivity.this,HomePageMainActivity.class);
					
					startActivity(i);
					finish();
				
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
			// dismiss the dialog once done
			pDialog.dismiss();
			if (success==0){
			new AlertDialog.Builder(LogInActivity.this)
			.setTitle("Wrong Credentials")
			.setMessage("Please enter your credentials again. " )
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
							finish();
					}
		}).show();}
			if (success==2){
				new AlertDialog.Builder(LogInActivity.this)
						.setTitle("No Internet Connection")
						.setMessage("Please check your internet connection and try again!" )
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).show();}

		}

	}
}

    
