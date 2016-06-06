package homePage;

import android.support.v4.app.Fragment;

import com.hmkcode.android.sign.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.net.Uri;
import android.graphics.PorterDuff;

public class OffersFragment extends Fragment {
    public OffersFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.home_offers, container, false);
        ImageView img = (ImageView)rootView.findViewById(R.id.pizzahutoffer);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.pizzahut.ca"));
                startActivity(intent);
            }
        });   
          
        return rootView;
    }
}