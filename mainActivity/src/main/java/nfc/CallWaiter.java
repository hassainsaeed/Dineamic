package nfc;



import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public  class CallWaiter extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	private String tableNumber;
	private Context context;
	public CallWaiter(Context context,String tableNumber) {
		this.tableNumber=tableNumber.substring(0,1);
		this.context=context;
	}

	/**
	 * Creating product
	 */
	protected String doInBackground(String... args) {

		URL url = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		

		
		String Url = "http://52.11.144.56/callWaiter.php";
	
	

			
			
				params.add(new BasicNameValuePair("Table_Number",tableNumber));
				
				
		

		
			JSONObject json = jsonParser.makeHttpRequest(Url,
					"GET", params);
		
		return null;
	}

}