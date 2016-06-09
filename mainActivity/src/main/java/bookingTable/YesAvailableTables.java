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

public class YesAvailableTables extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_yes_available_tables);
		ActionBar bar = getActionBar();
//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
		Intent intent = getIntent();
		String message = intent.getStringExtra(BookingTableMainActivity.EXTRA_MESSAGE);
		
		TextView textView = (TextView) findViewById(R.id.editText1);
		textView.setTextSize(20);
		textView.setText(message);

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

		}
		return super.onOptionsItemSelected(item);
	}


	
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
		startActivity(intent);
	}
}
