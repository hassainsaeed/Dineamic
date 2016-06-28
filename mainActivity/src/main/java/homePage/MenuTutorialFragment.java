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

public class MenuTutorialFragment extends Fragment {
	private ViewPager viewpager;
	FragmentPagerAdapter adapterViewPager;

	public MenuTutorialFragment () {
		
	}

	// Inflate the view for the fragment based on layout XML
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.parking_tutorial_page, container, false);
        viewpager = (ViewPager) rootView.findViewById(R.id.tabs_pager);
        //have to get childfragmentmanager not fragmentmanager to make it work
        //or else if back is pressed the pages will not restore
        //***** set the menu tabs adapter to this fragment
        adapterViewPager = new TabsMenuAdapter(getChildFragmentManager());
        viewpager.setAdapter(adapterViewPager);
		  return rootView;
	    }

	   
	}




