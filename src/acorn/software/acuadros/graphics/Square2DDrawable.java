package acorn.software.acuadros.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

public class Square2DDrawable extends Drawable {
	/* Square width */
	public final static float WIDTH = 45.0f;
	
	/* Square position */
	private PointF point;
	
	/* Color and style */
	private Paint paint;
	private int alpha;

	/* Borders of the square */
	private Border2DDrawable borderUp;
	private Border2DDrawable borderDown;
	private Border2DDrawable borderLeft;
	private Border2DDrawable borderRight;

	public Square2DDrawable(PointF point) {
		// Init position params
		this.point = point;
		
		// Init alpha
		this.alpha = 255;
		
		// Init paint
		this.paint = new Paint();
		this.paint.setARGB(alpha, 243,243,246);
		this.paint.setStyle(Paint.Style.FILL);
		
		// Create borders
		this.borderUp = new Border2DDrawable(this.point, new PointF(this.point.x + WIDTH, this.point.y));
		this.borderDown = new Border2DDrawable(new PointF(this.point.x, this.point.y + WIDTH), new PointF(this.point.x + WIDTH, this.point.y + WIDTH));
		this.borderLeft = new Border2DDrawable(this.point, new PointF(this.point.x, this.point.y + WIDTH));
		this.borderRight = new Border2DDrawable(new PointF(this.point.x + WIDTH, this.point.y), new PointF(this.point.x + WIDTH, this.point.y + WIDTH));
	}

	@Override
	public void draw(Canvas canvas) {
		// Draw box
		canvas.drawRect(this.point.x, this.point.y, this.point.x + WIDTH, this.point.y + WIDTH, paint);
		
		// Draw borders
		this.borderUp.draw(canvas);
		this.borderDown.draw(canvas);
		this.borderLeft.draw(canvas);
		this.borderRight.draw(canvas);
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
	 * Activate up border 
	 * @return
	 */
	public boolean activateBorderUp() {
		return this.borderUp.activate();
	}
	
	/**
	 * Activate down border 
	 * @return
	 */
	public boolean activateBorderDown() {
		return this.borderDown.activate();
	}
	
	/**
	 * Activate left border 
	 * @return
	 */
	public boolean activateBorderLeft() {
		return this.borderLeft.activate();
	}
	
	/**
	 * Activate right border 
	 * @return
	 */
	public boolean activateBorderRight() {
		return this.borderRight.activate();
	}
	
	public boolean checkScore() {
		if(this.borderUp.isActive() && this.borderDown.isActive() && this.borderLeft.isActive() && this.borderRight.isActive() ) {
			this.paint.setARGB(alpha, 51, 51, 212);
			return true;
		} else {
			return false;
		}
	}
}