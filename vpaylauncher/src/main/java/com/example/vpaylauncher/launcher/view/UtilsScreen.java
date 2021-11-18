package com.example.vpaylauncher.launcher.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.yunnex.vpay.R;

/**
 * Created by lion on 16/6/7.
 *
 * 桌面工具类
 */
public class UtilsScreen
{
	// 图片加载器
	private static final int DEFAULT_BG = R.drawable.ic_launcher;
	private static ImageLoader         mImageLoader;
	private static DisplayImageOptions mDisplayImageOptions;

	/**
	 * 加载图片
	 *
	 * @param uri
	 * @param imageView
	 */
	public static void displayImage(String uri, ImageView imageView)
	{
		if (mImageLoader == null)
			mImageLoader = ImageLoader.getInstance();
		if (mDisplayImageOptions == null)
			mDisplayImageOptions = new DisplayImageOptions.Builder().
					cacheInMemory(true).
					bitmapConfig(Bitmap.Config.RGB_565).
					showImageOnLoading(DEFAULT_BG).
					showImageForEmptyUri(DEFAULT_BG).
					showImageOnFail(DEFAULT_BG).
					cacheOnDisc(true).
					build();

		mImageLoader.displayImage(uri, imageView, mDisplayImageOptions);
	}


	/**
	 * 获取 屏幕宽度像素
	 *
	 * @param context
	 * @return
	 */
	public static int getWindowWidthPixels(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取 屏幕高度像素
	 *
	 * @param context
	 * @return
	 */
	public static int getWindowHeightPixels(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

}
