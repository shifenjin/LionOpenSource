package com.example.vpaylauncher.launcher;

import android.content.Context;
import android.util.Log;

import com.yunnex.vpay.launcher.db.CellInfo;
import com.yunnex.vpay.launcher.db.CellInfoDBHelper;
import com.yunnex.vpay.launcher.utils.LauncherUtils;
import com.yunnex.vpay.util.VPayLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 16/9/6.
 *
 * 应用入口 相关业务类
 */
public class CellInfoController
{
	private static CellInfoController instance;

	private Context mContext;

	private CellInfoController(Context context)
	{
		mContext = context;
	}

	public static CellInfoController getInstance(Context context)
	{
		if (instance == null)
			instance = new CellInfoController(context);

		return instance;
	}

	/**
	 * 更新桌面应用数据库（增加或删除应用入口时）
	 *
	 * @param launcherPageToAdd 承载 新的应用入口 的 桌面页面 索引
	 */
	public void updateCellInfoDB(Integer launcherPageToeAdd)
	{
		List<CellInfo> cellInfoListFromSystem = LauncherUtils
				.getLauncherCellList(mContext);

		List<CellInfo> cellInfoListFromDB = CellInfoDBHelper.getInstance(mContext).loadAll();
		List<String> activityNameListFromDB = new ArrayList<>();
		if (cellInfoListFromDB != null)
		{
			for (CellInfo cellInfoFromDB : cellInfoListFromDB)
			{
				activityNameListFromDB.add(cellInfoFromDB.getActivityName());
			}
		}

		List<String> activityNameListFromAppStore = new ArrayList<>();
		if (cellInfoListFromSystem != null)
		{
			for (CellInfo CellInfoFromAppStore : cellInfoListFromSystem)
			{
				activityNameListFromAppStore.add(CellInfoFromAppStore.getActivityName());
			}
		}

		// 删除应用
		for (String activityNameFromDB : activityNameListFromDB)
		{
			if (!activityNameListFromAppStore.contains(activityNameFromDB))
			{
				delete(activityNameFromDB);
			}
		}

		// 增加应用
		for (String activityNameFromAppStore : activityNameListFromAppStore)
		{
			if (!activityNameListFromDB.contains(activityNameFromAppStore))
			{
				CellInfo cellInfoAdd = null;
				for (CellInfo cellInfo : cellInfoListFromSystem)
				{
					if (cellInfo.getActivityName().equals(activityNameFromAppStore))
					{
						cellInfoAdd = cellInfo;
					}
				}
				add(cellInfoAdd.getActivityName(), cellInfoAdd.getPkgName(), launcherPageToAdd);
			}
		}

	}

	/**
	 * 更新 应用消息数
	 *
	 * @param activityName
	 * @param msgNum
	 */
	public void updateMsgNum(String activityName, int msgNum)
	{
		CellInfoDBHelper.getInstance(mContext).update(activityName, msgNum);
	}

	// 移动应用
	public void rmove(Integer screenIndexFrom, Integer positionIndexFrom, Integer screenIndexTo, Integer positionIndexTo)
	{
		// 同屏移动
		if (screenIndexFrom != null && positionIndexFrom != null &&
				screenIndexTo != null && positionIndexTo != null)
		{
			CellInfo cellInfoMove = CellInfoDBHelper.getInstance(mContext).loadCellInfoByPostion(screenIndexFrom, positionIndexFrom);
			if (cellInfoMove != null)
			{
				String activityName = cellInfoMove.getActivityName();
				if (screenIndexFrom.equals(screenIndexTo))
				{
					List<CellInfo> CellInfoList = CellInfoDBHelper.getInstance(mContext).loadCellInfoByScreenIndex(screenIndexFrom);
					// 起始位置比终点小
					if (positionIndexFrom < positionIndexTo)
					{
						for (CellInfo cellInfo : CellInfoList)
						{
							int positionIndex = cellInfo.getPositionIndex();
							if (positionIndex > positionIndexFrom && positionIndex <= positionIndexTo)
							{
								cellInfo.setPositionIndex(--positionIndex);
								CellInfoDBHelper.getInstance(mContext).update(cellInfo);
							}
						}
						CellInfoDBHelper.getInstance(mContext).update(activityName, null, positionIndexTo);
					}
					// 起始位置比终点大
					else
					{
						for (CellInfo cellInfo : CellInfoList)
						{
							int positionIndex = cellInfo.getPositionIndex();
							if (positionIndex < positionIndexFrom && positionIndex >= positionIndexTo)
							{
								cellInfo.setPositionIndex(++positionIndex);
								CellInfoDBHelper.getInstance(mContext).update(cellInfo);
							}
						}
						CellInfoDBHelper.getInstance(mContext).update(activityName, null, positionIndexTo);
					}
				}
				// 跨屏移动
				else
				{
					List<CellInfo> cellInfoListScreenFrom = CellInfoDBHelper.getInstance(mContext).loadCellInfoByScreenIndex(screenIndexFrom);
					if (cellInfoListScreenFrom != null)
					{
						for (CellInfo cellInfo : cellInfoListScreenFrom)
						{
							int positionIndex = cellInfo.getPositionIndex();
							if (positionIndexFrom < positionIndex)
							{
								cellInfo.setPositionIndex(--positionIndex);
								CellInfoDBHelper.getInstance(mContext).update(cellInfo);
							}
						}
					}

					List<CellInfo> cellInfoListScreenTo = CellInfoDBHelper.getInstance(mContext).loadCellInfoByScreenIndex(screenIndexTo);
					if (cellInfoListScreenTo != null)
					{
						for (CellInfo cellInfo : cellInfoListScreenTo)
						{
							int positionIndex = cellInfo.getPositionIndex();
							if (positionIndexTo <= positionIndex)
							{
								cellInfo.setPositionIndex(++positionIndex);
								CellInfoDBHelper.getInstance(mContext).update(cellInfo);
							}
						}
					}
					CellInfoDBHelper.getInstance(mContext).update(activityName, screenIndexTo, positionIndexTo);
				}
			}
			else
			{
				Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 移动应用 ：查询不到数据，移动失败!");
			}
		}
	}

//	/**
//	 * 获取桌面应用
//	 *
//	 * @return
//	 */
//	public List<CellInfo> loadAllCellInfo()
//	{
//		List<CellInfo> cellInfoListFromSystem = LauncherUtils.getLauncherCellList(mContext);
//
//		List<CellInfo> cellInfoListFromDB = CellInfoDBHelper.getInstance(mContext).loadAll();
//
//
//		for (CellInfo cellInfoFromDB : cellInfoListFromDB)
//		{
//			for (CellInfo cellInfoFromSystem : cellInfoListFromSystem)
//			{
//				String activityName = cellInfoFromSystem.getActivityName();
//				if (activityName != null && activityName.equals(cellInfoFromDB.getActivityName()))
//				{
//					cellInfoFromDB.setAppIconDrawable(cellInfoFromSystem.getAppIconDrawable());
//					cellInfoFromDB.setAppName(cellInfoFromSystem.getAppName());
//
//					break;
//				}
//			}
//		}
//
//		return cellInfoListFromDB;
//	}

//	/**
//	 * 获取桌面应用数据（ 指定屏幕索引 ）
//	 *
//	 * @param screenIndex
//	 * @return
//	 */
//	public List<CellInfo> loadCellInfoListByScreenIndex(Integer screenIndex)
//	{
//		List<CellInfo> cellInfoList = loadAllCellInfo();
//		List<CellInfo> cellInfoListByScreenIndex = new ArrayList<>();
//		for (CellInfo cellInfo : cellInfoList)
//		{
//			if (cellInfo.getScreenIndex() == screenIndex)
//				cellInfoListByScreenIndex.add(cellInfo);
//		}
//
//		return cellInfoListByScreenIndex;
//	}

	/**
	 * 获取桌面应用数据（ 指定屏幕索引、位置）
	 *
	 * @param screenIndex   指定屏幕
	 * @param positionIndex 指定位置
	 * @return
	 */
	public CellInfo loadCellInfoByPostion(Integer screenIndex, Integer positionIndex)
	{
		return CellInfoDBHelper.getInstance(mContext).loadCellInfoByPostion(screenIndex, positionIndex);
	}

	// 增加 应用入口
	private void add(String activityName, String pkgName, int screenIndex)
	{
		int positionIndex = CellInfoDBHelper.getInstance(mContext).getPositionIndexMax(screenIndex) + 1;
		CellInfo cellInfo = new CellInfo(null, activityName, pkgName, screenIndex, positionIndex, 0);
		CellInfoDBHelper.getInstance(mContext).add(cellInfo);
		Log.i(VPayLogUtils.TAG_LAUNCHER, "桌面 - 更新数据库 - 增加 ：" + activityName + "  屏幕和位置 ：" +
				screenIndex + " " + positionIndex);
	}


	// 删除 应用入口
	private void delete(String activityName)
	{
		CellInfo cellInfoDelete = CellInfoDBHelper.getInstance(mContext).loadCellInfoByActivityName(activityName);
		if (cellInfoDelete != null)
		{
			int screenIndex = cellInfoDelete.getScreenIndex();
			List<CellInfo> cellInfoList = CellInfoDBHelper.getInstance(mContext).loadCellInfoByScreenIndex(screenIndex);
			if (cellInfoList != null)
			{
				int positionIndex = cellInfoDelete.getPositionIndex();
				for (CellInfo cellInfo : cellInfoList)
				{
					if (positionIndex < cellInfo.getPositionIndex())
					{
						int positionIndexNew = cellInfo.getPositionIndex() - 1;
						CellInfoDBHelper.getInstance(mContext).update(cellInfo.getActivityName(), null, positionIndexNew);
					}
				}
			}

			CellInfoDBHelper.getInstance(mContext).delete(cellInfoDelete);
			Log.i(VPayLogUtils.TAG_LAUNCHER, "桌面 - 更新数据库 - 删除 ：+ activityName ：" + cellInfoDelete.getActivityName());
		}
		else
		{
			Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 更新数据库 - 删除 ：查询不到数据，删除失败! - " + activityName);
		}
	}

}
