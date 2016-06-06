package homePage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hmkcode.android.sign.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dynamicMenu.MenuSplashActivity;
import globalVariables.GlobalVariable;
import loginSignupPage.LogInActivity;
import loginSignupPage.MainActivity;

public class SignOutFragment extends Fragment {
    public SignOutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_offers, container, false);
        String FILENAME = "authentication";


        getActivity().deleteFile(FILENAME);
        GlobalVariable.setCustomerUserName("");
        GlobalVariable.setTableNumber(0);


        Intent i=new Intent(this.getActivity(), MyAlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this.getActivity(), 0, i, 0);
// Her I should get the getSystemService from the Activity, but how??
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
// Following unfortunately not working ...
        alarmManager.cancel(pi);


                Intent ii = new Intent(getActivity(), MainActivity.class);
        startActivity(ii);


        return rootView;
    }
}