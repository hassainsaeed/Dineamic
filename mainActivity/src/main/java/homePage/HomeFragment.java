package homePage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;


//Fragment for the home page
public class HomeFragment extends Fragment {
	private ImageView tutorial_1;
	private ImageView tutorial_2;
    private ImageView userProfile;
	FragmentPagerAdapter adapterViewPager;

	public HomeFragment() {

	}


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home, container, false);

        tutorial_1 = (ImageView) rootView.findViewById(R.id.tutorial_item_1);
        tutorial_2 = (ImageView) rootView.findViewById(R.id.tutorial_item_2);
        userProfile = (ImageView) rootView.findViewById(R.id.userProfile);
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
                intent.putExtra("SAVE_HISTORY", false);
                startActivityForResult(intent, 0);
            }



        });
        userProfile.setOnClickListener(new View.OnClickListener()
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
                Intent intent = new Intent(getActivity(), UserProfile.class);

                startActivity(intent);
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