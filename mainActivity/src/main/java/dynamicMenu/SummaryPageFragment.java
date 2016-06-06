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
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.hmkcode.android.sign.R;

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
import homePage.HomePageMainActivity;
import loginSignupPage.JSONParser;
import loginSignupPage.LogInActivity;
import loginSignupPage.SignUpActivity;
import nfc.NFCDetails;
import payment.PaymentActivity;

public class SummaryPageFragment extends Fragment {
	JSONParser jsonParser = new JSONParser();
	String url_sentOrders = "http://52.11.144.56/sentOrders.php";
	public static Intent i;
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
		final String[] breakfast_comments = DummySectionFragment.breakfast_comments;
		final String[] lunchanddinner_comments =DummySectionFragment.lunchanddinner_comments;
		final String[] desserts_comments = DummySectionFragment.desserts_comments;
		final String[] drinks_comments= DummySectionFragment.drinks_comments;
		
		View rootView = inflater.inflate(R.layout.dynamic_menu_summary_page_fragment, container, false);

		TableLayout tl = (TableLayout) rootView.findViewById(R.id.summarytable);
		LinearLayout ll= (LinearLayout) rootView.findViewById(R.id.view3);
		//tl.setBackground(getResources().getDrawable(R.drawable.summary_page_black_paper));
		TableRow row = new TableRow(getActivity());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		TextView SummaryIntro = new TextView(SummaryPageFragment.this.getActivity());
		SummaryIntro.setId(4444);
		Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(),
				"orange juice 2.0.ttf");
        Log.e("NOOO", getArguments().getInt("tableNumber") + "");
		SummaryIntro.setText(
				"Zak's Dinner \nCheck number 5555\nWaiter Adam\nTable " + getArguments().getInt("tableNumber") + "\n" + dateFormat.format(date) + "\n\n ");
		SummaryIntro.setTextColor(Color.parseColor("#fb1d91db"));


		SummaryIntro.setTextAlignment(3);
		SummaryIntro.setTextSize(26);
	//	SummaryIntro.setTypeface(face);
		row.addView(SummaryIntro);
		tl.addView(row);
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
	//	ItemName.setTypeface(face);
	//	Quantity.setTypeface(face);
	//	Price.setTypeface(face);
		rowDesc.addView(ItemName);
	//	rowDesc.addView(Quantity);
		rowDesc.addView(Price);
		tl.addView(rowDesc);
		Log.d(getTag(), "Count is" +count_4[0]+ count_4[1]);
		for (int i = 0; i < DummySectionFragment.numItems_breakfast; i++) {
			int counter=count_1[i];
			while (counter > 0) {
				TableRow tr = new TableRow(getActivity());
				subtotal += (breakfast_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());

				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());

				itemName.setText(breakfast[i]);
				itemName.setTextColor(Color.parseColor("#fb1d91db"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			//	itemName.setTypeface(face);
			//	itemQuantity.setTypeface(face);
			//	itemPrice.setTypeface(face);

				itemPrice.setText("$" + breakfast_price[i]);
				itemPrice.setTextColor(Color.parseColor("#fb1d91db"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);

				tr.addView(itemName);

				tr.addView(itemPrice);
				tl.addView(tr);
				counter--;
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_lunchanddinner; i++) {
			int counter=count_2[i];

			while (counter > 0) {
				TableRow tr = new TableRow(getActivity());
				subtotal += (lunchanddinner_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());
				//TextView itemQuantity = new TextView(SummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());
				//itemName.setTypeface(face);
				//itemQuantity.setTypeface(face);
				//itemPrice.setTypeface(face);
				itemName.setText(lunchanddinner[i]);
				itemName.setTextColor(Color.parseColor("#fb1d91db"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
				/*itemQuantity.setText(" @ " + count_2[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + lunchanddinner_price[i]);
				itemPrice.setTextColor(Color.parseColor("#fb1d91db"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);

				tr.addView(itemName);
				//tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
				counter--;
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_dessert; i++) {
			int counter=count_3[i];

			while (counter > 0) {
				TableRow tr = new TableRow(getActivity());
				subtotal += (desserts_price[i]);
				TextView itemName = new TextView(SummaryPageFragment.this.getActivity());
				//TextView itemQuantity = new TextView(SummaryPageFragment.this.getActivity());
				TextView itemPrice = new TextView(SummaryPageFragment.this.getActivity());
				//itemName.setTypeface(face);
				//itemQuantity.setTypeface(face);
				//itemPrice.setTypeface(face);
				itemName.setText(desserts[i]);
				itemName.setTextColor(Color.parseColor("#fb1d91db"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			/*	itemQuantity.setText(" @ " + count_3[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + desserts_price[i]);
				itemPrice.setTextColor(Color.parseColor("#fb1d91db"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);

				tr.addView(itemName);
				//tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
				counter--;
			}
		}
		for (int i = 0; i < DummySectionFragment.numItems_drinks; i++) {
			int counter=count_4[i];

		while(counter > 0) {
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
				itemName.setTextColor(Color.parseColor("#fb1d91db"));
				itemName.setTextAlignment(3);
				itemName.setTextSize(20);
			/*	itemQuantity.setText(" @ " + count_4[i]);
				itemQuantity.setTextColor(Color.parseColor("#fb1d91db"));
				itemQuantity.setTextAlignment(3);
				itemQuantity.setTextSize(20);*/
				itemPrice.setText("$" + drinks_price[i]);
				itemPrice.setTextColor(Color.parseColor("#fb1d91db"));
				itemPrice.setTextAlignment(3);
				itemPrice.setTextSize(20);

				tr.addView(itemName);
			//	tr.addView(itemQuantity);
				tr.addView(itemPrice);
				tl.addView(tr);
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
		tr.addView(itemSubtotal);
		tr.addView(itemSubtotal2);
		TableRow tr2 = new TableRow(getActivity());
		TextView itemTaxes = new TextView(SummaryPageFragment.this.getActivity());
		//itemTaxes.setTypeface(face);
		itemTaxes.setText("Taxes:");
		itemTaxes.setTextColor(Color.parseColor("#fb1d91db"));
		itemTaxes.setTextAlignment(3);
		itemTaxes.setTextSize(20);
		tr2.addView(itemTaxes);
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

		tr2.addView(itemTaxes2);
		TableRow tr3 = new TableRow(getActivity());
		TextView itemTotalDue = new TextView(SummaryPageFragment.this.getActivity());
		itemTotalDue.setId(5557);
		itemTotalDue.setText("Total Due:");
		itemTotalDue.setTextColor(Color.parseColor("#fb1d91db"));
		itemTotalDue.setTextAlignment(3);
		itemTotalDue.setTextSize(20);
		//itemTotalDue.setTypeface(face);
		tr3.addView(itemTotalDue);
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
		tr3.addView(itemTotalDue2);
		tl.addView(tr);
		tl.addView(tr2);
		tl.addView(tr3);
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
                //new sendMenuOrder(breakfast, breakfast_price, lunchanddinner, lunchanddinner_price, desserts,
                //	desserts_price, drinks, drinks_price, count_1, count_2, count_3, count_4).execute();
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
                startActivity(i);
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