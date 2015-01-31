package com.example.longdemo;

import java.util.List;

import com.dk.view.drop.CoverManager;
import com.dk.view.drop.WaterDrop;
import com.dk.view.drop.DropCover.OnDragCompeteListener;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��waterdrop ��Hlistview Adapter
 * 
 * @author fangc
 * 
 */
public class GridViewAdapter extends BaseAdapter {
	private String names[];

	List list;
	private Context mContext;
	private TextView name_tv;
	private ImageView img;
	private View deleteView;
	private boolean isShowDelete;
	private boolean isStart;
	private boolean flag;// DropCover �����¼���

	private int mLastMotionX, mLastMotionY;
	// �Ƿ��ƶ���
	private boolean isMoved;
	// �Ƿ��ͷ���
	private boolean isReleased;
	// �Ƿ�Ҫִ���ͷź����
	private boolean isup_tocontinue = true;
	// �ƶ�����ֵ
	private static final int TOUCH_SLOP = 4;
	Handler handler;

	// HandlerThread thread;

	public boolean isStart() {
		return isStart;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
		notifyDataSetChanged();
	}

	public boolean isFlag() {
		return flag;
	}

	/**
	 * false:����item ������¼��ڶ���ֹͣʱҪ��Ϊfalse���򻹿�����ק ;
	 * true:��ʼ����ʱ ��Ϊtrue ��ʾ���Խ����϶�ɾ��
	 * 
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private GridView grideview;
	Activity activity;

	public GridViewAdapter(Context mContext, Activity activity, String names[],
			List list, Handler handdler) {
		this.mContext = mContext;
		this.names = names;

		this.activity = activity;
		this.list = list;
		// thread = new HandlerThread("dolongpress");
		this.handler = handdler;
	}

	public void setIsShowDelete(boolean isShowDelete) {
		this.isShowDelete = isShowDelete;
		notifyDataSetChanged();
	}

	/**
	 * �Զ��峤��
	 * 
	 * @param context
	 * @return
	 */
	// ������runnable
	Runnable mLongPressRunnable = new Runnable() {

		@Override
		public void run() {
			// �����󡣡���

			Toast.makeText(activity, "Long click", Toast.LENGTH_SHORT).show();
			isup_tocontinue = false;
			handler.sendEmptyMessage(0);

		}
	};

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void deleteCell(View v, final int index) {

		AnimationListener al = new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {

				list.remove(index);
				notifyDataSetChanged();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
		};

		collapse(v, al);

	}

	private void collapse(final View v, AnimationListener al) {

		final int initialwidth = v.getMeasuredWidth();

		Animation anim = new Animation() {
			private Transformation t;

			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				this.t = t;
				if (interpolatedTime == 1) {
					v.setVisibility(View.GONE);
				} else {
					v.getLayoutParams().width = initialwidth
							- (int) (initialwidth * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (al != null) {
			anim.setAnimationListener(al);
		}
		anim.setDuration(400);
		anim.setFillAfter(true);
		v.startAnimation(anim);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item,
				null);

		// Log.e("fc", "getview ����������");

		final View view = convertView;
		final ViewGroup viewgroup = parent;
		final int position1 = position;
		// img = (ImageView) convertView.findViewById(R.id.img);

		LinearLayout layout = (LinearLayout) convertView
				.findViewById(R.id.ll_grid_item);

		name_tv = (TextView) convertView.findViewById(R.id.name_tv);
		deleteView = convertView.findViewById(R.id.delete_markView);
		deleteView.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
		final WaterDrop drop = (WaterDrop) convertView.findViewById(R.id.img);
		drop.setText(String.valueOf(position));
		drop.setImgview((Integer) list.get(position));
		drop.setFlag(flag);
		drop.setOnDragCompeteListener(new OnDragCompeteListener() {

			@Override
			public void onDrag() {

				deleteCell(view, position1);

			}
		});

		if (false) { // ��д������������
			/**
			 * ��дlongOnclick click
			 */
			drop.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int x = (int) event.getX();
					int y = (int) event.getY();

					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:

						Log.e("fangchao", "ontouch  ====DOWM======>dowm");
						mLastMotionX = x;
						mLastMotionY = y;
						isMoved = false;
						isup_tocontinue = true;

						handler.removeCallbacks(mLongPressRunnable);

						handler.postDelayed(mLongPressRunnable,
								ViewConfiguration.getLongPressTimeout() + 100);
						break;
					case MotionEvent.ACTION_MOVE:

						if (isMoved) {
							handler.removeCallbacks(mLongPressRunnable);
							break;
						}
						if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
								|| Math.abs(mLastMotionY - y) > TOUCH_SLOP) {
							// �ƶ�������ֵ�����ʾ�ƶ���
							isMoved = true;
							handler.removeCallbacks(mLongPressRunnable);
						}

						break;
					case MotionEvent.ACTION_UP:

						// �ͷ���

						handler.removeCallbacks(mLongPressRunnable);
						if (isup_tocontinue) {
							Log.e("fangchao", "ontouch  =====UP======>��������������");
							Toast.makeText(activity, "���", Toast.LENGTH_SHORT)
									.show();
						}

						break;

					}

					return true;
				}
			});

			// CoverManager.getInstance().finish(this, event.getRawX(),
			// event.getRawY());
		}
		// img.setBackgroundResource((Integer)list.get(position));
		// Log.e("eeeeee", list.get(position).toString());
		// name_tv.setText(position);

		deleteView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * view.clearAnimation(); view.setVisibility(View.GONE);
				 * list.remove(position1); notifyDataSetChanged(); Log.e("ddd",
				 * "delete ======="+position1);
				 */
				deleteCell(view, position1);

			}

		});
		return convertView;
	}
}
