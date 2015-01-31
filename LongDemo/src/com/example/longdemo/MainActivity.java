package com.example.longdemo;

import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bangbang.support.v4.widget.HAbsListView;
import org.bangbang.support.v4.widget.HAbsListView.OnScrollListener;
import org.bangbang.support.v4.widget.HListView;
import org.bangbang.support.v4.widget.AdapterView.OnItemLongClickListener;

import com.dk.view.drop.CoverManager;

import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;

import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.widget.GridView;
import android.widget.LinearLayout;
/**
 * ���Զ��� ��קɾ��
 * @author fc
 *
 */
public class MainActivity extends Activity {
	Animation animation;
	LayoutAnimationController lac;
	HListView mGridView;
	/**
	 * �ⲿ���� ���봥��ȡ������  
	 * r1 ���������layout��Ҫ����   android:clickable="true"
	 * �������˸����� ������layout touch�¼���������ݣ�����ȡ������
	 */
RelativeLayout rl;//
	View touchview=null;
	public HListView getmGridView() {
		return mGridView;
	}

	public void setmGridView(HListView mGridView) {
		this.mGridView = mGridView;
	}

	LinearLayout layout;
	private GridViewAdapter mAdapter;
	ImageView iv;
	/**
	 * ��ʼ����
	 */
	private boolean isstart = false;
	private boolean isShowDelete = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		animation = AnimationUtils.loadAnimation(this, R.anim.list_anim);
		// �õ�һ��LayoutAnimationController����
		lac = new LayoutAnimationController(animation);
		// ���ÿؼ���ʾ��˳��
		lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
		// ���ÿؼ���ʾ���ʱ�䣻0 ��ʾһ��
		lac.setDelay(0);
		setContentView(R.layout.activity_main);
		layout = (LinearLayout) findViewById(R.id.llayout);
		rl=(RelativeLayout) findViewById(R.id.whole_layout);
		mGridView = (HListView) findViewById(R.id.Scrollview);
		
		CoverManager.getInstance().init(this);
		CoverManager.getInstance().setMaxDragDistance(400);//����ק����
		CoverManager.getInstance().setExplosionTime(250);//��ըʱ��
		
		
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(
					org.bangbang.support.v4.widget.AdapterView<?> parent,
					View view, int position, long id) {
				setAnimation();
				Log.e("fc", "item on long click ");
				return false;
			}

			
		});
	
		mGridView.setFocusable(true);

		rl.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e("touch ", event.getAction()+"");
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					//touchview=getTouchNotHlistview(mGridView, event);
				
							if(isstart)
							{
								cancelAnimations();
							return true;}
							else
							return false;
					
				}
		
				return false;
				
			}});
		
		/**
		 * ����ȡ��
		 *//*
		touchview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(isstart)
				{
					cancelAnimations();
				return true;}
				else
				return false;
			}
		});*/
		mGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(HAbsListView view, int scrollState) {
				
				
				if (isstart)
					mGridView.setLayoutAnimation(lac);
			}

			@Override
			public void onScroll(HAbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			

			}
		});
		List mylist = new ArrayList<Integer>();
		int array[] = new int[] { R.drawable.custom_clothes,
				R.drawable.custom_dessert,
				R.drawable.custom_diapers,
				R.drawable.custom_toy,
				R.drawable.custom_mom,
				R.drawable.custom_milk,
				R.drawable.custom_food,
				R.drawable.custom_bowl,
				R.drawable.custom_bathtub, R.drawable.custom_buggy };
		for (int i = 0; i < array.length; i++) {
			mylist.add(array[i]);
		}
		Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
			
				super.handleMessage(msg);
				setAnimation();
				Log.e("fangc", "handle message");
			}
			
		};
		mAdapter = new GridViewAdapter(MainActivity.this, this, new String[] {
				"111", "2222", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13" }, mylist,handler);
		mGridView.setAdapter(mAdapter);


		
	
		findViewById(R.id.bt1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mGridView.clearAnimation();
				Log.e("aa", "onbuttonclick");
			}
		});
		

		/**
		 * ����ȡ������  mGridView.getChildAt(i).clearAnimation();
		 */
		/*layout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				mGridView.clearAnimation();
				int count = mGridView.getChildCount();
				for (int i = 0; i < count; i++) {
					mGridView.getChildAt(i).clearAnimation();
				}
			mAdapter.setFlag(false);
				mAdapter.setIsShowDelete(false);
				isstart = false;
				mAdapter.setStart(isstart);
				Log.e("aa", "onlayouttouch");
				return false;
			}
		});*/
	}

	/*
	 * @Override public boolean onItemLongClick(AdapterView<?> parent, View
	 * view, int position, long id) { if (isShowDelete) { isShowDelete = false;
	 * } else { isShowDelete = true; }
	 * 
	 * 
	 * 
	 * 
	 * TranslateAnimation alphaAnimation2 = new TranslateAnimation(150f, 350f,
	 * 50, 50); alphaAnimation2.setDuration(1000);
	 * alphaAnimation2.setRepeatCount(Animation.INFINITE);
	 * alphaAnimation2.setRepeatMode(Animation.REVERSE);
	 * 
	 * 
	 * 
	 * //ΪListView����LayoutAnimationController���ԣ�
	 * mGridView.setLayoutAnimation(lac); //
	 * mGridView.startAnimation(animation); // alphaAnimation2.cancel();
	 * mAdapter.setIsShowDelete(true); return true; }
	 */
	
	/**
	 * ���Hlistview����Ĳ���
	 * @param hListViews
	 * @param ev
	 * @return
	 */
	private View getTouchNotHlistview(HListView hListView, MotionEvent ev){
		if(hListView == null ){
			return null;
		}
		Rect mRect = new Rect();
		HListView v = hListView ;
			v.getHitRect(mRect);
			Log.e("fc","lllllllllllll");
			if(!mRect.contains((int)ev.getX(), (int)ev.getY())){
				return v;
				
			
		}
		return null;
	}
	/**
	 * ȡ������
	 * @author fc
	 */
	void  cancelAnimations(){
		mGridView.clearAnimation();
		int count = mGridView.getChildCount();
		for (int i = 0; i < count; i++) {
			mGridView.getChildAt(i).clearAnimation();
		}
	mAdapter.setFlag(false);
		mAdapter.setIsShowDelete(false);
		isstart = false;
		mAdapter.setStart(isstart);
		
	}
	/**
	 * ��ʼ����
	 * @author fc 
	 */
	public  void setAnimation(){
		mGridView.setLayoutAnimation(lac);
		// mGridView.startAnimation(animation);
		// alphaAnimation2.cancel();
			mAdapter.setIsShowDelete(true);
			mAdapter.setFlag(true);
		    isstart = true;
		    mAdapter.setStart(isstart);
	}

	@Override
	public void onBackPressed() {
	if(isstart){
		cancelAnimations();
	}
	else finish();
		
	}

}
