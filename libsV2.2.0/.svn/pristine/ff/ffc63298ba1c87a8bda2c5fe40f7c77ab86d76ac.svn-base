package com.opensky.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.opensky.R;

public class UploadProgressBar extends ProgressBar {

	public UploadProgressBar(Context context) {
		super(context);
		init();
	}

	public UploadProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public UploadProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		Resources res = getResources();
		Drawable d = res.getDrawable(R.drawable.progressbar);
		setProgressDrawable(d);
	}

	@Override
	public synchronized void setProgress(int progress) {
		super.setProgress(progress);
		invalidate();
	}

	Paint p = new Paint();

	FontMetrics fm = p.getFontMetrics();

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		p.setTypeface(Typeface.DEFAULT_BOLD);
		String s = getProgress() + "%";
		p.setColor(Color.GREEN);
		p.setTextSize(30);
		int textH = (int) (fm.bottom - fm.top);
		int textW = (int) p.measureText(s);
		canvas.drawText(s, getMeasuredWidth() / 2 - textW / 2,
				getMeasuredHeight() / 2 + textH / 2, p);
	}
}
