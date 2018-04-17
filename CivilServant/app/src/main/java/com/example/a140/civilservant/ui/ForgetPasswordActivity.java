package com.example.a140.civilservant.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a140.civilservant.R;
import com.example.a140.civilservant.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by a140 on 2018/4/17.
 * 忘记/重置 密码
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    //邮箱
    private EditText et_email;
    //忘记密码
    private Button btn_forget_password;
    //旧密码
    private EditText et_now;
    //新密码
    private EditText et_new;
    private EditText et_new_password;
    //修改密码
    private Button btn_update_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    //初始化View
    private void initView() {
        et_email = (EditText) findViewById(R.id.et_email);
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);
    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_password:
                //获取新旧密码
                String nowpass = et_now.getText().toString().trim();
                String newpass = et_new.getText().toString().trim();
                String newpassword = et_new_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(nowpass) & !TextUtils.isEmpty(newpass) & !TextUtils.isEmpty(newpassword)) {
                    //判断新旧密码是否一致
                    if (newpass.equals(newpassword)) {
                        //修改密码
                        MyUser.updateCurrentUserPassword(nowpass, newpass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this, "密码修改成功，可以用新密码进行登录啦", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this, "失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                    } else {
                        Toast.makeText(this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_forget_password:
                //获取email
                final String email = et_email.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(email)) {
                    //发送邮件
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, "重置密码请求成功，请到" + email + "邮箱进行密码重置操作", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "请输入注册时的邮箱", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
