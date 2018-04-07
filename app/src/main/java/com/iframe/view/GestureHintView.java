package com.iframe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.iframe.R;


/**
 * 
 * @author suetming (suetming.ma@creditcloud.com)
 * 
 *         创建时间：2013-8-21 上午9:29:39
 * 
 */
public class GestureHintView extends View {

	Drawable normal;

	Drawable selected;

	Paint paint;

	int wPoint;

	int hPoint;

	double hLength;

	double wLength;

	String code = "";

	public GestureHintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init(context, attrs);
	}

	void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.App);

		try {
			normal = typedArray
					.getDrawable(R.styleable.App_normal);
			selected = typedArray
					.getDrawable(R.styleable.App_selected);
			wPoint = normal.getIntrinsicWidth();
			hPoint = normal.getIntrinsicHeight();
		} catch (Exception e) {
			typedArray.recycle();
		}

		paint = new Paint();
	}

	public GestureHintView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context, attrs);
	}

	public GestureHintView(Context context) {
		super(context);

		normal = context.getResources().getDrawable(
				R.drawable.ic_gesture_hint);
		selected = context.getResources().getDrawable(
				R.drawable.ic_gesture_hint_s);
		wPoint = normal.getIntrinsicWidth();
		hPoint = normal.getIntrinsicHeight();
	}

	public void setCode(String code) {
		this.code = code;
		invalidate();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		double x = (wLength - wPoint) / 2;
		double y = (hLength - hPoint) / 2;

		for (int i = 0; i < 9; i++) {
			normal.setBounds((int) (x + i % 3 * wLength),
					(int) (y + (i / 3) * hLength),
					(int) (x + i % 3 * wLength + wPoint),
					(int) (y + (i / 3) * hLength + hPoint));
			normal.draw(canvas);
		}

		for (int i = 0; i < code.length(); i++) {
			int idx = code.charAt(i) - 48;
			selected.setBounds((int) (x + idx % 3 * wLength),
					(int) (y + (idx / 3) * hLength),
					(int) (x + idx % 3 * wLength + wPoint),
					(int) (y + (idx / 3) * hLength + hPoint));
			selected.draw(canvas);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int w = getMeasuredWidth(), h = w;

		setMeasuredDimension(w, w); // Snap to width

		int paddingTop = getPaddingTop();

		int paddingBottom = getPaddingBottom();

		int paddingLeft = getPaddingLeft();

		int paddingRight = getPaddingRight();

		hLength = (h - paddingBottom - paddingTop) / 3.0;
		wLength = (w - paddingLeft - paddingRight) / 3.0;
	}

}