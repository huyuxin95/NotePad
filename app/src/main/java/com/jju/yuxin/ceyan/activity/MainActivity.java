package com.jju.yuxin.ceyan.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.jju.yuxin.ceyan.R;
import com.jju.yuxin.ceyan.dao.UserDataBase;
import com.jju.yuxin.ceyan.domain.UserBean;

public class MainActivity extends Activity {

    private EditText etusername;
    private EditText edpwd;
    private CheckBox cbjizhumima;
    private CheckBox cbzidongdenglu;
    private Button btdenglu;
    private Button btregis;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("userid", MODE_PRIVATE);
        edit = sp.edit();
        initialize();


        boolean jizi = sp.getBoolean("jizi", false);
        boolean zidong = sp.getBoolean("zidong", false);
        if (jizi){
            int user_id = sp.getInt("id", -1);
            final UserDataBase userDataBase=new UserDataBase(getApplicationContext());
            //根据首选项传入的id获取用户对象
            UserBean userBean = userDataBase.Select(user_id);
            etusername.setText(userBean.name+"");
            edpwd.setText(userBean.pwd+"");
            cbjizhumima.setChecked(true);
        }else{
            cbjizhumima.setChecked(false);
        }
        if (zidong){
            login();
        }else{
            cbzidongdenglu.setChecked(false);
        }
        cbjizhumima.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edit.putBoolean("jizi", true);
                }else{
                    edit.putBoolean("jizi", false);
                }
                edit.commit();
            }
        });
        cbzidongdenglu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edit.putBoolean("zidong", true);
                    cbzidongdenglu.setChecked(true);
                }else{
                    edit.putBoolean("zidong", false);
                    cbzidongdenglu.setChecked(false);
                }
                edit.commit();
            }
        });
    }

    private void login() {
        String name = etusername.getText().toString();
        String pwd = edpwd.getText().toString();
        UserDataBase userdb=new UserDataBase(getApplicationContext());
        UserBean loginuser = userdb.Select(name, pwd);
        if (loginuser!=null){
            Intent intent=new Intent(MainActivity.this,BeiWang_Activity.class);
            startActivity(intent);
            edit.putInt("id",loginuser._id);
            edit.commit();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "用户名或密码错误!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 控件的实例化
     */
    private void initialize() {
        etusername = (EditText) findViewById(R.id.et_username);
        edpwd = (EditText) findViewById(R.id.ed_pwd);

        cbjizhumima = (CheckBox) findViewById(R.id.cb_jizhumima);
        cbzidongdenglu = (CheckBox) findViewById(R.id.cb_zidongdenglu);

        btdenglu = (Button) findViewById(R.id.bt_denglu);
        btregis = (Button) findViewById(R.id.bt_regis);
        BtListener listener=new BtListener();
        btdenglu.setOnClickListener(listener);
        btregis.setOnClickListener(listener);


    }

    /**
     * 按钮的点击事件
     */
    public class BtListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //登录监听
                case R.id.bt_denglu:
                    login();
                    break;
                //注册监听
                case R.id.bt_regis:
                    Intent intent=new Intent(MainActivity.this,Regis_Activity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }

}
