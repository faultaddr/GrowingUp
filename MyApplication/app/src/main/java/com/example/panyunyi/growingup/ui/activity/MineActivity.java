package com.example.panyunyi.growingup.ui.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panyunyi.growingup.Constant;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.app.Application;
import com.example.panyunyi.growingup.manager.LoginSession;
import com.example.panyunyi.growingup.manager.OrderInfoSession;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.ui.custom.CircleImageView;
import com.example.panyunyi.growingup.ui.custom.RoundImageView;
import com.example.panyunyi.growingup.util.ACache;
import com.example.panyunyi.growingup.util.UriToPathUtil;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MineActivity extends BaseActivity {

    static OkHttpClient client = new OkHttpClient();
    @BindView(R.id.mine_activity_medal)
    RelativeLayout medalLayout;
    @BindView(R.id.mine_activity_account)
    RelativeLayout accountLayout;
    @BindView(R.id.mine_activity_message)
    RelativeLayout messageLayout;
    /*    @BindView(R.id.mine_activity_setting)
        RelativeLayout settingLayout;*/
    @BindView(R.id.mine_activity_recommend)
    RelativeLayout recommendLayout;
    @BindView(R.id.mine_activity_rmb)
    RelativeLayout rmbLayout;
    @BindView(R.id.exit_button)
    ImageView exitButton;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_pic)
    CircleImageView userPic;

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
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
        String fileName = System.currentTimeMillis() + ".jpg";
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
            fileNameTemp = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileNameTemp = "";
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(Environment.getExternalStorageDirectory().toString() + "/Boohee")));
        return fileNameTemp;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        ButterKnife.bind(this);
        initView();

    }



    private void initView() {
        userName.setText(LoginSession.getLoginSession().getLoginedUser().getUserName());
        Log.i(">>userPic", Constant.API_URL + "/userPic/" + LoginSession.getLoginSession().getLoginedUser().getUserId() + ".jpg");
        Picasso.with(this).setIndicatorsEnabled(true);
        Picasso picasso = new Picasso.Builder(this)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {

                    }
                })
                .build();
        picasso.with(this).load(Constant.API_URL + "/userPic/" + LoginSession.getLoginSession().getLoginedUser().getUserId() + ".jpg").resize(140, 140)
                .into(userPic);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.mine_activity_medal, R.id.mine_activity_account, R.id.mine_activity_message, //R.id.mine_activity_setting,
            R.id.mine_activity_recommend, R.id.mine_activity_rmb, R.id.exit_button, R.id.user_pic})
    void butterknifeOnItemClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.user_pic:
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);


                break;

            case R.id.mine_activity_medal:
                /*
                * 勋章
                * */

                break;
            case R.id.mine_activity_account:
                /*
                * 账户
                * */
                intent.setClass(this, InfoActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_activity_message:
                /*
                * 消息
                * */
                if (OrderInfoSession.getOrderInfoSession().getOrderedSession() == null && ACache.get(getApplicationContext(), "session").getAsObject("sessionDetail") == null) {
                    Toast.makeText(getApplication(), "暂无相关预定", Toast.LENGTH_LONG).show();
                } else {
                    intent.setClass(this, MessageActivity.class);
                    startActivity(intent);
                }
                break;
            /*case R.id.mine_activity_setting:
                *//*
                * 设置
                *//*

                Snackbar.make(view, "no setting now", Snackbar.LENGTH_SHORT)
                        .setAction("ok", null)
                        .show();
                break;*/
            case R.id.mine_activity_recommend:
                /*
                * 推荐
                * */

                boolean WeChatIsExist = false;
                for (ApplicationInfo info : Application.mApplicationInfos) {
                    if (info.packageName.equals("com.tencent.mm")) {
                        WeChatIsExist = true;
                    }
                }
                if(WeChatIsExist) {
                    Resources res = getResources();
                    Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.main_activity_communist);
                    String filePrefix = null;
                    try {
                        filePrefix = saveImageToGallery(this, bmp);
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


                    Uri uri = Uri.parse(filePrefix);

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
                }else{
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("http://weixin.qq.com/cgi-bin/readtemplate?uin=&stype=&promote=&fr=&lang=zh_CN&ADTAG=&check=false&t=weixin_download_method&sys=android&loc=weixin,android,web,0");
                    intent.setData(content_url);
                    startActivity(intent);
                }
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
                    boolean AliPayIsExist = false;
                    for (ApplicationInfo info : Application.mApplicationInfos) {
                        if (info.packageName.equals("com.eg.android.AlipayGphone")) {
                            AliPayIsExist = true;
                        }
                    }
                    if (AliPayIsExist) {
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
                    }else{

                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("https://mobile.alipay.com/index.htm");
                        intent.setData(content_url);
                        startActivity(intent);
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            String s = UriToPathUtil.getRealFilePath(getApplicationContext(), uri);
            System.out.println(s);
            //File file = new File(s);
            ExecutorService exs = Executors.newCachedThreadPool();

            FileUpLoad fileUpLoad = new FileUpLoad(MediaType.parse("multipart/form-data;boundary=2000000"), Constant.API_URL + "/fileUploadPage?userId=" + LoginSession.getLoginSession().getLoginedUser().getUserId(), s);
            //大家对Future对象如果陌生，说明你用带返回值的线程用的比较少，要多加练习
            Future<Object> future = exs.submit(fileUpLoad);//使用线程池对象执行任务并获取返回对象
            try {
                String result = future.get().toString();
                Log.i("result", result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                CircleImageView imageView = (CircleImageView) findViewById(R.id.user_pic);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

    public class FileUpLoad implements Callable {
        MediaType mediaType;
        String uploadUrl;
        String localPath;

        FileUpLoad(MediaType m, String u, String l) {
            mediaType = m;
            uploadUrl = u;
            localPath = l;
        }

        public String put() throws IOException {
            File file = new File(localPath);
            //RequestBody body = RequestBody.create(mediaType, file);

            MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (file != null) {
                // MediaType.parse() 里面是上传的文件类型。
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                String fileName = file.getName();
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                requestBody.addFormDataPart("headImage", fileName, body);
            }

            Request request = new Request.Builder().url(uploadUrl).post(requestBody.build()).tag(this).build();
            // readTimeout("请求超时时间" , 时间单位);
            client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("lfq", "onFailure");
                   Snackbar.make(userPic,"更改失败",Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        Log.i("lfq", response.message() + " , body " + str);
                        Snackbar.make(userPic,"更改成功",Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.i("lfq", response.message() + " error : body " + response.body().string());
                    }
                }
            });
            return "true";

        }

        @Override
        public Object call() throws Exception {
            return put();
        }
    }//上传

}

