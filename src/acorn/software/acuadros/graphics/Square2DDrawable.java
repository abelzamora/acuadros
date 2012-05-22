package acorn.software.acuadros.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public class Square2DDrawable extends Drawable {
	private final static float WIDTH = 45.0f;
	private final static float MARGIN_ERROR = 7.0f;

	private Paint paint;
	private Paint paintLeft;
	private Paint paintUp;
	private Paint paintRight;
	private Paint paintDown;
	private Paint circulo;
	private Paint letter;

	private int alpha;
	private float x;
	private float y;

	// Border of the square
	private boolean borderUp = false;
	private boolean borderDown = false;
	private boolean borderLeft = false;
	private boolean borderRight = false;

	// Initialize color
	private int color = Color.GRAY;
	private int colorChecked = Color.RED;

	// Initialize canvas
	private Canvas canvas;
	
	public String userLetter = " "; 

	private void initializePaint() {
		this.paintLeft = new Paint();
		this.paintLeft.setStyle(Paint.Style.FILL_AND_STROKE);
		this.paintRight = new Paint();
		this.paintRight.setStyle(Paint.Style.FILL_AND_STROKE);
		this.paintDown = new Paint();
		this.paintDown.setStyle(Paint.Style.FILL_AND_STROKE);
		this.paintUp = new Paint();
		this.paintUp.setStyle(Paint.Style.FILL_AND_STROKE);

		circulo = new Paint();
		circulo.setColor(Color.LTGRAY);
	}

	public Square2DDrawable(float x, float y) {
		this.paint = new Paint();
		initializePaint();
		this.alpha = 255;
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Canvas canvas) {
		// Draw box
		paint.setARGB(alpha, 243,243,246);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawRect(x, y, x + getWidth(), y + getWidth(), paint);
		
		this.canvas = canvas;
		setLetter();
		// this.paint.setColor(color);

		// left border
		drawLineLeft();
		// up border
		drawLineUp();
		// right border
		drawLineRight();
		// down border
		drawLineDown();

		//canvas.drawCircle(x, y, MARGIN_ERROR, circulo);
	}

	private void drawLineRight() {
		if (borderRight)
			this.paintRight.setColor(colorChecked);
		else
			this.paintRight.setColor(color);
		canvas.drawLine(x + getWidth(), y, x + getWidth(), y + getWidth(), paintRight);
	}

	private void drawLineLeft() {
		// left border
		if (borderLeft)
			this.paintLeft.setColor(colorChecked);
		else
			this.paintLeft.setColor(color);
		canvas.drawLine(x, y, x, y + getWidth(), paintLeft);
	}

	private void drawLineUp() {
		// up border
		if (borderUp)
			this.paintUp.setColor(colorChecked);
		else
			this.paintUp.setColor(color);
		canvas.drawLine(x, y, x + getWidth(), y, paintUp);
	}

	private void drawLineDown() {
		if (borderDown)
			this.paintDown.setColor(colorChecked);
		else
			this.paintDown.setColor(color);
		canvas.drawLine(x, y + getWidth(), x + getWidth(), y + getWidth(), paintDown);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.OPAQUE;
	}

	@Override
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	/**
	 * 
	 */
	public boolean insideX(int col, int row, float xx, float yy, float scaleX, float scaleY) {

		boolean inside = false;

		if (scaleX < 0)
			scaleX = xx + scaleX;
		else
			scaleX = xx - scaleX;

		if (scaleY < 0)
			scaleY = yy + scaleY;
		else
			scaleY = yy - scaleY;

		float fromX = col * getWidth();
		float toX = fromX + getWidth();

		// borde superior
		float fromSupY = (row * getWidth()) - getMarginError();
		float toSupY = (row * getWidth()) + getMarginError();

		if (fromX <= scaleX && scaleX <= toX) {
			// ha pulsado borde inferior o superior
			if (fromSupY <= scaleY && scaleY <= toSupY) {
				// ha pulsado en el borde superior
				if(borderUp == false){
					inside = true;
					borderUp = true;
				}
			}
		}

		return inside;

	}
	
	/**
	 * 
	 */
	public boolean insideXLimit(int col, int row, float xx, float yy, float scaleX, float scaleY) {

		boolean inside = false;

		if (scaleX < 0)
			scaleX = xx + scaleX;
		else
			scaleX = xx - scaleX;

		if (scaleY < 0)
			scaleY = yy + scaleY;
		else
			scaleY = yy - scaleY;

		float fromX = col * getWidth();
		float toX = fromX + getWidth();

		// borde inferior
		float fromSupY = ((row * getWidth())+getWidth()) - getMarginError();
		float toSupY = ((row * getWidth())+getWidth()) + getMarginError();

		if (fromX <= scaleX && scaleX <= toX) {
			// ha pulsado borde inferior o superior
			if (fromSupY <= scaleY && scaleY <= toSupY) {
				// ha pulsado en el borde superior
				if(borderDown == false){
					inside = true;
					borderDown = true;
				}
			}
		}

		return inside;

	}

	/**
	 * 
	 */
	public boolean insideY(int col, int row, float xx, float yy, float scaleX, float scaleY) {

		boolean inside = false;

		if (scaleX < 0)
			scaleX = xx + scaleX;
		else
			scaleX = xx - scaleX;

		if (scaleY < 0)
			scaleY = yy + scaleY;
		else
			scaleY = yy - scaleY;

		float fromY = row * getWidth();
		float toY = fromY + getWidth();

		float fromLeftX = (col * getWidth()) - getMarginError();
		float toLeftX = (col * getWidth()) + getMarginError();

		if ((fromY <= scaleY && scaleY <= toY)) {
			// se ha pulsado en una columna
			if (fromLeftX <= scaleX && scaleX <= toLeftX) {
				if(borderLeft == false){
					inside = true;
					borderLeft = true;
				}
			}
		}

		return inside;
	}
	
	/**
	 * Set letter
	 * @return
	 */
	public void setLetter()
	{
		letter = new Paint();
		letter.setTextSize(20.0f);
		letter.setTypeface(Typeface.DEFAULT_BOLD);
		letter.setColor(Color.BLACK);
		
		this.canvas.drawText(this.userLetter, (x+x+getWidth()-getMarginError())/2, (y+y+getWidth()+2*getMarginError())/2, letter);
	}

	public boolean isBorderUp() {
		return borderUp;
	}

	public void setBorderUp(boolean borderUp) {
		this.borderUp = borderUp;
	}

	public boolean isBorderDown() {
		return borderDown;
	}

	public void setBorderDown(boolean borderDown) {
		this.borderDown = borderDown;
	}

	public boolean isBorderLeft() {
		return borderLeft;
	}

	public void setBorderLeft(boolean borderLeft) {
		this.borderLeft = borderLeft;
	}

	public boolean isBorderRight() {
		return borderRight;
	}

	public void setBorderRight(boolean borderRight) {
		this.borderRight = borderRight;
	}

	/**
	 * @return the marginError
	 */
	public static float getWidth() {
		return WIDTH;
	}

	/**
	 * @return the marginError
	 */
	public static float getMarginError() {
		return MARGIN_ERROR;
	}
}