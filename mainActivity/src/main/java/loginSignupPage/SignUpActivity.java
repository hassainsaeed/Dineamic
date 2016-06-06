




package loginSignupPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.view.Menu;

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

public class SignUpActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();	//JSONParser jsonParser = new JSONParser();
	EditText username;
	EditText pass;
	

	// url to create new product
	private static String url_signUpUsers = "http://52.11.144.56/signUpUsers.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	public static int success=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.start_page_sign_up_screen);

		// Edit Text
		username = (EditText) findViewById(R.id.etUserName);
		pass = (EditText) findViewById(R.id.etPass);
	

		// Create button
		Button btnSignUp = (Button) findViewById(R.id.btnSignUp);

		// button click event
		btnSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new CreateNewUser().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SignUpActivity.this);
			pDialog.setMessage("Creating New User");
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
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Username", userName));
			params.add(new BasicNameValuePair("Password", userPassword));
			if (!Utilities.CheckInternetConnection(SignUpActivity.this)){
				success=2;
				return "";
			}

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_signUpUsers,
					"GET", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				 success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					//Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
					//startActivity(i);
					
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
			if (success==2){
				new AlertDialog.Builder(SignUpActivity.this)
						.setTitle("No Internet Connection")
						.setMessage("Please check your internet connection and try again!" )
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						}).show();}
			else{new AlertDialog.Builder(SignUpActivity.this)

					.setMessage("Sign up successful!")
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// continue with delete
							dialog.dismiss();
						}
					})

					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();
		}}

	}
}
