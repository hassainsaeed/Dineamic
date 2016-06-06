package bookingTable;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.hmkcode.android.sign.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import globalVariables.GlobalVariable;

public class AddToWaitList extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_add_to_wait_list);

		TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
	    String mPhoneNumber = tMgr.getLine1Number();
	    
	    EditText editText = (EditText) findViewById(R.id.editPhoneNum);
	    editText.setText(mPhoneNumber, EditText.BufferType.EDITABLE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() { }
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.booking_table_add_to_wait_list, container,false);
			return rootView;
		}
	
	}
	

	//This function is to convert the InputStream from the HTMLURLConnection into a string
	private String readStream(InputStream is) {
		    try {
		      ByteArrayOutputStream bo = new ByteArrayOutputStream();
		      int i = is.read();
		      while(i != -1) {
		        bo.write(i);
		        i = is.read();
		      }
		      return bo.toString();
		    } catch (IOException e) {
		      return "";
		    }
	}
	
	public void addToWaitList (View view) {
		new addCustomerToWaitListDB().execute();
	}
	
	
	private class addCustomerToWaitListDB extends AsyncTask <Void,Void, Void> {
		URL url = null;
		String output = null;
		EditText editTextName = (EditText) findViewById(R.id.pleaseEnterText);
		String customerName = editTextName.getText().toString();
		EditText editTextNumber = (EditText) findViewById(R.id.editPhoneNum);
		String customerNumber = editTextNumber.getText().toString();
		String customerEmail = customerName.replace(" ", "_") + "@gmail.com";
		final GlobalVariable globalNumSeats = (GlobalVariable)getApplicationContext(); 
		final int numSeats = globalNumSeats.getNumSeats();
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				url = new URL("http://52.11.144.56/addUserAndPhoneNumberToWaitList.php?customerEmail=" + customerEmail + "&customerName=" + URLEncoder.encode(customerName) + "&customerNumber=" + URLEncoder.encode(customerNumber) + "&numSeats=" + numSeats);
			} catch (MalformedURLException e) {
				// Catch if the URL is incorrect for whatever reason (this should technically never happen)
				e.printStackTrace();
			}
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				output = readStream(in);
			} catch (IOException e) {
				// Catch if theres an IO exception with reading the website data
				e.printStackTrace();
			}
			return null;
		}	
		
		@Override
		protected void onPostExecute(Void result) {
			if (output.equals("New record created successfully.")) {
				Intent intent = new Intent(AddToWaitList.this, HaveBeenAddedToWaitList.class);
				startActivity(intent);
			} else {
				new AlertDialog.Builder(AddToWaitList.this)
					.setTitle("Something Went Wrong")
					.setMessage("Sorry, but for some reason we could not add you to the Waitlist. Please try adding yourself to the waitlist again. If the error persists, please contact Eatomatic's admins at:... \n\n Error: " + output)
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
									finish();
							}
				}).show();
			}
			super.onPostExecute(result);
		}
	}
	
	
	
	
}
