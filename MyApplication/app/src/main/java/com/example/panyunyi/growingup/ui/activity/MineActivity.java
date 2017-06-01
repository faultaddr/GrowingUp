package com.example.panyunyi.growingup.ui.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panyunyi.growingup.BaseActivity;
import com.example.panyunyi.growingup.MainActivity;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.service.MsgService;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends BaseActivity {

    @BindView(R.id.mine_activity_medal)
    RelativeLayout medalLayout;
    @BindView(R.id.mine_activity_account)
    RelativeLayout accountLayout;
    @BindView(R.id.mine_activity_message)
    RelativeLayout messageLayout;
    @BindView(R.id.mine_activity_setting)
    RelativeLayout settingLayout;
    @BindView(R.id.mine_activity_recommend)
    RelativeLayout recommendLayout;
    @BindView(R.id.mine_activity_rmb)
    RelativeLayout rmbLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        ButterKnife.bind(this);


    }

    @OnClick({R.id.mine_activity_medal, R.id.mine_activity_account, R.id.mine_activity_message, R.id.mine_activity_setting, R.id.mine_activity_recommend, R.id.mine_activity_rmb})
    void butterknifeOnItemClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.mine_activity_medal:
                /*
                * 勋章
                * */

                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_activity_account:
                /*
                * 账户
                * */
                break;
            case R.id.mine_activity_message:
                /*
                * 消息
                * */
                break;
            case R.id.mine_activity_setting:
                /*
                * 设置

                */
                Snackbar.make(view, "no setting now",Snackbar.LENGTH_SHORT)
                        .setAction("ok", null)
                        .show();
                break;
            case R.id.mine_activity_recommend:
                /*
                * 推荐
                * */
                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                //intent.setAction("android.intent.action.SEND");
////                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
////                intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
////                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setType("image/*");
                Uri imageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir() + "/" + "main_activity_accept" + ".png"));
                //intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + getResources().getResourcePackageName(R.drawable.main_activity_accept) + "/"
                + getResources().getResourceTypeName(R.drawable.main_activity_accept) + "/"
                + getResources().getResourceEntryName(R.drawable.main_activity_accept));
                Log.i("image",""+imageUri+" "+uri);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");

                ArrayList<Uri> imageUris = new ArrayList<>();
                    imageUris.add(uri);

                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                startActivity(intent);

                break;
            case R.id.mine_activity_rmb:
                /*
                * 打赏
                * */

//                intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//                ComponentName comp = new ComponentName("com.tencent.mm",
//                        "com.tencent.mm.ui.tools.ShareImgUI");
//                intent.setComponent(comp);
//                intent.setAction("android.intent.action.SEND");
////                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
////                intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
////                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                startActivity(intent);

//                intent.setType("image/*");
//                Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + getResources().getResourcePackageName(R.drawable.main_activity_accept) + "/"
//                + getResources().getResourceTypeName(R.drawable.main_activity_accept) + "/"
//                + getResources().getResourceEntryName(R.drawable.main_activity_accept));
//                //Uri imageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir() + "/" + "main_activity_accept" + ".png"));
//                intent.putExtra(Intent.EXTRA_STREAM, uri);
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("http://weixin://scanqrcode");
//                intent.setData(content_url);
//                //intent.setClassName("com.android.browser","com.android.browser/.BrowserActivity");
                //startActivity(intent);

//                ComponentName componentName=new ComponentName("com.tencent.mm",
//                       "com.tencent.mm.plugin.setting.ui.qrcode.GetQRCodeInfoUI");
//                intent.setComponent(componentName);
//                intent.setAction("android.intent.action.VIEW");
//                intent.setType("image/*");
//                Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + getResources().getResourcePackageName(R.drawable.main_activity_accept) + "/"
//                    + getResources().getResourceTypeName(R.drawable.main_activity_accept) + "/"
//                    + getResources().getResourceEntryName(R.drawable.main_activity_accept));
//                //Uri imageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir() + "/" + "main_activity_accept" + ".png"));
//                intent.putExtra(Intent.EXTRA_STREAM, uri);
//                startActivity(intent);
                try {
                    Uri uriPay = Uri.parse("alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/FKX05743OGSVQYI4G901E8");
                    intent = new Intent(Intent.ACTION_VIEW, uriPay);
//                intent.setType("image/*");
//                Uri uri1 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                + getResources().getResourcePackageName(R.drawable.payme) + "/"
//                    + getResources().getResourceTypeName(R.drawable.payme) + "/"
//                    + getResources().getResourceEntryName(R.drawable.payme));
//                //Uri imageUri = Uri.fromFile(new File(getApplicationContext().getFilesDir() + "/" + "main_activity_accept" + ".png"));
//                intent.putExtra(Intent.EXTRA_STREAM, uri1);
                    startActivity(intent);
                } catch (Exception exception) {
//                    Snackbar snackbar = Snackbar.make(v, "It is Snackbar", Snackbar.LENGTH_SHORT);
//                    View snackbarView = snackbar.getView();
//                    //设置显示位置居中
//                    ViewGroup.LayoutParams vl =
//                            snackbarView.getLayoutParams();
//                    CoordinatorLayout.LayoutParams cl = new CoordinatorLayout.LayoutParams(vl.width, vl.height);
//                    cl.gravity = Gravity.CENTER;
//                    snackbarView.setLayoutParams(cl);
//                    //设置字体的颜色
//                    ((TextView)snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.RED);
//                    //设置背景颜色
//                    snackbarView.setBackgroundColor(Color.GREEN);
//                    //自定义动画
////        snackbarView.setAnimation();
//                    //设置icon
//                    ImageView imageView = new ImageView(this);
//                    imageView.setBackgroundResource(R.mipmap.ic_launcher);
//                    Snackbar.SnackbarLayout ss = (Snackbar.SnackbarLayout) snackbarView;
//                    ss.addView(imageView, 0);
//
//                    snackbar.setAction("点击", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplication(), "Toast", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    snackbar.show();
                    Snackbar.make(view, "you have not installed aliPay",Snackbar.LENGTH_SHORT)
                            .setAction("ok", null)
                            .show();
                }
                break;
            default:
                break;
        }
    }
}