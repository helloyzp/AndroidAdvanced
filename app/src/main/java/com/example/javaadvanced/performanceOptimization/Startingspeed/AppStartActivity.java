package com.example.javaadvanced.performanceOptimization.Startingspeed;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.example.javaadvanced.R;


public class AppStartActivity extends Activity {

    ImageView mLogo = null;
    private final int TIME_ANIMATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //演示白屏黑屏现象，添加几秒睡眠时间，以确保白屏黑屏一会儿的效果明显，在项目应用要去掉这睡眠时间
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 在这里之前，黑屏或者白屏都是window的背景颜色，是窗口背景，还没到界面的布局呢，要执行setContentView后才显示布局
        setContentView(R.layout.activity_app_start);
        mLogo = this.findViewById(R.id.logo);
        useAnimator();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    //AnimatorSet
    private void useAnimator() {
        mLogo.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        ObjectAnimator scalex = ObjectAnimator.ofFloat(mLogo, "scaleX", 0, 1);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(mLogo, "scaleY", 0, 1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mLogo, "rotation", 0.0f, 360f);
        rotation.addListener(new ObjectAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLogo.setLayerType(View.LAYER_TYPE_NONE,null);
                mStartHandler.sendEmptyMessageDelayed(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        AnimatorSet mSetPlayer = new AnimatorSet();
        mSetPlayer.setDuration(TIME_ANIMATION);
        mSetPlayer.play(scalex).with(scaley).with(rotation);
        mSetPlayer.start();
    }

    private Handler mStartHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //test
            Intent it = new Intent(AppStartActivity.this, HomeActivity.class);
            startActivity(it);
            finish();
        }
    };

}
