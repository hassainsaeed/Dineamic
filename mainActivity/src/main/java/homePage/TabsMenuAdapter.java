package homePage;


import java.io.ObjectInputStream.GetField;

import com.hmkcode.android.sign.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TabsMenuAdapter extends FragmentPagerAdapter {
	
	private static int NUM_ITEMS = 3;
	private ImageView step_2;
	private ImageView step_3;


    public TabsMenuAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
//    	parkingStepsGuide = new ParkingStepsGuide();
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0: // Fragment # 1 - This will show FirstFragment
            return new MenuStepsGuide();
        case 1: // Fragment # 2 - This will show FirstFragment different title
            return new MenuStepsGuide(){
            	@Override
            	public View onCreateView(LayoutInflater inflater,
            			ViewGroup container, Bundle savedInstanceState) {
            		View rootView = inflater.inflate(R.layout.menu_steps_page, container, false);
            step_2 = (ImageView) rootView.findViewById(R.id.menu_steps);
            step_2.setBackgroundResource(R.drawable.tutorials_step2);
            		return rootView;
            	}
            };
        case 2: // Fragment # 2 - This will show FirstFragment different title
            return new MenuStepsGuide(){
            	@Override
            	public View onCreateView(LayoutInflater inflater,
            			ViewGroup container, Bundle savedInstanceState) {
            		View rootView = inflater.inflate(R.layout.menu_steps_page, container, false);
            step_3 = (ImageView) rootView.findViewById(R.id.menu_steps);
            step_3.setBackgroundResource(R.drawable.tutorials_menu_step3);
            		return rootView;
            	}
            };
        default:
            return new ParkingStepsGuide();
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
    	int pos = position+1;
        return "Step " + pos;
    }
    

}
