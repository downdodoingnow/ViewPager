package com.example.autochangepicture;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyAdapter extends PagerAdapter {

	private int[] imageId;
	private List<ImageView> mList;
	private Context context;

	public MyAdapter(Context context, int[] imageId) {
		this.context = context;
		this.imageId = imageId;
		mList = new ArrayList<ImageView>();
	}

	@Override
	public int getCount() {
		// 无限大的数用于实现轮播
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position = position % imageId.length;
		ImageView image = new ImageView(context);
		image.setImageResource(imageId[position]);
		image.setScaleType(ImageView.ScaleType.FIT_XY);
		container.addView(image);
		mList.add(image);
		return image;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mList.get(position));
	}

}
