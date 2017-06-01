package com.example.panyunyi.growingup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import com.example.panyunyi.growingup.ui.custom.PicWordView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivty extends AppCompatActivity {

    @BindView(R.id.textView)
    PicWordView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activty);
        ButterKnife.bind(this);


        textView.setText("fdsafdsfafsdaf<img" + R.drawable.main_activity_accept + "/>fdsafsdafsdfds");
    }
}
