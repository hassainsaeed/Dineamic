package menu;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.hmkcode.android.sign.R;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.squareup.picasso.Picasso;

import globalVariables.GlobalVariable;
import qrScanner.scanQRCode;

public class Menu_pdf extends Activity implements OnTouchListener {
	private static final String TAG = "Touch";
	int finalHeight, finalWidth;
	private String restaurantName;
	//moving and zooming the image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();

	// possible states: drag the menu or zoom into it
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	int mode = NONE;

	// Remembering vars for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;
	float fixedDist= 1f;
	int zoom;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_pdf);
		ActionBar bar = getActionBar();
//for color
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fb1d91db")));

		Intent intent = getIntent();
		this.restaurantName = GlobalVariable.getRestaurantName();
		String menuURL = "http://52.11.144.56/" + restaurantName + "_menu.jpg";

		ImageView view = (ImageView) findViewById(R.id.image1);
		Picasso.with(Menu_pdf.this)
				.load(menuURL)
				.into(view);
		view.setScaleType(ImageView.ScaleType.FIT_CENTER);
		view.setOnTouchListener(this);
		finalHeight = view.getMeasuredHeight();
		finalWidth = view.getMeasuredWidth();

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
				Intent intent = new Intent(Menu_pdf.this, CaptureActivity.class);
				intent.setAction("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		new scanQRCode(requestCode,resultCode,intent,Menu_pdf.this).execute();
	}
	//End Code for the QR Scanner in the Action Bar

	public boolean onTouch(View v, MotionEvent event) {
		ImageView view = (ImageView) v;
		view.setScaleType(ImageView.ScaleType.MATRIX);
		float scale;


		// Handling events
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN: //first finger down only
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());

				mode = DRAG;
				break;
			// case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP: //second finger lifted
				mode = NONE;

				break;
			case MotionEvent.ACTION_POINTER_DOWN: //second finger down
				oldDist = spacing(event);

				if (oldDist > 5f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;

				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {

					matrix.set(savedMatrix);
					if (view.getLeft() >= -392){
						matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
					}


				}
				if (mode == ZOOM) { //pinch zooming
					int height = view.getMeasuredHeight();
					int width = view.getMeasuredWidth();
					float newDist = spacing(event);


					if (newDist > 5f) {
						matrix.set(savedMatrix);
						scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
					}
				}


				break;
		}


		// Perform the transformation
		view.setImageMatrix(matrix);

		return true; // indicate event was handled
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		if(x<0 &&y<0){zoom=0;}//in}
		if (x>0 &&y>0){zoom=1;}//out}
		return (float) Math.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

}
