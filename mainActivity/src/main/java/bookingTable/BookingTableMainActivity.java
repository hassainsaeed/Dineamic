package bookingTable;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import globalVariables.GlobalVariable;
import qrScanner.scanQRCode;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

public class BookingTableMainActivity extends FragmentActivity  {
	public final static String EXTRA_MESSAGE = "com.example.bookatable.MESSAGE";

	int numSeats;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_main_activity);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
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
	public void findTableForCustomer(int numSeats1) {
		numSeats = numSeats1;
		new findTableSpaceFromDatabase().execute();
	}

	
	private class findTableSpaceFromDatabase extends AsyncTask<Void, Void, Void>{
		//HTTP Post
		URL url = null;
		String output;
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				url = new URL("http://52.11.144.56/getAvailableTablesOfRestaurant.php?seatsrequested=" + numSeats);
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
				intent.putExtra(EXTRA_MESSAGE, message);
				startActivity(intent);
			} else {
				final GlobalVariable globalNumSeats = (GlobalVariable) getApplicationContext();
				globalNumSeats.setNumSeats(numSeats);
				Intent intent = new Intent(BookingTableMainActivity.this,NoAvailableTables.class);
				startActivity(intent);
			}
			
		
			super.onPostExecute(result);
		}
	}
	
}
