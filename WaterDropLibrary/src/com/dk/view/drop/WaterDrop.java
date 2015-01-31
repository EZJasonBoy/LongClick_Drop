package com.dk.view.drop;

import com.dk.view.drop.DropCover.OnDragCompeteListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.bangbang.support.v4.widget.HListView;

public class WaterDrop extends RelativeLayout {
	private Paint mPaint = new Paint();
	private TextView mTextView;
	private ImageView imgview;
	/**
	 * ÊÇ·ñ¿ÉÍÏ¶¯
	 */
	private boolean flag = false;

	public ImageView getImgview() {
		return imgview;
	}

	public void setImgview(int imgid) {
		this.imgview.setImageResource(imgid);
	}

	private DropCover.OnDragCompeteListener mOnDragCompeteListener;
	private boolean mHolderEventFlag;

	public WaterDrop(Context context) {
		super(context);
		init();
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public WaterDrop(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setText(String str) {
		mTextView.setText(str);
	}

	public void setTextSize(int size) {
		mTextView.setTextSize(size);
	}

	@SuppressLint("NewApi")
	private void init() {
		mPaint.setAntiAlias(true);
		if (VERSION.SDK_INT > 11) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		imgview = new ImageView(getContext());
		mTextView = new TextView(getContext());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		imgview.setImageResource(R.drawable.ic_launcher);
		imgview.setLayoutParams(params);
		mTextView.setTextSize(13);
		mTextView.setTextColor(0xffffffff);
		mTextView.setLayoutParams(params);
		// addView(mTextView);
		addView(imgview);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		// mPaint.setColor(0xffff3400);
		// canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2,
		// mPaint);
		Log.e("fc", "dispatchDraw");
		super.dispatchDraw(canvas);
		
	}

	@Override
	protected void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		Log.e("fc", "onAttachedToWindow");
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Log.e("fc", "onDraw");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		ViewGroup parent = getScrollableParent();
		
		if (flag) {
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				Log.e("tt", "down");
				mHolderEventFlag = !CoverManager.getInstance().isRunning();
				if (mHolderEventFlag) {
					if (parent != null)
						parent.requestDisallowInterceptTouchEvent(true);
					CoverManager.getInstance().start(this, event.getRawX(),
							event.getRawY(), mOnDragCompeteListener);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				Log.e("tt", "move8************");
				if (mHolderEventFlag) {
					CoverManager.getInstance().update(event.getRawX(),
							event.getRawY());
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				Log.e("tt", "up+cancel=============");
				if (mHolderEventFlag) {
					if (parent != null)
						parent.requestDisallowInterceptTouchEvent(false);
					CoverManager.getInstance().finish(this, event.getRawX(),
							event.getRawY());
				}
				break;
			}
			return true;
		} else
			return false;

	}

	private ViewGroup getScrollableParent() {
		View target = this;
		while (true) {
			View parent;
			try {
				/**
				 * ViewRootImpl cannot be cast to android.view.View
				 */
				parent = (View) target.getParent();
			} catch (Exception e) {
				return null;
			}
			if (parent == null)
				return null;
			if (parent instanceof ListView || parent instanceof ScrollView
					|| parent instanceof HListView) {
				return (ViewGroup) parent;
			}
			target = parent;
		}

	}

	public void setOnDragCompeteListener(
			OnDragCompeteListener onDragCompeteListener) {
		mOnDragCompeteListener = onDragCompeteListener;
	}
}
