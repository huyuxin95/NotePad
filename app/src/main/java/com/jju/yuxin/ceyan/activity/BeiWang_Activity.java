package com.jju.yuxin.ceyan.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.adapters.BeiWangListAdapter;
import com.jju.yuxin.ceyan.dao.BeiWangDataBase;
import com.jju.yuxin.ceyan.db.BeiWangOpenHelper;
import com.jju.yuxin.ceyan.domain.BeiWang;
import com.jju.yuxin.ceyan.domain.UserBean;

import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.activity
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 9:47.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */
public class BeiWang_Activity extends Activity {

    private static final String TAG ="MainActivity";
    private EditText ettitle;
    private EditText etcontext;
    private EditText ettime;
    private Button btinster;
    private Button btseach;
    private ListView lvshowdata;
    private LinearLayout activitymain;
    private BeiWangOpenHelper mySqliteOpenHelper;
    private SQLiteDatabase db;
    private PopupWindow popupWindow;
    private BeiWang clickedbeiwang;
    private BeiWangListAdapter myAdapter;
    private List<BeiWang> select;
    private BeiWangDataBase beiWangDataBase;
    private SharedPreferences sp;
    private int user_id;
    private ImageView iv_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_beiwang);
        //将登陆用户的id保存下来
        sp = getSharedPreferences("userid", MODE_PRIVATE);
        user_id = sp.getInt("id", -1);
       // loginuser = getIntent().getParcelableExtra("loginuser");
        beiWangDataBase=new BeiWangDataBase(getApplicationContext());
        initialize();
        updateListView();

    }


    /**
     * 控件的初始化
     */
    private void initialize() {

        ettitle = (EditText) findViewById(R.id.et_title);
        etcontext = (EditText) findViewById(R.id.et_context);
        ettime = (EditText) findViewById(R.id.et_time);
        btinster = (Button) findViewById(R.id.bt_inster);
        btseach = (Button) findViewById(R.id.bt_seach);
        lvshowdata = (ListView) findViewById(R.id.lv_showdata);
        activitymain = (LinearLayout) findViewById(R.id.activity_main);
        iv_person=(ImageView) findViewById(R.id.iv_person);
        MyListener listener=new MyListener();
        btinster.setOnClickListener(listener);
        btseach.setOnClickListener(listener);
        //长按弹出删除的PopupWindow
        lvshowdata.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i(TAG, "onItemLongClick" + "position:"+position+"id:"+id);
                Object obj = lvshowdata.getItemAtPosition(position);
                dismissPopupWindow();

                if (obj != null && obj instanceof BeiWang) {
                    clickedbeiwang = (BeiWang) obj;
                    View popup_view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_item_layout, null);
                    LinearLayout ll_delete = (LinearLayout) popup_view.findViewById(R.id.ll_delete);

                    ll_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            beiWangDataBase.Delete(clickedbeiwang.getId());
                            myAdapter.remove(position);
                            myAdapter.notifyDataSetChanged();
                            dismissPopupWindow();

                        }
                    });


                    popupWindow = new PopupWindow(popup_view, -2, -2);
                    // 动画播放有一个前提条件： 窗体必须要有背景资源。 如果窗体没有背景，动画就播放不出来。
                    popupWindow.setBackgroundDrawable(new ColorDrawable(
                            Color.TRANSPARENT));
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    popupWindow.showAtLocation(parent, Gravity.LEFT
                            + Gravity.TOP, 300, location[1]);
                    ScaleAnimation sa = new ScaleAnimation(0.5f, 1.0f, 0.5f,
                            1.0f, Animation.RELATIVE_TO_SELF, 0,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    sa.setDuration(200);
                    AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
                    aa.setDuration(200);
                    AnimationSet set = new AnimationSet(false);
                    set.addAnimation(aa);
                    set.addAnimation(sa);
                    popup_view.startAnimation(set);

                }

                return true;
            }
        });
       //listview详情查看
        lvshowdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismissPopupWindow();
                BeiWang itemBeiwang = (BeiWang) lvshowdata.getItemAtPosition(position);
                Intent intent1=new Intent(BeiWang_Activity.this,XiangqingActivity.class);
                intent1.putExtra("beiwang", itemBeiwang);
                startActivity(intent1);
                finish();
            }
        });
        //用户详情
        iv_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(BeiWang_Activity.this,UserActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
    //使存在的PopupWindow消失
    private void dismissPopupWindow() {
        if (popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow=null;
        }

    }
    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismissPopupWindow();
            String title = ettitle.getText().toString();
            String time = ettime.getText().toString();
            String context = etcontext.getText().toString();
            switch (v.getId()) {
                //插入
                case R.id.bt_inster:
                    beiWangDataBase.Insert(title,context,time,user_id);
                    Toast.makeText(BeiWang_Activity.this,"插入成功！", Toast.LENGTH_SHORT).show();
                    ettitle.setText("");
                    ettime.setText("");
                    etcontext.setText("");
                    updateListView();
                    break;
                //查询
                case R.id.bt_seach:
                    updateListView();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 更新listview
     */
    public void updateListView(){
        select = beiWangDataBase.Select(user_id);
        myAdapter = new BeiWangListAdapter(select,BeiWang_Activity.this);
        lvshowdata.setAdapter(myAdapter);
    }
}
