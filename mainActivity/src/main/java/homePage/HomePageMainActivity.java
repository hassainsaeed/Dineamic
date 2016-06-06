package homePage;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.hmkcode.android.sign.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageMainActivity extends FragmentActivity {
	 private String[] toolbar_options;
	 private TypedArray toolbar_icons;
	 private ActionBarDrawerToggle mDrawerToggle;
	 private DrawerLayout NavDrawerLayout;
	 private ListView DrawerList;
	 private ArrayAdapter<String> mAdapter;
	 private CharSequence mDrawerTitle;
	 private CharSequence mTitle;
	 private LinkedHashMap <String, Integer > titleToIconMap;
	 NavigationAdapter NavAdapter;
	 private ViewPager viewPager;
	 private TabsParkingAdapter tabAdapter;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_main_activity);
		//get the drawerlayout and the drawerlist form activitymain.xml
		//and make them functional
		NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar bar = getActionBar();
//for color
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
		DrawerList = (ListView) findViewById(R.id.drawer_list);
		View header = getLayoutInflater().inflate(R.layout.home_header, null);
		//add the headerviewer to the drawerlist
		//NOTE: will only show once an actual array has been added
        DrawerList.addHeaderView(header);
        //grabs the titles of the toolbar_options from strings.xml
        toolbar_options = getResources().getStringArray(R.array.toolbar_options);
        //grabs the images that will be appended to toolbar_options
        toolbar_icons = getResources().obtainTypedArray(R.array.toolbar_icons);


        ////////**********************************************
//		  View parkingTutorial= inflater.inflate(R.layout.parking_tutorial_page, container, false);
//		  viewPager = (ViewPager) findViewById(R.id.tutorial_tabs_pager);
//	       tabAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//	       viewPager.setAdapter(tabAdapter);
        
        ///*******************************************************
        
        
        
        titleToIconMap = new LinkedHashMap<String, Integer>();
        for (int i=0; i<toolbar_options.length; i++){
        titleToIconMap.put(toolbar_options[i], toolbar_icons.getResourceId(i, -1));
        }
        
        //adding the listview titles from the string.xml
        //using custom layout to show titles which requires a constructor
        //with 4 parameters
        NavAdapter= new NavigationAdapter(this,titleToIconMap);
        DrawerList.setAdapter(NavAdapter);
        
        
        //toolbar option selector top left initiation
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                NavDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* top left icon*/
                R.string.app_name,  /* "open drawer" description */
                R.string.hello_world  /* "close drawer" description */
                ){
        	public void onDrawerOpened(View drawerView) {
        	}
        	
        	public void onDrawerClosed(View view) {
        	}
        };	 
        NavDrawerLayout.setDrawerListener(mDrawerToggle);
        //Hides the android icon and only shows the ic_drawer icon
        getActionBar().setDisplayShowHomeEnabled(false);
        //make top left clickable
        getActionBar().setDisplayHomeAsUpEnabled(true);	
        
    	
    	//**************** react when a listview item is clicked **********
    	DrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
            	AccessFragment(position);
            }
        });
    	// make the home page the default one
    	AccessFragment(1);
    	
    	//********************************************************
	}

	
	//**************************** method that shows fragment based on click 
    private void AccessFragment(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
        case 1:
            fragment = new HomeFragment();
            break;
            
        case 6:
        	fragment = new OffersFragment();
        	break;

        case 9:
            fragment = new SignOutFragment();
            break;
 
        default:
        	Toast.makeText(getApplicationContext(),"Option "+toolbar_options[position-1]+" not available!", Toast.LENGTH_SHORT).show();
            fragment = new HomeFragment();
            position=1;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
 

            DrawerList.setItemChecked(position, true);
            DrawerList.setSelection(position);
            setTitle(toolbar_options[position-1]);
            NavDrawerLayout.closeDrawer(DrawerList);
        } else {
        	//show message if the fragmet does not exist
            Log.e("Error  ", "AcessFragment"+position);
        }
    }

	//the 2 methods make the ic_drawer icon visible
	protected void onPostCreate(Bundle savedInstanceState) {
	    super.onPostCreate(savedInstanceState);
	    // Sync the toggle state after onRestoreInstanceState has occurred.
	    mDrawerToggle.syncState();
	}
	
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	    mDrawerToggle.onConfigurationChanged(newConfig);
	}
	@Override
	//when top left drawertoggle is pressed, open the tab
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		 // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.e("mDrawerToggle pushed", "x");
          return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
	}
}
