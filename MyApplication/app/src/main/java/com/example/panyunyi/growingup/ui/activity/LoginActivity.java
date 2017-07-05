package com.example.panyunyi.growingup.ui.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.panyunyi.growingup.MainActivity;
import com.example.panyunyi.growingup.entity.remote.User;
import com.example.panyunyi.growingup.manager.LoginImpl;
import com.example.panyunyi.growingup.util.ACache;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.ui.custom.JellyInterpolator;
import com.orhanobut.logger.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LoginActivity extends BaseActivity implements OnClickListener {
    //TODO-LIST: 增加注册页面
    private TextView mBtnLogin;

    private View progress;

    private View mInputLayout;

    private float mWidth, mHeight;

    private LinearLayout mName, mPsw;

    private EditText userId;

    private EditText passWord;

    private TextView signUp;

    private RelativeLayout mainToAll;

    private ImageView backButton;

    private final Lock lock = new ReentrantLock();

    private Condition notComplete = lock.newCondition();
    private Condition notEmpty = lock.newCondition() ;

    private String nameString,psString;

    private static int TAG=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        initView();


//        if(getIntent()!=null) {
//            Intent intent=getIntent();
//            Log.i("intent",intent.toString());
//            userId.setText(intent.getStringExtra("userId"));
//            passWord.setText(intent.getStringExtra("passWord"));
//            nameString=intent.getStringExtra("userId");
//            psString=intent.getStringExtra("passWord");
//            Bundle bundle=new Bundle();
//            bundle.putString("userId",nameString);
//            bundle.putString("passWord",psString);
//            Message msg=new Message();
//            msg.what=2;
//            msg.setData(bundle);
//            Log.i("msgg",msg.toString());
//            handler.sendMessage(msg);
//
//        }
//        ACache mCache=ACache.get(getApplication(),"User");
//        if(mCache.getAsString("username")!=null){
//            Message msg=new Message();
//            msg.what=1;
//            handler.sendMessage(msg);
//        }
    }

    private void initView() {
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        signUp=(TextView)findViewById(R.id.signup);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        backButton=(ImageView)findViewById(R.id.back);
        backButton.setVisibility(View.INVISIBLE);
        mName = (LinearLayout) findViewById(R.id.input_layout_name);
        mPsw = (LinearLayout) findViewById(R.id.input_layout_psw);
        userId=(EditText) findViewById(R.id.userId);
        passWord=(EditText)findViewById(R.id.passWord);
        mainToAll=(RelativeLayout)findViewById(R.id.mainToAll);
        mBtnLogin.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();
        nameString=userId.getText().toString();
        psString=passWord.getText().toString();
        inputAnimator(mInputLayout, mWidth, mHeight);
        mName.setVisibility(View.INVISIBLE);
        mPsw.setVisibility(View.INVISIBLE);


        Message message=new Message();
        message.what=2;
        handler.sendMessage(message);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){



            Log.i("daole","fsdafs");
            nameString=data.getStringExtra("userId");
            psString=data.getStringExtra("passWord");



        }

    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(View.VISIBLE);
                try {
                    progressAnimator(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mInputLayout.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) throws InterruptedException {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();





    }
    public android.os.Handler handler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case 2:
                    User user=new User();
                    user.userId=nameString;
                    user.userPassword=psString;
                    Log.i(">>>",nameString);
                    Log.i(">>>",psString);
                    LoginImpl login=new LoginImpl(user);
                    boolean result=login.login();
                    Log.i(">>>result",result+"");
                    if(result)
                    {
                        Intent intent=new Intent();
                        intent.setClass(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    break;

            }

            super.handleMessage(msg);
        }

    };





}
