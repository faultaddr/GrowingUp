package com.example.panyunyi.growingup.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.panyunyi.growingup.R;
import com.example.panyunyi.growingup.ui.fragment.BlankFragment;

import java.util.zip.Inflater;

/**
 * Created by panyunyi on 2017/6/22.
 */

public class ContentDialog extends android.support.v4.app.DialogFragment {
    private TextView textView;
    public boolean isDismiss=false;

    String mContent;
    public ContentDialog() {
    }

    @Override
    public boolean getShowsDialog() {

        return super.getShowsDialog();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        dialog.dismiss();
        isDismiss=true;

        super.onDismiss(dialog);
    }

    public static ContentDialog newInstance(String content) {
        ContentDialog contentDialog = new ContentDialog();
        Bundle args = new Bundle();
        args.putString("content", content);
        contentDialog.setArguments(args);
        return contentDialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.dialog_content,container);
        textView=(TextView)view.findViewById(R.id.dialog_text);
        textView.setText(this.getArguments().getString("content"));

        return view;
    }


}
