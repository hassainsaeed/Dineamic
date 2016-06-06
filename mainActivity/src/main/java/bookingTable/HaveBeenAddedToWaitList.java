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

public class HaveBeenAddedToWaitList extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.bookatable.MESSAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_table_have_been_added_to_wait_list);

		Intent serviceIntent = new Intent(this,AddToWaitlistService.class);
		this.startService(serviceIntent);
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
			View rootView = inflater.inflate(R.layout.booking_table_have_been_added_to_wait_list, container,false);
			return rootView;
		}
		
	}
	
	public void goBack(View view) {
		Intent intent = new Intent(getApplicationContext(), BookingTableMainActivity.class);
		startActivity(intent);
	}

}
