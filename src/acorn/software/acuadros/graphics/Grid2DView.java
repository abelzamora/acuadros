package acorn.software.acuadros.graphics;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Scroller;

public class Grid2DView extends View implements OnGestureListener,
		OnDoubleTapListener {
	/* Gesture listener params */
	private GestureDetector gestureScanner;
	private ScaleGestureDetector mScaleDetector;
	private float mScaleFactor = 1.0f;
	private boolean blockGesture;

	/* View size and position params */
	private float width;
	private float height;

	/* Device size and position params */
	private float x;
	private float y;
	private int dWidth;
	private int dHeigth;

	/* First load flag */
	private boolean firstLoad;

	/* Standard text message */
	private String textMsg;
	
	/* Number of rows and columns */
	private int nRows;
	private int nColumms;

	/* Scale */
	private float scaleX;
	private float scaleY;


	/* Grid array */
	private Vector<Vector<Square2DDrawable>> squares;

	/**
	 * Grid2DView constructor
	 * @param context
	 * @param dWidth
	 * @param dHeight
	 */
	public Grid2DView(Context context) {
		super(context);
		init(context);
	}

	public Grid2DView(Context context, AttributeSet attSet, int arg) {
		super(context, attSet, arg);
		init(context);
	}

	public Grid2DView(Context context, AttributeSet attSet) {
		super(context, attSet);
		init(context);
	}

	public void init(Context context) {
		// Generic text message
		textMsg = "";

		// FIrst load
		firstLoad = true;

		// Gesture listeners
		gestureScanner = new GestureDetector(this);
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

		// Number of rows and columns in the grid
		this.nRows = 10;
		this.nColumms = 10;

		// Load squares into grid
		this.squares = new Vector<Vector<Square2DDrawable>>();
		// Initialize variables
				float currentX;
				float currentY;
				for (int i = 0; i <= this.nColumms; i++) {
					currentX = i * Square2DDrawable.getWidth();
					this.squares.add(new Vector<Square2DDrawable>());

					for (int j = 0; j <= this.nRows; j++) {
						currentY = j * Square2DDrawable.getWidth();
						// TODO ¿Porqué funciona al contrario?
						this.squares.get(i).add(new Square2DDrawable(currentX, currentY));

					}

				}

		// Get grids limit
		this.width = this.nColumms * Square2DDrawable.getWidth();
		this.height = this.nRows * Square2DDrawable.getWidth();
	}

	/**
	 * Draw squares in the grid
	 * @param canvas
	 */
	public void drawSquares(Canvas canvas) {
		for (int i = 0; i < this.squares.size(); i++) {
			for (int j = 0; j < this.squares.get(i).size() ; j++) {
				this.squares.get(i).get(j).draw(canvas);
			}
		}
	}

	/**
	 * Draw center text
	 * @param text
	 * @param color
	 * @param size
	 */
	public void drawMessage(Canvas canvas, String text, int color, int size) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setTextSize(size);
		paint.setTypeface(Typeface.MONOSPACE);
		float textX = ((float) canvas.getWidth() / 2)
				- (paint.measureText(text) / 2);
		float textY = (canvas.getHeight() / 2) - size;
		canvas.drawText(textMsg, textX, textY, paint);
	}

	/**
	 * Center grid to initial position
	 */
	private void centerGrid() {
		// Get position to center grid
		this.x = - (width - this.dWidth) / 2;
		this.y = - (height - this.dHeigth) / 2;
		this.scaleX = this.x;
		this.scaleY = this.y;
	}

	/**
	 * Center grid to certain column and row
	 * @param column
	 * @param row
	 */
	private void centerGrid(int column, int row) {
		this.x = - (int) (((Square2DDrawable.getWidth() * mScaleFactor * (column) * 2) - this.dWidth) / 2 + (Square2DDrawable.getWidth() * mScaleFactor / 2));
		this.y = - (int) (((Square2DDrawable.getWidth() * mScaleFactor * (row) * 2) - this.dHeigth) / 2 + (Square2DDrawable.getWidth() * mScaleFactor / 2));
	}

	/**
	 * Get column from x coor pressed
	 * @param pressedX
	 * @return
	 */
	private int getColumn(float pressedX) {
		return (int) ((pressedX - this.x) / (Square2DDrawable.getWidth()  * mScaleFactor));
	}

	/**
	 * Get row from y coor pressed
	 * @param pressedY
	 * @return
	 */
	private int getRow(float pressedY) {
		return (int) ((pressedY - this.y) / (Square2DDrawable.getWidth() * mScaleFactor));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(firstLoad) {
			// Get device size
			this.dWidth = canvas.getWidth();
			this.dHeigth = canvas.getHeight();

			// Center grid
			this.centerGrid();
			firstLoad = false;
		}

		canvas.save();
		canvas.translate(x, y);
		canvas.scale(mScaleFactor, mScaleFactor);
		this.drawSquares(canvas);
		canvas.restore();
		drawMessage(canvas, textMsg, Color.RED, 18);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		blockGesture = false;
		mScaleDetector.onTouchEvent(event);
		if(!blockGesture) {
			gestureScanner.onTouchEvent(event);
		}
		invalidate();
		return true;
	}

	@Override
	public boolean onDown(MotionEvent event) {
		float xx = event.getX();
		float yy = event.getY();

		int column = getColumn(xx);
		int row = getRow(yy);

		Square2DDrawable square = this.squares.get(column).get(row);
		if(square.insideX(column, row, xx, yy, this.scaleX, this.scaleY))
			checkContigousBorderRow(column, row);
		if(square.insideY(column, row, xx, yy, this.scaleX, this.scaleY))
			checkContigousBorderColumn(column, row);
		
		checkBorderSquare(square);
		return true;
	}
	
	/**
	 * Check if the square is close
	 */
	private void checkBorderSquare(Square2DDrawable square)
	{
		if(square.isBorderDown() && square.isBorderLeft() && square.isBorderRight() && square.isBorderUp())
		{
			// Set letter
			square.userLetter = "P";
		}
	}
	
	private void checkContigousBorderColumn(int column, int row){
		if(column>0){
			this.squares.get(column-1).get(row).setBorderRight(true);
		}
		
	}
	
	private void checkContigousBorderRow(int column, int row)
	{
		if(row>0){
			this.squares.get(column).get(row -1).setBorderDown(true);
		}
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Scroller scroller = new Scroller(this.getContext());
		scroller.extendDuration(20);
		//scroller.fling(x, y, (int) velocityX, (int) velocityY, (int) -limitX, (int) (2*limitX), (int) -limitY, (int) (2*limitX));
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		x -= distanceX;
		y -= distanceY;
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// Get column and row tapped
		int column = this.getColumn(e.getX());
		int row = this.getRow(e.getY());

		// Calculate scale factor
		if(mScaleFactor < 4.0f) {
			// Max zoom
			mScaleFactor = 5.0f;

			// Center zoom in tapped square
			centerGrid(column, row);
		} else {
			// Min zoom
			mScaleFactor = 1.0f;

			// Center grid to initial position
			centerGrid();
		}
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {

		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {

		return false;
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
	    public boolean onScale(ScaleGestureDetector detector) {
			blockGesture = true;
			mScaleFactor *= detector.getScaleFactor();
	        mScaleFactor = Math.max(1.0f, Math.min(mScaleFactor, 5.0f));
	        return true;
	    }
	}
}