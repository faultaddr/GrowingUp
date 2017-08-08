package com.example.panyunyi.growingup.ui.activity;

import org.apache.commons.io.IOUtils;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panyunyi.growingup.manager.LoginSession;
import com.example.panyunyi.growingup.manager.OrderInfoSession;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.util.ACache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.URIResolver;

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
    @BindView(R.id.exit_button)
    ImageView exitButton;
    @BindView(R.id.user_name)
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        userName.setText(LoginSession.getLoginSession().getLoginedUser().getUserName());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.mine_activity_medal, R.id.mine_activity_account, R.id.mine_activity_message, R.id.mine_activity_setting, R.id.mine_activity_recommend, R.id.mine_activity_rmb,R.id.exit_button})
    void butterknifeOnItemClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.mine_activity_medal:
                /*
                * 勋章
                * */

                break;
            case R.id.mine_activity_account:
                /*
                * 账户
                * */
                intent.setClass(this,InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_activity_message:
                /*
                * 消息
                * */
                if(OrderInfoSession.getOrderInfoSession().getOrderedSession()==null&& ACache.get(getApplicationContext(),"session").getAsObject("sessionDetail")==null){
                    Toast.makeText(getApplication(), "暂无相关预定", Toast.LENGTH_LONG).show();
                }else{
                intent.setClass(this,MessageActivity.class);
                startActivity(intent);}
                break;
            case R.id.mine_activity_setting:
                /*
                * 设置
                */

                Snackbar.make(view, "no setting now", Snackbar.LENGTH_SHORT)
                        .setAction("ok", null)
                        .show();
                break;
            case R.id.mine_activity_recommend:
                /*
                * 推荐
                * */
                Resources res=getResources();
                Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.main_activity_communist);
                String filePrefix= null;
                try {
                    filePrefix = saveImageToGallery(this,bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Drawable drawable = getResources().getDrawable(R.drawable.main_activity_accept);
                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
                intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setType("image");





                    Uri uri=Uri.parse(filePrefix);

                    Log.i("uri", "" + uri.getScheme());

                    intent.setAction(Intent.ACTION_SEND);
//
//
                    intent.setType("image");
                    Log.i("image", " " + uri);
//
////                ArrayList<Uri> imageUris = new ArrayList<>();
////                    imageUris.add(uri);
//
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(intent);


/*                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 200);*/
                //TODO:  微信的deeplink被封了 所有这边需要 再等等看  分享到朋友圈总是失败  文件类型不通过
/*                //String url = "mqqwpa://im/chat?chat_type=group&uin=463028**3&version=1";//这是调到指定的qq群
                String url="mqqwpa://im/chat?chat_type=wpa&uin=779087031";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));*/
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
                    Snackbar.make(view, "you have not installed aliPay", Snackbar.LENGTH_SHORT)
                            .setAction("ok", null)
                            .show();
                }
                break;
            case R.id.exit_button:
                LoginSession.getLoginSession().exit();
            default:
                break;
        }
    }

    public void inputstreamtofile(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    private byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK) {

            // 获取相机返回的数据，并转换为Bitmap图片格式，这是缩略图
            Uri uri = data.getData();
            Log.i("data",data.getType()+"");
            Log.i("uri",uri.toString());
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            intent.setAction(Intent.ACTION_SEND);
//
//
            intent.setType("image/*");

//
////                ArrayList<Uri> imageUris = new ArrayList<>();
////                    imageUris.add(uri);
//

            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "我在用花生宝贝App哦~分享你也一起用吧！http://zjqsn.com/app/ 快去下载吧！");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM,uri);
            startActivity(intent);


        }
    }
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }
    public static String saveImageToGallery(Context context, Bitmap bmp) throws IOException {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis()+".jpg";
        String fileNameTemp;
        File file = new File(appDir, fileName);
        file.createNewFile();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            fileNameTemp=MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileNameTemp="";
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(Environment.getExternalStorageDirectory().toString()+"/Boohee")));
        return fileNameTemp;
    }
}
