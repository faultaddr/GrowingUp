package com.example.panyunyi.growingup.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.panyunyi.growingup.ui.base.BaseActivity;
import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.adapter.InspireAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspireActivity extends BaseActivity {
    @BindView(R.id.inspire_main)
    RecyclerView recyclerViewInspire;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspire);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManagerInspire=new LinearLayoutManager(this);
        recyclerViewInspire.setHasFixedSize(true);
        recyclerViewInspire.setLayoutManager(layoutManagerInspire);
        recyclerViewInspire.setAdapter(new InspireAdapter(this));

    }

}
