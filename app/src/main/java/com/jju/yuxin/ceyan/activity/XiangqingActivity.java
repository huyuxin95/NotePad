package com.jju.yuxin.ceyan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.dao.BeiWangDataBase;
import com.jju.yuxin.ceyan.domain.BeiWang;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.ceyan.activity
 * Created by yuxin.
 * Created time 2016/9/28 0028 上午 10:57.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class XiangqingActivity extends Activity {


    private EditText etxianqgingtitle;
    private EditText etxianqgingcontext;
    private EditText etxianqgingtime;
    private Button bt_update;
    private BeiWangDataBase beiWangDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_xiangqing);

        beiWangDataBase = new BeiWangDataBase(getApplicationContext());
        initialize();
        //将传入的BeiWang对象获取
        Intent intent = getIntent();
        final BeiWang beiwang = intent.getParcelableExtra("beiwang");
        etxianqgingtitle.setText(beiwang.getTitle());
        etxianqgingcontext.setText(beiwang.getContent());
        etxianqgingtime.setText(beiwang.getDate());

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beiWangDataBase.Update(beiwang.getId(),etxianqgingtitle.getText().toString(),etxianqgingcontext.getText().toString(),etxianqgingtime.getText().toString());
                Toast.makeText(getApplicationContext(), "修改成功!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 控件的初始化
     */
    private void initialize() {

        etxianqgingtitle = (EditText) findViewById(R.id.et_xianqging_title);
        etxianqgingcontext = (EditText) findViewById(R.id.et_xianqging_context);
        etxianqgingtime = (EditText) findViewById(R.id.et_xianqging_time);
        bt_update = (Button) findViewById(R.id.bt_update);
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
            Intent main_intent = new Intent(XiangqingActivity.this, BeiWang_Activity.class);
            startActivity(main_intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
