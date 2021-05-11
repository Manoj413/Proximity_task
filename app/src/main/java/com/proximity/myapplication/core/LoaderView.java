package com.proximity.myapplication.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.proximity.myapplication.R;

public class LoaderView extends ConstraintLayout {


    ProgressBar progressBar;

    TextView txtLoaderText;
    Context mContext;
    String mLoadingText;

    public LoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View v = inflate(context, R.layout.view_loader, this);
        txtLoaderText = v.findViewById(R.id.txtLoaderText);
        progressBar = v.findViewById(R.id.centerLoader);
    }


    public String getmLoadingText() {
        return mLoadingText;
    }

    public void setmLoadingText(String mLoadingText) {
        this.mLoadingText = mLoadingText;


        if (getmLoadingText()!=null && !getmLoadingText().isEmpty()) {
            txtLoaderText.setText(getmLoadingText());
        }
    }
}

