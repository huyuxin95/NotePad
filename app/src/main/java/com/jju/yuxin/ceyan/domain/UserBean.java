package com.jju.yuxin.ceyan.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.domain
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 8:30.
 * Version   1.0;
 * Describe : 用户Bean,实现了 Parcelable接口
 * History:
 * ==============================================================================
 */

public class UserBean implements Parcelable {
    public int _id;   //用户id
    public String name; //用户名
    public String pwd;   //密码
    public int sex;    //性别

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.name);
        dest.writeString(this.pwd);
        dest.writeInt(this.sex);
    }

    public UserBean() {
    }

    protected UserBean(Parcel in) {
        this._id = in.readInt();
        this.name = in.readString();
        this.pwd = in.readString();
        this.sex = in.readInt();
    }

    public static final Parcelable.Creator<UserBean> CREATOR = new Parcelable.Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel source) {
            return new UserBean(source);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };
}
