package com.example.vpaylauncher.launcher.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cellInfoDaoConfig;

    private final CellInfoDao cellInfoDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cellInfoDaoConfig = daoConfigMap.get(CellInfoDao.class).clone();
        cellInfoDaoConfig.initIdentityScope(type);

        cellInfoDao = new CellInfoDao(cellInfoDaoConfig, this);

        registerDao(CellInfo.class, cellInfoDao);
    }
    
    public void clear() {
        cellInfoDaoConfig.getIdentityScope().clear();
    }

    public CellInfoDao getCellInfoDao() {
        return cellInfoDao;
    }

}