package bookingTable;

import android.Manifest;
import android.app.ActionBar;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import globalVariables.GlobalVariable;
import qrScanner.scanQRCode;


public class BookingTableMainActivity extends FragmentActivity  {
	//public final static String EXTRA_MESSAGE = "com.example.bookatable.MESSAGE";
	private String restaurantName;
	int numSeats;
	static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_main_activity);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));

		Intent intent = getIntent();
		this.restaurantName = intent.getStringExtra("Restaurant Name");

	}

	//Code for the QR Scanner in the Action Bar
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
				Intent intent = new Intent(BookingTableMainActivity.this, CaptureActivity.class);
				intent.setAction("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		new scanQRCode(requestCode,resultCode,intent,BookingTableMainActivity.this).execute();
	}
	//End Code for the QR Scanner in the Action Bar


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

	//Button LIstener for booking seats - when User enters how many seats they want, check DB
	//(via HttpURLCOnnection) to see if theres available tables. Depending on if there are,
	public void buttonClick2(View view) {
		findTableForCustomer(2);
	}
	public void buttonClick3(View view) {
		findTableForCustomer(3);
	}
	public void buttonClick4(View view) {
		findTableForCustomer(4);
	}
	public void buttonClick5(View view) {
		findTableForCustomer(5);
	}
	public void buttonClick8(View view) {
		findTableForCustomer(8);
	}
	public void buttonClickOther(View view) {
		EditText edittext = (EditText) findViewById(R.id.editText2);
		int otherSeats = Integer.parseInt(edittext.getText().toString());
		if ((otherSeats < 0) || (otherSeats > 10)) {
			new AlertDialog.Builder(this)
					.setTitle("Invalid Number of Seats")
					.setMessage("Sorry, but this restaurant only supports up to a maximum of 10 customers per table")
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// TODO Nothing
						}
					}).show();
		} else {
			findTableForCustomer(otherSeats);
		}
	}

	public void buttonClickCheckStatus(View view) {

		int permissonCheck = ContextCompat.checkSelfPermission(BookingTableMainActivity.this, Manifest.permission.READ_PHONE_STATE);
		if (permissonCheck != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(BookingTableMainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
		} else {
			new checkStatusOnWaitList().execute();
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
					new checkStatusOnWaitList().execute();

				} else {

					// permission denied, boo!
					new AlertDialog.Builder(BookingTableMainActivity.this)
							.setTitle("Error: Cannot Check the Status of Waitlist")
							.setMessage("Sorry, but you cannot view your status on the waitlist unless you grant the Dine-Amic application permission to read your phone state")
							.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									// TODO Nothing
								}
							}).show();
				}
				return;
			}
			// other 'case' lines to check for other
			// permissions this app might request
		}
	}


	public void findTableForCustomer(int numSeats1) {
		numSeats = numSeats1;
		new findTableSpaceFromDatabase().execute();
	}


	private class checkStatusOnWaitList extends AsyncTask<Void, Void, Void> {
		URL url = null;
		String output;
		TelephonyManager tMgr =(TelephonyManager)BookingTableMainActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
		String mPhoneNumber = tMgr.getLine1Number();

		protected Void doInBackground (Void... params) {
			try {
				url = new URL("http://52.11.144.56/getStatusOfWaitlist.php?phoneNumber=" + URLEncoder.encode(mPhoneNumber));
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

		protected void onPostExecute (Void result) {
			output = output.substring(1,output.length()-1);
			if (output.split("_")[0].equals("InLine")) {
				int timeStart = Integer.parseInt(output.split("_")[1])*10;
				int timeEnd = Integer.parseInt(output.split("_")[1])*10 + 20;
				new AlertDialog.Builder(BookingTableMainActivity.this)
						.setTitle("Position #" + output.split("_")[1] + " on the waitlst")
						.setMessage("You are currently in position #" + output.split("_")[1] + " of the waitlist, please wait " + timeStart
								+ " to " + timeEnd + " minutes for your table to be ready. Thank you fr your patience.")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// TODO Nothing
							}
						}).show();
			} else if (output.equals("Ready")) {
				new AlertDialog.Builder(BookingTableMainActivity.this)
						.setTitle("Your Table is Ready!")
						.setMessage("Your table is ready and currently awaiting your arrival. Please come to the restaurant within the next 10 minutes to save your table. Thank you.")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// TODO Nothing
							}
						}).show();
			} else {
				new AlertDialog.Builder(BookingTableMainActivity.this)
						.setTitle("Not on any Waitlists!")
						.setMessage("Sorry, but your status could not be retrieved because you are not on any restaurant's waitlists. If there is an error with this, please speak to the restaurant staff.")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// TODO Nothing
							}
						}).show();
			}
		}
	}

	private class findTableSpaceFromDatabase extends AsyncTask<Void, Void, Void>{
		//HTTP Post
		URL url = null;
		String output;

		@Override
		protected Void doInBackground(Void... params) {
			try {
				url = new URL("http://52.11.144.56/getAvailableTablesOfRestaurant.php?seatsrequested=" + numSeats + "&Restaurant_Name=" + restaurantName);
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
			//THIS IS FOR TEST PURPOSES ONLY - SHOULD REMOVE
			if (!output.equals("\"None\"")) {
				int table_num = Character.getNumericValue(output.charAt(15));
				String message = "Please enter the resturant and seat your self at Table #" + table_num + ". A waiter will be with you shortly.";
				Intent intent = new Intent(BookingTableMainActivity.this,YesAvailableTables.class);
				intent.putExtra("Restaurant Name", restaurantName);
				intent.putExtra("Message", message);
				startActivity(intent);
			} else {
				final GlobalVariable globalNumSeats = (GlobalVariable) getApplicationContext();
				globalNumSeats.setNumSeats(numSeats);
				Intent intent = new Intent(BookingTableMainActivity.this,NoAvailableTables.class);
				intent.putExtra("Restaurant Name", restaurantName);
				startActivity(intent);
			}


			super.onPostExecute(result);
		}
	}

}