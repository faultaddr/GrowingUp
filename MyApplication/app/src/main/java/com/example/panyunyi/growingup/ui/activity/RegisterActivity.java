package com.example.panyunyi.growingup.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.entity.remote.GTeacherEntity;
import com.example.panyunyi.growingup.entity.remote.UserInfo;
import com.example.panyunyi.growingup.service.MsgService;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.activity_register_back)
    TextView backButton;
    @BindView(R.id.activity_register_sid)
    EditText registerStudentId;//学号
    String sId;
    @BindView(R.id.activity_register_name)
    EditText registerName;
    String name;
    @BindView(R.id.activity_register_weChat)
    EditText registerWeChat;
    String weChat;
    @BindView(R.id.activity_register_aliPay)
    EditText registerAliPay;
    String aliPay;
    @BindView(R.id.activity_register_institute)
    EditText registerInstitute;
    String institute;
    @BindView(R.id.activity_register_major)
    EditText registerMajor;
    String major;
    @BindView(R.id.activity_register_password)
    EditText registerPassWord;
    String password;
    @BindView(R.id.activity_register_phone)
    EditText registerPhone;
    String phone;
    @BindView(R.id.activity_register_class)
    EditText registerClass;
    String sClass;
    @BindView(R.id.activity_register_register)
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sId = registerStudentId.getText().toString();
                name = registerName.getText().toString();
                phone = registerPhone.getText().toString();
                sClass = registerClass.getText().toString();
                institute = registerInstitute.getText().toString();
                major = registerMajor.getText().toString();
                aliPay = registerAliPay.getText().toString();
                password = registerPassWord.getText().toString();
                weChat = registerWeChat.getText().toString();
                if (sId != null && password != null) {
                    UserInfo userInfo = new UserInfo.UserBuilder().
                            userId(sId).
                            userName(name).
                            userPhone(phone).
                            userClass(sClass).
                            userInstitute(institute).
                            userGrade(major).
                            userAliPay(aliPay).
                            userPassword(password).
                            userWechat(weChat).
                            userRole("0").
                            build();
                    ExecutorService exs = Executors.newCachedThreadPool();

                    String JsonArray=JSON.toJSONString(userInfo);
                    //MsgService.getMethod ct = new MsgService.getMethod(Constant.API_URL+"/showTeacher"+userInfo);//实例化任务对象
                    //大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
                    MsgService.Post post=new MsgService.Post(Constant.API_URL+"/register",JsonArray);
                    Future<Object> future = exs.submit(post);//使用线程池对象执行任务并获取返回对象
                    String result = null;
                    try {
                        result=future.get().toString();
                        Log.i("result",result);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if(result.equals(true)){
                        Toast.makeText(getApplication(),"注册成功小宝贝",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(getApplication(),"注册失败请检查网络或必填项",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
