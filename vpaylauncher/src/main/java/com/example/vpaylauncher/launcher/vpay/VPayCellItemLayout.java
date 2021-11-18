package com.example.vpaylauncher.launcher.vpay;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;

import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.view.CellItemLayout;

/**
 * Created by lion on 2017/2/20.
 */

public class VPayCellItemLayout extends CellItemLayout
{
	public VPayCellItemLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void darkenIcon()
	{
		if (mAppIconImageView != null && !isDark)
		{
			mAppIconImageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
			isDark = true;
		}
	}

	@Override
	protected void restoreIcon()
	{
		if (mAppIconImageView != null && isDark)
		{
			mAppIconImageView.clearColorFilter();
			isDark = false;
		}
	}

	@Override
	protected void updateMsgNumView()
	{
		if (mMsgNum > 0)
		{
			if (mMsgNum <= 99)
			{
				if (mMsgNum < 10)
				{
					mMsgNumTextView.setWidth((int) getContext().getResources().getDimension(R.dimen.msg_num_bg_heidht));
				}
				else
				{
					mMsgNumTextView.setWidth((int) (
							getContext().getResources().getDimension(R.dimen.msg_num_bg_heidht) * 1.3
					));
				}
				mMsgNumTextView.setText(String.valueOf(mMsgNum));
			}
			else
			{
				mMsgNumTextView.setWidth((int) (
						getContext().getResources().getDimension(R.dimen.msg_num_bg_heidht) * 1.4
				));
				mMsgNumTextView.setText(String.valueOf("99+"));
			}
			mMsgNumTextView.setVisibility(VISIBLE);
		}
		else
		{
			mMsgNumTextView.setVisibility(GONE);
		}
	}
}
