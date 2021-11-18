package com.example.vpaylauncher.launcher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.yunnex.vpay.R;

/**
 * Created by lion on 2017/2/10.
 * <p>
 * 桌面 页面
 */

public class LauncherPageLayout extends RelativeLayout
{
	private LauncherLayout mLauncherLayout; // 所属 桌面
	private Integer mLauncherPageIndex;	// 页面 索引

	private CellGroupLayout mCellGroupLayout; // 持有的 应用入口布局

	private boolean cellMoveLeft;	// 页面是否支持应用左移
	private boolean cellMoveRight;	// 页面是否支持应用右移

	public LauncherPageLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LauncherPageLayout);
		cellMoveLeft = typedArray.getBoolean(R.styleable.LauncherPageLayout_cell_move_left, false);
		cellMoveRight = typedArray.getBoolean(R.styleable.LauncherPageLayout_cell_move_right, false);
	}

	public boolean canCellMoveLeft()
	{
		return cellMoveLeft;
	}

	public boolean canCellMoveRight()
	{
		return cellMoveRight;
	}

	public LauncherLayout getLauncherLayout()
	{
		return mLauncherLayout;
	}

	public void setLauncherLayout(LauncherLayout launcherLayout)
	{
		mLauncherLayout = launcherLayout;
	}

	public Integer getLauncherPageIndex()
	{
		return mLauncherPageIndex;
	}

	public void setLauncherPageIndex(Integer launcherPageIndex)
	{
		mLauncherPageIndex = launcherPageIndex;
	}

	public CellGroupLayout getCellGroupLayout()
	{
		return mCellGroupLayout;
	}

	public void setCellGroupLayout(CellGroupLayout cellGroupLayout)
	{
		mCellGroupLayout = cellGroupLayout;
	}
}
