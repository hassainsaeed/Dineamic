package loginSignupPage;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import globalVariables.GlobalVariable;
import homePage.HomePageMainActivity;
import loginSignupPage.LogInActivity.LogInUser;

public class LogInMenuActivity extends Activity implements OnClickListener{
	Button btnDefaultSignIn;
	//Custom button
	private ProgressDialog pDialog;
	 String name=null;
	 String id=null;
	JSONParser jsonParser = new JSONParser();	//JSONParser jsonParser = new JSONParser();
	
	// url to create new product
		private static String url_signUpUsers = "http://52.11.144.56/signUpUsers.php";

		// JSON Node names
		private static final String TAG_SUCCESS = "success";
	//Creating Facebook CallbackManager Value
	public static CallbackManager callbackmanager;
	private Button fbbutton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // Initialize SDK before setContentView(Layout ID)
		 FacebookSdk.sdkInitialize(getApplicationContext());
		  if(name!=null && id!=null){
          	Intent i=new Intent(LogInMenuActivity.this,HomePageMainActivity.class);
				
				startActivity(i);
				return;
				}
		setContentView(R.layout.start_page_log_in_menu);

		
		 btnDefaultSignIn = (Button) findViewById(R.id.btnDefaultSignIn);
	        
	        
	        btnDefaultSignIn.setOnClickListener(this);
	     // Initialize layout button
	        fbbutton = (Button) findViewById(R.id.login_button);
	       
	        fbbutton.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                // Call private method
	                onFblogin();
					/*while(name==null || id==null){

					}
					new LogInUser().execute();*/

	               
	            }
	        });
	     
	    }
	

		@Override
		public void onClick(View v) {
			Intent i = null;
			switch(v.getId()){
				case R.id.btnDefaultSignIn:
					i = new Intent(this,LogInActivity.class);
					break;
				
			}
			startActivity(i);
		
	}

		//Private method to handle Facebook login and callback
		private void onFblogin()
		{
		 callbackmanager = CallbackManager.Factory.create();

		 // Set permissions 
		 LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","user_photos","public_profile"));

		 LoginManager.getInstance().registerCallback(callbackmanager,
		         new FacebookCallback<LoginResult>() {
		             @Override
		             public void onSuccess(LoginResult loginResult) {

		                 System.out.println("Success");
		                 GraphRequest.newMeRequest(
		                         loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
		                             @Override
		                             public void onCompleted(JSONObject json, GraphResponse response) {
		                                 if (response.getError() != null) {
		                                     // handle error
		                                     System.out.println("ERROR");
		                                 } else {
		                                     System.out.println("Success");
		                                    
		                                     try {
		                                    	
		                                         String jsonresult = String.valueOf(json);
		                                         System.out.println("JSON Result"+jsonresult);

		                                         name = json.getString("name");
		                                         id = json.getString("id");
		                                         String str_firstname = json.getString("first_name");
		                                         String pass = json.getString("last_name");


		                                     } catch (JSONException e) {
		                                         e.printStackTrace();
		                                     }
											 new LogInUser().execute(name,id);
		                                 }
		                             }

		                         }).executeAsync();

		             }

		             @Override
		             public void onCancel() {
		                 Log.d("Facebook","On cancel");
		             }

		             @Override
		             public void onError(FacebookException error) { 
		                 Log.d("Facebook",error.toString());
		             }
		 });
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);

		 callbackmanager.onActivityResult(requestCode, resultCode, data);
		}
		
		class LogInUser extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				pDialog = new ProgressDialog(LogInMenuActivity.this);
				pDialog.setMessage("Logging In");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * Creating product
			 * */
			protected String doInBackground(String... args) {

				String userName = args[0];
				
				String userPassword = args[1];
				//System.out.println("whats "+name + " "+id);
				userName=userName.toLowerCase().replaceAll("\\s+",""); 
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Username", userName));
				params.add(new BasicNameValuePair("Password", userPassword));
				

				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_signUpUsers,
						"GET", params);
				
				// check log cat from response
				Log.d("Create Response", json.toString());
				// closing this screen
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


				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						// successfully created product
						//Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
						//startActivity(i);
						Log.d("Success","");


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
				Intent i=new Intent(LogInMenuActivity.this,HomePageMainActivity.class);

				startActivity(i);
			}

		}


}




