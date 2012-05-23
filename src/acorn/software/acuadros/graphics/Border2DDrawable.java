package acorn.software.acuadros.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

public class Border2DDrawable extends Drawable {
	/* Border position */
	private PointF p1;
	private PointF p2;

	/* Color and style */
	private int alpha;
	private Paint paint;
	
	/* Active */
	private boolean active;
	
	/**
	 * Constructor
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Border2DDrawable(PointF p1, PointF p2) {
		// Init position params
		this.p1 = p1;
		this.p2 = p2;

		// Active
		this.active = false;
		
		// Init alpha
		this.alpha = 255;

		// Init paint
		this.paint = new Paint();
		this.paint.setARGB(this.alpha, 100, 100, 100);
		this.paint.setStyle(Paint.Style.FILL);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawLine(this.p1.x, this.p1.y, this.p2.x, this.p2.y, this.paint);

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
	 * Activate border
	 * @return
	 */
	public boolean activate() {
		if(active) {
			// It is already active
			return false;
		} else {
			// Activate border
			active = true;
			this.paint.setColor(Color.BLUE);
			return active;
		}
	}
	
	/**
	 * Is border active
	 * @return
	 */
	public boolean isActive() {
		return this.active;
	}
}