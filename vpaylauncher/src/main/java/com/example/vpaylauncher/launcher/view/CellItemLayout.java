package com.example.vpaylauncher.launcher.view;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.CellInfoController;
import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.view.config.LauncherConfig;
import com.yunnex.vpay.util.VPayLogUtils;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by lion on 16/6/8.
 * <p>
 * 应用入口 类
 */
public abstract class CellItemLayout extends LinearLayout
{
	private GestureDetector mGestureDetector;

	protected ImageView mAppIconImageView;
	protected TextView  mAppNameTextView;
	protected TextView  mMsgNumTextView;

	protected String mPkgName;	// 应用包名
	private String    mActivityName;	// 应用入口类名
	protected int mMsgNum;	// 消息数

	public Handler               mHandler;
	public CellAnimationRunnable mCellAnimationRunnable;

	private LauncherLayout mLauncherLayout;    // 所属 桌面

	// cell在当前屏幕 上一次位置 索引
	public    int     mIndexLast;
	// cell在当前屏幕 当前位置 索引
	public    int     mIndexCurrent;
	// cell移动操作之前在屏幕的位置 索引
	public    Integer mIndexBeforeMove;
	// cell移动操作之后在屏幕的位置 索引
	public    Integer mIndexAfterMove;
	// 屏幕索引
	public    int     mScreenIndex;
	// 是否为被拖动cell
	public boolean isDragged;
	// 是否变暗
	public    boolean isDark;

	public CellItemLayout(final Context context, AttributeSet attrs)
	{
		super(context, attrs);

		mGestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener()
		{
			@Override
			public boolean onDown(MotionEvent e)
			{
				Log.i("testGestureDetector", "onDown");
				if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_NONE)
					darkenIcon();

				return false;
			}

			@Override
			public void onShowPress(MotionEvent e)
			{
				Log.i("testGestureDetector", "onShowPress");
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e)
			{
				Log.i("testGestureDetector", "onSingleTapUp");

				restoreIcon();
				// 启动应用
				startApp();

				return false;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
			{
				Log.i("testGestureDetector", "onScroll");
				restoreIcon();

				return false;
			}

			@Override
			public void onLongPress(MotionEvent e)
			{
				if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_NONE)
				{
					LauncherLayout.screenControlState = LauncherLayout.SCREEN_CONTROL_STATE_CELL_MOVING;
					Log.i("test", "桌面状态 - 应用移位状态");

					//					Toast.makeText(context, "长按了应用", Toast.LENGTH_SHORT).show();

					isDragged = true;
					restoreIcon();
					setVisibility(View.INVISIBLE);

					createDragLayout(e.getRawX(), e.getRawY());
				}
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
			{
				return false;
			}
		});

		if (LauncherLayout.CAN_APP_MOVE)
			mGestureDetector.setIsLongpressEnabled(true);
		else
			mGestureDetector.setIsLongpressEnabled(false);

		setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				boolean bool = mGestureDetector.onTouchEvent(event);
				if (event.getAction() == MotionEvent.ACTION_CANCEL)
				{
					if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_NONE)
						restoreIcon();
				}
				return bool;
			}
		});

		mHandler = new Handler();
	}

	public void setLauncherLayout(LauncherLayout launcherLayout)
	{
		mLauncherLayout = launcherLayout;
	}

	public String getPkgName()
	{
		return mPkgName;
	}

	public void setPkgName(String pkgName)
	{
		mPkgName = pkgName;
	}

	public String getActivityName()
	{
		return mActivityName;
	}

	public void setActivityName(String activityName)
	{
		mActivityName = activityName;
	}

	public int getMsgNum()
	{
		return mMsgNum;
	}

	public ImageView getAppIconImageView()
	{
		return mAppIconImageView;
	}

	public void setAppName(String appName)
	{
		mAppNameTextView.setText(appName);
	}

	public String getAppName()
	{
		return mAppNameTextView.getText().toString();
	}

	/**
	 * 启动应用
	 */
	private void startApp()
	{
		CellInfo cellInfo = CellInfoController.getInstance(getContext()).loadCellInfoByPostion(mScreenIndex,
				mIndexCurrent);
		if (cellInfo != null)
		{
			Intent launchIntent = new Intent();
			launchIntent.setComponent(new ComponentName(cellInfo.getPkgName(), cellInfo.getActivityName()));
			launchIntent.setAction(Intent.ACTION_MAIN);
			launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			try
			{
				getContext().startActivity(launchIntent);
			}
			catch (Exception e)
			{
				Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 启动应用 ：启动失败! - " + e.getMessage());
				Toast.makeText(getContext(), "数据异常，启动失败！", Toast.LENGTH_SHORT).show();
			}

		}
		else
		{
			Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 启动应用 ：启动失败! - CellInfo 为空");
		}

	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		mAppIconImageView = (ImageView) findViewById(R.id.appIcon);
		mAppNameTextView = (TextView) findViewById(R.id.appName);
		mMsgNumTextView = (TextView) findViewById(R.id.msgNum);
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		if (!CellItemEventBus.getInstance().isRegistered(this))
			CellItemEventBus.getInstance().register(this);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		if (CellItemEventBus.getInstance().isRegistered(this))
			CellItemEventBus.getInstance().unregister(this);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSpaceSize, heightSpaceSize);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		boolean intercepted = true;
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				break;
			}
		}

		return intercepted;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				Log.i("test", "CellItemLayout onTouchEvent - ACTION_DOWN");
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_CELL_MOVING)
				{
					if (isDragged)
					{
						onDragLayout(event.getRawX(), event.getRawY());

						LauncherPageLayout launcherPageLayoutCurrent = mLauncherLayout.getCurrentLauncherPageLayout();
						if (launcherPageLayoutCurrent != null)
						{
							// 位于屏幕左边界区域
							if (event.getRawX() < LauncherConfig.edgeOfwidthToSwitchScreen && launcherPageLayoutCurrent.canCellMoveLeft())
							{
								// 取消应用移动设置、增加设置
								CellGroupLayout cellGroupLayout = launcherPageLayoutCurrent.getCellGroupLayout();
								if (cellGroupLayout != null)
								{
									cellGroupLayout.mCellMovingLockTo = null;
									cellGroupLayout.cancelCellMoving();
									cellGroupLayout.cancelCellAdding();
									mIndexAfterMove = null;

									// 同屏
									if (mScreenIndex == LauncherLayout.screenCurrentIndex)
									{
										if (mIndexBeforeMove != null && !mIndexBeforeMove.equals(mIndexCurrent))
										{
											cellGroupLayout.cellMoving(this, mIndexCurrent, mIndexBeforeMove);
											mIndexAfterMove = null;
										}
									}
									// 跨屏
									else
									{
										if (cellGroupLayout.mCellAddedLast != null && !cellGroupLayout.mCellAddedLast.equals(cellGroupLayout.getChildCount()))
										{
											cellGroupLayout.cellAdding(this, cellGroupLayout.mCellAddedLast, cellGroupLayout.getChildCount());
										}
									}
								}

								// 设置 切屏设置
								if (mLauncherLayout != null)
									mLauncherLayout.setupSwitchScreenBySlide(0);


							}
							// 位于屏幕右边界区域
							else if (event.getRawX() > UtilsScreen.getWindowWidthPixels(getContext()) - LauncherConfig.edgeOfwidthToSwitchScreen && launcherPageLayoutCurrent.canCellMoveRight())
							{
								// 取消应用移动设置、增加设置
								CellGroupLayout cellGroupLayout = launcherPageLayoutCurrent.getCellGroupLayout();
								if (cellGroupLayout != null)
								{
									cellGroupLayout.mCellMovingLockTo = null;
									cellGroupLayout.cancelCellMoving();
									cellGroupLayout.cancelCellAdding();
									mIndexAfterMove = null;

									// 同屏
									if (mScreenIndex == LauncherLayout.screenCurrentIndex)
									{
										if (mIndexBeforeMove != null && !mIndexBeforeMove.equals(mIndexCurrent))
										{
											cellGroupLayout.cellMoving(this, mIndexCurrent, mIndexBeforeMove);

										}
									}
									// 跨屏
									else
									{
										if (cellGroupLayout.mCellAddedLast != null && !cellGroupLayout.mCellAddedLast.equals(cellGroupLayout.getChildCount()))
										{
											cellGroupLayout.cellAdding(this, cellGroupLayout.mCellAddedLast, cellGroupLayout.getChildCount());
										}
									}
								}

								// 设置 切屏设置
								if (mLauncherLayout != null)
									mLauncherLayout.setupSwitchScreenBySlide(1);
							}
							// 位于屏幕中间区域
							else
							{
								Log.i("test", "移动选中应用 - 触屏坐标 在屏幕 中的坐标 ：" + event.getRawX() + " " + event.getRawY());
								// 同屏
								if (mScreenIndex == LauncherLayout.screenCurrentIndex)
								{
									if (mLauncherLayout != null)
										mLauncherLayout.cancelSwitchScreenBySlide();

									int cellIndexFrom = mIndexCurrent;
									int cellIndexTo = getCellIndexInCellLayout((CellGroupLayout) getParent(), event.getRawX(), event.getRawY());
									((CellGroupLayout) getParent()).checkCellMoveInCellGroup(this, mIndexBeforeMove, cellIndexFrom, cellIndexTo);
								}
								// 跨屏
								else
								{
									mLauncherLayout.cancelSwitchScreenBySlide();

									CellGroupLayout cellGroupLayout = launcherPageLayoutCurrent.getCellGroupLayout();
									if (cellGroupLayout != null)
									{
										// 如果目标屏幕为主屏，且主屏应该个数超过可容纳个数，不触发移动应用操作
										if (cellGroupLayout.mCellLimitWidth * cellGroupLayout.mCellLimitHeight != 0)
											if (cellGroupLayout.getCellCount() >= cellGroupLayout.mCellLimitWidth * cellGroupLayout.mCellLimitHeight)
												break;

										int cellIndexTo = getCellIndexInCellLayout(cellGroupLayout, event.getRawX(), event.getRawY());
										Log.i("test", "移动选中应用 - 触屏坐标 在 第" + String.valueOf(LauncherLayout.screenCurrentIndex) + "屏 的 应用布局的 应用索引位置是 " + String.valueOf(cellIndexTo));

										cellGroupLayout.checkCellAdd(this, cellIndexTo);
									}
								}
							}
						}
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				Log.i("test", "CellItemLayout onTouchEvent - ACTION_UP");
				if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_CELL_MOVING)
				{
					if (isDragged)
					{
						removeDragLayout();

						LauncherLayout.screenControlState = LauncherLayout.SCREEN_CONTROL_STATE_NONE;
						Log.i("test", "桌面状态 - 无操作状态");

						LauncherPageLayout launcherPageLayoutCurrent = mLauncherLayout.getCurrentLauncherPageLayout();
						if (mLauncherLayout != null)
							mLauncherLayout.cancelSwitchScreenBySlide();

						CellGroupLayout cellGroupLayout = launcherPageLayoutCurrent.getCellGroupLayout();
						if (cellGroupLayout != null)
						{
							cellGroupLayout.clearCellGroupAnimation();
							cellGroupLayout.cancelCellMoving();
							cellGroupLayout.cancelCellAdding();


							int cellIndexTo = getCellIndexInCellLayout(cellGroupLayout, event.getRawX(), event.getRawY());
							// 生效范围释放移动应用
							if (cellIndexTo != -1)
							{
								// 同屏
								if (mScreenIndex == LauncherLayout.screenCurrentIndex)
								{
									// 同步数据库，刷新界面
									if (mIndexBeforeMove != null && mIndexAfterMove != null)
									{
										Log.i("test", "应用移动 - 同屏：" + String.valueOf(mScreenIndex) + "屏   " +
												"移动前 ：" + mIndexBeforeMove + "  移动后：" + mIndexAfterMove);
										CellInfoController.getInstance(getContext()).move(mScreenIndex, mIndexBeforeMove, LauncherLayout.screenCurrentIndex, mIndexAfterMove);
									}
								}
								// 跨屏
								else
								{
									if (mIndexBeforeMove != null && mIndexAfterMove != null)
									{
										Log.i("test", "应用移动 - 跨屏：" + String.valueOf(mScreenIndex) + "屏 到 " +
												"" + LauncherLayout.screenCurrentIndex + "屏" +
												"  " +
												"移动前 ：" + mIndexBeforeMove + "  移动后：" + mIndexAfterMove);
										CellInfoController.getInstance(getContext()).move(mScreenIndex, mIndexBeforeMove, LauncherLayout.screenCurrentIndex, mIndexAfterMove);
									}
								}
							}
							else
							{
								Toast.makeText(getContext(), "不在生效范围，应用移动失败", Toast.LENGTH_SHORT).show();
							}
						}

						// 刷新桌面
						LauncherEventBus.getInstance().post(new LauncherRefreshEvent(null));
					}
				}
				break;
			}
			case MotionEvent.ACTION_CANCEL:
			{
				Log.i("test", "CellItemLayout onTouchEvent - ACTION_CANCEL");

				if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_CELL_MOVING)
				{
					if (isDragged)
					{
						removeDragLayout();

						LauncherLayout.screenControlState = LauncherLayout.SCREEN_CONTROL_STATE_NONE;
						Log.i("test", "桌面状态 - 无操作状态");

						LauncherPageLayout launcherPageLayoutCurrent = mLauncherLayout.getCurrentLauncherPageLayout();
						CellGroupLayout cellGroupLayout = launcherPageLayoutCurrent.getCellGroupLayout();
						if (cellGroupLayout != null)
						{
							cellGroupLayout.clearCellGroupAnimation();
							cellGroupLayout.cancelCellMoving();
							cellGroupLayout.cancelCellAdding();
						}

						// 刷新桌面
						LauncherEventBus.getInstance().post(new LauncherRefreshEvent(null));
					}
				}
				break;
			}
		}
		return true;
	}

	private void createDragLayout(float rawX, float rawY)
	{
		mLauncherLayout.mMirrorCellItemLayout.moveTo(rawX, rawY);
		mLauncherLayout.mMirrorCellItemLayout.setMirrorCellItemLayout(mAppIconImageView.getDrawable(), mAppNameTextView.getText().toString());
		mLauncherLayout.mMirrorCellItemLayout.visible();
		mLauncherLayout.mMirrorCellItemLayout.moveTo(rawX, rawY);
	}

	private void onDragLayout(float rawX, float rawY)
	{
		Log.i("test", "拖拽位置 - " + rawX + " " + rawY);
		mLauncherLayout.mMirrorCellItemLayout.moveTo(rawX, rawY);
	}

	private void removeDragLayout()
	{
		mLauncherLayout.mMirrorCellItemLayout.gone();
	}

	/**
	 * 获取 指定屏幕坐标 在 指定CellLayout位置 的 CellItemLayout 的 index值
	 *
	 * @param cellGroupLayout 指定CellLayout
	 * @param rawX            指定屏幕坐标X轴值
	 * @param rawY            指定屏幕坐标Y轴值
	 * @return CellItemLayout 的 index值（当位置不在CellLayout内时，返回 -1）
	 */
	private int getCellIndexInCellLayout(CellGroupLayout cellGroupLayout, float rawX, float rawY)
	{
		int[] rawXY = new int[2];
		cellGroupLayout.getLocationOnScreen(rawXY);
		Log.i("test", "移动选中应用 - cellLayout 在屏幕中的 左上坐标 ：" + rawXY[0] + " " + rawXY[1]);

		float x = rawX - rawXY[0];
		float y = rawY - rawXY[1];
		Log.i("test", "移动选中应用 - 触屏坐标 在指定CellLayout 中的坐标 ：" + x + " " + y);

		int index;

		// 屏幕坐标在CellLayout内
		if (x > 0 && x < cellGroupLayout.getWidth() && y > 0 & y < cellGroupLayout.getHeight())
		{
			// 屏幕坐标在ScrollCellLayout内
			ViewParent viewParent = cellGroupLayout.getParent();
			if (viewParent instanceof ScrollCellGroupLayout)
			{
				ScrollCellGroupLayout scrollCellGroupLayout = (ScrollCellGroupLayout) viewParent;
				index = (int) (y / LauncherLayout.mCellHeight) * cellGroupLayout.mCellLimitWidth + (int) (
						x / LauncherLayout.mCellWidth
				);

				y = y - scrollCellGroupLayout.getScrollY();
				if (x > 0 && x < scrollCellGroupLayout.getWidth() && y > 0 && y <
						scrollCellGroupLayout.getHeight())
				{
					return index;
				}
				else
				{
					index = -1;
				}
			}
			else
			{
				index = (int) (y / LauncherLayout.mCellHeight) * cellGroupLayout.mCellLimitWidth + (int) (
						x / LauncherLayout.mCellWidth
				);
			}

			Log.i("test", "移动选中应用 - 触屏坐标 在指定CellLayout 中的index值 ：" + index);
		}
		// 屏幕坐标不在CellLayout内
		else
		{
			index = -1;
			Log.i("test", "移动选中应用 - 触屏坐标 不在在指定CellLayout内, index值 ：" + index);
		}
		return index;
	}

	/**
	 * 使icon变暗
	 */
	abstract protected void darkenIcon();

	/**
	 * 还原icon
	 */
	abstract protected void restoreIcon();

	/**
	 * 更新 消息数视图
	 */
	abstract protected void updateMsgNumView();

	@Subscribe
	public void restore(CellItemRestoreIconEvent event)
	{
		restoreIcon();
	}

	@Subscribe
	public void updateMsgNum(CellItemUpdateMsgNumEvent event)
	{
		Bundle bundle = event.getBundle();
		if (bundle.containsKey(mActivityName))
		{
			updateMsgNum(bundle.getInt(mActivityName));
		}
	}

	public void updateMsgNum(Integer msgNum)
	{
		mMsgNum = msgNum;
		Log.i("CellItemLayout_Msg", "应用更新数为:" + msgNum);
		updateMsgNumView();
	}

	// 应用入口 移动动画------------------------------begin

	/**
	 * 应用动画 事件
	 */

	public class CellAnimationRunnable implements Runnable
	{
		private CellItemLayout mCellItemLayout;
		private int            xFrom;
		private int            xTo;
		private int            yFrom;
		private int            yTo;
		private int            mDuration;

		public CellAnimationRunnable(CellItemLayout cellItemLayout, int xFrom, int xTo, int
				yFrom, int yTo, int duration)
		{
			mCellItemLayout = cellItemLayout;
			this.xFrom = xFrom;
			this.xTo = xTo;
			this.yFrom = yFrom;
			this.yTo = yTo;
			this.mDuration = duration;
		}

		@Override
		public void run()
		{
			Log.i("test", "应用移动 动画运行 ：应用名字 - " + getAppName() + "  " + xFrom + " " +
					"" + xTo + "   " + yFrom + " " + yTo);

			ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mCellItemLayout, "translationX", xTo);
			objectAnimatorX.setDuration(mDuration);
			ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mCellItemLayout, "translationY", yTo);
			objectAnimatorY.setDuration(mDuration);
			objectAnimatorX.start();
			objectAnimatorY.start();

			mCellAnimationRunnable = null;
		}
	}

	protected void setCellAnimationRunnable(CellItemLayout cellItemLayout, int xFrom, int xTo, int
			yFrom, int yTo, int duration)
	{
		mCellAnimationRunnable = new CellAnimationRunnable(cellItemLayout, xFrom, xTo, yFrom, yTo,
				duration);
	}

	protected void clearCellAnimation()
	{
		// 取消动画
		if (mCellAnimationRunnable != null)
		{
			Log.i("test", "应用移动 动画移除 ：应用名字 - " + getAppName());
			mHandler.removeCallbacks(mCellAnimationRunnable);
			mCellAnimationRunnable = null;
		}

		// 如果动画已开始，停止动画
		if (getAnimation() != null)
			clearAnimation();
	}
	// 应用入口 移动动画------------------------------end
}
