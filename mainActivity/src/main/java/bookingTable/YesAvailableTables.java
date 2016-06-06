package bookingTable;

import com.hmkcode.android.sign.R;

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

		Intent intent = getIntent();
		String message = intent.getStringExtra(BookingTableMainActivity.EXTRA_MESSAGE);
		
		TextView textView = (TextView) findViewById(R.id.editText1);
		textView.setTextSize(20);
		textView.setText(message);

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
			View rootView = inflater.inflate(R.layout.booking_table_no_available_tables, container,false);
			return rootView;
		}
		
	}
	
	public void goBack(View view) {
		Intent intent = new Intent(getApplicationContext(), BookingTableMainActivity.class);
		startActivity(intent);
	}
}
