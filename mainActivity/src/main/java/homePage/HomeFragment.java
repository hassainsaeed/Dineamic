package homePage;

import java.util.LinkedHashMap;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import bookingTable.BookingTableMainActivity;
import dynamicMenu.DynamicMenuFragmentActivity;
import menu.Menu_pdf;


//Fragment for the home page
public class HomeFragment extends Fragment {
	private ImageView tutorial_1;
	private ImageView tutorial_2;

	FragmentPagerAdapter adapterViewPager;

	public HomeFragment() {

	}


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home, container, false);

        tutorial_1 = (ImageView) rootView.findViewById(R.id.tutorial_item_1);
        tutorial_2 = (ImageView) rootView.findViewById(R.id.tutorial_item_2);
        tutorial_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            	/*FragmentManager fragmentManager2 = getFragmentManager();
            	FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            	ParkingTutorialFragment parkingFrag = new ParkingTutorialFragment();
            	//need the code below when back is clicked
            	fragmentTransaction2.addToBackStack("Back");
//            	fragmentTransaction2.hide(HomeFragment.this);
//            	fragmentTransaction2.add(R.id.content_frame, parkingFrag);
            	//the line below makes fragment contents overlap drawerlayout
//            	fragmentTransaction2.add(android.R.id.content, parkingFrag);
//            	fragmentTransaction2.commit();
            	fragmentTransaction2.replace(R.id.content_frame, parkingFrag).commit();*/
                //scan
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }



        });


        tutorial_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
            	FragmentManager fragmentManager2 = getFragmentManager();
            	FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            	MenuTutorialFragment menuFrag = new MenuTutorialFragment();
            	//need the code below when back is clicked
            	fragmentTransaction2.addToBackStack("Back");
//            	fragmentTransaction2.hide(HomeFragment.this);
//            	fragmentTransaction2.add(R.id.content_frame, parkingFrag);
            	//the line below makes fragment contents overlap drawerlayout
//            	fragmentTransaction2.add(android.R.id.content, parkingFrag);
//            	fragmentTransaction2.commit();
            	fragmentTransaction2.replace(R.id.content_frame, menuFrag).commit();
            }
        });

        return rootView;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                //handle succesful scan
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(getActivity(), "FORMAT: " + format + " CONTENT: " + contents, Toast.LENGTH_SHORT);
                toast.show();

                if (contents.equals("BookingTableMainActivity")) {
                    Intent bookingTableIntent = new Intent(getActivity(), BookingTableMainActivity.class);
                    startActivity(bookingTableIntent);
                } else if (contents.equals("Menu_pdf")) {
                    Intent staticMenuIntent = new Intent(getActivity(), Menu_pdf.class);
                    startActivity(staticMenuIntent);
                } else if (contents.equals("DynamicMenuFragmentActivity")) {
                    Intent dynamicMenuIntent = new Intent(getActivity(), DynamicMenuFragmentActivity.class);
                    startActivity(dynamicMenuIntent);
                }



                //Compare the String contents and formats… if it matches with the expected contents and format then we launch the new Android activity for Book a Table or Interactive Menu or Send Order or etc in this section of code
                Log.d("contents", "contents: " + contents);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //handle cancel
                Toast toast = Toast.makeText(getActivity(), "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("contents", "RESULT_CANCELED");
            }
        }
    }

}