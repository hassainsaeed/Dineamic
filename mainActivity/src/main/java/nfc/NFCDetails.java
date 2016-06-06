package nfc;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.hmkcode.android.sign.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import dynamicMenu.DishStatusActivity;
import dynamicMenu.DynamicMenuFragmentActivity;
import dynamicMenu.MenuSplashActivity;
import dynamicMenu.SummaryPageFragment;
import globalVariables.GlobalVariable;

/**
 * Activity for reading data from an NDEF Tag.
 *
 * @author Hamad Shaikh
 *
 */
public class NFCDetails extends Activity {
 
 public static final String MIMETYPE_TEXT_PLAIN = "text/plain";
 public static final String TAG = "NfcTut";

 String []breakfast,lunchanddinner,desserts ,drinks ,breakfast_comments,lunchanddinner_comments,desserts_comments,drinks_comments;
 int []count_1,count_2,count_3,count_4 ;
 double []breakfast_price,lunchanddinner_price ,desserts_price,drinks_price;
 private TextView tV_ReadNFC;
 private NfcAdapter nfcAdapt;
    String customerName;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_nfcdetails);
  ActionBar bar = getActionBar();
//for color
  bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));

  //initialize control

//  Log.e(TAG,"BYEE"+count_1[0]);

  tV_ReadNFC = (TextView) findViewById(R.id.tV_ReadNFC);
  //initialise nfc adapter 
  nfcAdapt = NfcAdapter.getDefaultAdapter(this);
  //check is NFC adapter initialized null when device doesn't support NFC
  if (nfcAdapt == null) {
   // device deosn't support nfc
   Toast.makeText(this, "your device doesn't support NFC.", Toast.LENGTH_SHORT).show();
   finish();
   return;
  }
  //check is NFC adapter feature enabled
  if (!nfcAdapt.isEnabled()) {
   tV_ReadNFC.setText("NFC is disabled.");
  } else {
   tV_ReadNFC.setText(R.string.attachNFCToRead);
  }
  handleIntent(getIntent());
 }
  
 @Override
 protected void onResume() {
  super.onResume();
  /*
   * It's important, that the activity is in the foreground (resumed). Otherwise
   * an IllegalStateException is thrown.
   */
  requestForegroundDispatch(this, nfcAdapt);
 }
  
 @Override
 protected void onPause() {
   
  //Call this before onPause, to avoid an IllegalArgumentException.
  stopForegroundDispatch(this, nfcAdapt);
  super.onPause();
 }
  
 @Override
 protected void onNewIntent(Intent intent) {
  /*
   * This method gets called, when a new Intent gets associated with the current activity instance.
   * Instead of creating a new activity, onNewIntent will be called.
   * In our case this method gets called, when the user attaches a Tag to the device.
   */
  handleIntent(intent);
 }
  
 private void handleIntent(Intent intent) {
  //get action from intent
  String action = intent.getAction();
  //is action matches the NDEF_DISCOVERED
  if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
   //what is the mime type
   String type = intent.getType();
   //is text plain or not
   if (MIMETYPE_TEXT_PLAIN.equals(type)) {
    //create tag instance and retrieve extended data from intent
    Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
    //execute background task 
    new NdefReaderBgTask().execute(tag);
     
   } else {
    Log.d(TAG, "mime type is not text/plain: " + type);
   }
  }
  //is action matches the ACTION_TECH_DISCOVERED
  else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
    
   // In case we would still use the Tech Discovered Intent
   Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
   //get the available technologies 
   String[] techList = tag.getTechList();
   //get class name
   String searchedTech = Ndef.class.getName();
    
   for (String tech : techList) {
    //tag matched then execute background task
    if (searchedTech.equals(tech)) {
     new NdefReaderBgTask().execute(tag);
     break;
    }
   }
  }
 }
  
 /**
  * @param act The corresponding {@link Activity} requesting the foreground dispatch.
  * @param adp The {@link NfcAdapter} used for the foreground dispatch.
  */
 public static void requestForegroundDispatch(final Activity act, NfcAdapter adp) {
  //create instance of intent
  final Intent intent = new Intent(act.getApplicationContext(), act.getClass());
  //set flags on top
  intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
  //crate instance of pending intent
  final PendingIntent pendingIntent = PendingIntent.getActivity(act.getApplicationContext(), 0, intent, 0);
  //create intent filters array
  IntentFilter[] filters = new IntentFilter[1];
  //create 2D array of techlist String
  String[][] techList = new String[][]{};
 
  // Note: This is the same filter as in our manifest.
  filters[0] = new IntentFilter();
  filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
  filters[0].addCategory(Intent.CATEGORY_DEFAULT);
  try {
   //add data type
   filters[0].addDataType(MIMETYPE_TEXT_PLAIN);
  } catch (MalformedMimeTypeException e) {
   //throw exception on different mime type
   throw new RuntimeException("Check your mime type.");
  }
  //enable foreground dispatch to current activity
  adp.enableForegroundDispatch(act, pendingIntent, filters, techList);
 }
 
 /**
  * @param act The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
  * @param adp The {@link NfcAdapter} used for the foreground dispatch.
  */
 public static void stopForegroundDispatch(final Activity act, NfcAdapter adp) {
  adp.disableForegroundDispatch(act);
 }
  
 /**
  * Background task for reading the data. Do not block the UI thread while reading.
  * @author Hamad Shaikh
  *
  */
 private class NdefReaderBgTask extends AsyncTask<Tag, Void, String> {
 
  @Override
  protected String doInBackground(Tag... params) {
   Tag tag = params[0];
    
   Ndef ndef = Ndef.get(tag);
   if (ndef == null) {
    // when NDEF is not supported by this Tag. 
    return null;
   }
   //Get the NdefMessage that was read from the tag at discovery time. 
   NdefMessage ndefMessage = ndef.getCachedNdefMessage();
   //Get the NDEF Records inside this NDEF Message.
   NdefRecord[] records = ndefMessage.getRecords();
   for (NdefRecord ndefRecord : records) {
    if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
     try {
      return readNDEFRecordText(ndefRecord);
     } catch (UnsupportedEncodingException e) {
      Log.e(TAG, "Unsupported Encoding", e);
     }
    }
   }
 
   return null;
  }
   
  private String readNDEFRecordText(NdefRecord record) throws UnsupportedEncodingException {
   /*
    * See NFC forum specification for "Text Record Type Definition" at 3.2.1 
    *
    * http://www.nfc-forum.org/specs/
    * 
    * bit_7 defines encoding
    * bit_6 reserved for future use, must be 0
    * bit_5..0 length of IANA language code
    */
   // get record pay load variable length
   byte[] payload = record.getPayload();
 
   // Get the Text Encoding
   String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
 
   // Get the Language Code
   int languageCodeLength = payload[0] & 0063;
    
   // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
   // e.g. "en"
    
   // Get the Text
   return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
  }
   
  @Override
  protected void onPostExecute(String result) {
   if (result.substring(1,result.length()).equals("Send Order")) {
       Bundle b = SummaryPageFragment.i.getExtras();
       int tableNumber=Integer.parseInt(result.substring(0, 1));
       customerName=b.getString("name");
       breakfast = b.getStringArray("breakfast");
       breakfast_price = b.getDoubleArray("breakfast_price");
       lunchanddinner = b.getStringArray("lunchanddinner");
       lunchanddinner_price = b.getDoubleArray("lunchanddinner_price");
       desserts = b.getStringArray("desserts");
       desserts_price = b.getDoubleArray("desserts_price");
       drinks = b.getStringArray("drinks");
       drinks_price = b.getDoubleArray("drinks_price");
       count_1 = b.getIntArray("count_1");
       count_2 = b.getIntArray("count_2");
       count_3 = b.getIntArray("count_3");
       count_4 = b.getIntArray("count_4");

      breakfast_comments= b.getStringArray("breakfast_comments");
       lunchanddinner_comments=b.getStringArray("lunchanddinner_comments");
      desserts_comments= b.getStringArray("desserts_comments");
     drinks_comments=  b.getStringArray("drinks_comments");



	   if(breakfast!=null || lunchanddinner!=null ||drinks!=null || desserts!=null){
   // tV_ReadNFC.setText("Your Order is sent !\nPlease enjoy your time until the waiter brings your order");

        AlertDialog.Builder alertbox = new AlertDialog.Builder(NFCDetails.this);
        alertbox.setMessage("Your Order is sent !\nPlease enjoy your time until the waiter brings your order");
           alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   Intent i = new Intent(NFCDetails.this, DynamicMenuFragmentActivity.class);
                   startActivity(i);
               }
           })

                  .setIcon(android.R.drawable.ic_dialog_alert)
                   .show();

		new dynamicMenu.sendMenuOrder(tableNumber,customerName,breakfast, breakfast_price, lunchanddinner, lunchanddinner_price, desserts,
				desserts_price, drinks, drinks_price, count_1, count_2, count_3, count_4,breakfast_comments,lunchanddinner_comments,desserts_comments,drinks_comments, NFCDetails.this).execute();}
	   else {//tV_ReadNFC.setText("Please press the send button and then tap again");

        AlertDialog.Builder alertbox = new AlertDialog.Builder(NFCDetails.this);
        alertbox.setMessage("Please press the send button and then tap again");
           alertbox.show();
           alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   Intent i = new Intent(NFCDetails.this, DynamicMenuFragmentActivity.class);
                   startActivity(i);
               }
           })

                   .setIcon(android.R.drawable.ic_dialog_alert)
                   .show();
       }



   }

    else if(result.substring(1,result.length()).equals("Call Waiter")){
    //tV_ReadNFC.setText("Your waiter is called! You will be served shortly.");
    new CallWaiter(NFCDetails.this,result).execute();
       AlertDialog.Builder alertbox = new AlertDialog.Builder(NFCDetails.this);
       alertbox.setMessage("Your waiter is called! You will be served shortly.");
       alertbox.show();
       alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
               Intent i = new Intent(NFCDetails.this, DynamicMenuFragmentActivity.class);
               startActivity(i);
           }
       })

               .setIcon(android.R.drawable.ic_dialog_alert)
               .show();
   }

   else if(result.substring(1,result.length()).equals("Dish Status")){
    Intent i = new Intent(NFCDetails.this, DishStatusActivity.class);
    startActivity(i);
   }
   else  if (result.substring(1,result.length()).equals("Dynamic Menu")) {

       int tableNumber=Integer.parseInt(result.substring(0,1));
       if (GlobalVariable.getCustomerUserName().equals("")){
        new AlertDialog.Builder(NFCDetails.this)
                .setTitle("Login Required")
                .setMessage("Please login to your account")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
                 }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
       }
    else{
    Intent i = new Intent(NFCDetails.this, MenuSplashActivity.class);
    GlobalVariable.setTableNumber(tableNumber);
       i.putExtra("tableNumber",tableNumber);
       startActivity(i);}
   }
	   
  }
 }
}