package com.shen.dribbble;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by shen on 2016/7/25.
 */
public class BaseActivity extends RxAppCompatActivity {

    private boolean isSetStatusBarColor = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSetStatusBarColor) setStatusBarColor();
    }

    protected void setSetStatusBarColor(boolean setStatusBarColor) {
        isSetStatusBarColor = setStatusBarColor;
    }

    /**
     * 设置状态栏颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void setStatusBarColor(){
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    protected void setToolBar(String title,boolean hasBackIcon){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (!title.isEmpty()) {
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);

        if (hasBackIcon) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
