package parking;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hmkcode.android.sign.R;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Parking_lot extends ActionBarActivity {
	TextView park0;
	TextView park1;
	TextView park2;
	TextView park3;
	RelativeLayout view;
	int height;
    int width ;
    int height_per_section;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parking_main_activity);
		//ActionBar bar = getActionBar();
//for color
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
		
		park0=(TextView)findViewById(R.id.park0);
		park1=(TextView)findViewById(R.id.park1);
		park2=(TextView)findViewById(R.id.park2);
		park3=(TextView)findViewById(R.id.park3);
		view= (RelativeLayout) findViewById(R.id.parkingLayout);
		

		
		Display display=getWindowManager().getDefaultDisplay();
		Point size=new Point();
		display.getSize(size);
		width=size.x;
		height=size.y;
        height_per_section= (int)(height/6);
        
		
		StrictMode.enableDefaults();
		this.getdata();
	}

    //This function is to convert the InputStream from the HTMLURLConnection into a string
    private String readStream(InputStream is) {
    	try {
    		ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
            bo.write(i);
            i = is.read();
            }
            return bo.toString();
         } catch (IOException e) {
            return "";
            }
    }
    
    
	public void getdata() 
	{
		String result="";
		InputStream in= null;
		URL url= null;
		
        try {
            url = new URL("http://52.11.144.56/access_parking_database.php?");
        } catch (MalformedURLException e) {
         // Catch if the URL is incorrect for whatever reason (this should technically never happen)
           e.printStackTrace();
        }
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            result = readStream(in);
        } catch (Exception e) {
            // Catch if theres an IO exception with reading the website data
            e.printStackTrace();
        }
        
        
        try {
        String s="";
        JSONArray jArray= new JSONArray(result);
    
        for (int i=0;i<jArray.length();i++){
        
	        
        	JSONObject json=jArray.getJSONObject(i);
        	int avail= json.getInt("Availability");
        	String current_park_string= "park"+i;
        	int current_park_id = getResources().getIdentifier(current_park_string, "id", getPackageName());
        	TextView current_park= (TextView)findViewById(current_park_id);
        	
        	if (current_park!= null) {
        		if (avail == 1){
        			if(i!=0){
                    ViewGroup.MarginLayoutParams crnt_prk = (ViewGroup.MarginLayoutParams) current_park.getLayoutParams();
                    int marg=((int)(height_per_section));
                    crnt_prk.topMargin =(int)marg -crnt_prk.topMargin;
                    current_park.setLayoutParams(crnt_prk);}
        			current_park.setText("Available");
                    current_park.setTextColor(Color.parseColor("#006400"));

        		}
        		else {
        			if(i!=0){
        		    ViewGroup.MarginLayoutParams crnt_prk = (ViewGroup.MarginLayoutParams) current_park.getLayoutParams();
                    int marg=((int)(height_per_section));
                    crnt_prk.topMargin =(int)marg-crnt_prk.topMargin;
                    current_park.setLayoutParams(crnt_prk);}
                    current_park.setText("Not Available");
        			current_park.setTextColor(Color.parseColor("#ff0000"));
        		  
        			}
        	}   	
        	}
        	}catch (Exception e) {
        		// Catch if theres an exception
        		e.printStackTrace();
        	}
        
    	}
        
        
        
        
        
        
		
	
}
