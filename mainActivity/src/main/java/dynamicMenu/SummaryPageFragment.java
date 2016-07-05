package dynamicMenu;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import com.hmkcode.android.sign.R;

import android.text.TextUtils;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import dynamicMenu.DynamicMenuFragmentActivity.DummySectionFragment;
import globalVariables.GlobalVariable;

import nfc.NFCDetails;
import payment.PaymentActivity;

public class SummaryPageFragment extends Fragment {
	String []breakfast,lunchanddinner,desserts ,drinks ,breakfast_comments,lunchanddinner_comments,desserts_comments,drinks_comments;
	int []count_1,count_2,count_3,count_4 ;
	int numItems_breakfast, numItems_lunchanddinner, numItems_dessert, numItems_drinks;
	double []breakfast_price,lunchanddinner_price ,desserts_price,drinks_price;
	boolean isEmpty;
public SummaryPageFragment(int numItems_breakfast, int numItems_lunchanddinner, int numItems_dessert,int  numItems_drinks, String[]breakfast,double [] breakfast_price,String[] lunchanddinner,double [] lunchanddinner_price,String[] desserts,
						   double []desserts_price,String[] drinks,double [] drinks_price, int []count_1, int []count_2,int [] count_3,int [] count_4,String [] breakfast_comments,String [] lunchanddinner_comments,String [] desserts_comments,String [] drinks_comments){
this.numItems_breakfast=numItems_breakfast;
	this.numItems_lunchanddinner=numItems_lunchanddinner;
	this.numItems_dessert=numItems_dessert;
	this.numItems_drinks=numItems_drinks;
	this.breakfast = breakfast;
this.breakfast_price = breakfast_price;
	 this.lunchanddinner = lunchanddinner;
	this.lunchanddinner_price = lunchanddinner_price;
	this.desserts = desserts;
	this.desserts_price = desserts_price;
	this.drinks = drinks;
	this.drinks_price = drinks_price;
	 this.count_1 = count_1;
	this.count_2 = count_2;
	this.count_3 = count_3;
	this.count_4 = count_4;
	this.breakfast_comments = breakfast_comments;
	this.lunchanddinner_comments = lunchanddinner_comments;
	 this.desserts_comments = desserts_comments;
	this.drinks_comments= drinks_comments;
	this.isEmpty = true;
	GlobalVariable.setIsOrderSent(false);
}
	public static Intent i;
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



		View rootView = inflater.inflate(R.layout.dynamic_menu_summary_page_fragment, container, false);

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.summarytable);
		LinearLayout ll= (LinearLayout) rootView.findViewById(R.id.view3);

		TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				1.0f);
		TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT,
				1.0f);
		//tl.setBackground(getResources().getDrawable(R.drawable.summary_page_black_paper));
		TableRow row = new TableRow(getActivity());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		TextView SummaryIntro = new TextView(SummaryPageFragment.this.getActivity());
		SummaryIntro.setId(4444);
		Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(),
				"orange juice 2.0.ttf");
		Log.e("SummaryPage Created", getArguments().getInt("tableNumber") + "");
		SummaryIntro.setText(
				"Zak's Diner \nCheck number 5555\nWaiter Adam\nTable " + getArguments().getInt("tableNumber") + "\n" + dateFormat.format(date) + "\n\n ");
		SummaryIntro.setTextColor(Color.parseColor("#fb1d91db"));


		SummaryIntro.setTextAlignment(3);
		SummaryIntro.setTextSize(26);
		//	SummaryIntro.setTypeface(face);
		row.addView(SummaryIntro, cellLp);
		tl.addView(row, rowLp);
		double subtotal = 0;
		final double tax = 1.13;
		TableRow rowDesc = new TableRow(getActivity());
		TextView ItemName = new TextView(SummaryPageFragment.this.getActivity());
		//	TextView Quantity = new TextView(SummaryPageFragment.this.getActivity());
		TextView Price = new TextView(SummaryPageFragment.this.getActivity());
		ItemName.setText("Item Name");
		//	Quantity.setText("Quantity");
		Price.setText("Price");
		ItemName.setTextColor(Color.parseColor("#fb1d91db"));
		//	Quantity.setTextColor(Color.parseColor("#fb1d91db"));
		Price.setTextColor(Color.parseColor("#fb1d91db"));
		ItemName.setTextSize(20);
		//	Quantity.setTextSize(20);
		Price.setTextSize(20);
		rowDesc.addView(ItemName, cellLp);
		rowDesc.addView(Price, cellLp);
		isEmpty = true;
		tl.addView(rowDesc, rowLp);
		Log.d(getTag(), "Count is" +count_4[0]+ count_4[1]);
		for (int i = 0; i <numItems_breakfast; i++) {
			int counter=count_1[i];
			while (counter > 0) {
				isEmpty = false;
				TableRow tr = new TableRow(getActivity());
				subtotal += (breakfast_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());

				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());

				itemName.setText(breakfast[i]);
				itemName.setTextColor(Color.parseColor("#000000"));

				itemName.setTextAlignment(3);
				itemName.setTextSize(20);

				//	itemName.setTypeface(face);
				//	itemQuantity.setTypeface(face);
				//	itemPrice.setTypeface(face);

				itemPrice.setText("$" + breakfast_price[i]);
				itemPrice.setTextColor(Color.parseColor("#000000"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
				tr.setBackgroundResource(R.drawable.border);
				itemPrice.setPadding(0,0,0,15);
				itemName.setPadding(20,0,0,0);
				tr.addView(itemName, cellLp);
				tr.addView(itemPrice, cellLp);
				tl.addView(tr, rowLp);
				if (breakfast_comments[i]!=null){
					TableRow tr2 = new TableRow(getActivity());
					TextView itemComment = new TextView(SummaryPageFragment.this.getActivity());
					itemComment.setTextSize(15);

					itemComment.setText(breakfast_comments[i]);
					itemComment.setPadding(20,0,0,0);
					tr2.addView(itemComment, cellLp);
					tl.addView(tr2, rowLp);

				}
				counter--;
			}
		}
		for (int i = 0; i < numItems_lunchanddinner; i++) {
			int counter=count_2[i];

			while (counter > 0) {
				isEmpty = false;
				TableRow tr = new TableRow(getActivity());
				subtotal += (lunchanddinner_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());
				//TextView itemQuantity = new TextView(SummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());
				//itemName.setTypeface(face);
				//itemQuantity.setTypeface(face);
				//itemPrice.setTypeface(face);
				itemName.setText(lunchanddinner[i]);
				itemName.setTextColor(Color.parseColor("#000000"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
				/*itemQuantity.setText(" @ " + count_2[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + lunchanddinner_price[i]);
				itemPrice.setTextColor(Color.parseColor("#000000"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
				tr.setBackgroundResource(R.drawable.border);
				itemPrice.setPadding(0,0,0,15);
				itemName.setPadding(20,0,0,0);
				tr.addView(itemName, cellLp);
				tr.addView(itemPrice, cellLp);
				tl.addView(tr, rowLp);
				if (lunchanddinner_comments[i]!=null){
					TableRow tr2 = new TableRow(getActivity());
					TextView itemComment = new TextView(SummaryPageFragment.this.getActivity());
					itemComment.setTextSize(15);
					itemComment.setText(lunchanddinner_comments[i]);
					itemComment.setPadding(20,0,0,0);
					tr2.addView(itemComment, cellLp);
					tl.addView(tr2, rowLp);

				}
				counter--;
			}
		}
		for (int i = 0; i < numItems_dessert; i++) {
			int counter=count_3[i];

			while (counter > 0) {
				isEmpty = false;
				TableRow tr = new TableRow(getActivity());
				subtotal += (desserts_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());
				//TextView itemQuantity = new TextView(SummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());
				//itemName.setTypeface(face);
				//itemQuantity.setTypeface(face);
				//itemPrice.setTypeface(face);
				itemName.setText(desserts[i]);
				itemName.setTextColor(Color.parseColor("#000000"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			/*	itemQuantity.setText(" @ " + count_3[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + desserts_price[i]);
				itemPrice.setTextColor(Color.parseColor("#000000"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);


				tr.setBackgroundResource(R.drawable.border);
				itemPrice.setPadding(0,0,0,15);
				itemName.setPadding(20,0,0,0);
				tr.addView(itemName, cellLp);
				tr.addView(itemPrice, cellLp);
				tl.addView(tr, rowLp);
				if (desserts_comments[i]!=null){
					TableRow tr2 = new TableRow(getActivity());
					TextView itemComment = new TextView(SummaryPageFragment.this.getActivity());
					itemComment.setTextSize(15);
					itemComment.setText(desserts_comments[i]);
					itemComment.setPadding(20,0,0,0);
					tr2.addView(itemComment, cellLp);
					tl.addView(tr2, rowLp);

				}
				counter--;
			}
		}
		for (int i = 0; i < numItems_drinks; i++) {
			int counter=count_4[i];

			while(counter > 0) {
				isEmpty = false;
				TableRow tr = new TableRow(getActivity());
				//Log.d(getTag(), "There is a drink");
				subtotal += (drinks_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());
				//TextView itemQuantity = new TextView(SummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());
				//itemName.setTypeface(face);
				//itemQuantity.setTypeface(face);
				//	itemPrice.setTypeface(face);
				itemName.setText(drinks[i]);
				itemName.setTextColor(Color.parseColor("#000000"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			/*	itemQuantity.setText(" @ " + count_4[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + drinks_price[i]);
				itemPrice.setTextColor(Color.parseColor("#000000"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);

				tr.setBackgroundResource(R.drawable.border);
				itemPrice.setPadding(0,0,0,15);
				itemName.setPadding(20,0,0,0);
				tr.addView(itemName, cellLp);
				tr.addView(itemPrice, cellLp);
				tl.addView(tr, rowLp);
				if (drinks_comments[i]!=null){
					TableRow tr2 = new TableRow(getActivity());
					TextView itemComment = new TextView(SummaryPageFragment.this.getActivity());
					itemComment.setTextSize(15);
					itemComment.setText(drinks_comments[i]);
					itemComment.setPadding(20,0,0,0);
					tr2.addView(itemComment, cellLp);
					tl.addView(tr2, rowLp);

				}
				counter--;
			}
		}
		TableRow tr = new TableRow(getActivity());
		TextView itemSubtotal = new TextView(SummaryPageFragment.this.getActivity());
		itemSubtotal.setId(5555);
		itemSubtotal.setText("Subtotal:");
		itemSubtotal.setTextColor(Color.parseColor("#fb1d91db"));
		itemSubtotal.setTextAlignment(3);
		itemSubtotal.setTextSize(20);
		//itemSubtotal.setTypeface(face);

		TextView itemSubtotal2 = new TextView(SummaryPageFragment.this.getActivity());
		itemSubtotal2.setId(5555);
		//itemSubtotal2.setTypeface(face);
		double val = subtotal;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemSubtotal2.setText("$" + val);
		itemSubtotal2.setTextColor(Color.parseColor("#fb1d91db"));
		itemSubtotal2.setTextAlignment(3);
		itemSubtotal2.setTextSize(20);
		tr.addView(itemSubtotal, cellLp);
		tr.addView(itemSubtotal2,cellLp);
		TableRow tr2 = new TableRow(getActivity());
		TextView itemTaxes = new TextView(SummaryPageFragment.this.getActivity());
		//itemTaxes.setTypeface(face);
		itemTaxes.setText("Taxes:");
		itemTaxes.setTextColor(Color.parseColor("#fb1d91db"));
		itemTaxes.setTextAlignment(3);
		itemTaxes.setTextSize(20);
		tr2.addView(itemTaxes,cellLp);
		TextView itemTaxes2 = new TextView(SummaryPageFragment.this.getActivity());
		itemTaxes2.setId(5556);
		//itemTaxes2.setTypeface(face);
		val = subtotal * 0.13;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemTaxes2.setText("$" + val);
		itemTaxes2.setTextColor(Color.parseColor("#fb1d91db"));
		itemTaxes2.setTextAlignment(3);
		itemTaxes2.setTextSize(20);
		tr2.addView(itemTaxes2, cellLp);
		TableRow tr3 = new TableRow(getActivity());
		TextView itemTotalDue = new TextView(SummaryPageFragment.this.getActivity());
		itemTotalDue.setId(5557);
		itemTotalDue.setText("Total Due:");
		itemTotalDue.setTextColor(Color.parseColor("#fb1d91db"));
		itemTotalDue.setTextAlignment(3);
		itemTotalDue.setTextSize(20);
		tr3.addView(itemTotalDue, cellLp);
		TextView itemTotalDue2 = new TextView(SummaryPageFragment.this.getActivity());
		itemTotalDue2.setId(5557);
		//itemTotalDue2.setTypeface(face);
		val = subtotal * tax;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemTotalDue2.setText("$" + val);
		itemTotalDue2.setTextColor(Color.parseColor("#fb1d91db"));
		itemTotalDue2.setTextAlignment(3);
		itemTotalDue2.setTextSize(20);
		tr3.addView(itemTotalDue2,cellLp);
		tl.addView(tr,rowLp);
		tl.addView(tr2, rowLp);
		tl.addView(tr3, rowLp);
		TableRow row2 = new TableRow(getActivity());
		TableRow.LayoutParams params = new TableRow.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
		params.setMargins(10,10,10,10);
		Button sendOrder = new Button(getActivity());
		sendOrder.setId(999);
		sendOrder.setLayoutParams(params);
		//sendOrder.setTypeface(face);
		sendOrder.setText("Send Order");
		sendOrder.setBackgroundColor(Color.parseColor("#fb1d91db"));
		sendOrder.setTextColor(Color.WHITE);
		sendOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			/*	new AlertDialog.Builder(getActivity())

				.setMessage("Please tap the send order NFC Tag " )
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
						}
			}).show();*/
				if (isEmpty == false) {
					i = new Intent(getActivity(), NFCDetails.class);
//                Log.e("OK ", GlobalVariable.getCustomerUserName());
					i.putExtra("name", GlobalVariable.getCustomerUserName());
					i.putExtra("breakfast", breakfast);
					i.putExtra("breakfast_price", breakfast_price);
					i.putExtra("lunchanddinner", lunchanddinner);
					i.putExtra("lunchanddinner_price", lunchanddinner_price);
					i.putExtra("desserts", desserts);
					i.putExtra("desserts_price", desserts_price);
					i.putExtra("drinks", drinks);
					i.putExtra("drinks_price", drinks_price);
					i.putExtra("count_1", count_1);
					i.putExtra("count_2", count_2);
					i.putExtra("count_3", count_3);
					i.putExtra("count_4", count_4);
					i.putExtra("breakfast_comments", breakfast_comments);
					i.putExtra("lunchanddinner_comments", lunchanddinner_comments);
					i.putExtra("desserts_comments", desserts_comments);
					i.putExtra("drinks_comments", drinks_comments);
					startActivity(i);
					Log.e("Global Variable isSent:", "" + GlobalVariable.getIsOrderSent());
				} else {
					new AlertDialog.Builder(getActivity())
							.setTitle("Error!")
							.setMessage("Please select at least one food items from the Menu before sending")
							.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).show();
				}
			}
		});


		Button paymentOptions = new Button(getActivity());
		paymentOptions.setId(998);
		//  paymentOptions.setTypeface(face);
		paymentOptions.setText("Payment Options");
		paymentOptions.setBackgroundColor(Color.parseColor("#fb1d91db"));
		paymentOptions.setLayoutParams(params);
		paymentOptions.setTextColor(Color.WHITE);
		paymentOptions.setPadding(10,0,0,0);
		paymentOptions.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			/*	new AlertDialog.Builder(getActivity())

				.setMessage("Please tap the send order NFC Tag " )
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
						}
			}).show();*/
				Log.e("Global Variable isSent:", "" + GlobalVariable.getIsOrderSent());
				if(GlobalVariable.getIsOrderSent() == true){
					i = new Intent(getActivity(), PaymentActivity.class);
					i.putExtra("name", GlobalVariable.getCustomerUserName());
					i.putExtra("tableNumber", getArguments().getInt("tableNumber"));
					i.putExtra("breakfast", breakfast);
					i.putExtra("breakfast_price", breakfast_price);
					i.putExtra("lunchanddinner", lunchanddinner);
					i.putExtra("lunchanddinner_price", lunchanddinner_price);
					i.putExtra("desserts", desserts);
					i.putExtra("desserts_price", desserts_price);
					i.putExtra("drinks", drinks);
					i.putExtra("drinks_price", drinks_price);
					i.putExtra("count_1", count_1);
					i.putExtra("count_2", count_2);
					i.putExtra("count_3", count_3);
					i.putExtra("count_4", count_4);
					startActivity(i);}
				else{
					new AlertDialog.Builder(getActivity())
					.setTitle("Error!")
					.setMessage("You must Send your Order first before you can go pay your bill")
							.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).show();
				}
				//new sendMenuOrder(breakfast, breakfast_price, lunchanddinner, lunchanddinner_price, desserts,
				//	desserts_price, drinks, drinks_price, count_1, count_2, count_3, count_4).execute();
				//new sendMenuOrder(breakfast, breakfast_price, lunchanddinner, lunchanddinner_price, desserts,
				//	desserts_price, drinks, drinks_price, count_1, count_2, count_3, count_4).execute();
			}
		});
		//row2.addView(sendOrder);
		//row2.addView(paymentOptions);
		ll.addView(sendOrder);
		ll.addView(paymentOptions);
		return rootView;
	}




}