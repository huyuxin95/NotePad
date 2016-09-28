package com.jju.yuxin.ceyan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.db
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 8:57.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class UserDBOpenHelper extends SQLiteOpenHelper {
    //创建表的SQL语句
    private static final String CREATE_USER_TAB = "create table user(_id Integer primary " +
            " key autoincrement,name text,pwd text,sex Integer)";
    private static final String TAG ="UserDBOpenHelper";
    private static UserDBOpenHelper mInstance=null;
    //数据库名
    private static final String USER_DB_NAME = "registeduser.db";
    //单例获取dbopenhelp对象
    public synchronized static  UserDBOpenHelper getInstance(Context context){
        if (mInstance==null){
            mInstance =new UserDBOpenHelper(context,USER_DB_NAME,null,1);
        }
        return mInstance;
    }


    public UserDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TAB);
        Log.i(TAG, "onCreate" + "CREATE_USER_TAB");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
