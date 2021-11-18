package com.example.vpaylauncher.launcher.vpay;

import android.content.Context;

import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.db.CellInfoDBHelper;
import com.yunnex.vpay.launcher.utils.LauncherUtils;
import com.yunnex.vpay.launcher.view.LauncherEventBus;
import com.yunnex.vpay.launcher.view.LauncherRefreshEvent;
import com.yunnex.vpay.lib.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lion on 2017/2/14.
 */

public class VPayLauncherUtils
{
	/**
	 * 掌贝 桌面内置应用 包名列表
	 *
	 * @return
	 */

	public static List<String> getLauncherInsetPkgNameList()
	{
		List<String> pkgNameList = new ArrayList<>();
		pkgNameList.add(PackageUtils.PACKAGE_QRCODE_SCANNER);    // 扫码
		pkgNameList.add(PackageUtils.PACKAGE_CARD_COUPON);    //	卡券
		pkgNameList.add(PackageUtils.PACKAGE_ESHOP);    // 商品订单
		pkgNameList.add(PackageUtils.PACKAGE_SOLICITATION);        // 拉客
		pkgNameList.add(PackageUtils.PACKAGE_SMALL_INDUSTRY);    // 店内点餐
		pkgNameList.add(PackageUtils.PACKAGE_TAKEOUT);    // 外卖
		pkgNameList.add(PackageUtils.PACKAGE_SMART_WAITER);    // 智能小二
		pkgNameList.add(PackageUtils.PACKAGE_CATERING);    // 排队
		pkgNameList.add(PackageUtils.PACKAGE_EINVOICE);    // 电子发票
		pkgNameList.add(PackageUtils.PACKAGE_EAPPOINT);    // 预约中心
		pkgNameList.add(PackageUtils.PACKAGE_APPSTORE);    // 掌柜应用
		pkgNameList.add(PackageUtils.PACKAGE_SETTING);    // 设置
		pkgNameList.add(PackageUtils.PACKAGE_HELP_CENTER);    // 帮助中心

		return pkgNameList;
	}

	/**
	 * 初始化 应用入口数据库
	 */
	public static void init(Context context)
	{
		CellInfoDBHelper.getInstance(context).deleteAll();

		// 扫码、卡券、商品订单、拉客、店内点餐、外卖、智能小二、排队、电子发票、预约中心、掌柜应用、设置、帮助中心
		int positionIndex = 0;
		int screenIndex = VPayLauncherLayout.MAIN_SCREEN;
		Set<String> operatorBlackList = LauncherUtils.getOperatorBlackList(context);
		List<CellInfo> CellInfoInsetList = LauncherUtils.getCellInfoByPkgName(context, getLauncherInsetPkgNameList());

		for (CellInfo cellInfoInset : CellInfoInsetList)
		{
			if (!operatorBlackList.contains(cellInfoInset.getPkgName()))
			{
				if (positionIndex >= VPayLauncherConfig.mainScreenCellCapacity && screenIndex == VPayLauncherLayout.MAIN_SCREEN)
				{
					screenIndex = VPayLauncherLayout.SIDE_SCREEN;
					positionIndex = 0;
				}
				String activityName = cellInfoInset.getActivityName();
				String pkgName = cellInfoInset.getPkgName();
				CellInfo cellInfo = new CellInfo(null, activityName, pkgName, screenIndex, positionIndex, 0);

				CellInfoDBHelper.getInstance(context).add(cellInfo);
				positionIndex++;
			}
		}

		if (screenIndex == VPayLauncherLayout.MAIN_SCREEN)
		{
			positionIndex = 0;
			screenIndex = VPayLauncherLayout.SIDE_SCREEN;
		}
		List<CellInfo> cellInfoList = LauncherUtils.getCellInfoFromSystemExceptBlacklist(context);
		if (cellInfoList != null)
		{
			for (CellInfo cellInfo : cellInfoList)
			{
				String activityName = cellInfo.getActivityName();

				if (activityName != null && !CellInfoDBHelper.getInstance(context).isAppExist(activityName) && !operatorBlackList.contains(activityName))
				{
					cellInfo.setScreenIndex(screenIndex);
					cellInfo.setPositionIndex(positionIndex);
					cellInfo.setMsgNum(0);
					CellInfoDBHelper.getInstance(context).add(cellInfo);
					positionIndex++;
				}
			}
		}

		// 刷新桌面
		LauncherEventBus.getInstance().post(new LauncherRefreshEvent(null));
	}
}
