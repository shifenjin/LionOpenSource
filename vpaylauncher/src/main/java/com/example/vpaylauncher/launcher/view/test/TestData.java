package com.example.vpaylauncher.launcher.view.test;

import android.content.Context;
import android.util.Log;

import com.yunnex.vpay.launcher.view.domain.AppInfo;
import com.yunnex.vpay.view.vpay.VPayLauncherLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 16/6/7.
 */
public class TestData
{
	private static TestData instance;

	private List<AppInfo> mTestDataList;

	private TestData(Context context)
	{
		mTestDataList = new ArrayList<>();

//		AppInfo appInfo = new AppInfo();
//		appInfo.setIcon(context.getResources().getDrawable(R.drawable.logo_smart_waiter));
//		appInfo.setName("奋进");
//		appInfo.setPositionIndex(0);
//		appInfo.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo1 = new AppInfo();
//		appInfo1.setIcon(context.getResources().getDrawable(R.drawable.logo_help_center));
//		appInfo1.setName("奋进1号");
//		appInfo1.setPositionIndex(1);
//		appInfo1.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo2 = new AppInfo();
//		appInfo2.setIcon(context.getResources().getDrawable(R.drawable.icon_appstore));
//		appInfo2.setName("奋进2号");
//		appInfo2.setPositionIndex(2);
//		appInfo2.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo3 = new AppInfo();
//		appInfo3.setIcon(context.getResources().getDrawable(R.drawable.takeout));
//		appInfo3.setName("奋进3号");
//		appInfo3.setPositionIndex(3);
//		appInfo3.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo4 = new AppInfo();
//		appInfo4.setIcon(context.getResources().getDrawable(R.drawable.icon_order_center));
//		appInfo4.setName("奋进4号");
//		appInfo4.setPositionIndex(4);
//		appInfo4.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo5 = new AppInfo();
//		appInfo5.setIcon(context.getResources().getDrawable(R.drawable.industy));
//		appInfo5.setName("奋进5号");
//		appInfo5.setPositionIndex(5);
//		appInfo5.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo6 = new AppInfo();
//		appInfo6.setIcon(context.getResources().getDrawable(R.drawable.customer));
//		appInfo6.setName("奋进6号");
//		appInfo6.setPositionIndex(6);
//		appInfo6.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo7 = new AppInfo();
//		appInfo7.setIcon(context.getResources().getDrawable(R.drawable.icon01));
//		appInfo7.setName("奋进7号");
//		appInfo7.setPositionIndex(7);
//		appInfo7.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo8 = new AppInfo();
//		appInfo8.setIcon(context.getResources().getDrawable(R.drawable.icon1_mt));
//		appInfo8.setName("奋进8号");
//		appInfo8.setPositionIndex(8);
//		appInfo8.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo9 = new AppInfo();
//		appInfo9.setIcon(context.getResources().getDrawable(R.drawable.icon03));
//		appInfo9.setName("奋进9号");
//		appInfo9.setPositionIndex(9);
//		appInfo9.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo10 = new AppInfo();
//		appInfo10.setIcon(context.getResources().getDrawable(R.drawable.icon3_nm));
//		appInfo10.setName("奋进10号");
//		appInfo10.setPositionIndex(10);
//		appInfo10.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo11 = new AppInfo();
//		appInfo11.setIcon(context.getResources().getDrawable(R.drawable.verify_error));
//		appInfo11.setName("奋进11号");
//		appInfo11.setPositionIndex(11);
//		appInfo11.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo12 = new AppInfo();
//		appInfo12.setIcon(context.getResources().getDrawable(R.drawable.verify_ok));
//		appInfo12.setName("奋进12号");
//		appInfo12.setPositionIndex(12);
//		appInfo12.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo13 = new AppInfo();
//		appInfo13.setIcon(context.getResources().getDrawable(R.drawable.vpay));
//		appInfo13.setName("奋进13号");
//		appInfo13.setPositionIndex(13);
//		appInfo13.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo14 = new AppInfo();
//		appInfo14.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo14.setName("奋进14号");
//		appInfo14.setPositionIndex(14);
//		appInfo14.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo15 = new AppInfo();
//		appInfo15.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo15.setName("奋进15号");
//		appInfo15.setPositionIndex(15);
//		appInfo15.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo16 = new AppInfo();
//		appInfo16.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo16.setName("奋进16号");
//		appInfo16.setPositionIndex(16);
//		appInfo16.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo17 = new AppInfo();
//		appInfo17.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo17.setName("奋进17号");
//		appInfo17.setPositionIndex(17);
//		appInfo17.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo18 = new AppInfo();
//		appInfo18.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo18.setName("奋进18号");
//		appInfo18.setPositionIndex(18);
//		appInfo18.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo19 = new AppInfo();
//		appInfo19.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo19.setName("奋进19号");
//		appInfo19.setPositionIndex(19);
//		appInfo19.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo20 = new AppInfo();
//		appInfo20.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo20.setName("奋进20号");
//		appInfo20.setPositionIndex(20);
//		appInfo20.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo21 = new AppInfo();
//		appInfo21.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo21.setName("奋进21号");
//		appInfo21.setPositionIndex(21);
//		appInfo21.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo22 = new AppInfo();
//		appInfo22.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo22.setName("奋进22号");
//		appInfo22.setPositionIndex(22);
//		appInfo22.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo23 = new AppInfo();
//		appInfo23.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo23.setName("奋进23号");
//		appInfo23.setPositionIndex(23);
//		appInfo23.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo24 = new AppInfo();
//		appInfo24.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo24.setName("奋进24号");
//		appInfo24.setPositionIndex(24);
//		appInfo24.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo25 = new AppInfo();
//		appInfo25.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo25.setName("奋进25号");
//		appInfo25.setPositionIndex(25);
//		appInfo25.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo26 = new AppInfo();
//		appInfo26.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo26.setName("奋进26号");
//		appInfo26.setPositionIndex(26);
//		appInfo26.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo27 = new AppInfo();
//		appInfo27.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo27.setName("奋进27号");
//		appInfo27.setPositionIndex(27);
//		appInfo27.setLauncherPageIndex(UtilsScreen.SIDE_SCREEN);
//
//		AppInfo appInfo_zero = new AppInfo();
//		appInfo_zero.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_zero.setName("首屏");
//		appInfo_zero.setPositionIndex(0);
//		appInfo_zero.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//		AppInfo appInfo_one = new AppInfo();
//		appInfo_one.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_one.setName("首屏1号");
//		appInfo_one.setPositionIndex(1);
//		appInfo_one.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//		AppInfo appInfo_two = new AppInfo();
//		appInfo_two.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_two.setName("首屏2号");
//		appInfo_two.setPositionIndex(2);
//		appInfo_two.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//		AppInfo appInfo_three = new AppInfo();
//		appInfo_three.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_three.setName("首屏3号");
//		appInfo_three.setPositionIndex(3);
//		appInfo_three.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//		AppInfo appInfo_four = new AppInfo();
//		appInfo_four.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_four.setName("首屏4号");
//		appInfo_four.setPositionIndex(4);
//		appInfo_four.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//		AppInfo appInfo_five = new AppInfo();
//		appInfo_five.setIcon(context.getResources().getDrawable(R.drawable.weixin));
//		appInfo_five.setName("首屏5号");
//		appInfo_five.setPositionIndex(5);
//		appInfo_five.setLauncherPageIndex(UtilsScreen.MAIN_SCREEN);
//
//
//		mTestDataList.add(appInfo_zero);
//		mTestDataList.add(appInfo_one);
//		mTestDataList.add(appInfo_two);
//		mTestDataList.add(appInfo_three);
//		mTestDataList.add(appInfo_four);
//		mTestDataList.add(appInfo_five);
//
//
//		mTestDataList.add(appInfo);
//		mTestDataList.add(appInfo1);
//		mTestDataList.add(appInfo2);
//		mTestDataList.add(appInfo3);
//		mTestDataList.add(appInfo4);
//		mTestDataList.add(appInfo5);
//		mTestDataList.add(appInfo6);
//		mTestDataList.add(appInfo7);
//		mTestDataList.add(appInfo8);
//		mTestDataList.add(appInfo9);
//		mTestDataList.add(appInfo10);
//		mTestDataList.add(appInfo11);
//		mTestDataList.add(appInfo12);
//		mTestDataList.add(appInfo13);
//		mTestDataList.add(appInfo14);
//		mTestDataList.add(appInfo15);
//		mTestDataList.add(appInfo16);
//		mTestDataList.add(appInfo17);
//		mTestDataList.add(appInfo18);
//		mTestDataList.add(appInfo19);
//		mTestDataList.add(appInfo20);
//		mTestDataList.add(appInfo21);
//		mTestDataList.add(appInfo22);
//		mTestDataList.add(appInfo23);
//		mTestDataList.add(appInfo24);
//		mTestDataList.add(appInfo25);
//		mTestDataList.add(appInfo26);
//		mTestDataList.add(appInfo27);
	}

	public static TestData getInstance(Context context)
	{
		if (instance == null)
			instance = new TestData(context);

		return instance;
	}

	public List<AppInfo> getTestDataList()
	{
		return mTestDataList;
	}

	/**
	 * 返回指定屏幕的应用个数
	 *
	 * @param screenIndex
	 * @return
	 */
	public int getScreenCellSize(int screenIndex)
	{
		if (mTestDataList != null)
		{
			int size = 0;
			for (AppInfo appInfo : mTestDataList)
			{
				if (appInfo.getScreenIndex() == screenIndex)
					size++;
			}
			return size;
		}
		else
			return 0;
	}

	/**
	 * 返回主屏的应用个数
	 *
	 * @return
	 */
	public int getMainScreenCellSize()
	{
		if (mTestDataList != null)
		{
			int size = 0;
			for (AppInfo appInfo : mTestDataList)
			{
				if (appInfo.getScreenIndex() == VPayLauncherLayout.MAIN_SCREEN)
					size++;
			}
			return size;
		}
		else
			return 0;
	}

	/**
	 * 返回副屏的应用个数
	 *
	 * @return
	 */
	public int getSideScreenCellSize()
	{
		if (mTestDataList != null)
		{
			int size = 0;
			for (AppInfo appInfo : mTestDataList)
			{
				if (appInfo.getScreenIndex() == VPayLauncherLayout.SIDE_SCREEN)
					size++;
			}
			return size;
		}
		else
			return 0;
	}

	/**
	 * 打印数据
	 */
	public void logData()
	{
		if (mTestDataList != null)
		{
			Log.i("test", "数据 --------------- \n");
			for (AppInfo appInfo : mTestDataList)
			{

				Log.i("test", "数据  " + appInfo.getName() + "  " + String.valueOf(appInfo.getPositionIndex()) +
						"" +
						"  屏幕：" +
						String.valueOf(appInfo.getScreenIndex()));
			}
			Log.i("test", "数据 --------------- \n");
		}
	}

	public void moveCellInDB(int screenIndexFrom, int cellIndexFrom, int screenIndexTo, int cellIndexTo)
	{
		// 检验数据是否合法
		if (screenIndexFrom < 0 || cellIndexFrom < 0 || screenIndexTo < 0 || cellIndexTo < 0)
			return;
		if (mTestDataList == null)
			return;


		// 同屏移动
		if (screenIndexFrom == screenIndexTo)
		{
			if (cellIndexFrom < cellIndexTo)
			{
				// 检验数据是否合法
				if (cellIndexTo >= getScreenCellSize(screenIndexFrom))
					return;

				for (AppInfo appInfo : mTestDataList)
				{
					if (appInfo.getScreenIndex() == screenIndexFrom)
					{
						if (appInfo.getPositionIndex() > cellIndexFrom && appInfo.getPositionIndex() <= cellIndexTo)
							appInfo.setPositionIndex(appInfo.getPositionIndex() - 1);
						else if (appInfo.getPositionIndex() == cellIndexFrom)
							appInfo.setPositionIndex(cellIndexTo);
					}
				}

			}
			else if (cellIndexFrom > cellIndexTo)
			{
				// 检验数据是否合法
				if (cellIndexFrom >= getScreenCellSize(screenIndexFrom))
					return;

				for (AppInfo appInfo : mTestDataList)
				{
					if (appInfo.getScreenIndex() == screenIndexFrom)
					{
						if (appInfo.getPositionIndex() >= cellIndexTo && appInfo.getPositionIndex() < cellIndexFrom)
							appInfo.setPositionIndex(appInfo.getPositionIndex() + 1);
						else if (appInfo.getPositionIndex() == cellIndexFrom)
							appInfo.setPositionIndex(cellIndexTo);
					}
				}
			}
		}
		// 跨屏移动
		else if (screenIndexFrom != screenIndexTo)
		{
			// 检验数据是否合法
			if (cellIndexFrom >= getScreenCellSize(screenIndexFrom) || cellIndexTo > getScreenCellSize(screenIndexTo))
				return;

			for (AppInfo appInfo : mTestDataList)
			{
				if (appInfo.getScreenIndex() == screenIndexFrom)
				{
					if (appInfo.getPositionIndex() == cellIndexFrom)
					{
						appInfo.setPositionIndex(cellIndexTo);
						appInfo.setScreenIndex(screenIndexTo);
					}
					else if (appInfo.getPositionIndex() > cellIndexFrom)
					{
						appInfo.setPositionIndex(appInfo.getPositionIndex() - 1);
					}
				}
				else if (appInfo.getScreenIndex() == screenIndexTo)
				{
					if (appInfo.getPositionIndex() >= cellIndexTo)
					{
						appInfo.setPositionIndex(appInfo.getPositionIndex() + 1);
					}
				}
			}
		}
	}
}
