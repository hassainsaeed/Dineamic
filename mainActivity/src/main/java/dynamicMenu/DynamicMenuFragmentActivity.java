
package dynamicMenu;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import globalVariables.GlobalVariable;
import homePage.HomePageMainActivity;
import qrScanner.scanQRCode;
import dynamicMenu.importMenu;

public class DynamicMenuFragmentActivity extends FragmentActivity implements ActionBar.TabListener {


    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    static ViewPager mViewPager;
    static int tableNumber;
    static boolean found=false;
      int numItems_breakfast;
      int numItems_lunchanddinner;
      int numItems_dessert;
      int numItems_drinks;

    //need a count for each fragment
      int[] count_1;
      int[] count_2;
      int[] count_3;
      int[] count_4;
      String[] breakfast;
      double[] breakfast_price;
      String[] breakfast_nutrition;
      String[] breakfast_ingredients;
      String[] lunchanddinner;
      double[] lunchanddinner_price;
      String[] lunchanddinner_nutrition;
      String[] lunchanddinner_ingredients;
      String[] desserts;
      double[] desserts_price;
      String[] desserts_nutrition;
      String[] desserts_ingredients;
     String[] drinks;
      double[] drinks_price;
      String[] drinks_nutrition;
      String[] breakfast_comments;
     String[] lunchanddinner_comments;
      String[] desserts_comments;
      String[] drinks_comments;
     String[] breakfast_images;
    //  {R.drawable.steak_and_eggs, R.drawable.breakfast_burrito, R.drawable.pancakes, R.drawable.oatmeal, R.drawable.cheeseomlette};

     String[] lunchanddinner_images;

    //    {R.drawable.grilled_cheese, R.drawable.crispy_chicken, R.drawable.chicken_caesar, R.drawable.blt, R.drawable.pulled_pork, R.drawable.chilli_burger, R.drawable.nachos};

     String[] desserts_images;
    //{R.drawable.cheesecake, R.drawable.friedmars, R.drawable.bananasplits,};

     String[] drinks_images;
    //  {R.drawable.orange_juice, R.drawable.tea, R.drawable.coffee, R.drawable.hot_chocolate, R.drawable.mint_chocolate};


    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
//        importMenu menuTask= new importMenu();
//        try {
//            menuTask.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        menuTask.execute();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.dynamic_menu_main_fragment);
        ActionBar bar = getActionBar();
//for color
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));
        Bundle b = getIntent().getExtras();

        setVariables();
        tableNumber = GlobalVariable.getTableNumber();
        GlobalVariable.setTableNumber(tableNumber);
       /* AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(System.currentTimeMillis());
        time.add(Calendar.SECOND, 5);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);*/

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setBackgroundColor(Color.WHITE);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
           /* View tabView = new View(this);
            tabView.setBackgroundColor(Color.WHITE); // set custom color
            tab.setCustomView(tabView);*/

            actionBar.addTab(actionBar.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));

        }
    }

    public void setVariables () {
        numItems_breakfast = GlobalVariable.getMenuNumberOfItems()[0];
        numItems_lunchanddinner = GlobalVariable.getMenuNumberOfItems()[1];
        numItems_dessert =  GlobalVariable.getMenuNumberOfItems()[2];
        numItems_drinks =  GlobalVariable.getMenuNumberOfItems()[3];

        //need a count for each fragment
        count_1 = new int[numItems_breakfast];
        count_2 = new int[numItems_lunchanddinner];
        count_3 = new int[numItems_dessert];
        count_4 = new int[numItems_drinks];
        breakfast = new String[numItems_breakfast];
        breakfast_price = new double[numItems_breakfast];
        breakfast_nutrition = new String[numItems_breakfast];
        breakfast_ingredients = new String[numItems_breakfast];
        lunchanddinner = new String[numItems_lunchanddinner];
        lunchanddinner_price = new double[numItems_lunchanddinner];
        lunchanddinner_nutrition = new String[numItems_lunchanddinner];
        lunchanddinner_ingredients = new String[numItems_lunchanddinner];
        desserts = new String[numItems_dessert];
        desserts_price = new double[numItems_dessert];
        desserts_nutrition = new String[numItems_dessert];
        desserts_ingredients = new String[numItems_dessert];
        drinks = new String[numItems_drinks];
        drinks_price = new double[numItems_drinks];
        drinks_nutrition = new String[numItems_drinks];
        breakfast_comments = new String[numItems_breakfast];
        lunchanddinner_comments = new String[numItems_lunchanddinner];
        desserts_comments = new String[numItems_dessert];
        drinks_comments= new String[numItems_drinks];
        breakfast_images = new String[numItems_breakfast];
        //  {R.drawable.steak_and_eggs, R.drawable.breakfast_burrito, R.drawable.pancakes, R.drawable.oatmeal, R.drawable.cheeseomlette};

        lunchanddinner_images = new String[numItems_lunchanddinner];

        //    {R.drawable.grilled_cheese, R.drawable.crispy_chicken, R.drawable.chicken_caesar, R.drawable.blt, R.drawable.pulled_pork, R.drawable.chilli_burger, R.drawable.nachos};

        desserts_images = new String[numItems_dessert];
        //{R.drawable.cheesecake, R.drawable.friedmars, R.drawable.bananasplits,};

        drinks_images = new String[numItems_drinks];
        //  {R.drawable.orange_juice, R.drawable.tea, R.drawable.coffee, R.drawable.hot_chocolate, R.drawable.mint_chocolate};

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(DynamicMenuFragmentActivity.this, HomePageMainActivity.class));
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_qr_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.qr_scanner:
                Intent intent = new Intent(DynamicMenuFragmentActivity.this, CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        new scanQRCode(requestCode,resultCode,intent,DynamicMenuFragmentActivity.this).execute();
    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {


        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {

                case 0:

                    Fragment fragment1 = new DummySectionFragment();
                    Bundle args1 = new Bundle();
                    args1.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    args1.putInt("tableNumber", tableNumber);
                    fragment1.setArguments(args1);
                    return fragment1;
                case 1:

                    Fragment fragment2 = new DummySectionFragment();
                    Bundle args2 = new Bundle();
                    args2.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    args2.putInt("tableNumber", tableNumber);
                    fragment2.setArguments(args2);
                    return fragment2;
                case 2:
                    Fragment fragment3 = new DummySectionFragment();
                    Bundle args3 = new Bundle();
                    args3.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    args3.putInt("tableNumber", tableNumber);
                    fragment3.setArguments(args3);
                    return fragment3;
                case 3:

                    Fragment fragment4 = new DummySectionFragment();
                    Bundle args4 = new Bundle();
                    args4.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    args4.putInt("tableNumber", tableNumber);
                    fragment4.setArguments(args4);
                    return fragment4;



                default:

                    Fragment fragment5 = new SummaryPageFragment(numItems_breakfast, numItems_lunchanddinner, numItems_dessert, numItems_drinks,breakfast, breakfast_price, lunchanddinner, lunchanddinner_price, desserts,
                            desserts_price, drinks, drinks_price, count_1, count_2, count_3, count_4,breakfast_comments,lunchanddinner_comments,desserts_comments,drinks_comments);
                    Bundle args5 = new Bundle();
                    args5.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                    Log.e("Waaay", tableNumber + "");
                    args5.putInt("tableNumber", tableNumber);
                    fragment5.setArguments(args5);
                    return fragment5;


            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                //case 0:

                //return "Starters";

                case 0:

                    return "BREAKFAST";
                case 1:

                    return "LUNCH & DINNER";
                case 2:

                    return "DESSERTS";
                case 3:

                    return "DRINKS";
                default:

                    return "SUMMARY PAGE";
            }
        }
    }


    public class DummySectionFragment extends Fragment {

        public static final String ARG_SECTION_NUMBER = "section_number";


        @SuppressWarnings("deprecation")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            ArrayList<JSONArray> menuList=GlobalVariable.getMenuListz();

            int index=0;
            for (int i = 0; i < menuList.size(); i++) {
                try {
                    if (menuList.get(i).getString(2).equals("breakfast")) {
                        breakfast[index] = menuList.get(i).getString(0);
                        breakfast_price[index] = menuList.get(i).getDouble(1);
                        breakfast_nutrition[index] = menuList.get(i).getString(3);
                        breakfast_ingredients[index] = menuList.get(i).getString(4);
                        breakfast_images[index] = menuList.get(i).getString(5);
                        index++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            index=0;
            for (int i = 0; i < menuList.size(); i++) {
                try {
                    if (menuList.get(i).getString(2).equals("lunchanddinner")) {
                        lunchanddinner[index] = menuList.get(i).getString(0);
                        lunchanddinner_price[index] = menuList.get(i).getDouble(1);
                        lunchanddinner_nutrition[index] = menuList.get(i).getString(3);
                        lunchanddinner_ingredients[index] = menuList.get(i).getString(4);
                        lunchanddinner_images[index] = menuList.get(i).getString(5);
                        index++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            index=0;
            for (int i = 0; i < menuList.size(); i++) {
                try {
                    if (menuList.get(i).getString(2).equals("desserts")) {
                        desserts[index] = menuList.get(i).getString(0);
                        desserts_price[index] = menuList.get(i).getDouble(1);
                        desserts_nutrition[index] = menuList.get(i).getString(3);
                        desserts_ingredients[index] = menuList.get(i).getString(4);
                        desserts_images[index] = menuList.get(i).getString(5);
                        index++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            index=0;
            for (int i = 0; i < menuList.size(); i++) {
                try {
                    if (menuList.get(i).getString(2).equals("drinks")) {
                        drinks[index] = menuList.get(i).getString(0);
                        drinks_price[index] = menuList.get(i).getDouble(1);
                        drinks_nutrition[index] = menuList.get(i).getString(3);
                        drinks_images[index] = menuList.get(i).getString(5);
                        index ++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }




            View rootView = inflater.inflate(R.layout.dynamic_menu_fragment_section_dummy, container, false);
            Bundle args = getArguments();

            int temp = args.getInt(ARG_SECTION_NUMBER);

            //if breakfast fragment
            if (temp == 1) {
                // Get the TableLayout
                TableLayout tl = (TableLayout) rootView.findViewById(R.id.maintable);
                ScrollView ll = (ScrollView) rootView.findViewById(R.id.scrollview1);
                tl.setBackgroundColor(Color.WHITE);
                ll.setBackgroundColor(Color.WHITE);
                // Go through each item in the array
                for (int current = 0; current < numItems_breakfast; current++) {
                    final int current_temp = current; //need this for the counter because it has to be final

                    // Create a TableRow and give it an ID
                    TableRow tr = new TableRow(getActivity());
                    tr.setId(1000 + current);

                    // Create a TextView
                    TextView labelTV = new TextView(DummySectionFragment.this.getActivity());
                    labelTV.setId(2000 + current);
                    labelTV.setText(breakfast[current]);
                    labelTV.setTextColor(Color.parseColor("#fb1d91db"));
                    labelTV.setTextAlignment(3);
                    labelTV.setTextSize(20);
                    android.widget.TableRow.LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);

                    //create the more info button
                    Button moreinfo = new Button(getActivity());
                    moreinfo.setId(3000 + current);
                    moreinfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_moreinfobutton));


                    moreinfo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String breakfast_nutrition_split[] = breakfast_nutrition[current_temp].split("\n"); //NOTSURE
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_pop_up, null,false);

                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);

                            //create all the rows for the table
                            TableRow trAmount = (TableRow)popupView.findViewById(R.id.rowAmount);
                            TableRow trCalories = (TableRow)popupView.findViewById(R.id.rowCalories);// NOTSURE
                            TableRow trFat = (TableRow)popupView.findViewById(R.id.rowFat);
                            TableRow trCarbs = (TableRow)popupView.findViewById(R.id.rowCarbs);
                            TableRow trSugar =(TableRow)popupView.findViewById(R.id.rowSugar);
                            TableRow trDescription =(TableRow)popupView.findViewById(R.id.rowDescription);

                            // Create a TextView for each row
                            TextView amountTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView caloriesTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView fatTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView carbsTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView sugarTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView descriptionTV = new TextView(DummySectionFragment.this.getActivity());

                            //set specifications for each textview
                            amountTV.setText(breakfast_nutrition_split[0]);
                            //caloriesTV.setBackgroundResource(R.drawable.border);
                            amountTV.setTextColor(Color.BLACK);
                            amountTV.setTypeface(null, Typeface.BOLD);
                            //caloriesTV.setTextColor(Color.parseColor("#fb1d91db"));
                            amountTV.setTextAlignment(3);
                            amountTV.setTextSize(25);

                            // add the rest of the nutritional info
                            caloriesTV.setText(breakfast_nutrition_split[1]);
                            fatTV.setText(breakfast_nutrition_split[2]);
                            carbsTV.setText(breakfast_nutrition_split[3]);
                            sugarTV.setText(breakfast_nutrition_split[4]);
                            descriptionTV.setText(Html.fromHtml("<b> DESCRIPTION</b>: "+"<br>"+breakfast_ingredients[current_temp]));
                            descriptionTV.setWidth(0); //makes text wrap around and not cut
                            descriptionTV.setPadding(10,0,0,0);


                            trAmount.addView(amountTV);
                            trCalories.addView(caloriesTV);
                            trFat.addView(fatTV);
                            trCarbs.addView(carbsTV);
                            trSugar.addView(sugarTV);
                            trDescription.addView(descriptionTV);

                            ImageView nutritionImageView = (ImageView) popupView.findViewById(R.id.popupimage);
                            //   new DownloadImageTask((ImageView)nutritionImageView)
                            //         .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");

                            Picasso.with(DynamicMenuFragmentActivity.this).load(breakfast_images[current_temp]).into(nutritionImageView);
                            // nutritionImageView.setImageResource();
                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });

                        }
                    });

                    //Create a TextView for price
                    TextView valueTV = new TextView(getActivity());
                    valueTV.setId(4000 + current);
                    valueTV.setText("$" + breakfast_price[current]);
                    valueTV.setTextAlignment(15);
                    valueTV.setTextSize(17);
                    valueTV.setTextColor(Color.parseColor("#fb1d91db"));

                    valueTV.setLayoutParams(params);


                    //create the ordering button capabilities
                    Button add = new Button(getActivity());
                    add.setText("+");
                    add.setTextColor(Color.parseColor("#fb1d91db"));
                    add.setBackgroundColor(Color.WHITE);

                    Button subtract = new Button(getActivity());
                    subtract.setText("-");
                    subtract.setTextColor(Color.parseColor("#fb1d91db"));
                    subtract.setBackgroundColor(Color.WHITE);


                    final TextView counter = new TextView(getActivity());
                    counter.setId(current);
                    counter.setText(String.valueOf(count_1[current]));
                    counter.setGravity(Gravity.CENTER);
                    counter.setTextColor(Color.parseColor("#fb1d91db"));
                    counter.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_blacksquare));

                    add.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            count_1[current_temp]++;
                            counter.setText(String.valueOf(count_1[current_temp]));
                        }
                    });
                    subtract.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (count_1[current_temp] > 0) {
                                count_1[current_temp]--;
                                counter.setText(String.valueOf(count_1[current_temp]));
                            }
                        }
                    });


                    // Add the TableRow to the TableLayout
                    TableRow.LayoutParams params1 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0.5f);

                    labelTV.setLayoutParams(params1);

                    TableRow.LayoutParams params2 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0f);

                    TableRow.LayoutParams paramsx = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT, 0.1f);

                    counter.setLayoutParams(paramsx);
                    subtract.setLayoutParams(paramsx);
                    add.setLayoutParams(paramsx);
                    moreinfo.setLayoutParams(params2);
                    valueTV.setLayoutParams(params2);

                    labelTV.setTextSize(17);
                    tr.addView(labelTV);
                    tr.addView(moreinfo);
                    tr.addView(valueTV);
                    tr.addView(subtract);
                    tr.addView(counter);
                    tr.addView(add);


                    TableRow tr2 = new TableRow(getActivity());
                    Button addComment = new Button(getActivity());
                    //addComment.setLayoutParams(params2);
                    addComment.setText("Add Comment");
                    addComment.setTextSize(17);
                    addComment.setTextColor(Color.WHITE);
                    addComment.setBackgroundColor(Color.parseColor("#fb1d91db"));
                    addComment.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) { //STOPPEDHERE
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_addcomment_popup, null,false);
                            final EditText comment = (EditText) popupView.findViewById(R.id.commentText);
                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);//makes keyboard popup to type
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);
                            comment.setText(breakfast_comments[current_temp]);
                            Button btnSubmit = (Button) popupView.findViewById(R.id.submitComment);
                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismissComment);
                            btnSubmit.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    breakfast_comments[current_temp] = comment.getText().toString();
                                    popupWindow.dismiss();
                                }
                            });
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });
                    //STOPPEDHERE
                    tr2.setPadding(0,0,500,50); //LTRB
                    tr2.addView(addComment);

                    tl.addView(tr);
                    tl.addView(tr2);

                }
                Button summary = new Button(getActivity());
                summary.setId(5000);
                summary.setText("Proceed to Checkout");
                summary.setBackgroundColor(Color.parseColor("#fb1d91db"));
                summary.setTextColor(Color.WHITE);

                summary.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        mViewPager.setCurrentItem(4);
                    }
                });
                TableRow tr = new TableRow(getActivity());
                tr.setGravity(Gravity.CENTER);
                tr.setId(1000 + 20);
                tr.addView(summary);
                tl.addView(tr);
            }


            //if lunch and dinner fragment
            if (temp == 2) {
                // Get the TableLayout
                TableLayout tl = (TableLayout) rootView.findViewById(R.id.maintable);
                ScrollView ll = (ScrollView) rootView.findViewById(R.id.scrollview1);
                tl.setBackgroundColor(Color.WHITE);
                ll.setBackgroundColor(Color.WHITE);
                for (int current = 0; current < numItems_lunchanddinner; current++) {
                    final int current_temp = current; //need this for the counter because it has to be final

                    // Create a TableRow and give it an ID
                    TableRow tr = new TableRow(getActivity());
                    tr.setId(10000 + current);

                    // Create a TextView
                    TextView labelTV = new TextView(DummySectionFragment.this.getActivity());
                    labelTV.setId(20000 + current);
                    labelTV.setText(lunchanddinner[current]);
                    labelTV.setTextColor(Color.parseColor("#fb1d91db"));
                    labelTV.setTextAlignment(3);
                    labelTV.setTextSize(17);
                    android.widget.TableRow.LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);


                    //create the more info button
                    Button moreinfo = new Button(getActivity());
                    moreinfo.setId(30000 + current);
                    moreinfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_moreinfobutton));
                    moreinfo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String lunchanddinner_nurtrition_split[] = lunchanddinner_nutrition[current_temp].split("\n");
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_pop_up, null);

                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);

                            //create all the rows for the table
                            TableRow trAmount = (TableRow)popupView.findViewById(R.id.rowAmount);
                            TableRow trCalories = (TableRow)popupView.findViewById(R.id.rowCalories);// NOTSURE
                            TableRow trFat = (TableRow)popupView.findViewById(R.id.rowFat);
                            TableRow trCarbs = (TableRow)popupView.findViewById(R.id.rowCarbs);
                            TableRow trSugar =(TableRow)popupView.findViewById(R.id.rowSugar);
                            TableRow trDescription =(TableRow)popupView.findViewById(R.id.rowDescription);

                            //create a TextView for each row
                            TextView amountTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView caloriesTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView fatTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView carbsTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView sugarTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView descriptionTV = new TextView(DummySectionFragment.this.getActivity());

                            //set specifications for each textview
                            amountTV.setText(lunchanddinner_nurtrition_split[0]);
                            amountTV.setTextColor(Color.BLACK);
                            amountTV.setTypeface(null, Typeface.BOLD);
                            //caloriesTV.setTextColor(Color.parseColor("#fb1d91db"));
                            amountTV.setTextAlignment(3);
                            amountTV.setTextSize(25);

                            // add the rest of the nutritional info
                            caloriesTV.setText(lunchanddinner_nurtrition_split[1]);
                            fatTV.setText(lunchanddinner_nurtrition_split[2]);
                            carbsTV.setText(lunchanddinner_nurtrition_split[3]);
                            sugarTV.setText(lunchanddinner_nurtrition_split[4]);
                            descriptionTV.setText(Html.fromHtml("<b> DESCRIPTION</b>: "+"<br>"+lunchanddinner_ingredients[current_temp]));
                            descriptionTV.setWidth(0); //makes text wrap around and not cut
                            descriptionTV.setPadding(10,0,0,0);

                            trAmount.addView(amountTV);
                            trCalories.addView(caloriesTV);
                            trFat.addView(fatTV);
                            trCarbs.addView(carbsTV);
                            trSugar.addView(sugarTV);
                            trDescription.addView(descriptionTV);



                            ImageView nutritionImageView = (ImageView) popupView.findViewById(R.id.popupimage);
                            Picasso.with(DynamicMenuFragmentActivity.this).load(lunchanddinner_images[current_temp]).into(nutritionImageView);

                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });


                    //Create a TextView for price
                    TextView valueTV = new TextView(getActivity());
                    valueTV.setId(40000 + current);
                    valueTV.setText("$" + lunchanddinner_price[current]);
                    valueTV.setTextAlignment(15);
                    valueTV.setTextSize(17);
                    valueTV.setTextColor(Color.parseColor("#fb1d91db"));

                    valueTV.setLayoutParams(params);


                    //create the ordering button capabilities
                    Button add = new Button(getActivity());
                    add.setText("+");
                    add.setTextColor(Color.parseColor("#fb1d91db"));
                    add.setBackgroundColor(Color.WHITE);
                    Button subtract = new Button(getActivity());
                    subtract.setText("-");
                    subtract.setTextColor(Color.parseColor("#fb1d91db"));
                    subtract.setBackgroundColor(Color.WHITE);
                    final TextView counter = new TextView(getActivity());
                    counter.setId(50000 + current);
                    counter.setText(String.valueOf(count_2[current]));
                    counter.setGravity(Gravity.CENTER);
                    counter.setTextColor(Color.parseColor("#fb1d91db"));

                    counter.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_blacksquare));

                    add.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            count_2[current_temp]++;
                            counter.setText(String.valueOf(count_2[current_temp]));
                        }
                    });
                    subtract.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (count_2[current_temp] > 0) {
                                count_2[current_temp]--;
                                counter.setText(String.valueOf(count_2[current_temp]));
                            }
                        }
                    });

                    // Add the TableRow to the TableLayout
                    TableRow.LayoutParams params3 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0.5f);

                    labelTV.setLayoutParams(params3);

                    TableRow.LayoutParams params4 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0f);
                    TableRow.LayoutParams paramsy = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT, 0.1f);

                    counter.setLayoutParams(paramsy);
                    subtract.setLayoutParams(paramsy);
                    add.setLayoutParams(paramsy);
                    moreinfo.setLayoutParams(params4);
                    valueTV.setLayoutParams(params4);


                    tr.addView(labelTV);
                    tr.addView(moreinfo);
                    tr.addView(valueTV);
                    tr.addView(subtract);
                    tr.addView(counter);
                    tr.addView(add);

                    TableRow tr2 = new TableRow(getActivity());
                    final EditText comment=new EditText(this.getActivity());
                    Button addComment = new Button(getActivity());
                    addComment.setText("Add Comment");
                    addComment.setTextColor(Color.WHITE);
                    addComment.setBackgroundColor(Color.parseColor("#fb1d91db"));
                    addComment.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_addcomment_popup, null,false);
                            final EditText comment = (EditText) popupView.findViewById(R.id.commentText);
                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);//makes keyboard popup to type
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);
                            comment.setText(lunchanddinner_comments[current_temp]);
                            Button btnSubmit = (Button) popupView.findViewById(R.id.submitComment);
                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismissComment);
                            btnSubmit.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    lunchanddinner_comments[current_temp] = comment.getText().toString();
                                    popupWindow.dismiss();
                                }
                            });
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();

                                }
                            });
                        }
                    });
                    tr2.setPadding(0,0,500,50); //LTRB
                    tr2.addView(addComment);

                    tl.addView(tr);
                    tl.addView(tr2);


                }
                Button summary = new Button(getActivity());
                summary.setId(5001);
                summary.setText("Proceed to Checkout");
                summary.setBackgroundColor(Color.parseColor("#fb1d91db"));
                summary.setTextColor(Color.WHITE);

                summary.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        mViewPager.setCurrentItem(4);
                    }
                });
                TableRow tr = new TableRow(getActivity());
                tr.setId(1000 + 21);
                tr.setGravity(Gravity.CENTER);
                tr.addView(summary);
                tl.addView(tr);
            }

            //if Dessert fragment
            if (temp == 3) {
                // Get the TableLayout
                TableLayout tl = (TableLayout) rootView.findViewById(R.id.maintable);
                ScrollView ll = (ScrollView) rootView.findViewById(R.id.scrollview1);
                tl.setBackgroundColor(Color.WHITE);
                ll.setBackgroundColor(Color.WHITE);

                for (int current = 0; current < numItems_dessert; current++) {
                    final int current_temp = current; //need this for the counter because it has to be final

                    // Create a TableRow and give it an ID
                    TableRow tr = new TableRow(getActivity());
                    tr.setId(100000 + current);

                    // Create a TextView for name
                    TextView labelTV = new TextView(DummySectionFragment.this.getActivity());
                    labelTV.setId(200000 + current);
                    labelTV.setText(desserts[current]);
                    labelTV.setTextColor(Color.parseColor("#fb1d91db"));
                    labelTV.setTextAlignment(3);
                    labelTV.setTextSize(17);
                    android.widget.TableRow.LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);


                    //create the more info button
                    Button moreinfo = new Button(getActivity());
                    moreinfo.setId(300000 + current);
                    moreinfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_moreinfobutton));
                    moreinfo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String dessert_nutrition_split[] = desserts_nutrition[current_temp].split("\n");
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_pop_up, null);

                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);

                            //create all the rows for the table
                            TableRow trAmount = (TableRow)popupView.findViewById(R.id.rowAmount);
                            TableRow trCalories = (TableRow)popupView.findViewById(R.id.rowCalories);// NOTSURE
                            TableRow trFat = (TableRow)popupView.findViewById(R.id.rowFat);
                            TableRow trCarbs = (TableRow)popupView.findViewById(R.id.rowCarbs);
                            TableRow trSugar =(TableRow)popupView.findViewById(R.id.rowSugar);
                            TableRow trDescription =(TableRow)popupView.findViewById(R.id.rowDescription);

                            // Create a TextView for each row
                            TextView amountTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView caloriesTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView fatTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView carbsTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView sugarTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView descriptionTV = new TextView(DummySectionFragment.this.getActivity());

                            //set specifications for each textview
                            amountTV.setText(dessert_nutrition_split[0]);
                            amountTV.setTextColor(Color.BLACK);
                            amountTV.setTypeface(null, Typeface.BOLD);
                            //caloriesTV.setTextColor(Color.parseColor("#fb1d91db"));
                            amountTV.setTextAlignment(3);
                            amountTV.setTextSize(25);

                            // add the rest of the nutritional info
                            caloriesTV.setText(dessert_nutrition_split[1]);
                            fatTV.setText(dessert_nutrition_split[2]);
                            carbsTV.setText(dessert_nutrition_split[3]);
                            sugarTV.setText(dessert_nutrition_split[4]);
                            descriptionTV.setText(Html.fromHtml("<b> DESCRIPTION</b>: "+"<br>"+desserts_ingredients[current_temp]));
                            descriptionTV.setWidth(0); //makes text wrap around and not cut
                            descriptionTV.setPadding(10,0,0,0);


                            trAmount.addView(amountTV);
                            trCalories.addView(caloriesTV);
                            trFat.addView(fatTV);
                            trCarbs.addView(carbsTV);
                            trSugar.addView(sugarTV);
                            trDescription.addView(descriptionTV);

                            ImageView nutritionImageView = (ImageView) popupView.findViewById(R.id.popupimage);
                            Picasso.with(DynamicMenuFragmentActivity.this).load(desserts_images[current_temp]).into(nutritionImageView);

                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });


                    //Create a TextView for price
                    TextView valueTV = new TextView(getActivity());
                    valueTV.setId(400000 + current);
                    valueTV.setText("$" + desserts_price[current]);
                    valueTV.setTextAlignment(3);
                    valueTV.setTextSize(17);
                    valueTV.setTextColor(Color.parseColor("#fb1d91db"));
                    valueTV.setLayoutParams(params);


                    //create the ordering button capabilities
                    Button add = new Button(getActivity());
                    add.setText("+");

                    add.setTextColor(Color.parseColor("#fb1d91db"));
                    add.setBackgroundColor(Color.WHITE);
                    Button subtract = new Button(getActivity());
                    subtract.setText("-");

                    subtract.setTextColor(Color.parseColor("#fb1d91db"));
                    subtract.setBackgroundColor(Color.WHITE);
                    final TextView counter = new TextView(getActivity());
                    counter.setId(500000 + current);
                    counter.setText(String.valueOf(count_3[current]));
                    counter.setGravity(Gravity.CENTER);
                    counter.setTextColor(Color.parseColor("#fb1d91db"));
                    counter.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_blacksquare));
                    add.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            count_3[current_temp]++;
                            counter.setText(String.valueOf(count_3[current_temp]));
                        }
                    });
                    subtract.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (count_3[current_temp] > 0) {
                                count_3[current_temp]--;
                                counter.setText(String.valueOf(count_3[current_temp]));
                            }
                        }
                    });

                    // Add the TableRow to the TableLayout

                    TableRow.LayoutParams params5 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0.5f);

                    labelTV.setLayoutParams(params5);

                    TableRow.LayoutParams params6 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0f);

                    TableRow.LayoutParams paramsz = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT, 0.1f);

                    counter.setLayoutParams(paramsz);
                    subtract.setLayoutParams(paramsz);
                    add.setLayoutParams(paramsz);
                    moreinfo.setLayoutParams(params6);
                    valueTV.setLayoutParams(params6);


                    tr.addView(labelTV);
                    tr.addView(moreinfo);
                    tr.addView(valueTV);
                    tr.addView(subtract);
                    tr.addView(counter);
                    tr.addView(add);

                    TableRow tr2 = new TableRow(getActivity());
                    final EditText comment=new EditText(this.getActivity());
                    Button addComment = new Button(getActivity());
                    addComment.setText("Add Comment");
                    addComment.setTextSize(17);
                    addComment.setTextColor(Color.WHITE);
                    addComment.setBackgroundColor(Color.parseColor("#fb1d91db"));
                    addComment.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_addcomment_popup, null,false);
                            final EditText comment = (EditText) popupView.findViewById(R.id.commentText);
                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);//makes keyboard popup to type
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);
                            comment.setText(desserts_comments[current_temp]);
                            Button btnSubmit = (Button) popupView.findViewById(R.id.submitComment);
                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismissComment);
                            btnSubmit.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    desserts_comments[current_temp] = comment.getText().toString();
                                    popupWindow.dismiss();
                                }
                            });
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });
                    tr2.setPadding(0,0,500,50);
                    tr2.addView(addComment);
                    tr.setGravity(Gravity.CENTER);
                    tl.addView(tr);
                    tl.addView(tr2);

                }
                Button summary = new Button(getActivity());
                summary.setId(5002);
                summary.setText("Proceed to Summary Page ");
                // Typeface face = Typeface.createFromAsset(this.getActivity().getAssets(),
                //       "Lobster_1.3.otf");
                summary.setBackgroundColor(Color.parseColor("#fb1d91db"));
                summary.setTextColor(Color.WHITE);
                //  summary.setTypeface(face);
                summary.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        mViewPager.setCurrentItem(4);
                    }
                });
                TableRow tr = new TableRow(getActivity());
                tr.setId(1000 + 22);
                tr.setGravity(Gravity.CENTER);
                tr.addView(summary);
                tl.addView(tr);
            }


            //if drinks fragment
            if (temp == 4) {
                // Get the TableLayout
                TableLayout tl = (TableLayout) rootView.findViewById(R.id.maintable);
                ScrollView ll = (ScrollView) rootView.findViewById(R.id.scrollview1);
                tl.setBackgroundColor(Color.WHITE);
                ll.setBackgroundColor(Color.WHITE);
                for (int current = 0; current < numItems_drinks; current++) {
                    final int current_temp = current; //need this for the counter because it has to be final

                    // Create a TableRow and give it an ID
                    TableRow tr = new TableRow(getActivity());
                    tr.setId(100 + current);


                    TextView labelTV = new TextView(DummySectionFragment.this.getActivity());


                    labelTV.setId(200 + current);
                    labelTV.setText(drinks[current]);
                    labelTV.setTextColor(Color.parseColor("#fb1d91db"));
                    labelTV.setTextAlignment(3);
                    labelTV.setTextSize(17);
                    android.widget.TableRow.LayoutParams params = new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);


                    //create the more info button
                    final Button moreinfo = new Button(getActivity());
                    moreinfo.setId(300 + current);
                    moreinfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_moreinfobutton));
                    moreinfo.setOnClickListener(new Button.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            String drinks_nutrition_split[] = drinks_nutrition[current_temp].split("\n");
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_pop_up, null);

                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);

                            //create all the rows for the table
                            TableRow trAmount = (TableRow)popupView.findViewById(R.id.rowAmount);
                            TableRow trCalories = (TableRow)popupView.findViewById(R.id.rowCalories);// NOTSURE
                            TableRow trFat = (TableRow)popupView.findViewById(R.id.rowFat);
                            TableRow trCarbs = (TableRow)popupView.findViewById(R.id.rowCarbs);
                            TableRow trSugar =(TableRow)popupView.findViewById(R.id.rowSugar);
                            TableRow trDescription =(TableRow)popupView.findViewById(R.id.rowDescription);

                            // Create a TextView for each row
                            TextView amountTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView caloriesTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView fatTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView carbsTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView sugarTV = new TextView(DummySectionFragment.this.getActivity());
                            TextView descriptionTV = new TextView(DummySectionFragment.this.getActivity());

                            //set specifications for each textview
                            amountTV.setText(drinks_nutrition_split[0]);
                            amountTV.setTextColor(Color.BLACK);
                            amountTV.setTypeface(null, Typeface.BOLD);
                            amountTV.setTextAlignment(3);
                            amountTV.setTextSize(25);

                            // add the rest of the nutritional info
                            caloriesTV.setText(drinks_nutrition_split[1]);
                            fatTV.setText(drinks_nutrition_split[2]);
                            carbsTV.setText(drinks_nutrition_split[3]);
                            sugarTV.setText(drinks_nutrition_split[4]);
                            descriptionTV.setText(Html.fromHtml("<b> DESCRIPTION</b>: "+"<br>")); //BUG: Add description for drinks
                            descriptionTV.setWidth(0); //makes text wrap around and not cut
                            descriptionTV.setPadding(10,0,0,0);


                            trAmount.addView(amountTV);
                            trCalories.addView(caloriesTV);
                            trFat.addView(fatTV);
                            trCarbs.addView(carbsTV);
                            trSugar.addView(sugarTV);
                            trDescription.addView(descriptionTV);

                            ImageView nutritionImageView = (ImageView) popupView.findViewById(R.id.popupimage);
                            Picasso.with(DynamicMenuFragmentActivity.this).load(drinks_images[current_temp]).into(nutritionImageView);

                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });


                    //Create a TextView for price
                    TextView valueTV = new TextView(getActivity());
                    valueTV.setId(400 + current);
                    valueTV.setText("$" + drinks_price[current]);
                    valueTV.setTextAlignment(3);
                    valueTV.setTextSize(17);
                    valueTV.setTextColor(Color.parseColor("#fb1d91db"));
                    valueTV.setLayoutParams(params);

                    //create the ordering button capabilities
                    Button add = new Button(getActivity());
                    add.setText("+");
                    add.setTextColor(Color.parseColor("#fb1d91db"));
                    add.setBackgroundColor(Color.WHITE);
                    Button subtract = new Button(getActivity());
                    subtract.setText("-");
                    subtract.setTextColor(Color.parseColor("#fb1d91db"));
                    subtract.setBackgroundColor(Color.WHITE);
                    final TextView counter = new TextView(getActivity());
                    counter.setId(current);
                    counter.setText(String.valueOf(count_4[current]));
                    counter.setGravity(Gravity.CENTER);
                    counter.setTextColor(Color.parseColor("#fb1d91db"));
                    counter.setBackgroundDrawable(getResources().getDrawable(R.drawable.rsz_blacksquare));

                    add.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            count_4[current_temp]++;
                            counter.setText(String.valueOf(count_4[current_temp]));
                        }
                    });
                    subtract.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) {
                            if (count_4[current_temp] > 0) {
                                count_4[current_temp]--;
                                counter.setText(String.valueOf(count_4[current_temp]));
                            }
                        }
                    });

                    // Add the TableRow to the TableLayout
                    TableRow.LayoutParams params7 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0.5f);

                    labelTV.setLayoutParams(params7);

                    TableRow.LayoutParams params8 = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.MATCH_PARENT, 0f);

                    TableRow.LayoutParams paramsw = new TableRow.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT, 0.1f);

                    counter.setLayoutParams(paramsw);
                    subtract.setLayoutParams(paramsw);
                    add.setLayoutParams(paramsw);
                    moreinfo.setLayoutParams(params8);
                    valueTV.setLayoutParams(params8);


                    tr.addView(labelTV);
                    tr.addView(moreinfo);
                    tr.addView(valueTV);
                    tr.addView(subtract);
                    tr.addView(counter);
                    tr.addView(add);

                    TableRow tr2 = new TableRow(getActivity());
                    final EditText comment=new EditText(this.getActivity());
                    Button addComment = new Button(getActivity());
                    addComment.setText("Add Comment");
                    addComment.setTextColor(Color.WHITE);
                    addComment.setBackgroundColor(Color.parseColor("#fb1d91db"));
                    addComment.setOnClickListener(new OnClickListener() {
                        public void onClick(View arg0) { //STOPPEDHERE
                            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            View popupView = layoutInflater.inflate(R.layout.dynamic_menu_addcomment_popup, null,false);
                            final EditText comment = (EditText) popupView.findViewById(R.id.commentText);
                            final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                            popupWindow.setFocusable(true);//makes keyboard popup to type
                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 10, 10);
                            popupWindow.setHeight(400);
                            popupWindow.setWidth(500);
                            comment.setText(drinks_comments[current_temp]);
                            Button btnSubmit = (Button) popupView.findViewById(R.id.submitComment);
                            Button btnDismiss = (Button) popupView.findViewById(R.id.dismissComment);
                            btnSubmit.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    drinks_comments[current_temp] = comment.getText().toString();
                                    popupWindow.dismiss();
                                }
                            });
                            btnDismiss.setOnClickListener(new Button.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    popupWindow.dismiss();
                                }
                            });
                        }
                    });
                    //STOPPEDHERE
                    tr2.setPadding(0,0,500,50); //LTRB
                    tr2.addView(addComment);
                    tr.setGravity(Gravity.CENTER);
                    tl.addView(tr);
                    tl.addView(tr2);
                }
                Button summary = new Button(getActivity());
                summary.setId(5005);
                summary.setText("Proceed to Summary Page");
                summary.setBackgroundColor(Color.parseColor("#fb1d91db"));
                summary.setTextColor(Color.WHITE);
                //   summary.setTypeface(face);
                summary.setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        mViewPager.setCurrentItem(4);

                        //    Log.e("NumberDrinks", numItems_drinks + "");

                    }
                });
                TableRow tr = new TableRow(getActivity());
                tr.setId(1000 + 25);
                tr.addView(summary);
                tr.setGravity(Gravity.CENTER);
                tl.addView(tr);
            }


            return rootView;

        }
    }

}