package com.example.lion_personal.lionopensource.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lion_personal.lionopensource.R;
import com.example.lion_personal.lionopensource.database.domain.DaoMaster;
import com.example.lion_personal.lionopensource.database.domain.DaoSession;
import com.example.lion_personal.lionopensource.database.domain.Student;
import com.example.lion_personal.lionopensource.database.domain.StudentDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        // sqlite
        String databaseName = "databaseName";
        int databaseVersion = 1;
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, databaseName, null, databaseVersion) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        // greendao

        // 创建数据库
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), databaseName);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession(IdentityScopeType.Session);

        StudentDao studentDao = daoSession.getStudentDao();
        Student student = new Student();
        student.setNum("00388338");
        student.setName("lion");
        student.setScore("88");
        student.setSex("male");

        // 增
        studentDao.insert(student);
        studentDao.insertOrReplace(student);
        // 删
        studentDao.delete(student);
        // 查
        List studentList = studentDao.queryBuilder()
                .where(StudentDao.Properties.Name.eq("lion"))
                .orderDesc(StudentDao.Properties.Score)
                // 查询数量
//                .buildCount()

                // 查询结果
                .list();

        // 改
        student.setScore("99");
        studentDao.update(student);
    }
}

class CustomDevOpenHelper extends DaoMaster.DevOpenHelper {

    public CustomDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}


