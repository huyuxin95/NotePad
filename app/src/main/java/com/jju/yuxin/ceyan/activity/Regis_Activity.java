package com.jju.yuxin.ceyan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.dao.UserDataBase;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.domain
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 8:52.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class Regis_Activity extends Activity {

    private static final String TAG ="Regis_Activity";
    private EditText etrgusernam;
    private EditText etpwd1;
    private EditText etpwd2;
    private RadioButton rbman;
    private RadioButton woman;
    private RadioGroup gbsex;
    private Button btregisok;
    private int sex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_regis);
        initialize();
        //性别选择
        gbsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i(TAG, "onCheckedChanged" + "checkedId"+checkedId);
                if (checkedId==R.id.rb_man){
                    sex=1;
                }else if (checkedId==R.id.woman){
                    sex=2;
                }
            }
        });
        //注册监听
        btregisok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etrgusernam.getText().toString();
                String pwd1 = etpwd1.getText().toString();
                String pwd2 = etpwd2.getText().toString();
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(pwd1)&&pwd1.equals(pwd2)){
                    UserDataBase userdb=new UserDataBase(getApplicationContext());
                    userdb.Insert(name,pwd1,sex);
                    Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "用户名或密码填写错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * 控件初始化
     */
    private void initialize() {

        etrgusernam = (EditText) findViewById(R.id.et_rgusernam);
        etpwd1 = (EditText) findViewById(R.id.et_pwd1);
        etpwd2 = (EditText) findViewById(R.id.et_pwd2);
        rbman = (RadioButton) findViewById(R.id.rb_man);
        woman = (RadioButton) findViewById(R.id.woman);
        gbsex = (RadioGroup) findViewById(R.id.gb_sex);
        btregisok = (Button) findViewById(R.id.bt_regis_ok);
    }
}
