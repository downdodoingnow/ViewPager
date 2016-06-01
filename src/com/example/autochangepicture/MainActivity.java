package com.example.autochangepicture;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements OnPageChangeListener,
		OnTouchListener {
	private ViewPager viewPager;
	private RadioGroup radioGroup;
	// 图片ID
	private int[] imageId = { R.drawable.f, R.drawable.fo, R.drawable.s,
			R.drawable.t };
	// 轮播到的当前图片和前一张图片
	private int index, preIndex;
	// 定时器用于实现轮播
	private Timer timer;
	private Myhandler handler;
	// 用于判断是否继续轮播
	private boolean isCountinue = true;

	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		handler = new Myhandler();
		initRadioButton();
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnTouchListener(this);
		adapter = new MyAdapter(this, imageId);
		viewPager.setAdapter(adapter);

		changePicTrue();
	}

	// 图片实现轮播
	public void changePicTrue() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (isCountinue) {
					handler.sendEmptyMessage(1);
				}
			}
		}, 3000, 3000);// 延迟2秒 每隔2秒发送一次
	}

	// 根据图片数初始化RadioButton
	public void initRadioButton() {
		for (int i = 0; i < imageId.length; i++) {
			ImageView image = new ImageView(this);
			// 设置背景选择器
			image.setImageResource(R.drawable.rg_selector);
			image.setPadding(20, 0, 0, 0);
			// 将按钮添加金RadioGroup
			radioGroup.addView(image, ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		// 默认选中第一个按钮
		radioGroup.getChildAt(0).setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class Myhandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (1 == msg.what) {
				index++;
				viewPager.setCurrentItem(index);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		index = position;
		setCurrentBnt(index % imageId.length);
	}

	// 设置对应按钮的位置
	public void setCurrentBnt(int currentBnt) {
		if (null != radioGroup.getChildAt(currentBnt)) {
			// 当前按钮选中
			radioGroup.getChildAt(currentBnt).setEnabled(false);
		}
		if (null != radioGroup.getChildAt(preIndex)) {
			radioGroup.getChildAt(preIndex).setEnabled(true);
			preIndex = currentBnt;
		}
	}

	// 用于判断是否进行轮播
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			isCountinue = false;
			break;
		default:
			isCountinue = true;
			break;
		}
		return false;
	}

}
