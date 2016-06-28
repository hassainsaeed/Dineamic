package nfc;



import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import loginSignupPage.JSONParser;

public  class CallWaiter extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	private String tableNumber;
	private Context context;
	private String restaurantName;
	public CallWaiter(Context context,String restaurantName, String tableNumber) {
		this.tableNumber=tableNumber.substring(0,1);
		this.context=context;
		this.restaurantName = restaurantName;
	}

	/**
	 * Creating product
	 */
	protected String doInBackground(String... args) {

		URL url = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		

		
		String Url = "http://52.11.144.56/callWaiter.php";
		params.add(new BasicNameValuePair("Restaurant_Name",restaurantName));
		params.add(new BasicNameValuePair("Table_Number",tableNumber));

		JSONObject json = jsonParser.makeHttpRequest(Url,
					"GET", params);
		
		return null;
	}

}