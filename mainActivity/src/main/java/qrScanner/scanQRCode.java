package qrScanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import bookingTable.BookingTableMainActivity;
import dynamicMenu.DishStatusActivity;
import dynamicMenu.DynamicMenuFragmentActivity;
import menu.Menu_pdf;
import nfc.CallWaiter;

/**
 * Created by Hussain on 2016-06-10.
 */
public class scanQRCode extends AsyncTask<String, String, String> {

    int requestCode;
    int resultCode;
    Intent intent;
    private Context context;
    String[] contents;
    String format;
    boolean isNotDineAmicQRCode = false;
    boolean isResultCancelled = false;
    boolean isWaiterCalled = false;
    boolean isSendOrderPressed = false;

    public scanQRCode (int RequestCode, int ResultCode, Intent Intent, Context Context) {
        this.requestCode = RequestCode;
        this.resultCode = ResultCode;
        this.intent = Intent;
        this.context = Context;
    }



    protected String doInBackground(String... args) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                //handle succesful scan
                contents = intent.getStringExtra("SCAN_RESULT").split("_");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");


                if (contents[0].equals("BookingTableMainActivity")) {
                    Intent bookingTableIntent = new Intent(context, BookingTableMainActivity.class);
                    bookingTableIntent.putExtra("Restaurant Name",contents[1]);
                    context.startActivity(bookingTableIntent);
                } else if (contents[0].equals("Menu")) {
                    Intent staticMenuIntent = new Intent(context, Menu_pdf.class);
                    staticMenuIntent.putExtra("Restaurant Name",contents[1]);
                    context.startActivity(staticMenuIntent);
                } else if (contents[0].equals("DynamicMenuFragmentActivity")) {
                    Intent dynamicMenuIntent = new Intent(context, DynamicMenuFragmentActivity.class);
                    context.startActivity(dynamicMenuIntent);
                } else if (contents[0].equals("CallWaiterToTable")) {
                    isWaiterCalled = true;
                } else if (contents[0].equals("DishStatus")) {
                    Intent i = new Intent(context, DishStatusActivity.class);
                    context.startActivity(i);
                } else if (contents[0].equals("SendOrderToKitchen")) {
                    isSendOrderPressed = true;
                }  else {
                    isNotDineAmicQRCode = true;
                }
                //Compare the String contents and formats… if it matches with the expected contents and format then we launch the new Android activity for Book a Table or Interactive Menu or Send Order or etc in this section of code
                Log.d("contents", "contents: " + contents);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                isResultCancelled = true;
                Log.d("contents", "RESULT_CANCELED");
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Toast toast = Toast.makeText(context, "FORMAT: " + format + " CONTENT: " + contents, Toast.LENGTH_SHORT);
        toast.show();

        if (isNotDineAmicQRCode == true) {
            new AlertDialog.Builder(context)
                    .setTitle("Error!")
                    .setMessage("Sorry, but this app could not read that barcode. Please scan a Dineamic QR Code")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Nothing
                        }
                    }).show();
        } else if (isWaiterCalled == true) {
            //tV_ReadNFC.setText("Your waiter is called! You will be served shortly.");
            new CallWaiter(context,contents[1],contents[2]).execute();
            new AlertDialog.Builder(context)
                    .setTitle("Waiter has been Called")
                    .setMessage("Your waiter has been called to your table. You will be served shortly.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Nothing
                        }
                    }).show();

        } else if (isSendOrderPressed == true) {
            new AlertDialog.Builder(context)
                    .setTitle("Error!")
                    .setMessage("Sorry, but you can only send your order to the kitchen through the 'Send Order' button of the interactive menu")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Nothing
                        }
                    }).show();
        }

        if (isResultCancelled == true) {
            toast = Toast.makeText(context, "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
            AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
            alertbox.setMessage("No scan data received!");
            alertbox.show();
        }
    }
}