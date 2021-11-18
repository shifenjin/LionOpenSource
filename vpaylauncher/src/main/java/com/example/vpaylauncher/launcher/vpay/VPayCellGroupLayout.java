package com.example.vpaylauncher.launcher.vpay;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.yunnex.vpay.R;
import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.view.CellGroupLayout;
import com.yunnex.vpay.launcher.view.CellItemLayout;
import com.yunnex.vpay.launcher.view.UtilsScreen;

import java.util.List;

/**
 * Created by lion on 2017/2/20.
 */

public class VPayCellGroupLayout extends CellGroupLayout
{
	public VPayCellGroupLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	protected void initCellItemLayout(List<CellInfo> cellInfoList)
	{
		if (cellInfoList != null)
		{
			LayoutInflater inflater = LayoutInflater.from(getContext());
			removeAllViews();

			mCellCount = cellInfoList.size();

			for (CellInfo cellInfo : cellInfoList)
			{
				CellItemLayout layout = (VPayCellItemLayout) inflater.inflate(R.layout.vpay_cell_item_layout, this, true);
				//				layout.setCellGroupLayout(this);
				layout.setLauncherLayout(mLauncherPageLayout.getLauncherLayout());
				layout.getLayoutParams().width = mCellWidth;
				layout.getLayoutParams().height = mCellHeight;

				// 应用类名
				layout.setActivityName(cellInfo.getActivityName());

				// 应用icon
				ImageView appIcon = layout.getAppIconImageView();
				Drawable appIconDrawable = cellInfo.getAppIconDrawable();
				if (appIconDrawable != null)
					appIcon.setImageDrawable(appIconDrawable);
				else
					UtilsScreen.displayImage(null, appIcon);

				// 应用名
				layout.setAppName(cellInfo.getAppName());

				// 应用消息数
				layout.updateMsgNum(cellInfo.getMsgNum());

				layout.mIndexBeforeMove = cellInfo.getPositionIndex();
				layout.mIndexCurrent = cellInfo.getPositionIndex();
				layout.mIndexLast = cellInfo.getPositionIndex();
				layout.mScreenIndex = cellInfo.getScreenIndex();
				layout.isDragged = false;
				layout.isDark = false;

			}
		}
	}
}
