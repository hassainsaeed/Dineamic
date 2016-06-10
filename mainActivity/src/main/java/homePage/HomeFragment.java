package homePage;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import qrScanner.scanQRCode;


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
        new scanQRCode(requestCode,resultCode,intent,getActivity()).execute();
    }

}