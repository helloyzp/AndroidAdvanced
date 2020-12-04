package com.example.customglide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3;

    private  String mLoadImageUrl = "https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.image1);
        imageView2 = findViewById(R.id.image2);
        imageView3 = findViewById(R.id.image3);

        // 使用
        // Glide.with(this).load(mLoadImageUrl).into(imageView);
    }

    // 加载此图片：https://cn.bing.com/sa/simg/hpb/LaDigue_EN-CA1115245085_1920x1080.jpg
    public void t1(View view) {
        Glide.with(this)
                .load(mLoadImageUrl)
                .into(imageView1); // inio 不能在异步线程运行
    }

    public void t2(View view) {
        Glide.with(this)
                .load(mLoadImageUrl)
                .into(imageView2);
    }

    public void t3(View view) {
        Glide.with(this)
                .load(mLoadImageUrl)
                .into(imageView3);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 1.这种方式侵入代码很强
        // 以前的框架必须在onDestroy()方法中进行注销，侵入代码很强
        /*
            XXXXX.unDestroy();
            XXXXX.unDestroy();
            XXXXX.unDestroy();
        */

        // 而Glide不用写这句代码，GLide内部会监听Activity什么时候释放了
        // 原理：GLide 对 Activity 生命周期方法监听管理
    }
}
