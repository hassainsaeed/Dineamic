package dynamicMenu;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hmkcode.android.sign.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public class DishStatusActivity extends FragmentActivity {
	JSONParser jsonParser = new JSONParser();
	static String url_splitItem = "http://52.11.144.56/splitItem.php";
	Typeface face;
	static ArrayList<String> list;
    static   ArrayList<JSONArray> list2;
	ArrayList<String> invitedCustomers;
    ArrayList splitedItemsCounter;
    String command ="";
	private static String url_inviteSomeone = "http://52.11.144.56/inviteSomeone.php";
    private static String url_updatePaymentSummary= "http://52.11.144.56/updatePaymentSummary.php";
	TableLayout tl;

	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
        splitItems x= new splitItems();
        command="updateSummary";
        x.execute("updateSummary");
		setContentView(R.layout.dynamic_menu_summary_page_fragment);

        ActionBar bar = getActionBar();
//for color
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();

        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(DishStatusActivity.this, DynamicMenuFragmentActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }

	public class splitItems extends AsyncTask<String, String, String> {
		private final ProgressDialog dialog = new ProgressDialog(DishStatusActivity.this);
		private int post = 0;

		@Override
		protected void onPreExecute() {
            if(command.equals("updateSummary")){
			this.dialog.setMessage("Fetching Orders on the table...");
			this.dialog.show();}


		}

		@Override
		protected String doInBackground(String... params) {
          if(params[0].equals("updateSummary")) {
                List<NameValuePair> paramss = new ArrayList<NameValuePair>();
                JSONParser jsonParser = new JSONParser();
                paramss.add(new BasicNameValuePair("currentCustomer", GlobalVariable.getCustomerUserName()));
                paramss.add(new BasicNameValuePair("tableNumber", GlobalVariable.getTableNumber() + ""));


                JSONObject json = jsonParser.makeHttpRequest(url_updatePaymentSummary,
                        "GET", paramss);
                //	Log.e("Hi", 5 + json.toString());

                try {
                    list2 = new ArrayList<JSONArray>();
                    //JSONArray orders2=new JSONArray();
                    JSONArray orders = (JSONArray) json.get("orders");
                    if (orders != null) {
                        int len = orders.length();
                        for (int i = 0; i < len; i++) {
                            list2.add((JSONArray) orders.get(i));
                        }

                    }


                    Log.e("length", list2.toString());

            /*for (int i = 0; i < customers.length(); i++) {
                JSONObject json_data = customers.getJSONObject(i);

                Log.e("Array", customers.getString(i) + json_data);


            }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

              post = 1;


            }


			return null;
		}

		@Override
		protected void onPostExecute(String s) {


            if(post==1) {

                TableLayout tl = (TableLayout) findViewById(R.id.summarytable);
                //tl.setBackground(getResources().getDrawable(R.drawable.summary_page_white_paper));
                TableRow row = new TableRow(DishStatusActivity.this);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                TextView SummaryIntro = new TextView(DishStatusActivity.this);
              //  Typeface face = Typeface.createFromAsset(SplitItemsActivity.this.getAssets(),
                //        "orange juice 2.0.ttf");
            //    SummaryIntro.setTypeface(face);
                SummaryIntro.setId(4444);
                SummaryIntro.setText(
                        "Zak's Dinner \nCheck number 5555\nWaiter Adam\n" +"Table "+ GlobalVariable.getTableNumber() + "\n" + dateFormat.format(date) + "\n\n");
                SummaryIntro.setTextColor(Color.parseColor("#fb1d91db"));
                SummaryIntro.setTextAlignment(3);
                SummaryIntro.setTextSize(26);
                row.addView(SummaryIntro);
                tl.addView(row);
                double subtotal = 0;
                final double tax = 1.13;
                TableRow rowDesc = new TableRow(DishStatusActivity.this);
                TextView ItemName = new TextView(DishStatusActivity.this);
              //  TextView Quantity = new TextView(SplitItemsActivity.this);
                TextView Price = new TextView(DishStatusActivity.this);
                ItemName.setText("Item Name");
               // Quantity.setText("Quantity");
                Price.setText("Status");
                ItemName.setTextColor(Color.parseColor("#fb1d91db"));
               // Quantity.setTextColor(Color.parseColor("#fb1d91db"));
                Price.setTextColor(Color.parseColor("#fb1d91db"));
          //      ItemName.setTypeface(face);
                //Quantity.setTypeface(face);
             //   Price.setTypeface(face);
                ItemName.setTextSize(20);
                //Quantity.setTextSize(20);
                Price.setTextSize(20);
                rowDesc.addView(ItemName);
                //rowDesc.addView(Quantity);
                rowDesc.addView(Price);
                tl.addView(rowDesc);
                int Id = 100000;
                //Log.d(, "Count is" + count_4[0] + count_4[1]);
                for (int i = 0; i < list2.size(); i++) {
                    TableRow tr = new TableRow(DishStatusActivity.this);

                    String item_status="";
                  //  int count = 0;
                    String item = "";
                    String orderNumber="";
                    int color=0;

                    try {
                        String StartTime=list2.get(i).getString(3);
                        String EndTime=list2.get(i).getString(4);
                        Log.e("StartTime",StartTime);
                        Log.e("EndTime",EndTime);

                        if(StartTime.equals("null")&&EndTime.equals("null")){
                        item_status = "Dish has not been started yet";
                        color=1 ;}
                        else if(StartTime.equals("null")||EndTime.equals("null")){
                            item_status = "Dish is being prepared";
                        color=2;}
                        else{
                            item_status = "Dish is completed";
                        color=3;}

                    //    count = Integer.parseInt(list2.get(i).getString(2));
                        item = list2.get(i).getString(1);
                        orderNumber=list2.get(i).getString(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Id += 2;


                    TextView itemName = new TextView(DishStatusActivity.this);
                    //TextView itemQuantity = new TextView(SplitItemsActivity.this);
                    TextView itemStatus = new TextView(DishStatusActivity.this);

                    itemName.setText(item);
                    itemName.setTextColor(Color.parseColor("#fb1d91db"));
                    itemName.setTextAlignment(3);
                    itemName.setTextSize(20);
               //     itemName.setTypeface(face);
                    itemName.setId(Id + 1);

                /*    itemQuantity.setText(" @ " + count);
                    itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
                    itemQuantity.setTextAlignment(3);
                    itemQuantity.setTextSize(20);
                    itemQuantity.setTypeface(face);*/
                    itemStatus.setText(item_status);
                    if(color==1)
                    itemStatus.setTextColor(Color.RED);
                    else if(color==2)
                        itemStatus.setTextColor(Color.BLUE);
                        else
                        itemStatus.setTextColor(Color.GREEN);
                    itemStatus.setTextAlignment(3);
                    itemStatus.setTextSize(20);
                //    itemPrice.setTypeface(face);

                    tr.addView(itemName);
                  //  tr.addView(itemQuantity);
                    tr.addView(itemStatus);

                    tl.addView(tr);



                }



            }
            dialog.dismiss();
		}

	}
}