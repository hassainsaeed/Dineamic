
package payment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import dynamicMenu.DynamicMenuFragmentActivity;
import globalVariables.GlobalVariable;
import loginSignupPage.JSONParser;

public class PaymentActivity extends FragmentActivity  {


    Typeface face;
    static   ArrayList<JSONArray> list;
    private static int refresh=0;
    private static String url_updatePaymentSummary= "http://52.11.144.56/updatePaymentSummary.php";
    TableLayout tl;
    private int tableNumber;

    public void onCreate(Bundle savedInstanceState) {

        tableNumber=this.getIntent().getExtras().getInt("tableNumber");
        super.onCreate(savedInstanceState);
        updatePaymentSummary x= new updatePaymentSummary();
        x.execute();
        setContentView(R.layout.dynamic_menu_summary_page_fragment);
        ActionBar bar = getActionBar();
//for color
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_qr_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.qr_scanner:
                Intent intent = new Intent(PaymentActivity.this, CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        if (refresh==1) {
            this.recreate();
            refresh=0;}
    }

    public class updatePaymentSummary extends AsyncTask<String, String, String> {

        private final ProgressDialog dialog = new ProgressDialog(PaymentActivity.this);
        private boolean post=false;
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Updating Bill....");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> paramss = new ArrayList<NameValuePair>();
            JSONParser jsonParser = new JSONParser();
            paramss.add(new BasicNameValuePair("currentCustomer", GlobalVariable.getCustomerUserName()));
            paramss.add(new BasicNameValuePair("tableNumber", PaymentActivity.this.getIntent().getExtras().getInt("tableNumber") + ""));
            Log.e("customer name", GlobalVariable.getCustomerUserName());
            Log.e("table Number",GlobalVariable.getTableNumber()+"");
            JSONObject json = jsonParser.makeHttpRequest(url_updatePaymentSummary,
                    "GET", paramss);


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
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            final   TableLayout tl = (TableLayout) findViewById(R.id.summarytable);
            double subtotal = 0;
            final double tax = 1.13;

            LinearLayout ll= (LinearLayout) findViewById(R.id.view3);

            TableLayout.LayoutParams rowLp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f);
            TableRow.LayoutParams cellLp = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1.0f);
            TableRow row = new TableRow(PaymentActivity.this);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            TextView SummaryIntro = new TextView(PaymentActivity.this);
            SummaryIntro.setId(4444);
            SummaryIntro.setText(
                    "Zak's Dinner \n"+ "Table "+ getIntent().getExtras().getInt("tableNumber")+"\n" + dateFormat.format(date) + "\n\n");
            SummaryIntro.setTextColor(Color.parseColor("#000000"));
            SummaryIntro.setTextAlignment(3);
            SummaryIntro.setTextSize(26);
            row.addView(SummaryIntro, cellLp);
            tl.addView(row, rowLp);


            TableRow rowDesc = new TableRow(PaymentActivity.this);
            rowDesc.setGravity(17);
            TextView ItemName = new TextView(PaymentActivity.this);
            TextView Price = new TextView(PaymentActivity.this);
            ItemName.setText("Item Name");
            Price.setText("Price");
            ItemName.setTextColor(Color.parseColor("#fb1d91db"));
            Price.setTextColor(Color.parseColor("#fb1d91db"));
            ItemName.setTextSize(20);
            Price.setTextSize(20);

            rowDesc.addView(ItemName, cellLp);
            rowDesc.addView(Price, cellLp);

            tl.addView(rowDesc, rowLp);
            for (int i = 0; i < list.size(); i++) {
                TableRow tr = new TableRow(PaymentActivity.this);
                double item_price= 0;
                String item="";
                try {
                    item_price = Double.parseDouble(list.get(i).getString(2));
                    item=list.get(i).getString(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                subtotal += (item_price) ;
                TextView itemName = new TextView(PaymentActivity.this);
                TextView itemPrice = new TextView(PaymentActivity.this);
                itemName.setText(item);
                itemName.setTextColor(Color.parseColor("#000000"));
                itemName.setTextSize(20);
                itemPrice.setText("$" + item_price);
                itemPrice.setTextColor(Color.parseColor("#000000"));
                itemPrice.setTextSize(20);

                if (i%2==0){
                    tr.setBackgroundColor(Color.parseColor("#d3d3d3"));

                }
                else { tr.setBackgroundColor(Color.parseColor("#a8a8a8"));}
                tr.addView(itemName, cellLp);
                tr.addView(itemPrice, cellLp);
                tl.addView(tr, rowLp);
            }

            //TOTAL AND TAXES
            TableRow tr = new TableRow(PaymentActivity.this);
            TextView itemSubtotal = new TextView(PaymentActivity.this);

            itemSubtotal.setId(5555);
            itemSubtotal.setText("Subtotal:");
            itemSubtotal.setTextColor(Color.parseColor("#fb1d91db"));
            itemSubtotal.setTextAlignment(3);
            itemSubtotal.setTextSize(20);

            TextView itemSubtotal2 = new TextView(PaymentActivity.this);
            itemSubtotal2.setId(5555);

            double val = subtotal;
            val = val * 100;
            val = Math.round(val);
            val = val / 100;

            itemSubtotal2.setText("$" + val);
            itemSubtotal2.setTextColor(Color.parseColor("#fb1d91db"));
            //itemSubtotal2.setTextAlignment(3);
            itemSubtotal2.setTextSize(20);

            tr.addView(itemSubtotal, cellLp);
            tr.addView(itemSubtotal2,cellLp);
            TableRow tr2 = new TableRow(PaymentActivity.this);
            TextView itemTaxes = new TextView(PaymentActivity.this);

            itemTaxes.setText("Taxes:");
            itemTaxes.setTextColor(Color.parseColor("#fb1d91db"));
            itemTaxes.setTextSize(20);
            tr2.addView(itemTaxes,cellLp);
            TextView itemTaxes2 = new TextView(PaymentActivity.this);
            itemTaxes2.setId(5556);
            val = subtotal * 0.13;
            val = val * 100;
            val = Math.round(val);
            val = val / 100;

            itemTaxes2.setText("$" + val);
            itemTaxes2.setTextColor(Color.parseColor("#fb1d91db"));
            itemTaxes2.setTextSize(20);
            tr2.addView(itemTaxes2, cellLp);
            TableRow tr3 = new TableRow(PaymentActivity.this);
            TextView itemTotalDue = new TextView(PaymentActivity.this);
            itemTotalDue.setId(5557);

            itemTotalDue.setText("Total Due:");
            itemTotalDue.setTextColor(Color.parseColor("#fb1d91db"));
            // itemTotalDue.setTextAlignment(3);
            itemTotalDue.setTextSize(20);
            tr3.addView(itemTotalDue, cellLp);
            TextView itemTotalDue2 = new TextView(PaymentActivity.this);
            itemTotalDue2.setId(5557);
            val = subtotal * tax;
            val = val * 100;
            val = Math.round(val);
            val = val / 100;

            itemTotalDue2.setText("$" + val);
            itemTotalDue2.setTextColor(Color.parseColor("#fb1d91db"));
            itemTotalDue2.setTextSize(20);
            tr3.addView(itemTotalDue2,cellLp);
            tl.addView(tr,rowLp);
            tl.addView(tr2, rowLp);
            tl.addView(tr3, rowLp);
            TableRow tr4 = new TableRow(PaymentActivity.this);

            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT  ,
                    ViewGroup.LayoutParams.MATCH_PARENT, 0.5f);
            params.setMargins(10,10,10,10);
            params.gravity= Gravity.CENTER;


            //BUTTONS
            Button payBill = new Button(PaymentActivity.this);
            payBill.setId(999);
            payBill.setText("Pay Bill");
            payBill.setLayoutParams(params);
            payBill.setBackgroundColor(Color.parseColor("#fb1d91db"));
            payBill.setTextColor(Color.WHITE);
            payBill.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(PaymentActivity.this, PayBillActivity.class);
                    startActivity(i);
                }
            });

            Button splitItems = new Button(PaymentActivity.this);
            splitItems.setId(998);
            splitItems.setText("Split Items");
            splitItems.setLayoutParams(params);
            splitItems.setBackgroundColor(Color.parseColor("#fb1d91db"));
            splitItems.setTextColor(Color.WHITE);
            splitItems.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    refresh=1;
                    Intent i = new Intent(PaymentActivity.this, SplitItemsActivity.class);
                    i.putExtra("tableNumber", getIntent().getExtras().getInt("tableNumber"));
                    startActivity(i);
                }
            });
            Button inviteSomeone = new Button(PaymentActivity.this);
            inviteSomeone.setId(997);
            inviteSomeone.setText("Pay For Someone Else");
            inviteSomeone.setLayoutParams(params);
            inviteSomeone.setBackgroundColor(Color.parseColor("#fb1d91db"));
            inviteSomeone.setTextColor(Color.WHITE);

            inviteSomeone.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    refresh=1;
                    Intent i = new Intent(PaymentActivity.this, InviteSomeoneActivity.class);
                    i.putExtra("tableNumber", getIntent().getExtras().getInt("tableNumber"));
                    startActivity(i);
                }
            });

            ll.addView(payBill);
            ll.addView(splitItems);
            ll.addView(inviteSomeone);

        }
    }


}