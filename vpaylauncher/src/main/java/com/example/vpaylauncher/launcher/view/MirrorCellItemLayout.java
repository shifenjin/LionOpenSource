package com.example.vpaylauncher.launcher.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunnex.vpay.R;

/**
 * Created by lion on 16/7/8.
 */
public class MirrorCellItemLayout
{
	private WindowManager              mWindowManager;
	private WindowManager.LayoutParams mWindowLayoutParams;
	private int                        mWindowLayoutParamsLastX;
	private int                        mWindowLayoutParamsLastY;

	private CellItemLayout mDragCellItemLayout;

	public MirrorCellItemLayout(Context context, @LayoutRes int resource)
	{
		// 初始化 镜像应用

		mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

		if (mDragCellItemLayout == null)
		{
			mDragCellItemLayout = (CellItemLayout) LayoutInflater.from(context).inflate(resource, null, false);
			mDragCellItemLayout.setVisibility(View.GONE);
		}
		mWindowLayoutParams = new WindowManager.LayoutParams();
		mWindowLayoutParams.format = PixelFormat.TRANSLUCENT;
		mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

		mWindowLayoutParams.alpha = 0.55f;
		mWindowLayoutParams.width = LauncherLayout.mCellWidth;
		mWindowLayoutParams.height = LauncherLayout.mCellHeight;
		mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

		if (mDragCellItemLayout != null)
		{
			new Handler().post(new Runnable()
			{
				public void run()
				{
					mWindowManager.addView(mDragCellItemLayout, mWindowLayoutParams);
				}
			});
		}
	}

	/**
	 * 设置 镜像应用
	 *
	 * @param appIcon 应用icon
	 * @param appName 应用名称
	 */
	public void setMirrorCellItemLayout(Drawable appIcon, String appName)
	{
		if (mDragCellItemLayout != null)
		{
			((ImageView) mDragCellItemLayout.findViewById(R.id.appIcon)).setImageDrawable(appIcon);
			((TextView) mDragCellItemLayout.findViewById(R.id.appName)).setText(appName);
		}
	}

	/**
	 * 移动镜像应用
	 *
	 * @param x 屏幕横坐标
	 * @param y 屏幕纵坐标
	 */
	public void moveTo(float x, float y)
	{
		if (mWindowLayoutParams != null && mDragCellItemLayout != null)
		{
			mWindowLayoutParams.x = (int) x - mWindowLayoutParams.width / 2;
			mWindowLayoutParams.y = (int) y - mWindowLayoutParams.height / 2;
//			if (Math.abs(mWindowLayoutParams.x - mWindowLayoutParamsLastX) < 50 && Math.abs(mWindowLayoutParams.y - mWindowLayoutParamsLastY) < 50)
//			{
				if (mWindowManager != null)
					mWindowManager.updateViewLayout(mDragCellItemLayout, mWindowLayoutParams);
//			}

			mWindowLayoutParamsLastX = mWindowLayoutParams.x;
			mWindowLayoutParamsLastY = mWindowLayoutParams.y;
		}
	}

	/**
	 * 显示 镜像应用
	 */
	public void visible()
	{
		if (mDragCellItemLayout != null)
			mDragCellItemLayout.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏 镜像应用
	 */
	public void gone()
	{
		if (mDragCellItemLayout != null)
			mDragCellItemLayout.setVisibility(View.GONE);
	}

	/**
	 * 释放资源
	 */
	public void clear()
	{
		if (mDragCellItemLayout != null)
		{
			new Handler().post(new Runnable()
			{
				public void run()
				{
					mWindowManager.removeView(mDragCellItemLayout);
					mDragCellItemLayout = null;
				}
			});

		}
	}
}
