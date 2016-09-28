package com.jju.yuxin.ceyan.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.disizhou
 * Created by yuxin.
 * Created time 2016/9/26 0026 上午 10:26.
 * Version   1.0;
 * Describe :  备忘录Bean,实现了 Parcelable接口
 * History:
 * ==============================================================================
 */

public class BeiWang implements Parcelable {

    private int id;   //记录id
    private String title;   //标题
    private String content;  //内容
    private String date;     //日期
    private int user_id;    //用户表的用户id

    public BeiWang() {
    }

    public BeiWang(int id, String title, String content, String date, int user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "BeiWang{" +
                "content='" + content + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", user_id=" + user_id +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeInt(this.user_id);
    }

    protected BeiWang(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.user_id = in.readInt();
    }

    public static final Parcelable.Creator<BeiWang> CREATOR = new Parcelable.Creator<BeiWang>() {
        @Override
        public BeiWang createFromParcel(Parcel source) {
            return new BeiWang(source);
        }

        @Override
        public BeiWang[] newArray(int size) {
            return new BeiWang[size];
        }
    };
}
