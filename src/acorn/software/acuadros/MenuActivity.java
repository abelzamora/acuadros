package acorn.software.acuadros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnTouchListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		// Paper like backgound color
		getWindow().getDecorView().setBackgroundColor(
				Color.argb(255, 243, 243, 246));
		LinearLayout linLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		linLayout.setBackgroundColor(Color.argb(255, 243, 243, 246));

		// Load font type
		Typeface tf = Typeface.createFromAsset(getAssets(), "data/fonts/actionj.ttf");

		// Get text views
		TextView tvMnuStart = (TextView) findViewById(R.id.mnuStart);
		TextView tvMnuSettings = (TextView) findViewById(R.id.mnuSettings);
		TextView tvMnuExit = (TextView) findViewById(R.id.mnuExit);

		// Apply font to text views
		tvMnuStart.setTypeface(tf);
		tvMnuSettings.setTypeface(tf);
		tvMnuExit.setTypeface(tf);

		// Set listener for touch events in text views
		tvMnuStart.setOnTouchListener(this);
		tvMnuSettings.setOnTouchListener(this);
		tvMnuExit.setOnTouchListener(this);

		// Initial help message
		Toast.makeText(this, "Press Start Game to play", Toast.LENGTH_LONG)
				.show();

	}

	/**
	 * Dialogo
	 */
	private void makeDialog() {

		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		dialog.setMessage("Settings");

		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_LONG)
						.show();
			}
		});

		dialog.setNegativeButton("Close",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});

		dialog.show();
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// Get event action
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Action down
			TextView tvMnuPressDown = (TextView) findViewById(view.getId());
			tvMnuPressDown.setTextColor(Color.rgb(32,32,154));
			break;
		case MotionEvent.ACTION_UP:
			// Action down
			TextView tvMnuPressUp = (TextView) findViewById(view.getId());
			tvMnuPressUp.setTextColor(Color.rgb(51,51,212));
			
			// Get view id pressed
			switch (view.getId()) {
			case R.id.mnuStart:
				// Start game
				Intent i = new Intent(this, AcuadrosActivity.class);
				startActivity(i);
				break;
			case R.id.mnuSettings:
				// Settings
				makeDialog();
				break;
			case R.id.mnuExit:
				// Exit
				finish();
				break;
			}
			break;
		}
		return true;
	}
}
