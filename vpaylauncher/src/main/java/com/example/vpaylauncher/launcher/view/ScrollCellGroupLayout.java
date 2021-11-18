package com.example.vpaylauncher.launcher.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lion on 16/7/5.
 */
public class ScrollCellGroupLayout extends ScrollView
{
	/* 滚动屏操作状态 */
	public static final int SCREEN_CONTROL_STATE_NONE = 0;	// 无操作状态
	public static final int SCREEN_CONTROL_STATE_SCROLLING = 1;	// 滚动状态

	public int scrollViewControlState;

	public ScrollCellGroupLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		scrollViewControlState = SCREEN_CONTROL_STATE_NONE;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if (LauncherLayout.screenControlState == LauncherLayout.SCREEN_CONTROL_STATE_CELL_MOVING)
			return false;

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				Log.i("test", "ScrollCellLayout onTouchEvent - ACTION_DOWN");
				break;
			}
			case MotionEvent.ACTION_MOVE:
			{
				Log.i("test", "ScrollCellLayout onTouchEvent - ACTION_MOVE");

				if (scrollViewControlState != SCREEN_CONTROL_STATE_SCROLLING)
				{
					scrollViewControlState = SCREEN_CONTROL_STATE_SCROLLING;
					CellItemEventBus.getInstance().post(new CellItemRestoreIconEvent("restore", null));
				}

				break;
			}
			case MotionEvent.ACTION_UP:
			{
				Log.i("test", "ScrollCellLayout onTouchEvent - ACTION_UP");

				scrollViewControlState = SCREEN_CONTROL_STATE_NONE;

				break;
			}
		}
		return super.onTouchEvent(event);
	}
}
