package com.example.vpaylauncher.launcher.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table cell_launcher.
*/
public class CellInfoDao extends AbstractDao<CellInfo, Long> {

    public static final String TABLENAME = "cell_launcher";

    /**
     * Properties of entity CellInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "ID");
        public final static Property ActivityName = new Property(1, String.class, "activityName", false, "ACTIVITY_NAME");
        public final static Property PkgName = new Property(2, String.class, "pkgName", false, "PKG_NAME");
        public final static Property ScreenIndex = new Property(3, Integer.class, "screenIndex", false, "SCREEN_INDEX");
        public final static Property PositionIndex = new Property(4, Integer.class, "positionIndex", false, "POSITION_INDEX");
        public final static Property MsgNum = new Property(5, Integer.class, "msgNum", false, "MSG_NUM");
    };


    public CellInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CellInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'cell_launcher' (" + //
                "'ID' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'ACTIVITY_NAME' TEXT," + // 1: activityName
                "'PKG_NAME' TEXT," + // 2: pkgName
                "'SCREEN_INDEX' INTEGER," + // 3: screenIndex
                "'POSITION_INDEX' INTEGER," + // 4: positionIndex
                "'MSG_NUM' INTEGER);"); // 5: msgNum
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_cell_launcher_ID ON cell_launcher" +
                " (ID);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'cell_launcher'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, CellInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String activityName = entity.getActivityName();
        if (activityName != null) {
            stmt.bindString(2, activityName);
        }
 
        String pkgName = entity.getPkgName();
        if (pkgName != null) {
            stmt.bindString(3, pkgName);
        }
 
        Integer screenIndex = entity.getScreenIndex();
        if (screenIndex != null) {
            stmt.bindLong(4, screenIndex);
        }
 
        Integer positionIndex = entity.getPositionIndex();
        if (positionIndex != null) {
            stmt.bindLong(5, positionIndex);
        }
 
        Integer msgNum = entity.getMsgNum();
        if (msgNum != null) {
            stmt.bindLong(6, msgNum);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public CellInfo readEntity(Cursor cursor, int offset) {
        CellInfo entity = new CellInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // activityName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pkgName
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // screenIndex
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // positionIndex
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5) // msgNum
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, CellInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setActivityName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPkgName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setScreenIndex(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setPositionIndex(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setMsgNum(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(CellInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(CellInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}