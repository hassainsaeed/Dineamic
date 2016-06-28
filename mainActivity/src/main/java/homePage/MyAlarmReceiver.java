package homePage;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public class MyAlarmReceiver extends BroadcastReceiver {
    Context context;

	//boolean post= false;
    @Override

	public void onReceive(Context context, Intent intent) {
this.context=context;
        if(GlobalVariable.getTableNumber()!=0&&!GlobalVariable.getCustomerUserName().equals("")){
        splitNotification x=new splitNotification();
        x.execute("send");}

	}


	public class splitNotification extends AsyncTask<String, String, String> {
 boolean post=false;
		ArrayList<JSONArray> list2 = null;
		@Override
		protected String doInBackground(String... params) {
			if(params[0].equals("send")) {
				List<NameValuePair> paramss = new ArrayList<NameValuePair>();
				JSONParser jsonParser = new JSONParser();
				paramss.add(new BasicNameValuePair("tableNumber", GlobalVariable.getTableNumber() + ""));
				paramss.add(new BasicNameValuePair("currentCustomer", GlobalVariable.getCustomerUserName()));

				//Log.e("hehe username", GlobalVariable.getCustomerUserName());
				//Log.e("hehe table", GlobalVariable.getTableNumber() + "");
				String url_splitNotification = "http://52.11.144.56/splitNotification.php";

				JSONObject json = jsonParser.makeHttpRequest(url_splitNotification,
						"GET", paramss);
				try {
					list2 = new ArrayList<JSONArray>();
					//JSONArray orders2=new JSONArray();
					JSONArray orders = (JSONArray) json.get("splits");
					if (orders != null) {
						int len = orders.length();
						for (int i = 0; i < len; i++) {
							list2.add((JSONArray) orders.get(i));
						}

					}


					Log.e("length alarm", list2.toString());

            /*for (int i = 0; i < customers.length(); i++) {
                JSONObject json_data = customers.getJSONObject(i);

                Log.e("Array", customers.getString(i) + json_data);


            }*/
				} catch (JSONException e) {
					e.printStackTrace();
				}
				post =true;
			}
			else   if(params[0].equals("delete")) {
				list2 = new ArrayList<JSONArray>();
				List<NameValuePair> paramss = new ArrayList<NameValuePair>();
				JSONParser jsonParser = new JSONParser();
				paramss.add(new BasicNameValuePair("tableNumber", GlobalVariable.getTableNumber() + ""));
				paramss.add(new BasicNameValuePair("currentCustomer", GlobalVariable.getCustomerUserName()));


				String url_removeSplitNotification = "http://52.11.144.56/removeSplitNotification.php";
				JSONObject json = jsonParser.makeHttpRequest(url_removeSplitNotification,
						"GET", paramss);
				post=false;
			}

return null;
		}
        @Override
        protected void onPostExecute(String s) {
			if (post){
            for (int i = 0; i < list2.size(); i++){
                try {
                    Toast.makeText(context, "Customer: " + list2.get(i).getString(0) + " has split the order: " + list2.get(i).getString(1) + " price: " + list2.get(i).getString(2) + "$ with you!", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }}}
			list2=new ArrayList<JSONArray>();
			splitNotification x=new splitNotification();
			x.execute("delete");


        }
	}
}