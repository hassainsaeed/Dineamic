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
import com.journeyapps.barcodescanner.CaptureActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import globalVariables.GlobalVariable;
import qrScanner.scanQRCode;

public class AddToWaitList extends Activity {
	static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 111;
	private TelephonyManager tMgr;
	private String mPhoneNumber;
	private String restaurantName;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_add_to_wait_list);
		ActionBar bar = getActionBar();
		//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
		Intent intent = getIntent();
		restaurantName = GlobalVariable.getRestaurantName();

		checkIfPermissionGranted();
	}

	public void checkIfPermissionGranted () {
		int permissonCheck = ContextCompat.checkSelfPermission(AddToWaitList.this, Manifest.permission.READ_PHONE_STATE);
		if (permissonCheck != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(AddToWaitList.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
		} else {
			tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
			mPhoneNumber = tMgr.getLine1Number();
			EditText editText = (EditText) findViewById(R.id.editPhoneNum);
			editText.setText(mPhoneNumber, EditText.BufferType.EDITABLE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					// permission was granted, yay!
					tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
					mPhoneNumber = tMgr.getLine1Number();
					EditText editText = (EditText) findViewById(R.id.editPhoneNum);
					editText.setText(mPhoneNumber, EditText.BufferType.EDITABLE);
				} else {

					// permission denied, boo!
					new AlertDialog.Builder(AddToWaitList.this)
							.setTitle("Error: Cannot be Added to the Waitlist")
							.setMessage("Sorry, but you cannot be added on the waitlist unless you grant the Dine-Amic application permission to read your phone state")
							.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									Intent intent = new Intent(getApplicationContext(), BookingTableMainActivity.class);
									startActivity(intent);
								}
							}).show();
				}
				return;
			}
			// other 'case' lines to check for other
			// permissions this app might request
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.action_bar_qr_button, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		//handle presses on the action bar items
		switch (item.getItemId()) {

			case R.id.qr_scanner:
				Intent intent = new Intent(AddToWaitList.this, CaptureActivity.class);
				intent.setAction("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
				return true;

		}
		return super.onOptionsItemSelected(item);
	}
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		new scanQRCode(requestCode,resultCode,intent,AddToWaitList.this).execute();
	}
	//End Code for the QR Scanner in the Action Bar

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
				url = new URL("http://52.11.144.56/php/addUserAndPhoneNumberToWaitList.php?customerEmail=" + customerEmail + "&customerName=" + URLEncoder.encode(customerName) + "&customerNumber=" + URLEncoder.encode(customerNumber) + "&numSeats=" + numSeats + "&restaurant_name=" + restaurantName);
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
						.setMessage("Sorry, but for some reason we could not add you to the Waitlist. Please try adding yourself to the waitlist again. If the error persists, please contact Dineamic's admins at:... \n\n Error: " + output)
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
