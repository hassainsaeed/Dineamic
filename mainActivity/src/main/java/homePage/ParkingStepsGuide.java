package homePage;

import com.hmkcode.android.sign.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingStepsGuide extends Fragment {
	FragmentPagerAdapter adapterViewPager;
	private ImageView step_1;

	public ParkingStepsGuide () {
		
	}

	// Inflate the view for the fragment based on layout XML
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.parking_steps_page, container, false);
		 step_1 = (ImageView) rootView.findViewById(R.id.parking_steps);
		step_1.setBackgroundResource(R.drawable.tutorials_step1);
		return rootView;
	    }

	   
	}




