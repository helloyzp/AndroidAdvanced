package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel;


import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.javaadvanced.R;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel.bean.PhotoSetInfo;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel.proxy.HttpCallback;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.proxymodel.proxy.HttpProxy;
import com.yuyh.library.BubblePopupWindow;

import java.util.HashMap;
import java.util.Map;

public class ProxyModelActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    Button button;
    TextView mounth;
    private String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    private Map<String, Object> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_model);

        button = findViewById(R.id.btn);
        mounth = findViewById(R.id.point);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                HttpProxy.obtain().get(url, params, new HttpCallback<PhotoSetInfo>() {
                    @Override
                    public void onFailure(String e) {
                        Log.d(TAG, "onFailure: " + e);
                    }

                    @Override
                    public void onSuccess(PhotoSetInfo result) {
                        initBubble(mounth, result.getDesc());
                        Log.d(TAG, "Network result：" + result.toString());
                    }
                });
                break;
        }
    }

    private void initBubble(View view, String info) {
        BubblePopupWindow leftTopWindow = new BubblePopupWindow(ProxyModelActivity.this);
        View bubbleView = getLayoutInflater().inflate(R.layout.layout_popup_view, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tvContent);
        tvContent.setText(info);
        leftTopWindow.setBubbleView(bubbleView); // 设置气泡内容
        leftTopWindow.show(view, Gravity.BOTTOM, 0); // 显示弹窗
    }
}
