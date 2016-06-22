package dynamicMenu;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public  class importMenu extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	public importMenu() {


	}

	/**
	 * Creating product
	 */
	protected String doInBackground(String... args) {

		URL url = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		  ArrayList<JSONArray> list=null;

		// getting JSON Object
		// Note that create product url accepts POST method
		//JSONObject json = jsonParser.makeHttpRequest(url_signInUsers,
			//	"GET", params);
		//Change the value of restuarantMenu to be a variable once selecting the restaturant fetaure is implemented
		params.add(new BasicNameValuePair("restaurantMenu", "ZAKS_MENU"));
		String Url = "http://52.11.144.56/importMenu.php";

			JSONObject json = jsonParser.makeHttpRequest(Url,
					"GET", params);


		try {
			list = new ArrayList<JSONArray>();
			JSONArray orders = (JSONArray) json.get("orders");
			if (orders != null) {
				int len = orders.length();
				for (int i=0;i<len;i++){
					list.add((JSONArray)orders.get(i));
				}

			}


			Log.e("length", list.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		GlobalVariable.setMenuList(list);
		int [] numOfMenuItems= new int [4];
		for (int i = 0; i < list.size(); i++) {
			try {
				if(list.get(i).getString(2).equals("breakfast")) {



                        numOfMenuItems[0]++;

                }
				else if(list.get(i).getString(2).equals("lunchanddinner")) {
					numOfMenuItems[1]++;
				}
				if(list.get(i).getString(2).equals("desserts")) {
					numOfMenuItems[2]++;
				}
				if(list.get(i).getString(2).equals("drinks")) {
					numOfMenuItems[3]++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}


		}
GlobalVariable.setMenuNumberOfItems(numOfMenuItems);
		return null;
	}

}