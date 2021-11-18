package com.example.vpaylauncher.launcher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.view.MotionEventCompat;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Scroller;

import com.example.vpaylauncher.launcher.view.config.LauncherConfig;
import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.utils.LauncherUtils;
import com.yunnex.vpay.launcher.view.config.LauncherConfig;
import com.yunnex.vpay.util.HardwareConfigUtils;
import com.yunnex.vpay.util.VPayLogUtils;

import org.greenrobot.eventbus.Subscribe;


/**
 * Created by lion on 16/6/7.
 */

/**
 * 桌面布局
 */
public abstract class LauncherLayout extends ViewGroup
{
	protected Context mContext;

	public static boolean CAN_APP_MOVE = true;	// 应用是否可拖动

	/* 屏幕操作状态*/
	public static final int SCREEN_CONTROL_STATE_NONE = 0;	// 无操作状态
	public static final int SCREEN_CONTROL_STATE_PAGE_MOVING = 1;	// 切屏状态
	public static final int SCREEN_CONTROL_STATE_CELL_MOVING = 2;	// 应用移位状态
	public static final int SCREEN_CONTROL_STATE_CELL_MORE = 3;	// 显示更多应用状态

	public static int screenCurrentIndex;	// 当前屏幕页面索引
	public static int screenControlState;	// 当前屏幕状态

	// 应用入口 高宽
	protected static int mCellWidth;
	protected static int mCellHeight;

	// 分别记录上次滑动的坐标
	private float mLastX          = 0;
	private float mLastY          = 0;
	// 分别记录上次滑动的坐标(onInterceptTouchEvent)
	private float mLastXIntercept = 0;
	private float mLastYIntercept = 0;

	private Scroller        mScroller;
	private VelocityTracker mVelocityTracker;

	public SwitchScreenBySlideRunnable mSwitchScreenBySlideRunnable;
	public static Handler                     mHandler;

	// 镜像应用
	public  MirrorCellItemLayout mMirrorCellItemLayout;
	private OnPageChangeListener mOnPageChangeListener;

	public LauncherLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mContext = context;

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LauncherLayout);

		mCellWidth = (int) typedArray.getDimension(R.styleable.LauncherLayout_cell_width, 0);
		mCellHeight = (int) typedArray.getDimension(R.styleable.LauncherLayout_cell_height, 0);

		typedArray.recycle();
		init(context);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		Log.i("test", "LauncherLayout生命周期 ：onAttachedToWindow");

		LauncherEventBus.getInstance().register(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(widthSpaceSize, heightSpaceSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int childLeft = 0;
		final int childCount = getChildCount();

		for (int i = 0; i < childCount; i++)
		{
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE)
			{
				int childWidth = UtilsScreen.getWindowWidthPixels(getContext());
				childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}

		// 根据 当前页面索引 显示页面
		scrollTo(UtilsScreen.getWindowWidthPixels(getContext()) * screenCurrentIndex, 0);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		Log.i("test", "LauncherLayout生命周期 ：onDetachedFromWindow");
		LauncherEventBus.getInstance().unregister(this);

		mVelocityTracker.recycle();
	}

	// 持有该对象的 activity 或 fragment onPause回调时调用
	public void onPause()
	{
		// 停止 CellLayout 界面行为
		LauncherEventBus.getInstance().post(new CellLayoutStopEvent(null));

		// 隐藏镜像应用
		if (mMirrorCellItemLayout != null)
			mMirrorCellItemLayout.gone();
	}

	public LauncherPageLayout getCurrentLauncherPageLayout()
	{
		return (LauncherPageLayout) getChildAt(screenCurrentIndex);
	}

	/**
	 * 设置屏幕
	 *
	 * @param screenIndex 屏幕索引
	 */
	public void switchScreenByIndex(int screenIndex)
	{
		if (screenIndex >= 0 && screenIndex <= getChildCount() - 1)
			screenCurrentIndex = screenIndex;
		else
			return;

		switchScreen(screenCurrentIndex);
	}

	/**
	 * 滑动切换屏幕
	 *
	 * @param leftOrRight 0: 切换到左屏  1：切换到右屏  （其他值不生效）
	 */
	private void switchScreenBySlide(int leftOrRight)
	{
		if (leftOrRight == 0)
			screenCurrentIndex = screenCurrentIndex - 1;
		else if (leftOrRight == 1)
			screenCurrentIndex = screenCurrentIndex + 1;
		else
			return;

		screenCurrentIndex = Math.max(0, Math.min(screenCurrentIndex, getChildCount() - 1));

		switchScreen(screenCurrentIndex);
	}

	private void switchScreen(int screenIndex)
	{
		screenCurrentIndex = screenIndex;

		Log.i("test", "当前屏幕索引 ：" + String.valueOf(screenCurrentIndex));

		int childWidth = UtilsScreen.getWindowWidthPixels(getContext());
		int scrollX = getScrollX();
		int dx = screenCurrentIndex * childWidth - scrollX;
		smoothScrollBy(dx, 0);

		if (mOnPageChangeListener != null)
		{
			mOnPageChangeListener.onPageSelected(screenIndex);
		}
	}

	private void init(Context context)
	{
		mScroller = new Scroller(getContext());
		mVelocityTracker = VelocityTracker.obtain();

		if (mHandler == null)
			mHandler = new Handler();

		// 设置应用是否可拖动
		CAN_APP_MOVE = HardwareConfigUtils.getFunAppMove();

		screenControlState = SCREEN_CONTROL_STATE_NONE;
	}

	/**
	 * 刷新应用界面
	 *
	 * @param event
	 */
	@Subscribe
	public void refreshView(LauncherRefreshEvent event)
	{
		screenControlState = SCREEN_CONTROL_STATE_NONE;
		if (mMirrorCellItemLayout != null)
			mMirrorCellItemLayout.gone();

		Bundle bundle = event.getBundle();
		if (bundle != null)
		{
			String pkgName = bundle.getString("pkgName");
			if (pkgName != null)
			{
				LauncherUtils.updateCellInfoCacheByPkgName(mContext, pkgName);
			}
		}

		onRefreshView();
		Log.i(VPayLogUtils.TAG_LAUNCHER, "桌面 - 刷新界面");

	}

	/**
	 * 刷新桌面时回调
	 */
	abstract protected void onRefreshView();

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		mVelocityTracker.addMovement(event);
		boolean intercepted = false;
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				int dxAbs = Math.abs(screenCurrentIndex * UtilsScreen.getWindowWidthPixels(getContext()) - getScrollX());
				if (!mScroller.isFinished() && dxAbs > LauncherConfig.scroolXLeftMinCanBeIntercept)
				{
					intercepted = true;
					screenControlState = SCREEN_CONTROL_STATE_PAGE_MOVING;
					Log.i("test", "桌面状态 - 切屏状态");
				}
				else
				{
					intercepted = false;
					screenControlState = SCREEN_CONTROL_STATE_NONE;
					Log.i("test", "桌面状态 - 无操作状态");
				}
				Log.i("test", "LauncherLayout onInterceptTouchEvent - ACTION_DOWN - " + intercepted);
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if (screenControlState == SCREEN_CONTROL_STATE_CELL_MOVING)
					return false;

				float deltaX = x - mLastXIntercept;
				float deltaY = y - mLastYIntercept;
				// 判断 滑动夹角是否小于30度
				if (Math.tan((LauncherConfig.slideAngleMax * Math.PI / 180)) > (
						Math.abs(deltaY) / Math.abs(deltaX)
				))
				{
					mVelocityTracker.computeCurrentVelocity(1000);
					float xVelocity = mVelocityTracker.getXVelocity();
					Log.i("test", "切屏 - 触发切屏 - xVelocity：" + xVelocity);
					intercepted = Math.abs(xVelocity) > LauncherConfig.slideXVelocity;
				}
				else
				{
					intercepted = false;
				}
				Log.i("test", "LauncherLayout onInterceptTouchEvent - ACTION_MOVE - " +
						intercepted + " - deltaX : " + String.valueOf(deltaX));

				break;
			}
			case MotionEvent.ACTION_UP:
			{
				intercepted = false;
				Log.i("test", "LauncherLayout onInterceptTouchEvent - ACTION_UP - " + intercepted);
				break;
			}
			default:
				break;
		}

		mLastX = x;
		mLastY = y;
		mLastXIntercept = x;
		mLastYIntercept = y;

		return intercepted;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		mVelocityTracker.addMovement(event);
		float x = event.getX(0);
		float y = event.getY(0);
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				Log.i("test", "LauncherLayout onTouchEvent - ACTION_DOWN");

				mScroller.abortAnimation();
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				Log.i("test", "LauncherLayout onTouchEvent - ACTION_MOVE - " + String.valueOf(x) + ", " + String.valueOf(y) + "  scrollX : " + getScrollX() + "  屏幕状态 ：" +
						screenControlState);

				if (screenControlState != SCREEN_CONTROL_STATE_PAGE_MOVING)
				{
					screenControlState = SCREEN_CONTROL_STATE_PAGE_MOVING;
					CellItemEventBus.getInstance().post(new CellItemRestoreIconEvent("restore", null));
				}

				float deltaX = x - mLastX;
				// 切屏边界（移动缓慢）
				if ((getScrollX() <= 0 && deltaX > 0) || (getScrollX() >= UtilsScreen
						.getWindowWidthPixels(getContext()) * (getChildCount() - 1) && deltaX < 0))
				{
					if (Math.abs(deltaX) < 50)
						scrollBy((int) -deltaX / 4, 0);
				}
				else
				{
					if (Math.abs(deltaX) < 50)
						scrollBy((int) -deltaX, 0);
					setCustomAlpha();
				}
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				Log.i("test", "LauncherLayout onTouchEvent - ACTION_UP");

				mVelocityTracker.computeCurrentVelocity(1000);
				float xVelocity = mVelocityTracker.getXVelocity();

				// 快速切屏
				if (Math.abs(xVelocity) >= 500)
				{
					if (xVelocity > 0)
						switchScreenBySlide(0);
					else
						switchScreenBySlide(1);
				}
				else
				{
					int childWidth = UtilsScreen.getWindowWidthPixels(getContext());
					int scrollX = getScrollX();
					screenCurrentIndex = (scrollX + childWidth / 2) / childWidth;
					screenCurrentIndex = Math.max(0, Math.min(screenCurrentIndex, getChildCount() - 1));

					switchScreen(screenCurrentIndex);
				}

				break;
			}
			default:
				break;
		}

		mLastX = x;
		mLastY = y;
		return true;
	}

	// 设置自定义透明度
	private void setCustomAlpha()
	{
		float screenWidth = UtilsScreen.getWindowWidthPixels(getContext());
		float positionOffset = (getScrollX() % screenWidth) / screenWidth;

		float alpha0 = 1 - (
				(float) (Math.cos((positionOffset + 1) * Math.PI) / 2.0f) + 0.5f
		);
		float alpha1 = (float) (Math.cos((positionOffset + 1) * Math.PI) / 2.0f) + 0.5f;

		View viewleft = null;
		View viewRight = null;
		int viewLeftIndex = (int) (getScrollX() / screenWidth);
		int viewRightIndex = viewLeftIndex + 1;
		if (viewLeftIndex >= 0 && viewLeftIndex < getChildCount())
			viewleft = getChildAt(viewLeftIndex);
		if (viewRightIndex >= 0 && viewRightIndex < getChildCount())
			viewRight = getChildAt(viewRightIndex);

		if (viewleft != null)
		{
			viewleft.setAlpha(alpha0);
		}
		if (viewRight != null)
		{
			viewRight.setAlpha(alpha1);
		}
		Log.i("test", "getScrollX() : " + getScrollX() + "   viewLeftIndex : " +
				viewLeftIndex + "   screenWidth : " + screenWidth +
				"   positionOffset : " +
				positionOffset + "  " +
				" alpha0 : " + alpha0);
	}

	private void smoothScrollBy(int dx, int dy)
	{
		mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
		invalidate();
	}

	@Override
	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			Log.i("test", "computeScroll - " + "currX : " + mScroller.getCurrX() + ", currY : " +
					mScroller.getCurrY());
			setCustomAlpha();

			postInvalidate();
		}
	}

	/**
	 * 设置 切屏
	 *
	 * @param leftOrRight 屏幕索引
	 */
	protected void setupSwitchScreenBySlide(int leftOrRight)
	{
		if (mSwitchScreenBySlideRunnable == null)
		{
			mSwitchScreenBySlideRunnable = new SwitchScreenBySlideRunnable(leftOrRight);
			mHandler.postDelayed(mSwitchScreenBySlideRunnable, LauncherConfig.timeSwitchScreenForReady);
		}
	}

	/**
	 * 取消 切屏
	 */
	protected void cancelSwitchScreenBySlide()
	{
		if (mSwitchScreenBySlideRunnable != null)
		{
			mHandler.removeCallbacks(mSwitchScreenBySlideRunnable);
			mSwitchScreenBySlideRunnable = null;
		}
	}

	/**
	 * 切屏 任务类
	 */
	public class SwitchScreenBySlideRunnable implements Runnable
	{
		int mScreenIndex;

		public SwitchScreenBySlideRunnable(int leftOrRight)
		{
			mScreenIndex = leftOrRight;
		}

		@Override
		public void run()
		{
			switchScreenBySlide(mScreenIndex);
			mSwitchScreenBySlideRunnable = null;
		}
	}

	public interface OnPageChangeListener
	{
		void onPageSelected(int pageIndex);
	}

	public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener)
	{
		mOnPageChangeListener = onPageChangeListener;
	}
}
