package com.example.sudokuapp.view.sudokugrid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;

public class SudokuCell extends BaseSudokuCell {


	private Paint mPaint;
	boolean isFilled;

	public SudokuCell( Context context ){
		super(context);
		mPaint = new Paint();
	}

	private boolean isBold = false;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawNumber(canvas);
		drawLines(canvas);
	}

	private void drawNumber(Canvas canvas){

		if (getValue() == 0) {

			mPaint.setColor(Color.WHITE);
			isFilled = true;

			mPaint.setTextSize(60);


			// Calculate the position for drawing an empty box
			int left = 0;
			int top = 0;
			int right = getWidth();
			int bottom = getHeight();

			canvas.drawRect(left, top, right, bottom, mPaint);
		} else {
			if (isFilled) {
				mPaint.setColor(Color.BLUE);
			} else {

				mPaint.setColor(Color.BLACK);

			}
			mPaint.setTextSize(60);
			mPaint.setStyle(Paint.Style.FILL);
			Rect bounds = new Rect();
			mPaint.getTextBounds(String.valueOf(getValue()), 0, String.valueOf(getValue()).length(), bounds);
			canvas.drawText(String.valueOf(getValue()), (getWidth() - bounds.width()) / 2, (getHeight() + bounds.height()) / 2, mPaint);
		}
		}

	private void drawLines(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(3);
		mPaint.setStyle(Style.STROKE);
		canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
	}
}

