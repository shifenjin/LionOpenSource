package com.example.vpaylauncher.launcher.db;

import android.content.Context;
import android.util.Log;

import com.yunnex.vpay.util.VPayLogUtils;

import java.util.List;

/**
 * Created by lion on 16/9/6.
 */
public class CellInfoDBHelper
{
	private static CellInfoDBHelper instance;

	private CellInfoDao mCellInfoDao;
	private DaoSession  mDaoSession;

	public static CellInfoDBHelper getInstance(Context context)
	{
		if (instance == null)
			instance = new CellInfoDBHelper(context);

		return instance;
	}

	public CellInfoDBHelper(Context context)
	{
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "celllauncher.db",
				null);
		DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
		mDaoSession = daoMaster.newSession();
		mCellInfoDao = mDaoSession.getCellInfoDao();
	}

	// 增
	public long add(CellInfo cellInfo)
	{
		if (cellInfo != null && cellInfo.getActivityName() != null)
		{
			String activityName = cellInfo.getActivityName();
			if (!isAppExist(activityName))
			{
				return mDaoSession.insert(cellInfo);
			}
			else
			{
				Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 增加 ：应用已存在，插入失败! - " + activityName);
				return -1;
			}
		}
		else
		{
			Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 增加 ：数据异常，插入失败!");
			return -1;
		}
	}

	// 删

	public void deleteAll()
	{
		mCellInfoDao.deleteAll();
	}

	public void delete(CellInfo cellInfo)
	{
		mDaoSession.delete(cellInfo);
	}

	public void delete(String activityName)
	{
		CellInfo cellInfo = new CellInfo();
		cellInfo.setActivityName(activityName);
		mCellInfoDao.delete(cellInfo);
//		mCellInfoDao.deleteByKey(activityName);
	}

	// 改

	public void update(CellInfo cellInfo)
	{
		mDaoSession.update(cellInfo);
	}

	/**
	 * 更新 位置
	 *
	 * @param activityName
	 * @param screenIndex
	 * @param positionIndex
	 */
	public void update(String activityName, Integer screenIndex, Integer positionIndex)
	{
		CellInfo CellInfo = loadCellInfoByActivityName(activityName);
		if (CellInfo != null)
		{
			if (screenIndex != null)
				CellInfo.setScreenIndex(screenIndex);
			if (positionIndex != null)
				CellInfo.setPositionIndex(positionIndex);

			update(CellInfo);
		}
		else
		{
			Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 修改 ：查询不到数据，修改失败! - " + activityName);
		}
	}

	/**
	 * 更新 消息数
	 *
	 * @param activityName
	 * @param msgNum
	 */
	public void update(String activityName, int msgNum)
	{
		CellInfo cellInfo = loadCellInfoByActivityName(activityName);
		if (cellInfo != null)
		{
			cellInfo.setMsgNum(msgNum);
			update(cellInfo);
		}
		else
		{
			Log.w(VPayLogUtils.TAG_LAUNCHER, "桌面 - 修改 ：查询不到数据，修改失败! - " + activityName);
		}
	}

	// 查

	public List<CellInfo> loadAll()
	{
		return mCellInfoDao.queryBuilder().orderAsc(CellInfoDao.Properties.ScreenIndex, CellInfoDao.Properties.PositionIndex).list();
	}

	public CellInfo loadCellInfoByPostion(Integer screenIndex, Integer positionIndex)
	{
		return mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ScreenIndex.eq(screenIndex), CellInfoDao.Properties.PositionIndex.eq(positionIndex)).unique();
	}

	public CellInfo loadCellInfoByActivityName(String activityName)
	{
		return mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ActivityName.eq(activityName))
				.unique();
	}

	public List<CellInfo> loadCellInfoByScreenIndex(int screenIndex)
	{
		return mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ScreenIndex.eq(screenIndex)).list();
	}

	public long loadCellInfoSizeByScreenIndex(int screenIndex)
	{
		return mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ScreenIndex.eq(screenIndex)).count();
	}

	// 应用是否存在
	public boolean isAppExist(String activityName)
	{
		return mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ActivityName.eq(activityName)).count() != 0;
	}

	// 获取 指定屏幕索引 最大的位置索引
	public Integer getPositionIndexMax(int screenIndex)
	{
		List<CellInfo> cellInfoList = mCellInfoDao.queryBuilder().where(CellInfoDao.Properties.ScreenIndex.eq(screenIndex)).orderDesc(CellInfoDao.Properties.PositionIndex).list();
		if (cellInfoList != null && !cellInfoList.isEmpty())
		{
			CellInfo cellInfo = cellInfoList.get(0);
			return cellInfo.getPositionIndex();
		}

		return -1;
	}
}
