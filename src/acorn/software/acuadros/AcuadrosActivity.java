package acorn.software.acuadros;

import acorn.software.acuadros.graphics.Grid2DView;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Acuadros activity class
 * 
 * @author joss
 * 
 */
public class AcuadrosActivity extends Activity implements OnTouchListener,
		OnGestureListener, OnDoubleTapListener {
	/* Grid 2D */
	private Grid2DView grid;

	private GestureDetector gestureScanner;
	private ScaleGestureDetector mScaleDetector;
	private boolean blockGesture;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Add main layout as content view
		setContentView(R.layout.main);

		// Main layout background color
		FrameLayout frLayout = (FrameLayout) findViewById(R.id.FrameLayout1);
		frLayout.setBackgroundColor(Color.argb(255, 233, 233, 236));

		// Load font type
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"data/fonts/cookies.ttf");

		// Get text views
		TextView tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);
		TextView tvPlayer2 = (TextView) findViewById(R.id.tvPlayer2);
		TextView tvPlayer3 = (TextView) findViewById(R.id.tvPlayer3);
		TextView tvPlayer4 = (TextView) findViewById(R.id.tvPlayer4);
		TextView tvGameMsg = (TextView) findViewById(R.id.tvGameMsg);

		// Apply font to text views
		tvPlayer1.setTypeface(tf);
		tvPlayer2.setTypeface(tf);
		tvPlayer3.setTypeface(tf);
		tvPlayer4.setTypeface(tf);
		tvGameMsg.setTypeface(tf);
		tvGameMsg.setVisibility(View.INVISIBLE);

		grid = (Grid2DView) findViewById(R.id.gridDView1);
		grid.setOnTouchListener(this);
		
		// Gesture listeners
		this.gestureScanner = new GestureDetector(this);
		this.mScaleDetector = new ScaleGestureDetector(grid.getContext(),
				new ScaleListener());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		blockGesture = false;
		mScaleDetector.onTouchEvent(event);
		if (!blockGesture) {
			gestureScanner.onTouchEvent(event);
		}
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		grid.zoomTo(e.getX(), e.getY());
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// DO NOTHING
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// DO NOTHING
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// DO NOTHING
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		grid.fling(e1, e2, velocityX, velocityY);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// DO NOTHING
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		grid.scroll(distanceX, distanceY);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// DO NOTHING
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		grid.singleTapUp(e.getX(), e.getY());
		return false;
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			blockGesture = true;
			float scaleFactor = grid.getScaleFactor();
			scaleFactor *= detector.getScaleFactor();
			scaleFactor = Math.max(1.0f, Math.min(scaleFactor, 5.0f));
			grid.setScaleFactor(scaleFactor);
			return true;
		}
	}
}