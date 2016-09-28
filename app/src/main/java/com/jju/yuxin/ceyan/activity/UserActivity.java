package com.jju.yuxin.ceyan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.dao.UserDataBase;
import com.jju.yuxin.ceyan.domain.UserBean;

import static android.content.ContentValues.TAG;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.activity
 * Created by yuxin.
 * Created time 2016/9/28 0028 下午 1:07.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class UserActivity extends Activity {

    private EditText etusername;
    private EditText etuserpwd1;
    private EditText et_user_pwd2;
    private RadioButton rbuserman;
    private RadioButton rbuserwoman;
    private RadioGroup gbusersex;
    private Button btuserupdate;
    private SharedPreferences sp;
    private int user_id;
    private UserBean userBean;
    private RadioGroup gb_user_sex;
    private int sexflag=1;
    private Button bt_user_loginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_user);
        initialize();
        sp = getSharedPreferences("userid", MODE_PRIVATE);
        user_id = sp.getInt("id", -1);
        final UserDataBase userDataBase=new UserDataBase(getApplicationContext());
        //根据首选项传入的id获取用户对象
        userBean = userDataBase.Select(user_id);
        etusername.setText(userBean.name);
        if ("1".equals(userBean.sex)){
            rbuserman.setChecked(true);
            rbuserwoman.setChecked(false);
        }else {
            rbuserman.setChecked(false);
            rbuserwoman.setChecked(true);
        }

        gb_user_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.rb_user_man){
                    sexflag=1;
                }else {
                    sexflag=2;
                }

            }
        });
        btuserupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etusername.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String pwd1 = etuserpwd1.getText().toString();
                String pwd2 = et_user_pwd2.getText().toString();
                if (!TextUtils.isEmpty(pwd1)&&pwd1.equals(pwd2)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(UserActivity.this);
                    View view = LayoutInflater.from(UserActivity.this).inflate(R.layout.layout_putpwd_item, null);
                    builder.setView(view);
                    Button bt_putpwd= (Button) view.findViewById(R.id.bt_putpwd);
                    final EditText et_oldpwd= (EditText) view.findViewById(R.id.et_oldpwd);
                    builder.show();

                    bt_putpwd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String old = et_oldpwd.getText().toString();
                            if (userBean.pwd.equals(old)){
                                String newname = etusername.getText().toString();

                                Log.i("TAG", "onClick" + "user_id"+user_id+"newname"+newname+"pwd1"+pwd1+"sexflag"+sexflag);
                                userDataBase.UpPwd(user_id,newname,pwd1,sexflag);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putInt("id",-1);
                                edit.putBoolean("jizi", false);
                                edit.putBoolean("zidong", false);
                                edit.commit();
                                Toast.makeText(getApplicationContext(), "信息修改成功", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(UserActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "密码错误,信息修改失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        bt_user_loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("id",-1);
                edit.putBoolean("jizi", false);
                edit.putBoolean("zidong", false);
                edit.commit();
                Intent intent=new Intent(UserActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 控件的初始化
     */
    private void initialize() {

        etusername = (EditText) findViewById(R.id.et_user_name);
        etuserpwd1 = (EditText) findViewById(R.id.et_user_pwd1);
        et_user_pwd2 = (EditText) findViewById(R.id.et_user_pwd2);
        rbuserman = (RadioButton) findViewById(R.id.rb_user_man);
        rbuserwoman = (RadioButton) findViewById(R.id.rb_user_woman);
        btuserupdate = (Button) findViewById(R.id.bt_user_update);
        gb_user_sex = (RadioGroup)findViewById(R.id.gb_user_sex);
        bt_user_loginout = (Button) findViewById(R.id.bt_user_loginout);
    }
    /**
     * 返回键的监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent main_intent = new Intent(UserActivity.this, BeiWang_Activity.class);
            startActivity(main_intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
