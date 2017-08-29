package com.example.panyunyi.growingup.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.ui.dialog.ContentDialog;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspireActivity extends BaseActivity {
    //InspireAdapter inspireAdapter=new InspireAdapter(this);
    private static ArrayList<String> al;
    @BindView(R.id.frame)
    SwipeFlingAdapterView flingContainer;
    @BindView(R.id.inspire_detail)
    TextView inspireDetail;
    @BindView(R.id.inspire_dislike)
    ImageView inspireDislike;
    @BindView(R.id.inspire_like)
    ImageView inspireLike;

    private static int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire);
        ButterKnife.bind(this);
    /*        RecyclerView.LayoutManager layoutManagerInspire=new LinearLayoutManager(this);
            recyclerViewInspire.setHasFixedSize(true);
            recyclerViewInspire.setLayoutManager(layoutManagerInspire);
            recyclerViewInspire.setAdapter(inspireAdapter);
            //实现在adapter中定义的接口
            inspireAdapter.setOnItemClickListener(new InspireAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    //TODO 这里需要访问后端实现 内容的web端添加
                    String url = "mqqwpa://im/chat?chat_type=group&uin=652024893&version=1";//这是调到指定的qq群
                    //String url="mqqwpa://im/chat?chat_type=wpa&uin=779087031";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

                @Override
                public void onItemLongClick(View view) {
                    ContentDialog dialog;
                    if((int)view.getTag()!=1)
                        dialog=ContentDialog.newInstance(getResources().getText(R.string.dialog_interact).toString());
                    else
                        dialog=ContentDialog.newInstance(getResources().getText(R.string.dialog_homework).toString());
                    dialog.show(getSupportFragmentManager(),"dialog");
                }
            });*/

        initFling();
        initText();
        initButton();

    }

    private void initButton() {
        inspireDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });
        inspireLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "mqqwpa://im/chat?chat_type=group&uin=652024893&version=1";//这是调到指定的qq群
                //String url="mqqwpa://im/chat?chat_type=wpa&uin=779087031";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
    }

    private void initText() {
        if(al.get(0).equals("互动小组")) {
            inspireDetail.setText(getResources().getText(R.string.dialog_interact).toString());
        }else{
            inspireDetail.setText(getResources().getText(R.string.dialog_homework).toString());
        }
    }

    private void initFling() {
        al = new ArrayList<String>();
        al.add("互动小组");
        al.add("小作业");


        //choose your favorite adapter
        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, R.layout.swipe_item, R.id.helloText, al);

        //set the listener and the adapter
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                initText();
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(InspireActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(InspireActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
    /*                al.add("XML ".concat(String.valueOf(i)));
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("LIST", "notified");
                    i++;*/
                al.add("互动小组");
                al.add("小作业");
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float v) {

            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //ContentDialog dialog;
               // dialog = ContentDialog.newInstance(getResources().getText(R.string.dialog_homework).toString());
                //dialog.show(getSupportFragmentManager(), "dialog");
                String url = "mqqwpa://im/chat?chat_type=group&uin=652024893&version=1";//这是调到指定的qq群
                //String url="mqqwpa://im/chat?chat_type=wpa&uin=779087031";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));


            }
        });
        flingContainer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }

}
