package bookingTable;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import qrScanner.scanQRCode;

public class YesAvailableTables extends Activity {
	private String restaurantName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_yes_available_tables);
		ActionBar bar = getActionBar();
//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
		Intent intent = getIntent();
		restaurantName = intent.getStringExtra("Restaurant Name");

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
				Intent intent = new Intent(YesAvailableTables.this, CaptureActivity.class);
				intent.setAction("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
				return true;

			case android.R.id.home:
				intent = new Intent(getApplicationContext(), BookingTableMainActivity.class);
				intent.putExtra("Restaurant Name", restaurantName);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		new scanQRCode(requestCode,resultCode,intent,YesAvailableTables.this).execute();
	}
	//End Code for the QR Scanner in the Action Bar



	public static class PlaceholderFragment extends Fragment {
		public PlaceholderFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.booking_table_no_available_tables, container,false);
			return rootView;
		}

	}

	public void goBack(View view) {
		Intent intent = new Intent(getApplicationContext(), BookingTableMainActivity.class);
		intent.putExtra("Restaurant Name", restaurantName);
		startActivity(intent);
	}
}