package acorn.software.acuadros.graphics;

import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class Grid2DView extends View {
	/* View size and position params */
	private float width;
	private float height;

	/* Device size and position params */
	private float x;
	private float y;
	private int dWidth;
	private int dHeigth;

	/* Scale factor */
	private float scaleFactor = 1.0f;

	/* First load flag */
	private boolean firstLoad;

	/* Standard text message */
	private String textMsg;

	/* Number of rows and columns */
	private int nRows;
	private int nColumms;

	/* Grid array */
	private Vector<Vector<Square2DDrawable>> squares;

	/**
	 * Grid2DView constructor
	 * 
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

	public float getScaleFactor() {
		return this.scaleFactor;
	}

	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
		invalidate();
	}

	public void init(Context context) {
		// Generic text message
		textMsg = "";

		// FIrst load
		firstLoad = true;

		// Number of rows and columns in the grid
		this.nRows = 10;
		this.nColumms = 10;

		// Load squares into grid
		this.squares = new Vector<Vector<Square2DDrawable>>();
		// Initialize variables
		float currentX;
		float currentY;
		for (int i = 0; i <= this.nColumms; i++) {
			currentX = i * Square2DDrawable.WIDTH;
			this.squares.add(new Vector<Square2DDrawable>());

			for (int j = 0; j <= this.nRows; j++) {
				currentY = j * Square2DDrawable.WIDTH;
				this.squares.get(i).add(
						new Square2DDrawable(new PointF(currentX, currentY)));

			}

		}

		// Get grids limit
		this.width = this.nColumms * Square2DDrawable.WIDTH;
		this.height = this.nRows * Square2DDrawable.WIDTH;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (firstLoad) {
			// Get device size
			this.dWidth = canvas.getWidth();
			this.dHeigth = canvas.getHeight();

			// Center grid
			this.centerGrid();
			firstLoad = false;
		}

		canvas.save();
		canvas.translate(x, y);
		canvas.scale(scaleFactor, scaleFactor);
		this.drawSquares(canvas);
		canvas.restore();
		drawMessage(canvas, textMsg, Color.RED, 18);
	}

	/**
	 * Draw squares in the grid
	 * 
	 * @param canvas
	 */
	public void drawSquares(Canvas canvas) {
		for (int i = 0; i < this.squares.size(); i++) {
			for (int j = 0; j < this.squares.get(i).size(); j++) {
				this.squares.get(i).get(j).draw(canvas);
			}
		}
	}

	/**
	 * Draw center text
	 * 
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
		this.x = -(width - this.dWidth) / 2;
		this.y = -(height - this.dHeigth) / 2;
	}

	/**
	 * Center grid to certain column and row
	 * 
	 * @param column
	 * @param row
	 */
	private void centerGrid(int column, int row) {
		this.x = -(int) (((Square2DDrawable.WIDTH * scaleFactor * (column) * 2) - this.dWidth) / 2 + (Square2DDrawable.WIDTH
				* scaleFactor / 2));
		this.y = -(int) (((Square2DDrawable.WIDTH * scaleFactor * (row) * 2) - this.dHeigth) / 2 + (Square2DDrawable.WIDTH
				* scaleFactor / 2));
	}

	/**
	 * Get column from x coor pressed
	 * 
	 * @param pressedX
	 * @return
	 */
	private float getColumn(float pressedX) {
		float column = ((pressedX - this.x) / (Square2DDrawable.WIDTH * scaleFactor));
		column = Math.min(this.nColumms, Math.max(0, column));
		return column;
	}

	/**
	 * Get row from y coor pressed
	 * 
	 * @param pressedY
	 * @return
	 */
	private float getRow(float pressedY) {
		float row = ((pressedY - this.y) / (Square2DDrawable.WIDTH * scaleFactor));
		row = Math.min(this.nRows, Math.max(0, row));
		return row;
	}

	public boolean fling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Scroller scroller = new Scroller(this.getContext());
		scroller.extendDuration(20);
		// scroller.fling(x, y, (int) velocityX, (int) velocityY, (int) -limitX,
		// (int) (2*limitX), (int) -limitY, (int) (2*limitX));
		return true;
	}

	public boolean scroll(float distanceX, float distanceY) {
		x -= distanceX;
		y -= distanceY;
		invalidate();
		return true;
	}

	public boolean singleTapUp(float x, float y) {
		float fColumn = this.getColumn(x);
		float fRow = this.getRow(y);

		int iColumn = (int) fColumn;
		int iRow = (int) fRow;
		float factorV = fColumn - iColumn;
		float factorH = fRow - iRow;

		if (factorV < 0.1f) {
			// Left square border
			try {
				if(this.squares.get(iColumn).get(iRow).activateBorderLeft()) {
					this.squares.get(iColumn).get(iRow).checkScore();
				}
				
				if(this.squares.get(iColumn - 1).get(iRow).activateBorderRight()) {
					this.squares.get(iColumn - 1).get(iRow).checkScore();
				}
			} catch (Exception e) {
				// Grid border
			}
		} else if (factorV > 0.9f) {
			// Right square border
			try {
				if(this.squares.get(iColumn).get(iRow).activateBorderRight()) {
					this.squares.get(iColumn).get(iRow).checkScore();
				}
				
				if(this.squares.get(iColumn + 1).get(iRow).activateBorderLeft()) {
					this.squares.get(iColumn + 1).get(iRow).checkScore();
				}
			} catch (Exception e) {
				// Grid border
			}
		} else if (factorH < 0.1f) {
			// Up square border
			try {
				if(this.squares.get(iColumn).get(iRow).activateBorderUp()) {
					this.squares.get(iColumn).get(iRow).checkScore();
				}
				
				if(this.squares.get(iColumn).get(iRow - 1).activateBorderDown()) {
					this.squares.get(iColumn).get(iRow - 1).checkScore();
				}
			} catch (Exception e) {
				// Grid border
			}
		} else if (factorH > 0.9f) {
			// Down square border
			try {
				if(this.squares.get(iColumn).get(iRow).activateBorderDown()) {
					this.squares.get(iColumn).get(iRow).checkScore();
				}
				if(this.squares.get(iColumn).get(iRow + 1).activateBorderUp() ) {
					this.squares.get(iColumn).get(iRow + 1).checkScore();
				}
			} catch (Exception e) {
				// Grid border
			}
		}

		invalidate();
		/*
		 * float xx = event.getX(); float yy = event.getY();
		 * 
		 * int column = getColumn(xx); int row = getRow(yy);
		 * 
		 * Square2DDrawable square = this.squares.get(column).get(row);
		 * if(square.insideX(column, row, xx, yy, this.scaleX, this.scaleY))
		 * checkContigousBorderRow(column, row); if(square.insideY(column, row,
		 * xx, yy, this.scaleX, this.scaleY)) checkContigousBorderColumn(column,
		 * row);
		 * 
		 * checkBorderSquare(square);
		 */
		return true;
	}

	public boolean zoomTo(float x, float y) {
		// Get column and row tapped
		int column = (int) this.getColumn(x);
		int row = (int) this.getRow(y);

		// Calculate scale factor
		if (scaleFactor < 4.0f) {
			// Max zoom
			scaleFactor = 5.0f;

			// Center zoom in tapped square
			centerGrid(column, row);
		} else {
			// Min zoom
			scaleFactor = 1.0f;

			// Center grid to initial position
			centerGrid();
		}
		invalidate();
		return true;
	}
}