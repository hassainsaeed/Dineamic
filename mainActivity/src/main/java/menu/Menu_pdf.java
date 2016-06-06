package menu;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;

import com.hmkcode.android.sign.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;

public class Menu_pdf extends Activity implements OnTouchListener {
	private static final String TAG = "Touch";
	int finalHeight, finalWidth;
	
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
	   ImageView view = (ImageView) findViewById(R.id.image1);
	   view.setScaleType(ImageView.ScaleType.FIT_CENTER);
	   view.setOnTouchListener(this);   
	   finalHeight = view.getMeasuredHeight();
       finalWidth = view.getMeasuredWidth();
	   
	}
	

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
	
