package acorn.software.acuadros;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Acuadros activity class
 * @author joss
 *
 */
public class AcuadrosActivity extends Activity implements OnTouchListener {
	/* Grid 2D */
	//private Grid2DView grid;

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
		Typeface tf = Typeface.createFromAsset(getAssets(), "data/fonts/cookies.ttf");
		
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

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.onTouchEvent(event);
		return true;
	}
}