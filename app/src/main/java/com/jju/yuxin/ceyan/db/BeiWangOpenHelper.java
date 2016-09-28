package com.jju.yuxin.ceyan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.disizhou
 * Created by yuxin.
 * Created time 2016/9/26 0026 上午 10:10.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class BeiWangOpenHelper extends SQLiteOpenHelper {
    private static BeiWangOpenHelper mInstance;
//创建表的SQL语句
    private final  String CREATE_DB
            = "create table beiwang(_id Integer primary  key autoincrement,title text,content text,date text,user_id Integer)";
    //创建表的数据库名语句
    private static final String CREATE_DB_NAME="class.db";
    //单例获取dbopenhelp对象
    public synchronized static BeiWangOpenHelper getInstance(Context context){
        if (mInstance==null){
        mInstance=new BeiWangOpenHelper(context,CREATE_DB_NAME,null,1);
        }
        return mInstance;
    }

    public BeiWangOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
