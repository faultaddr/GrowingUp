package com.example.panyunyi.growingup.ui.activity;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.adapter.InspireAdapter;
import com.example.panyunyi.growingup.ui.dialog.ContentDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspireActivity extends BaseActivity {
    @BindView(R.id.inspire_main)
    RecyclerView recyclerViewInspire;

    InspireAdapter inspireAdapter=new InspireAdapter(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManagerInspire=new LinearLayoutManager(this);
        recyclerViewInspire.setHasFixedSize(true);
        recyclerViewInspire.setLayoutManager(layoutManagerInspire);
        recyclerViewInspire.setAdapter(inspireAdapter);
        //实现在adapter中定义的接口
        inspireAdapter.setOnItemClickListener(new InspireAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                //TODO 这里需要访问后端实现 内容的web端添加
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
        });


    }

}
