package com.example.vpaylauncher.launcher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.view.config.LauncherConfig;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by lion on 16/6/7.
 * <p>
 * 应用入口 布局 类
 */
public abstract class CellGroupLayout extends ViewGroup
{
	private String TAG = "CellGroupLayout";

	public int mCellLimitWidth     = 0;    // 横向可容纳个数
	public int mCellLimitHeight    = 0;    // 纵向可容纳个数
	public int mCellLimitHeightMin = 0;    // 纵向最少可容纳个数

	protected LauncherPageLayout mLauncherPageLayout;

	protected int mCellWidth;        // 应用入口 宽
	protected int mCellHeight;    // 应用入口 高

	protected int mCellCount = 0;        // 应用入口 个数

	public  Handler            mHandler;
	private CellMovingRunnable mCellMovingRunnable;  // 应用入口布局 移动 任务
	public  Integer            mCellMovingLockTo;    // 同屏应用移动中，应用即将要去向的位置的index
	private CellAddingRunnable mCellAddingRunnable;        // 应用入口布局 增加 任务
	public  Integer            mCellAddedLast;    // 跨屏应用添加中，上一次添加位置的index
	public  Integer            mCellAddingLockTo;        // 跨屏应用添加中，应用即将添加的位置的index

	public CellGroupLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CellGroupLayout);
		mCellLimitWidth = typedArray.getInteger(R.styleable.CellGroupLayout_cell_limit_width, 0);
		mCellLimitHeight = typedArray.getInteger(R.styleable.CellGroupLayout_cell_limit_height, 0);
		if (mCellLimitHeight == 0)
		{
			mCellLimitHeightMin = typedArray.getInteger(R.styleable.CellGroupLayout_cell_limit_height_min, 0);
		}
		typedArray.recycle();

		mCellWidth = LauncherLayout.mCellWidth;
		mCellHeight = LauncherLayout.mCellHeight;

		mHandler = new Handler();
	}

	public void setLauncherPageLayout(LauncherPageLayout launcherPageLayout)
	{
		mLauncherPageLayout = launcherPageLayout;
	}

	public int getCellCount()
	{
		return mCellCount;
	}

	@Override
	protected void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		LauncherEventBus.getInstance().register(this);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		LauncherEventBus.getInstance().unregister(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int widthSpaceSize = LauncherLayout.mCellWidth * mCellLimitWidth;

		int heightSpaceSize;
		// 有固定高
		if (mCellLimitHeight != 0)
		{
			heightSpaceSize = LauncherLayout.mCellHeight * mCellLimitHeight;
		}
		// 无固定高
		else
		{
			if (mCellLimitWidth != 0)
			{
				if (((mCellCount - 1) / mCellLimitWidth + 1) < mCellLimitHeightMin)
				{
					heightSpaceSize = LauncherLayout.mCellHeight * mCellLimitHeightMin;
				}
				// 最低高生效
				else
				{
					heightSpaceSize = LauncherLayout.mCellHeight * ((mCellCount - 1) / mCellLimitWidth + 1);
				}
			}
			else
			{
				heightSpaceSize = 0;
			}

		}

		setMeasuredDimension(widthSpaceSize, heightSpaceSize);
		Log.i(TAG, "mCellWidth " + LauncherLayout.mCellWidth);
		Log.i(TAG, "mCellHeigth " + LauncherLayout.mCellHeight);
		Log.i(TAG, "widthSpaceSize " + widthSpaceSize);
		Log.i(TAG, "heightSpaceSize " + heightSpaceSize);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			final CellItemLayout childView = (CellItemLayout) getChildAt(i);

			if (childView.mIndexBeforeMove == childView.mIndexCurrent)
			{
				int indexCurrent = childView.mIndexCurrent;
				int left = LauncherLayout.mCellWidth * (indexCurrent % mCellLimitWidth);
				int top = LauncherLayout.mCellHeight * (indexCurrent / mCellLimitWidth);
				int right = left + LauncherLayout.mCellWidth;
				int bottom = top + LauncherLayout.mCellHeight;
				childView.layout(left, top, right, bottom);
			}
		}
	}

	public void initCell(List<CellInfo> cellInfoList)
	{
		mCellMovingRunnable = null;
		mCellMovingLockTo = null;
		mCellAddingRunnable = null;
		mCellAddedLast = null;
		mCellAddingLockTo = null;

		initCellItemLayout(cellInfoList);
	}

	/**
	 * 初始化 应用入口 视图
	 *
	 * @param cellInfoList
	 */
	abstract protected void initCellItemLayout(List<CellInfo> cellInfoList);

	/**
	 * 检测 是否添加 应用入口
	 *
	 * @param cellItemLayoutAdding
	 * @param index
	 */
	public void checkCellAdd(CellItemLayout cellItemLayoutAdding, int index)
	{
		if (index > getChildCount())
			index = getChildCount();

		if (mCellAddedLast != null && mCellAddedLast.equals(index))
		{
			Log.i("test", "应用拖拽 - 跨屏： 拖拽 起点与终点 相同");
			mCellAddingLockTo = null;
			cancelCellAdding();

			return;
		}

		if (mCellAddingLockTo != null && mCellAddingLockTo.equals(index))
		{
			Log.i("test", "应用拖拽 - 跨屏： 拖拽终点 与 即将增加终点相同，即将增加..." + "   增加终点 : " +
					mCellAddingLockTo);
			return;
		}
		else if (index < 0)
		{
			Log.i("test", "应用拖拽 - 跨屏 - 增加应用 - 不在生效范围内");

			cancelCellAdding();
			mCellAddingLockTo = null;

			if (mCellAddedLast == null || mCellAddedLast.equals(getChildCount()))
			{
				mCellAddedLast = null;
				return;
			}
			cellAdding(cellItemLayoutAdding, mCellAddedLast, getChildCount());
			mCellAddedLast = null;
			cellItemLayoutAdding.mIndexAfterMove = null;
			Log.i("test", "应用拖拽 - 跨屏 - 移动后位置 - " + cellItemLayoutAdding.mIndexAfterMove);
		}
		else
		{
			//			if (mCellAddedLast == -1)
			//				mCellAddedLast = getChildCount();
			Log.i("test", "应用拖拽 - 跨屏： 启动 新的 即将增加终点" + "   舍弃增加终点 : " + mCellAddingLockTo + "   " +
					"新增加终点 : " + index);
			mCellAddingLockTo = index;
			cancelCellAdding();
			if (mCellAddingLockTo.equals(getChildCount()))
				cellAdding(cellItemLayoutAdding, mCellAddedLast, mCellAddingLockTo);
			else
				setupCellAdding(cellItemLayoutAdding, mCellAddedLast, mCellAddingLockTo, LauncherConfig.timeCellMovingForReady);
		}
	}

	/**
	 * 设置 应用入口增加
	 *
	 * @param cellItemLayoutAdding
	 * @param cellAddedLast        上一次增加的位置索引
	 * @param cellAddingLockTo     增加的位置索引
	 * @param delayMillisecond     延迟执行时间
	 */
	public void setupCellAdding(CellItemLayout cellItemLayoutAdding, Integer cellAddedLast, int cellAddingLockTo, int delayMillisecond)
	{
		if (mCellAddingRunnable == null)
		{
			mCellAddingRunnable = new CellAddingRunnable(cellItemLayoutAdding, cellAddedLast, cellAddingLockTo);
			mHandler.postDelayed(mCellAddingRunnable, delayMillisecond);
		}
	}

	/**
	 * 取消 应用增加设置
	 */
	public void cancelCellAdding()
	{
		if (mCellAddingRunnable != null)
		{
			mHandler.removeCallbacks(mCellAddingRunnable);
			mCellAddingRunnable = null;
		}
	}

	/**
	 * 应用增加
	 *
	 * @param cellItemLayoutAdding
	 * @param cellAddedLast        上一次增加的位置索引
	 * @param cellAddingLockTo     增加的位置索引
	 */
	public void cellAdding(CellItemLayout cellItemLayoutAdding, Integer cellAddedLast, int cellAddingLockTo)
	{
		if (cellAddedLast == null)
			cellAddedLast = getChildCount();

		// 应用增加 - 数据变动
		if (cellAddedLast < cellAddingLockTo)
		{
			for (int i = 0; i < getChildCount(); i++)
			{
				CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);

				cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;

				int index = cellItemLayout.mIndexCurrent;
				if (index < cellAddedLast || index > cellAddingLockTo)
					continue;

				cellItemLayout.mIndexCurrent = --index;
			}
		}
		else if (cellAddedLast > cellAddingLockTo)
		{
			for (int i = 0; i < getChildCount(); i++)
			{
				CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);

				cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;

				int index = cellItemLayout.mIndexCurrent;
				if (index < cellAddingLockTo || index > cellAddedLast)
					continue;

				cellItemLayout.mIndexCurrent = ++index;
			}
		}
		mCellAddingLockTo = null;
		cellItemLayoutAdding.mIndexAfterMove = cellAddingLockTo;
		Log.i("test", "应用拖拽 - 跨屏 - 移动后位置 - " + cellItemLayoutAdding.mIndexAfterMove);

		// 应用增加 - 执行动画
		for (int i = 0; i < getChildCount(); i++)
		{
			final CellItemLayout childView = (CellItemLayout) getChildAt(i);

			if (childView.mIndexCurrent == childView.mIndexLast)
				continue;

			int indexCurrent = childView.mIndexCurrent;
			int leftBeforeMove = 0;
			int topBeforeMove = 0;
			if (childView.mIndexBeforeMove != null)
			{
				leftBeforeMove = LauncherLayout.mCellWidth * (childView.mIndexBeforeMove % mCellLimitWidth);
				topBeforeMove = LauncherLayout.mCellHeight * (childView.mIndexBeforeMove / mCellLimitWidth);
			}
			int left = LauncherLayout.mCellWidth * (indexCurrent % mCellLimitWidth);
			int top = LauncherLayout.mCellHeight * (indexCurrent / mCellLimitWidth);
			int right = left + LauncherLayout.mCellWidth;
			int bottom = top + LauncherLayout.mCellHeight;

			if (childView.getVisibility() == View.VISIBLE)
			{
				int indexLast = childView.mIndexLast;
				int leftLast = LauncherLayout.mCellWidth * (indexLast % mCellLimitWidth);
				int topLast = LauncherLayout.mCellHeight * (indexLast / mCellLimitWidth);

				if (childView.mCellAnimationRunnable != null)
				{
					Log.i("test", "应用移动 动画移除 ：应用名字 - " + childView.getAppName());
					childView.mHandler.removeCallbacks(childView.mCellAnimationRunnable);
					childView.mCellAnimationRunnable = null;
				}
				Log.i("test", "应用移动 动画发送 ：应用名字 - " + childView.getAppName());
				childView.setCellAnimationRunnable(childView, leftLast - (int) childView.getX(), left - leftBeforeMove, topLast - (int) childView.getY(), top - topBeforeMove, LauncherConfig.cellMovingAnimationDuration);

				int animationDelayed;
				if (cellAddedLast < cellAddingLockTo)
				{
					animationDelayed = (childView.mIndexLast - cellAddedLast) * LauncherConfig.cellMovingInterval;
				}
				else
				{
					animationDelayed = (childView.mIndexLast - cellAddingLockTo) * LauncherConfig.cellMovingInterval;
				}
				childView.mHandler.postDelayed(childView.mCellAnimationRunnable, animationDelayed);

			}
		}

		// 应用增加 - 改变数据
		for (int i = 0; i < getChildCount(); i++)
		{
			CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);
			cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;
		}
		mCellAddedLast = cellAddingLockTo;
	}

	public class CellAddingRunnable implements Runnable
	{
		private CellItemLayout mCellItemLayoutAdding;
		private Integer        mCellAddLastIndex;
		private int            mCellAddIndex;

		public CellAddingRunnable(CellItemLayout cellItemLayoutAdding, Integer indexLast, int index)
		{
			mCellItemLayoutAdding = cellItemLayoutAdding;
			mCellAddLastIndex = indexLast;
			mCellAddIndex = index;
		}

		@Override
		public void run()
		{
			cellAdding(mCellItemLayoutAdding, mCellAddLastIndex, mCellAddIndex);
		}
	}


	/**
	 * 检测 是否在布局内移动 应用入口
	 *
	 * @param indexBeforeMove
	 * @param cellIndexFrom
	 * @param cellIndexTo
	 */
	public void checkCellMoveInCellGroup(CellItemLayout cellItemLayoutMoving, Integer indexBeforeMove, int cellIndexFrom, int cellIndexTo)
	{
		//		Log.i("test", "应用移动 - 同屏");

		int from = cellIndexFrom;
		int to = cellIndexTo;

		if (to >= getChildCount())
			to = getChildCount() - 1;

		if (from == to)
		{
			Log.i("test", "应用拖拽 - 同屏： 拖拽 起点与终点 相同 ");
			mCellMovingLockTo = null;
			cancelCellMoving();

			return;
		}

		if (mCellMovingLockTo != null && mCellMovingLockTo.equals(to))
		{
			Log.i("test", "应用拖拽 - 同屏： 拖拽终点 与 即将移动终点相同，即将移动..." + "   移动终点 : " + mCellMovingLockTo);
			return;
		}
		else if (to < 0)
		{
			Log.i("test", "应用拖拽 - 同屏： 拖拽 起点或终点 不在生效范围内");
			cancelCellMoving();
			mCellMovingLockTo = null;

			if (indexBeforeMove != null && indexBeforeMove.equals(from))
				return;

			if (indexBeforeMove != null)
			{
				cellMoving(cellItemLayoutMoving, from, indexBeforeMove);
				cellItemLayoutMoving.mIndexAfterMove = null;
				Log.i("test", "应用拖拽 - 同屏 - 移动后位置 - " + cellItemLayoutMoving.mIndexAfterMove);
			}
		}
		else
		{
			Log.i("test", "应用拖拽 - 同屏： 启动 新的 即将移动终点" + "   舍弃移动终点 : " + mCellMovingLockTo + "   新移动终点 : " + to);

			mCellMovingLockTo = to;
			cancelCellMoving();
			if (mCellMovingLockTo.equals(getChildCount() - 1))
				cellMoving(cellItemLayoutMoving, from, mCellMovingLockTo);
			else
				setupCellMoving(cellItemLayoutMoving, from, mCellMovingLockTo, LauncherConfig.timeCellMovingForReady);
		}
	}

	/**
	 * 设置 应用移动
	 *
	 * @param from             起点索引
	 * @param to               终点索引
	 * @param delayMillisecond 延迟执行时间
	 */
	public void setupCellMoving(CellItemLayout cellItemLayoutMoving, int from, int to, int delayMillisecond)
	{
		if (mCellMovingRunnable == null)
		{
			Log.i("test", "应用拖拽 - 同屏： 设置 应用移动");
			mCellMovingRunnable = new CellMovingRunnable(cellItemLayoutMoving, from, to);
			mHandler.postDelayed(mCellMovingRunnable, delayMillisecond);
		}
	}

	/**
	 * 取消 应用移动设置
	 */
	public void cancelCellMoving()
	{
		if (mCellMovingRunnable != null)
		{
			Log.i("test", "应用拖拽 - 同屏： 取消 应用移动设置");
			mHandler.removeCallbacks(mCellMovingRunnable);
			mCellMovingRunnable = null;
		}
	}

	/**
	 * 应用移动
	 *
	 * @param cellItemLayoutMoving
	 * @param from                 起点索引
	 * @param to                   终点索引
	 */
	public void cellMoving(CellItemLayout cellItemLayoutMoving, int from, int to)
	{
		// 应用移动 - 数据变动
		if (from < to)
		{
			for (int i = 0; i < getChildCount(); i++)
			{
				CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);

				cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;

				int index = cellItemLayout.mIndexCurrent;
				if (index < from || index > to)
					continue;

				if (index == from)
					cellItemLayout.mIndexCurrent = to;
				else
					cellItemLayout.mIndexCurrent = --index;
			}
		}
		else if (from > to)
		{
			for (int i = 0; i < getChildCount(); i++)
			{
				CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);

				cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;

				int index = cellItemLayout.mIndexCurrent;
				if (index < to || index > from)
					continue;

				if (index == from)
					cellItemLayout.mIndexCurrent = to;
				else
					cellItemLayout.mIndexCurrent = ++index;
			}
		}
		mCellMovingLockTo = null;
		cellItemLayoutMoving.mIndexAfterMove = to;
		Log.i("test", "应用拖拽 - 同屏 - 移动后位置 - " + cellItemLayoutMoving.mIndexAfterMove);


		// 应用移动 - 执行动画
		for (int i = 0; i < getChildCount(); i++)
		{
			final CellItemLayout childView = (CellItemLayout) getChildAt(i);

			if (childView.mIndexCurrent == childView.mIndexLast)
				continue;

			int indexCurrent = childView.mIndexCurrent;
			int leftBeforeMove = 0;
			int topBeforeMove = 0;
			if (childView.mIndexBeforeMove != null)
			{
				leftBeforeMove = LauncherLayout.mCellWidth * (childView.mIndexBeforeMove % mCellLimitWidth);
				topBeforeMove = LauncherLayout.mCellHeight * (childView.mIndexBeforeMove / mCellLimitWidth);
			}
			int left = LauncherLayout.mCellWidth * (indexCurrent % mCellLimitWidth);
			int top = LauncherLayout.mCellHeight * (indexCurrent / mCellLimitWidth);
			int right = left + LauncherLayout.mCellWidth;
			int bottom = top + LauncherLayout.mCellHeight;

			if (childView.getVisibility() == View.VISIBLE)
			{
				int indexLast = childView.mIndexLast;
				int leftLast = LauncherLayout.mCellWidth * (indexLast % mCellLimitWidth);
				int topLast = LauncherLayout.mCellHeight * (indexLast / mCellLimitWidth);

				if (childView.mCellAnimationRunnable != null)
				{
					Log.i("test", "应用移动 动画移除 ：应用名字 - " + childView.getAppName());
					childView.mHandler.removeCallbacks(childView.mCellAnimationRunnable);
					childView.mCellAnimationRunnable = null;
				}
				Log.i("test", "应用移动 动画发送 ：应用名字 - " + childView.getAppName() + "   leftLast : " +
						leftLast + "   left : " +
						left + "   childView.getX() : " +
						childView.getX());
				childView.setCellAnimationRunnable(childView, leftLast - (int) childView.getX(), left - leftBeforeMove, topLast - (int) childView.getY(), top - topBeforeMove, LauncherConfig.cellMovingAnimationDuration);

				int animationDelayed;
				if (from < to)
				{
					animationDelayed = (childView.mIndexLast - from) * LauncherConfig.cellMovingInterval;
				}
				else
				{
					animationDelayed = (childView.mIndexLast - to) * LauncherConfig.cellMovingInterval;
				}
				childView.mHandler.postDelayed(childView.mCellAnimationRunnable, animationDelayed);

			}
		}

		// 应用移动 - 改变数据
		for (int i = 0; i < getChildCount(); i++)
		{
			CellItemLayout cellItemLayout = (CellItemLayout) getChildAt(i);
			cellItemLayout.mIndexLast = cellItemLayout.mIndexCurrent;
		}
	}

	public class CellMovingRunnable implements Runnable
	{
		private CellItemLayout mCellItemLayoutMoving;
		private int            mCellFromIndex;
		private int            mCellToIndex;

		public CellMovingRunnable(CellItemLayout cellItemLayoutMoving, int cellFromIndex, int cellToIndex)
		{
			mCellItemLayoutMoving = cellItemLayoutMoving;
			mCellFromIndex = cellFromIndex;
			mCellToIndex = cellToIndex;
		}

		@Override
		public void run()
		{
			cellMoving(mCellItemLayoutMoving, mCellFromIndex, mCellToIndex);
		}
	}


	/**
	 * 清空 应用入口布局 动画
	 */
	public void clearCellGroupAnimation()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			final CellItemLayout childView = (CellItemLayout) getChildAt(i);
			childView.clearCellAnimation();
		}
	}

	/**
	 * 停止 CellLayout 界面行为
	 *
	 * @param event
	 */
	@Subscribe
	public void cellLayoutStop(CellLayoutStopEvent event)
	{
		clearCellGroupAnimation();
		cancelCellMoving();
		cancelCellAdding();
	}
}
