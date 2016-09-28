package com.jju.yuxin.ceyan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jju.yuxin.ceyan.db.UserDBOpenHelper;
import com.jju.yuxin.ceyan.domain.UserBean;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.i;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.dao
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 9:00.
 * Version   1.0;
 * Describe : 对用户数据库的操作类
 * History:
 * ==============================================================================
 */

public class UserDataBase {

    private static final String TAG ="UserDataBase";
    private static SQLiteDatabase db;
    private final UserDBOpenHelper helper;

    /**
     * 在UserDataBase的构造方法中获取OpenHelper的对象,从而获取到WritableDatabase
     * @param context
     */
    public UserDataBase(Context context) {
        helper = UserDBOpenHelper.getInstance(context);
        db=helper.getWritableDatabase();
        i(TAG, "UserDataBase" + db);
    }

    /**
     * 注册操作
     * @param name
     * @param pwd
     * @param sex
     */
    public  void Insert(String name, String pwd,int sex) {

        db.execSQL("insert into user(name,pwd,sex)values(?,?,?)", new String[]{name, pwd, String.valueOf(sex)});
    }

    /**
     * 登录操作
     * @param name
     * @param pwd
     * @return
     */
    public  UserBean Select(String name, String pwd) {
        UserBean loginuser = null;
        Cursor cursor = db.rawQuery("select * from user where name=? and pwd=?", new String[]{name,pwd});
        while (cursor.moveToNext()) {
            i(TAG, "Select" + cursor.toString());
            loginuser=new UserBean();
            loginuser._id= cursor.getInt(0);
            loginuser.name = cursor.getString(1);
            loginuser.pwd = cursor.getString(2);
            loginuser.sex = cursor.getInt(3);
        }
        cursor.close();
        return loginuser;
    }
    /**
     * 查询用户
     * @param userid
     * @return
     */
    public  UserBean Select(int userid) {
        UserBean loginuser = null;
        Cursor cursor = db.rawQuery("select * from user where _id=?", new String[]{String.valueOf(userid)});
        while (cursor.moveToNext()) {
            loginuser=new UserBean();
            loginuser._id= cursor.getInt(0);
            loginuser.name = cursor.getString(1);
            loginuser.pwd = cursor.getString(2);
            loginuser.sex = cursor.getInt(3);
        }
        cursor.close();
        return loginuser;
    }

    /**
     * 删除操作
     * @param name
     */
    public  void Delete(String name) {
        db.execSQL("delete from user where name=?", new String[]{name});
    }

    /**
     * 更新密码操作
     * @param name
     * @param newPwd
     */
    public  void UpPwd(String name,String newPwd) {
        db.execSQL("update user set pwd=? where name=?", new String[]{newPwd,name});
    }

    /**
     * 更新用户信息
     * @param id
     * @param name
     * @param newPwd
     * @param sex
     */
    public  void UpPwd(int id,String name,String newPwd,int sex) {
        db.execSQL("update user set name=?,pwd=?,sex=?where _id=?", new String[]{name,newPwd, String.valueOf(sex), String.valueOf(id)});
    }
}
