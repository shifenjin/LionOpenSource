package com.example.vpaylauncher.launcher.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.db.CellInfoDBHelper;
import com.yunnex.vpay.lib.utils.PackageUtils;
import com.yunnex.vpay.lib.utils.SystemAppUtils;
import com.yunnex.vpay.util.UtilsApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hugo.weaving.DebugLog;

/**
 * Created by lion on 2017/3/23.
 */

public class LauncherUtils
{
	public static final String TAG = "LauncherUtils";

	private static List<CellInfo> mLauncherCellInfoList = new ArrayList<>();

	/**
	 * 获取 桌面应用入口（根据屏幕索引）
	 *
	 * @param context
	 * @return
	 */
	public static List<CellInfo> getLauncherCellList(Context context, Integer screenIndex)
	{
		List<CellInfo> cellInfoList = getLauncherCellList(context);
		List<CellInfo> cellInfoListByScreenIndex = new ArrayList<>();
		for (CellInfo cellInfo : cellInfoList)
		{
			if (cellInfo.getScreenIndex() == screenIndex)
				cellInfoListByScreenIndex.add(cellInfo);
		}

		return cellInfoListByScreenIndex;
	}

	/**
	 * 获取 桌面应用入口
	 *
	 * @return
	 */
	public static List<CellInfo> getLauncherCellList(Context context)
	{
		List<CellInfo> cellInfoList = getCellInfoFromSystemExceptBlacklist(context);

		// 获取 桌面入口 位置、消息数信息
		List<CellInfo> cellInfoListFromDB = CellInfoDBHelper.getInstance(context).loadAll();
		for (CellInfo cellInfoFromSystem : cellInfoList)
		{
			for (CellInfo cellInfoFromDB : cellInfoListFromDB)
			{
				String activityNameFromDB = cellInfoFromDB.getActivityName();
				if (activityNameFromDB != null && activityNameFromDB.equals(cellInfoFromSystem.getActivityName()))
				{
					cellInfoFromSystem.setScreenIndex(cellInfoFromDB.getScreenIndex());
					cellInfoFromSystem.setPositionIndex(cellInfoFromDB.getPositionIndex());
					cellInfoFromSystem.setMsgNum(cellInfoFromDB.getMsgNum());
				}
			}
		}

		return cellInfoList;
	}

	/**
	 * 获取 除去黑名单的 应用入口
	 *
	 * @param context
	 * @return
	 */
	public static List<CellInfo> getCellInfoFromSystemExceptBlacklist(Context context)
	{
		List<CellInfo> cellInfoListFromSystem = getAllCellInfoFromSystem(context);

		// 桌面入口 黑名单应用
		Set<String> appLauncherBlackList = LauncherUtils.getLauncherBlackList(context);

		// 去除 桌面入口黑名单应用
		List<CellInfo> cellInfoListFromSystemRemoveBlackList = new ArrayList<>();
		for (CellInfo cellInfo : cellInfoListFromSystem)
		{
			String pkgName = cellInfo.getPkgName();
			if (appLauncherBlackList.contains(pkgName))
				continue;
			cellInfoListFromSystemRemoveBlackList.add(cellInfo);
		}

		return cellInfoListFromSystemRemoveBlackList;
	}

	/**
	 * 获取 桌面应用入口 黑名单
	 *
	 * @return
	 */
	public static Set<String> getLauncherBlackList(Context context)
	{
		Set<String> appLauncherBlackSet = new HashSet<>();

		// 设备系统应用 黑名单
		appLauncherBlackSet.addAll(SystemAppUtils.getAppSystemWithLauncherBlackList());
		// 掌贝内置应用入口 黑名单
		appLauncherBlackSet.addAll(getLauncherBlackList_VPayInsetApp());
		// 操作员 黑名单
		appLauncherBlackSet.addAll(getOperatorBlackList(context));

		return appLauncherBlackSet;
	}

	/**
	 * 桌面 掌贝内置应用入口 黑名单
	 *
	 * @return
	 */
	public static Set<String> getLauncherBlackList_VPayInsetApp()
	{
		Set<String> blackSet = new HashSet<>();

		blackSet.add(PackageUtils.PACKAGE_MESSAGE_RECEIVER_COMMON); //	消息通知检测（公用）
		blackSet.add(PackageUtils.PACKAGE_MESSAGE_RECEIVER_WQ); //	消息通知检测（味千）
		//		appInsetBlackList.add(PACKAGE_HELP_CENTER); //	帮助中心
		blackSet.add(PackageUtils.PACKAGE_CARD_STATISTICS); //	卡券统计
		blackSet.add(PackageUtils.PACKAGE_SYSTEM_UPDATE); //	系统更新
		blackSet.add(PackageUtils.PACKAGE_VPAY); //	设备
		blackSet.add(PackageUtils.PACKAGE_PRINT_SDK); //	打印SDK
		blackSet.add(PackageUtils.PACKAGE_EELINE_POS); //	移联刷卡
		blackSet.add(PackageUtils.PACKAGE_STK);    //	SIM卡应用开发工具箱
		blackSet.add(PackageUtils.PACKAGE_POSP); //	银联刷卡
		blackSet.add(PackageUtils.PACKAGE_ZSETTINGS);    // 移动网络
		blackSet.add(PackageUtils.PACKAGE_DOWNLOAD_PROVIDER);    // 下载工具
		blackSet.add(PackageUtils.PACKAGE_PINYIN);    // 谷歌输入法
		blackSet.add(PackageUtils.PACKAGE_THIRD_PARTY_PAY); //	云移收款
		blackSet.add(PackageUtils.PACKAGE_VPAY_DEBUG); //	设备开发工具
		blackSet.add(PackageUtils.PACKAGE_AUTO_UPDATE); //     自更新应用
		blackSet.add(PackageUtils.PACKAGE_FILE_UPLOAD);    // 日志上传

		blackSet.add(PackageUtils.PACKAGE_PRINT_TEST);    // 打印测试
		blackSet.add(PackageUtils.PACKAGE_Z5_FIRMWARE);    // Z5_firmware

		return blackSet;
	}

	/**
	 * 获取 操作员权限黑名单
	 *
	 * @return
	 */
	public static Set<String> getOperatorBlackList(Context context)
	{
		// 获取 权限黑名单数据
		String blackListString = null;
		final Uri URI_BLACK_LIST = Uri.parse("content://com.yunnex.vpay.userdb/user/" + Integer.MAX_VALUE);
		Cursor cursor = null;
		try
		{
			cursor = context.getContentResolver().query(URI_BLACK_LIST, null, null, null, null);
			if (cursor != null)
			{
				while (cursor.moveToNext())
				{
					String pkgNameList = cursor.getString(cursor.getColumnIndexOrThrow("packName"));
					if (pkgNameList != null)
						blackListString = pkgNameList;
				}
			}
		}
		finally
		{
			if (cursor != null)
				cursor.close();
		}

		Set<String> stringSet = new HashSet<>();
		if (blackListString != null)
		{
			String[] pkgNameList = blackListString.split(",");
			if (pkgNameList != null)
			{
				for (String pkgName : pkgNameList)
				{
					// 非系统应用不受操作员黑名单控制
					if (UtilsApp.isSystemApp(context, pkgName))
						stringSet.add(pkgName);
				}
			}
		}
		if (stringSet != null)
		{
			android.util.Log.i(TAG, "操作员黑名单 : " + stringSet.toString());
		}
		else
		{
			android.util.Log.i(TAG, "Black list : null");
		}

		return stringSet;
	}

	/**
	 * 获取所有设备应用入口（从系统）
	 *
	 * @return
	 */
	@DebugLog
	private static List<CellInfo> getAllCellInfoFromSystem(Context context)
	{
		PackageManager pm = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.GET_RESOLVED_FILTER);


		List<String> launcherCellInfoListCacheStr = new ArrayList<>();
		for (CellInfo cellInfo : mLauncherCellInfoList)
		{
			String activityName = cellInfo.getActivityName();
			launcherCellInfoListCacheStr.add(activityName);
		}

		List<String> launcherCellInfoListStr = new ArrayList<>();
		for (ResolveInfo resolveInfo : resolveInfos)
		{
			String activityName = resolveInfo.activityInfo.name;
			launcherCellInfoListStr.add(activityName);
		}

		for (int i = 0; i < launcherCellInfoListCacheStr.size(); i++)
		{
			if (!launcherCellInfoListStr.contains(launcherCellInfoListCacheStr.get(i)))
			{
				launcherCellInfoListCacheStr.remove(i);
				i--;
			}
		}

		for (int i = 0; i < mLauncherCellInfoList.size(); i++)
		{
			CellInfo cellInfo = mLauncherCellInfoList.get(i);
			String activityName = cellInfo.getActivityName();
			if (!launcherCellInfoListCacheStr.contains(activityName))
			{
				mLauncherCellInfoList.remove(i);
				i--;
			}
		}
		if (resolveInfos != null)
		{
			for (ResolveInfo reInfo : resolveInfos)
			{
				String activityName = reInfo.activityInfo.name;
				if (!launcherCellInfoListCacheStr.contains(activityName))
				{
					String pkgName = reInfo.activityInfo.packageName;
					String appLabel = (String) reInfo.loadLabel(pm);
					// 耗时
					Drawable icon = reInfo.loadIcon(pm);

					Intent launchIntent = new Intent();
					launchIntent.setComponent(new ComponentName(pkgName, activityName));
					CellInfo cellInfo = new CellInfo();
					cellInfo.setAppName(appLabel);
					cellInfo.setActivityName(activityName);
					cellInfo.setPkgName(pkgName);
					cellInfo.setAppIconDrawable(icon);
					cellInfo.setIntent(launchIntent);

					mLauncherCellInfoList.add(cellInfo);
				}
			}
		}

		return mLauncherCellInfoList;
	}

	/**
	 * 获取 应用入口 列表（根据应用包名列表
	 *
	 * @param context
	 * @param pkgNameList 应用包名
	 * @return
	 */
	public static List<CellInfo> getCellInfoByPkgName(Context context, List<String> pkgNameList)
	{
		List<CellInfo> cellInfoList = new ArrayList<>();

		PackageManager pm = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.GET_RESOLVED_FILTER);

		for (String pkgName : pkgNameList)
		{
			if (resolveInfos != null)
			{
				for (ResolveInfo reInfo : resolveInfos)
				{
					if (pkgName.equals(reInfo.activityInfo.packageName))
					{
						String activityName = reInfo.activityInfo.name;
						CellInfo cellInfo = new CellInfo(null, activityName, pkgName, null, null, 0);
						cellInfoList.add(cellInfo);
						break;
					}
				}
			}
		}

		return cellInfoList;
	}

	/**
	 * 更新 入口缓存
	 *
	 * @param context
	 * @param pkgName	应用包名
	 */
	public static void updateCellInfoCacheByPkgName(Context context, String pkgName)
	{
		PackageManager pm = context.getPackageManager();
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		mainIntent.setPackage(pkgName);
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, PackageManager.GET_RESOLVED_FILTER);
		if (resolveInfos != null)
		{
			for (ResolveInfo reInfo : resolveInfos)
			{
				String activityName = reInfo.activityInfo.name;
				if (mLauncherCellInfoList != null)
				{
					for (CellInfo cellInfo : mLauncherCellInfoList)
					{
						if (cellInfo.getActivityName().equals(activityName))
						{
							cellInfo.setAppName((String)reInfo.loadLabel(pm));
							// 耗时
							cellInfo.setAppIconDrawable(reInfo.loadIcon(pm));
						}
					}
				}
			}
		}
	}

}
