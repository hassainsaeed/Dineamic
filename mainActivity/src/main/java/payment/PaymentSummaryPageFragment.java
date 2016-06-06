package payment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.hmkcode.android.sign.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
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
import homePage.HomePageMainActivity;
import loginSignupPage.JSONParser;
import loginSignupPage.LogInActivity;
import loginSignupPage.LogInMenuActivity;
import loginSignupPage.SignUpActivity;
import nfc.NFCDetails;

public class PaymentSummaryPageFragment extends Fragment {
	JSONParser jsonParser = new JSONParser();
	String url_sentOrders = "http://52.11.144.56/sentOrders.php";

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final String[] breakfast = DummySectionFragment.breakfast;
		final double[] breakfast_price = DummySectionFragment.breakfast_price;
		final String[] lunchanddinner = DummySectionFragment.lunchanddinner;
		final double[] lunchanddinner_price = DummySectionFragment.lunchanddinner_price;
		final String[] desserts = DummySectionFragment.desserts;
		final double[] desserts_price = DummySectionFragment.desserts_price;
		final String[] drinks = DummySectionFragment.drinks;
		final double[] drinks_price = DummySectionFragment.drinks_price;
		final int[] count_1 = DummySectionFragment.count_1;
		final int[] count_2 = DummySectionFragment.count_2;
		final int[] count_3 = DummySectionFragment.count_3;
		final int[] count_4 = DummySectionFragment.count_4;
		
		View rootView = inflater.inflate(R.layout.dynamic_menu_summary_page_fragment, container, false);
		//LinearLayout l = (LinearLayout) rootView.findViewById(R.id.scrollview2); 
		//l.setBackground(R.drawable.summary_page_white_paper);
		
		TableLayout tl = (TableLayout) rootView.findViewById(R.id.summarytable);
		//tl.setBackground(getResources().getDrawable(R.drawable.summary_page_white_paper));
		TableRow row = new TableRow(getActivity());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		TextView SummaryIntro = new TextView(PaymentSummaryPageFragment.this.getActivity());
		//Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(),
		//		"orange juice 2.0.ttf");
		//SummaryIntro.setTypeface(face);
		SummaryIntro.setId(4444);
		SummaryIntro.setText(
				"Zak's Dinner \nCheck number 5555\nWaiter Adam\nTable 5\n " + dateFormat.format(date) + "\n\n ");
		SummaryIntro.setTextColor(Color.BLACK);
		SummaryIntro.setTextAlignment(3);
		SummaryIntro.setTextSize(26);
		row.addView(SummaryIntro);
		tl.addView(row);
		double subtotal = 0;
		final double tax = 1.13;
		TableRow rowDesc = new TableRow(getActivity());
		TextView ItemName = new TextView(PaymentSummaryPageFragment.this.getActivity());
		TextView Quantity = new TextView(PaymentSummaryPageFragment.this.getActivity());
		TextView Price = new TextView(PaymentSummaryPageFragment.this.getActivity());
		ItemName.setText("Item Name");
		Quantity.setText("Quantity");
		Price.setText("Price");
	//	ItemName.setTypeface(face);
	//	Quantity.setTypeface(face);
	//	Price.setTypeface(face);
		ItemName.setTextSize(20);
		Quantity.setTextSize(20);
		Price.setTextSize(20);
		rowDesc.addView(ItemName);
		rowDesc.addView(Quantity);
		rowDesc.addView(Price);
		tl.addView(rowDesc);
		Log.d(getTag(), "Count is" +count_4[0]+ count_4[1]);
		for (int i = 0; i < DummySectionFragment.numItems_breakfast; i++) {
			TableRow tr = new TableRow(getActivity());
			if (count_1[i] > 0) {
				subtotal += (breakfast_price[i]) * (count_1[i]);
				TextView itemName = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemQuantity = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(PaymentSummaryPageFragment.this.getActivity());

				itemName.setText(breakfast[i]);
				itemName.setTextColor(Color.BLACK);
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			//	itemName.setTypeface(face);
				itemQuantity.setText(" @ " + count_1[i]);
				itemQuantity.setTextColor(Color.BLACK);
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);
			//	itemQuantity.setTypeface(face);
				itemPrice.setText("$" + breakfast_price[i]);
				itemPrice.setTextColor(Color.BLACK);
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
			//	itemPrice.setTypeface(face);

				tr.addView(itemName);
				tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_lunchanddinner; i++) {
			TableRow tr = new TableRow(getActivity());
			if (count_2[i] > 0) {
				subtotal += (lunchanddinner_price[i]) * (count_2[i]);
				TextView itemName = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemQuantity = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(PaymentSummaryPageFragment.this.getActivity());

				itemName.setText(lunchanddinner[i]);
				itemName.setTextColor(Color.BLACK);
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			//	itemName.setTypeface(face);
				itemQuantity.setText(" @ " + count_2[i]);
				itemQuantity.setTextColor(Color.BLACK);
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);
			//	itemQuantity.setTypeface(face);
				itemPrice.setText("$" + lunchanddinner_price[i]);
				itemPrice.setTextColor(Color.BLACK);
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
			//	itemPrice.setTypeface(face);
				tr.addView(itemName);
				tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_dessert; i++) {
			TableRow tr = new TableRow(getActivity());
			if (count_3[i] > 0) {
				subtotal += (desserts_price[i]) * (count_3[i]);
				TextView itemName = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemQuantity = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(PaymentSummaryPageFragment.this.getActivity());

				itemName.setText(desserts[i]);
				itemName.setTextColor(Color.BLACK);
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
				//itemName.setTypeface(face);
				itemQuantity.setText(" @ " + count_3[i]);
				itemQuantity.setTextColor(Color.BLACK);
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);
			//	itemQuantity.setTypeface(face);
				itemPrice.setText("$" + desserts_price[i]);
				itemPrice.setTextColor(Color.BLACK);
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
			//	itemPrice.setTypeface(face);
				tr.addView(itemName);
				tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_drinks; i++) {
			TableRow tr = new TableRow(getActivity());
			if (count_4[i] > 0) {
				Log.d(getTag(), "There is a drink");
				subtotal += (drinks_price[i]) * (count_4[i]);
				TextView itemName = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemQuantity = new TextView(PaymentSummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(PaymentSummaryPageFragment.this.getActivity());

				itemName.setText(drinks[i]);
				itemName.setTextColor(Color.BLACK);
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			//	itemName.setTypeface(face);
				itemQuantity.setText(" @ " + count_4[i]);
				itemQuantity.setTextColor(Color.BLACK);
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);
			//	itemQuantity.setTypeface(face);
				itemPrice.setText("$" + drinks_price[i]);
				itemPrice.setTextColor(Color.BLACK);
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);
			//	itemPrice.setTypeface(face);

				tr.addView(itemName);
				tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
			}
		}
		TableRow tr = new TableRow(getActivity());
		TextView itemSubtotal = new TextView(PaymentSummaryPageFragment.this.getActivity());
		itemSubtotal.setId(5555);
		itemSubtotal.setText("Subtotal:");
		itemSubtotal.setTextColor(Color.RED);
		itemSubtotal.setTextAlignment(3);
		itemSubtotal.setTextSize(20);
	//	itemSubtotal.setTypeface(face);
		TextView itemSubtotal2 = new TextView(PaymentSummaryPageFragment.this.getActivity());
		itemSubtotal2.setId(5555);
	//	itemSubtotal2.setTypeface(face);
		double val = subtotal;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemSubtotal2.setText("$" + val);
		itemSubtotal2.setTextColor(Color.RED);
		itemSubtotal2.setTextAlignment(3);
		itemSubtotal2.setTextSize(20);
		tr.addView(itemSubtotal);
		tr.addView(itemSubtotal2);
		TableRow tr2 = new TableRow(getActivity());
		TextView itemTaxes = new TextView(PaymentSummaryPageFragment.this.getActivity());
	//	itemTaxes.setTypeface(face);
		itemTaxes.setText("Taxes:");
		itemTaxes.setTextColor(Color.RED);
		itemTaxes.setTextAlignment(3);
		itemTaxes.setTextSize(20);
		tr2.addView(itemTaxes);
		TextView itemTaxes2 = new TextView(PaymentSummaryPageFragment.this.getActivity());
		itemTaxes2.setId(5556);
		//itemTaxes2.setTypeface(face);
		val = subtotal * 0.13;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemTaxes2.setText("$" + val);
		itemTaxes2.setTextColor(Color.RED);
		itemTaxes2.setTextAlignment(3);
		itemTaxes2.setTextSize(20);

		tr2.addView(itemTaxes2);
		TableRow tr3 = new TableRow(getActivity());
		TextView itemTotalDue = new TextView(PaymentSummaryPageFragment.this.getActivity());
		itemTotalDue.setId(5557);
		itemTotalDue.setText("Total Due:");
		itemTotalDue.setTextColor(Color.RED);
		itemTotalDue.setTextAlignment(3);
		itemTotalDue.setTextSize(20);
	//	itemTotalDue.setTypeface(face);
		tr3.addView(itemTotalDue);
		TextView itemTotalDue2 = new TextView(PaymentSummaryPageFragment.this.getActivity());
		itemTotalDue2.setId(5557);
		val = subtotal * tax;
		val = val * 100;
		val = Math.round(val);
		val = val / 100;
		itemTotalDue2.setText("$" + val);
		itemTotalDue2.setTextColor(Color.RED);
		itemTotalDue2.setTextAlignment(3);
		itemTotalDue2.setTextSize(20);
		//itemTotalDue2.setTypeface(face);
		tr3.addView(itemTotalDue2);
		tl.addView(tr);
		tl.addView(tr2);
		tl.addView(tr3);
		TableRow tr4 = new TableRow(getActivity());
		 TableRow.LayoutParams params = new TableRow.LayoutParams(
         	    LayoutParams.WRAP_CONTENT,
         	    LayoutParams.MATCH_PARENT, 0.5f);
		Button payBill = new Button(getActivity());
		payBill.setId(999);
		payBill.setText("Pay Bill");
		payBill.setLayoutParams(params);
		//payBill.setTypeface(face);
		payBill.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent i = new Intent(getActivity(), PayBillActivity.class);

				startActivity(i);


			}
		});
		Button splitItems = new Button(getActivity());
		splitItems.setId(998);
		splitItems.setText("Split Items");
		splitItems.setLayoutParams(params);
	//	splitItems.setTypeface(face);
		splitItems.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               
              
            	Intent i=new Intent(getActivity(),SplitItemsActivity.class);
				
				startActivity(i);
			
               
            }
        });
		Button inviteSomeone = new Button(getActivity());
		inviteSomeone.setId(997);
		inviteSomeone.setText("Invite Someone");
		inviteSomeone.setLayoutParams(params);
		//inviteSomeone.setTypeface(face);
		inviteSomeone.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
               
              
            	Intent i=new Intent(getActivity(),InviteSomeoneActivity.class);

				
				startActivity(i);
			
               
            }
        });
		tr4.addView(payBill);
		tr4.addView(splitItems);
		tr4.addView(inviteSomeone);
		tl.addView(tr4);
		return rootView;
	}

	
}