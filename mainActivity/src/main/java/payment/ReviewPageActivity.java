package payment;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hmkcode.android.sign.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dynamicMenu.DynamicMenuFragmentActivity.DummySectionFragment;
import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public class ReviewPageActivity extends FragmentActivity {

    RatingBar bar;
    TextView itemText;
    Typeface face;
    static   ArrayList<JSONArray> list;
    JSONParser jsonParser = new JSONParser();
	private static String url_updatePaymentSummary= "http://52.11.144.56/updatePaymentSummary.php";
	private static String url_submitReviews= "http://52.11.144.56/submitReviews.php";
	private static String url_removeUserOrders= "http://52.11.144.56/removeUserOrders.php";
    TableLayout tl;
    private int tableNumber;
	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
        submitReview x= new submitReview();
          x.execute("update");
		setContentView(R.layout.dynamic_menu_summary_page_fragment);
        ActionBar bar = getActionBar();
//for color
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));




	}
	public class submitReview extends AsyncTask<String, String, String> {
		private final ProgressDialog dialog = new ProgressDialog(ReviewPageActivity.this);
		private boolean post=false;
		@Override
		protected void onPreExecute() {
			this.dialog.setMessage("Updating Bill....");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
            if(params[0].equals("submit")){
                List<NameValuePair> param = new ArrayList<NameValuePair>();
                int id=100000;
                for (int i = 0; i < list.size(); i++) {
                    id+=2;
                    bar=(RatingBar) findViewById(id);
                    itemText=(TextView) findViewById(id+1);
                    float stars =this.getRating();

                    String item=this.getItemText();
                    Log.e("ratings",stars+" "+item);
                    param.add(new BasicNameValuePair("User", GlobalVariable.getCustomerUserName()));
                    param.add(new BasicNameValuePair("Item"+i,item));
                    param.add(new BasicNameValuePair("Rating"+i, stars+""));
					param.add(new BasicNameValuePair("TableNumber",GlobalVariable.getTableNumber()+""));
                }
                JSONObject json = jsonParser.makeHttpRequest(url_submitReviews,
                        "GET", param);
				 json = jsonParser.makeHttpRequest(url_removeUserOrders,
						"GET", param);
				post=false;
            }
            else if(params[0].equals("update")) {
                List<NameValuePair> paramss = new ArrayList<NameValuePair>();
                JSONParser jsonParser = new JSONParser();
                paramss.add(new BasicNameValuePair("currentCustomer", GlobalVariable.getCustomerUserName()));
                paramss.add(new BasicNameValuePair("tableNumber", GlobalVariable.getTableNumber() + ""));


                JSONObject json = jsonParser.makeHttpRequest(url_updatePaymentSummary,
                        "GET", paramss);
                //	Log.e("Hi", 5 + json.toString());

                try {
                    list = new ArrayList<JSONArray>();
                    //JSONArray orders2=new JSONArray();
                    JSONArray orders = (JSONArray) json.get("orders");
                    if (orders != null) {
                        int len = orders.length();
                        for (int i = 0; i < len; i++) {
                            list.add((JSONArray) orders.get(i));
                        }

                    }


                    Log.e("length", list.toString());

            /*for (int i = 0; i < customers.length(); i++) {
                JSONObject json_data = customers.getJSONObject(i);

                Log.e("Array", customers.getString(i) + json_data);


            }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

			post=true;
            }
			return null;
		}
		@Override
		protected void onPostExecute(String s) {
			dialog.dismiss();
			if(!post){
				new AlertDialog.Builder(ReviewPageActivity.this)

						.setMessage("Your comments have been submitted. Thank you!")
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// continue with delete
								dialog.dismiss();
							}
						})

						.setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
			if(post) {
           /* final String[] breakfast = DynamicMenuFragmentActivity.DummySectionFragment.breakfast;
            final double[] breakfast_price = DynamicMenuFragmentActivity.DummySectionFragment.breakfast_price;
            final String[] lunchanddinner = DynamicMenuFragmentActivity.DummySectionFragment.lunchanddinner;
            final double[] lunchanddinner_price = DynamicMenuFragmentActivity.DummySectionFragment.lunchanddinner_price;
            final String[] desserts = DynamicMenuFragmentActivity.DummySectionFragment.desserts;
            final double[] desserts_price = DynamicMenuFragmentActivity.DummySectionFragment.desserts_price;
            final String[] drinks = DynamicMenuFragmentActivity.DummySectionFragment.drinks;
            final double[] drinks_price = DynamicMenuFragmentActivity.DummySectionFragment.drinks_price;
            final int[] count_1 = DynamicMenuFragmentActivity.DummySectionFragment.count_1;
            final int[] count_2 = DynamicMenuFragmentActivity.DummySectionFragment.count_2;
            final int[] count_3 = DynamicMenuFragmentActivity.DummySectionFragment.count_3;
            final int[] count_4 = DynamicMenuFragmentActivity.DummySectionFragment.count_4;*/
				TableLayout tl = (TableLayout) findViewById(R.id.summarytable);
				//tl.setBackground(getResources().getDrawable(R.drawable.summary_page_white_paper));
				TableRow row = new TableRow(ReviewPageActivity.this);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				TextView SummaryIntro = new TextView(ReviewPageActivity.this);
				//	Typeface face = Typeface.createFromAsset(ReviewPageActivity.this.getAssets(),
				//			"orange juice 2.0.ttf");
				//	SummaryIntro.setTypeface(face);
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
				TableRow rowDesc = new TableRow(ReviewPageActivity.this);
				TextView ItemName = new TextView(ReviewPageActivity.this);
				//TextView Quantity = new TextView(ReviewPageActivity.this);
				TextView Price = new TextView(ReviewPageActivity.this);
				ItemName.setText("Item Name");
				//Quantity.setText("Quantity");
				Price.setText("Price");
				ItemName.setTextColor(Color.parseColor("#fb1d91db"));
				//Quantity.setTextColor(Color.parseColor("#fb1d91db"));
				Price.setTextColor(Color.parseColor("#fb1d91db"));
				//ItemName.setTypeface(face);
				//Quantity.setTypeface(face);
				//	Price.setTypeface(face);
				ItemName.setTextSize(20);
				//Quantity.setTextSize(20);
				Price.setTextSize(20);
				rowDesc.addView(ItemName);
				//rowDesc.addView(Quantity);
				rowDesc.addView(Price);
				tl.addView(rowDesc);
				int Id = 100000;
				//Log.d(, "Count is" + count_4[0] + count_4[1]);
				for (int i = 0; i < list.size(); i++) {
					TableRow tr = new TableRow(ReviewPageActivity.this);
					TableRow tr2 = new TableRow(ReviewPageActivity.this);
					double item_price = 0;
					//int count=0;
					String item = "";
					try {
						item_price = Double.parseDouble(list.get(i).getString(2));
						//	count = Integer.parseInt(list.get(i).getString(2));
						item = list.get(i).getString(1);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Id += 2;

					subtotal += (item_price);
					TextView itemName = new TextView(ReviewPageActivity.this);
					//TextView itemQuantity = new TextView(ReviewPageActivity.this);
					TextView itemPrice = new TextView(ReviewPageActivity.this);
					RatingBar rating = new RatingBar(ReviewPageActivity.this);
					rating.setId(Id);
					itemName.setText(item);
					itemName.setTextColor(Color.parseColor("#fb1d91db"));
					itemName.setTextAlignment(3);
					itemName.setTextSize(20);
					//	itemName.setTypeface(face);
					itemName.setId(Id + 1);

				/*itemQuantity.setText(" @ " + count);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);
				itemQuantity.setTypeface(face);*/
					itemPrice.setText("$" + item_price);
					itemPrice.setTextColor(Color.parseColor("#fb1d91db"));
					itemPrice.setTextAlignment(3);
					itemPrice.setTextSize(20);
					//	itemPrice.setTypeface(face);

					tr.addView(itemName);
					//tr.addView(itemQuantity);
					tr.addView(itemPrice);
					tr2.addView(rating);
					tl.addView(tr);
					tl.addView(tr2);
				}


				TableRow tr = new TableRow(ReviewPageActivity.this);
				TextView itemSubtotal = new TextView(ReviewPageActivity.this);
				itemSubtotal.setId(5555);
				itemSubtotal.setText("Subtotal:");
				itemSubtotal.setTextColor(Color.parseColor("#fb1d91db"));
				itemSubtotal.setTextAlignment(3);
				itemSubtotal.setTextSize(20);
				//	itemSubtotal.setTypeface(face);
				TextView itemSubtotal2 = new TextView(ReviewPageActivity.this);
				itemSubtotal2.setId(5555);
				//	itemSubtotal2.setTypeface(face);
				double val = subtotal;
				val = val * 100;
				val = Math.round(val);
				val = val / 100;
				itemSubtotal2.setText("$" + val);
				itemSubtotal2.setTextColor(Color.parseColor("#fb1d91db"));
				itemSubtotal2.setTextAlignment(3);
				itemSubtotal2.setTextSize(20);
				tr.addView(itemSubtotal);
				tr.addView(itemSubtotal2);
				TableRow tr2 = new TableRow(ReviewPageActivity.this);
				TextView itemTaxes = new TextView(ReviewPageActivity.this);
				//itemTaxes.setTypeface(face);
				itemTaxes.setText("Taxes:");
				itemTaxes.setTextColor(Color.parseColor("#fb1d91db"));
				itemTaxes.setTextAlignment(3);
				itemTaxes.setTextSize(20);
				tr2.addView(itemTaxes);
				TextView itemTaxes2 = new TextView(ReviewPageActivity.this);
				itemTaxes2.setId(5556);
				//	itemTaxes2.setTypeface(face);
				val = subtotal * 0.13;
				val = val * 100;
				val = Math.round(val);
				val = val / 100;
				itemTaxes2.setText("$" + val);
				itemTaxes2.setTextColor(Color.parseColor("#fb1d91db"));
				itemTaxes2.setTextAlignment(3);
				itemTaxes2.setTextSize(20);

				tr2.addView(itemTaxes2);
				TableRow tr3 = new TableRow(ReviewPageActivity.this);
				TextView itemTotalDue = new TextView(ReviewPageActivity.this);
				itemTotalDue.setId(5557);
				itemTotalDue.setText("Total Due:");
				itemTotalDue.setTextColor(Color.parseColor("#fb1d91db"));
				itemTotalDue.setTextAlignment(3);
				itemTotalDue.setTextSize(20);
				//	itemTotalDue.setTypeface(face);
				tr3.addView(itemTotalDue);
				TextView itemTotalDue2 = new TextView(ReviewPageActivity.this);
				itemTotalDue2.setId(5557);
				val = subtotal * tax;
				val = val * 100;
				val = Math.round(val);
				val = val / 100;
				itemTotalDue2.setText("$" + val);
				itemTotalDue2.setTextColor(Color.parseColor("#fb1d91db"));
				itemTotalDue2.setTextAlignment(3);
				itemTotalDue2.setTextSize(20);
				//	itemTotalDue2.setTypeface(face);
				tr3.addView(itemTotalDue2);
				tl.addView(tr);
				tl.addView(tr2);
				tl.addView(tr3);
				TextView reviewText = new TextView(ReviewPageActivity.this);
				reviewText.setText("Additional Comments");
				reviewText.setTextColor(Color.parseColor("#fb1d91db"));
				reviewText.setTextAlignment(3);
				reviewText.setTextSize(20);
				//	reviewText.setTypeface(face);
				EditText reviewBox = new EditText(ReviewPageActivity.this);
				reviewBox.setNestedScrollingEnabled(true);
				reviewBox.setMaxWidth(20);
				Button submitReviews = new Button(ReviewPageActivity.this);
				//	submitReviews.setTypeface(face);
				submitReviews.setText("Submit");
				submitReviews.setBackgroundColor(Color.parseColor("#fb1d91db"));
				submitReviews.setTextColor(Color.WHITE);
				submitReviews.setOnClickListener(new View.OnClickListener() {
					RatingBar bar = (RatingBar) findViewById(200);

					@Override
					public void onClick(View v) {
						submitReview x = new submitReview();
						x.execute("submit");


					}
				});
				TableRow tr4 = new TableRow(ReviewPageActivity.this);
				TableRow tr5 = new TableRow(ReviewPageActivity.this);
				TableRow tr6 = new TableRow(ReviewPageActivity.this);
				tr4.addView(reviewText);
				tr5.addView(reviewBox);
				tr6.addView(submitReviews);
				tl.addView(tr4);
				tl.addView(tr5);
				tl.addView(tr6);
			}

		}

        public float getRating() {
            return bar.getRating();
        }


        public String getItemText() {
            return (String) itemText.getText();
        }
    }


}