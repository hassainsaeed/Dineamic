package loginSignupPage;
 
import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import globalVariables.GlobalVariable;
import homePage.HomePageMainActivity;

public class SplashActivity extends Activity {
 
   private static String TAG = SplashActivity.class.getName();
   private static long SLEEP_TIME = 2;    // Sleep for some time
 
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
 
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar
 
      setContentView(R.layout.start_page_splash);
 
      // Start timer and launch main activity
      IntentLauncher launcher = new IntentLauncher();
      launcher.start();
   }
 
   private class IntentLauncher extends Thread {
      @Override
      /**
       * Sleep for some time and than start new activity.
       */
      public void run() {
         try {
            // Sleeping
            Thread.sleep(SLEEP_TIME*1000);
         } catch (Exception e) {
            Log.e(TAG, e.getMessage());
         }
 
         // Start main activity
         FileInputStream fis = null;
         try {
            fis = openFileInput("authentication");
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
         if(fis!=null){
         InputStreamReader isr = new InputStreamReader(fis);
         BufferedReader bufferedReader = new BufferedReader(isr);
         StringBuilder sb = new StringBuilder();
         String line= null;
         try {
            line = bufferedReader.readLine();
         } catch (IOException e) {
            e.printStackTrace();
         }

           if (line  != null) {
               GlobalVariable.setCustomerUserName(line);
              Intent intent = new Intent(SplashActivity.this, HomePageMainActivity.class);
              SplashActivity.this.startActivity(intent);
              SplashActivity.this.finish();

            }
           else{
              Intent intent = new Intent(SplashActivity.this, MainActivity.class);
              SplashActivity.this.startActivity(intent);
              SplashActivity.this.finish();}

         }
        else{
         Intent intent = new Intent(SplashActivity.this, MainActivity.class);
         SplashActivity.this.startActivity(intent);
         SplashActivity.this.finish();}
      }
   }
}
//433