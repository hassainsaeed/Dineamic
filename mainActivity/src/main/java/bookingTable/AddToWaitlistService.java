package bookingTable;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import com.hmkcode.android.sign.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class AddToWaitlistService extends Service {
	public final static String EXTRA_MESSAGE = "com.example.bookatable.MESSAGE";
	//NotifyServiceReceiver notifyServiceReceiver;
	//final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
    //final static int RQS_STOP_SERVICE = 1;
    public static final long NOTIFY_INTERVAL = 20* 1000; //20 seconds
	private Handler mHandler = new Handler();
	private Timer mTimer = null;	
	
    /*@Override
    public void onCreate() {
        
    	notifyServiceReceiver = new NotifyServiceReceiver();
        super.onCreate();
    }*/
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {	
    	
    	if (mTimer != null) {
    		//cancel if already existing timer
    		mTimer.cancel();
    	} else {
    		//create new mTimer
    		mTimer = new Timer();
    	}
	
    	//Schedule Task
    	mTimer.scheduleAtFixedRate(new TimerTaskFindSpace(), 0, NOTIFY_INTERVAL);
    	return START_STICKY;
    }

    class TimerTaskFindSpace extends TimerTask {
    	@Override
    	public void run() {
    		//run on another thread
    		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				new findTableSpaceFromDatabase().execute();
			}
    		});
    	}
    }

    private class findTableSpaceFromDatabase extends AsyncTask<Void, Void, Void>{
    	URL url = null;
    	String output ="";
		//final GlobalVariableNumSeats globalNumSeats = (GlobalVariableNumSeats)getApplicationContext();  //Keep for now, will Replace with new PHP file
		//final int numSeats = globalNumSeats.getNumSeats(); //Keep for now, will replace with new PHP file
    	TelephonyManager tMgr =(TelephonyManager)AddToWaitlistService.this.getSystemService(Context.TELEPHONY_SERVICE);
	    String mPhoneNumber = tMgr.getLine1Number();
    	
    	
		@Override
		protected Void doInBackground(Void... params) {
			try {
				url = new URL("http://52.11.144.56/findPositionOnWaitList.php?customerNumber=" + mPhoneNumber);
			} catch (MalformedURLException e) {
				// Catch if the URL is incorrect for whatever reason (this should technically never happen)
				e.printStackTrace();
			}
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				output = readStream(in);
			} catch (IOException e) {
				// Catch if theres an IO exception with reading the website data
				e.printStackTrace();
			}
	
			return null;
		}
	
		@Override
		protected void onPostExecute(Void result) {
			if (!output.contains("None")) {
				int table_num = Character.getNumericValue(output.charAt(15));
				Intent intent2 = new Intent(getApplicationContext(), YesAvailableTables.class);
				String message = "Please enter the resturant and seat your self at Table #" + table_num + ". A waiter will be with you shortly.";
				intent2.putExtra(EXTRA_MESSAGE, message);
				PendingIntent pIntent = PendingIntent.getActivity(AddToWaitlistService.this,0,intent2,0);
		
				Notification noti = new Notification.Builder(AddToWaitlistService.this)
				.setTicker("Your Table is Ready")
				.setContentTitle("Your Table is Ready")
				.setContentText("Please come to the Resturant in the next 10 minutes to claim your spot at Table #" + table_num)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent).getNotification();
		
				noti.flags=Notification.FLAG_AUTO_CANCEL;
				NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(0, noti);
				AddToWaitlistService.this.stopSelf();
			}
			super.onPostExecute(result);
		}
    }


    @Override
    public void onDestroy() {
    	super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
    	return null;
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

/*
    public class NotifyServiceReceiver extends BroadcastReceiver{

    	@Override
    	public void onReceive(Context arg0, Intent arg1) {
   
    		int rqs = arg1.getIntExtra(STOP_SERVICE_BROADCAST_KEY, 0);
       
    		if (rqs == RQS_STOP_SERVICE){
    			stopSelf();
    			((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
    			.cancelAll();
    		}
    	}
    } */
    
}
