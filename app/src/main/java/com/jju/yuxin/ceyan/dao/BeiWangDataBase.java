package com.jju.yuxin.ceyan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jju.yuxin.ceyan.db.BeiWangOpenHelper;
import com.jju.yuxin.ceyan.domain.BeiWang;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.disizhou
 * Created by yuxin.
 * Created time 2016/9/26 0026 上午 10:18.
 * Version   1.0;
 * Describe : 备忘录数据库的操作
 * History:
 * ==============================================================================
 */

public class BeiWangDataBase {

    private final BeiWangOpenHelper beiWangOpenHelper;
    private static  SQLiteDatabase db;
    /**
     * 在UserDataBase的构造方法中获取OpenHelper的对象,从而获取到ReadableDatabase
     * @param context
     */
    public BeiWangDataBase(Context context) {
        beiWangOpenHelper = BeiWangOpenHelper.getInstance(context);
        db = beiWangOpenHelper.getReadableDatabase();
    }

    /**
     * 插入
     * @param title
     * @param content
     * @param date
     * @param user_id
     */
    public  void Insert(String title, String content, String date,int user_id) {
        db.execSQL("insert into beiwang(title,content,date,user_id)values(?,?,?,?)", new String[]{title, content, date, String.valueOf(user_id)});
    }

    /**
     * 根据用户id查询
     * @param user_id
     * @return
     */
    public  List<BeiWang> Select(int user_id) {
        Cursor cursor = db.rawQuery("select * from beiwang where user_id=?", new String[]{String.valueOf(user_id)});
        List<BeiWang> beiwangs = new ArrayList<>();
        while (cursor.moveToNext()) {

            int anInt = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String date = cursor.getString(3);
            BeiWang beiwang = new BeiWang(anInt, title, content, date,user_id);
            beiwangs.add(beiwang);
        }
        cursor.close();
        return beiwangs;
    }

    /**
     * 删除操作
     * @param id
     */
    public void Delete(int id){
        db.execSQL("delete from beiwang where _id=?",new String[]{String.valueOf(id)});
    }

    /**
     * 更新操作
     * @param id
     * @param title
     * @param content
     * @param date
     */
    public  void Update(int id,String title, String content, String date) {
        db.execSQL("update beiwang set title=? ,content=?,date=? where _id=?", new String[]{title,content,date, String.valueOf(id)});
    }

}
