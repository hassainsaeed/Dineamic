package dynamicMenu;

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

public  class sendMenuOrder extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	String name;
	int tableNumber;
	public static final String TAG = "NfcTut";

	String []breakfast,lunchanddinner,desserts ,drinks ,breakfast_comments,lunchanddinner_comments,desserts_comments,drinks_comments;
	 int []count_1,count_2,count_3,count_4 ;
	 double []breakfast_price,lunchanddinner_price ,desserts_price,drinks_price;
	private Context context;
	public sendMenuOrder(int tableNumber, String customerName, String[] Breakfast, double[] Breakfast_price, String[] Lunchanddinner,
						 double[] Lunchanddinner_price, String[] Desserts, double[] Desserts_price, String[] Drinks,
						 double[] Drinks_price, int[] Count_1, int[] Count_2, int[] Count_3, int[] Count_4, String[] Breakfast_comments, String[] Lunchanddinner_comments, String[] Desserts_comments, String[] Drinks_comments, Context context) {
		this.tableNumber=tableNumber;
		name=customerName;
		breakfast = Breakfast;
		breakfast_price = Breakfast_price;
		lunchanddinner = Lunchanddinner;
		lunchanddinner_price = Lunchanddinner_price;
		desserts = Desserts;
		desserts_price = Desserts_price;
		drinks = Drinks;
		drinks_price = Drinks_price;
		count_1 = Count_1;
		count_2 = Count_2;
		count_3 = Count_3;
		count_4 = Count_4;
		breakfast_comments=Breakfast_comments;
		lunchanddinner_comments=Lunchanddinner_comments;
		desserts_comments=Desserts_comments;
		drinks_comments=Drinks_comments;

		this.context=context;
	}

	/**
	 * Creating product
	 */
	protected String doInBackground(String... args) {

		URL url = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		

		// getting JSON Object
		// Note that create product url accepts POST method
		//JSONObject json = jsonParser.makeHttpRequest(url_signInUsers,
			//	"GET", params);
		
		String Url = "http://52.11.144.56/sentOrders.php";
		Boolean sectionUsed = false;
		int counter = 0;
		//final GlobalVariable globalUserName = (GlobalVariable)context.getApplicationContext();
		//final String userName = GlobalVariable.getCustomerUserName();
		Log.e(TAG, "My name is" + name);
		Log.e(TAG,"Table Number is" +tableNumber);

		for (int i = 0; i < count_1.length; i++) {
			int counter1=count_1[i];

			while (counter1 > 0) {
				sectionUsed = true;
				params.add(new BasicNameValuePair("Table_Number"+counter,tableNumber+""));
				params.add(new BasicNameValuePair("Customer_Name"+counter,name));
				params.add(new BasicNameValuePair("Order"+counter, breakfast[i]));
				params.add(new BasicNameValuePair("Price"+counter, breakfast_price[i]+""));
				params.add(new BasicNameValuePair("Comments"+counter, breakfast_comments[i]));
			//	params.add(new BasicNameValuePair("Number_Of_Orders"+counter, count_1[i]+""));
				
				//Url += "Order" + i + "=" + breakfast[i] + "&Price" + i + "=" + breakfast_price[i]
					//	+ "&Number_Of_Orders" + i + "=" + count_1[i] + "&";
				counter++;
				counter1--;
			}
		}

	
		for (int i = 0; i < count_2.length; i++) {
			int counter1=count_2[i];

			while (counter1 > 0) {
				sectionUsed = true;
				params.add(new BasicNameValuePair("Table_Number"+counter,tableNumber+""));
				params.add(new BasicNameValuePair("Customer_Name"+counter,name));
				params.add(new BasicNameValuePair("Order"+counter, lunchanddinner[i]));
				params.add(new BasicNameValuePair("Price"+counter, lunchanddinner_price[i]+""));
				params.add(new BasicNameValuePair("Comments"+counter, lunchanddinner_comments[i]));
				//params.add(new BasicNameValuePair("Number_Of_Orders"+counter, count_2[i]+""));
				//Url += "Order" + tempI + "=" + lunchanddinner[i] + "&Price" + tempI+ "="
				//		+ lunchanddinner_price[i] + "&Number_Of_Orders"+ tempI + "=" + count_2[i] + "&";
				counter++;
				counter1--;
			}
		}

		
		for (int i = 0; i < count_3.length; i++) {

			int counter1=count_3[i];

			while(counter1 > 0) {
				sectionUsed = true;
				params.add(new BasicNameValuePair("Table_Number"+counter,tableNumber+""));
				params.add(new BasicNameValuePair("Customer_Name"+counter,name));
				params.add(new BasicNameValuePair("Order"+counter, desserts[i]));
				params.add(new BasicNameValuePair("Price"+counter, desserts_price[i]+""));
				params.add(new BasicNameValuePair("Comments"+counter, desserts_comments[i]));
				//params.add(new BasicNameValuePair("Number_Of_Orders"+counter, count_3[i]+""));
			//	Url += "Order" + tempI+ "=" + drinks[i] + "&Price" + tempI + "=" + drinks_price[i]
				//		+ "&Number_Of_Orders" + tempI + "=" + count_3[i] + "&";
				counter++;
				counter1--;
			}
		}

		for (int i = 0; i < count_4.length; i++)
			
		{
			int counter1=count_4[i];

			while(counter1 > 0) {
				sectionUsed = true;
				params.add(new BasicNameValuePair("Table_Number"+counter,tableNumber+""));
				params.add(new BasicNameValuePair("Customer_Name"+counter,name));
				params.add(new BasicNameValuePair("Order"+counter, drinks[i]));
				params.add(new BasicNameValuePair("Price"+counter, drinks_price[i]+""));
				params.add(new BasicNameValuePair("Comments"+counter, drinks_comments[i]));
			//	params.add(new BasicNameValuePair("Number_Of_Orders"+counter, count_4[i]+""));
				//Url += "Order" + tempI + "=" + desserts[i] + "&Price" +tempI + "="
					//	+ desserts_price[i] + "&Number_Of_Orders" + tempI+ "=" + count_4[i] + "&";
			counter++;
				counter1--;
			}
		}
		if (sectionUsed)

		{
		/*	try {
				Url = Url.substring(0, Url.length() - 1);
				Log.d(getTag(), Url);
				url = new URL(Url);
			} catch (MalformedURLException e) {
				// Catch if the URL is incorrect for whatever reason (this
				// should technically never happen)
				e.printStackTrace();
			}
			try {
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				// output = readStream(in);
			} catch (IOException e) {
				// Catch if theres an IO exception with reading the website
				// data
				e.printStackTrace();
			}*/
			JSONObject json = jsonParser.makeHttpRequest(Url,
					"GET", params);
		}
		return null;
	}

}