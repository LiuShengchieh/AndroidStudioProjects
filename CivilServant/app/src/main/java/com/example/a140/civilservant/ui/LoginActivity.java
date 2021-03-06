package com.example.a140.civilservant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a140.civilservant.MainActivity;
import com.example.a140.civilservant.R;
import com.example.a140.civilservant.entity.MyUser;
import com.example.a140.civilservant.utils.ShareUtils;
import com.example.a140.civilservant.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by a140 on 2018/4/17.
 * 登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册按钮
    private Button btn_registered;

    /*姓名，密码，登录，记住密码，忘记密码*/
    private EditText et_name;
    private EditText et_password;
    private Button btnLogin;
    private CheckBox keep_password;
    private TextView tv_forget;

    //自定义Dialog
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    //初始化View
    private void initView() {
        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        keep_password = (CheckBox) findViewById(R.id.keep_password);

        //设置选中的状态
        Boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if (isCheck) {
            //自动填入用户名和密码
            et_name.setText(ShareUtils.getString(this, "name", ""));
            et_password.setText(ShareUtils.getString(this, "password", ""));
        }

        //dialog
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btnLogin:
                //1、获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    //跳转到主页面
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱进行验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //假设输入用户名和密码后，未点击登录，直接退出
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());
        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            //删除用户名和密码
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
