package com.cube.autlab.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cube.autlab.MainActivity;
import com.cube.autlab.R;

/**
 * Created by c-ten on 2017/11/26.
 */

import android.view.Window;
import android.view.WindowManager;

public class WelComeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示welcome画面
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wel);
        //延迟0.7秒后执行run方法中的页面跳转
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelComeActivity.this, MainActivity.class);
                startActivity(intent);
                WelComeActivity.this.finish();
            }
        }, 1500);
    }
}