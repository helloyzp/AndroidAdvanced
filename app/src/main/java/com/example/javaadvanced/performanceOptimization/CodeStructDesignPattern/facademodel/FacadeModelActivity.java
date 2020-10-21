package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.facademodel;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaadvanced.R;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.facademodel.bean.PhotoSetInfo;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.facademodel.facade.FacadeNetwork;
import com.yuyh.library.BubblePopupWindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象出一个网络层FacadeNetwork，负责网络请求，供view层调用。
 * 这样如果需要更换第三方网络请求库只需要在FacadeNetwork中修改即可。
 *
 * 存在的问题：
 * 违背了开闭原则，一旦更换第三方网络请求库，FacadeNetwork这个类的代码需要全面的修改，而不能复用已有的代码。
 * 解决方案：
 * 利用代理模式重构代码，惨啊可能：ProxyModelActivity
 */
public class FacadeModelActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    Button button;
    TextView mounth;
    private String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    private Map<String,Object> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facade_model);

        button = findViewById(R.id.btn);
        mounth = findViewById(R.id.point);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                FacadeNetwork<String> facadeNetwork = FacadeNetwork.getInstance(this);
                facadeNetwork.get(url, params, new FacadeNetwork.Callback<PhotoSetInfo>() {
                    @Override
                    public void onSuccess(PhotoSetInfo response) {
                        initBubble(mounth, response.getDesc());
                        Log.d(TAG, "Network result：" + response );
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }).start();
                    }


                    @Override
                    public void onFailed(String failed) {

                    }
                });
                break;
        }
    }

    private void initBubble(View view, String info){
        BubblePopupWindow leftTopWindow = new BubblePopupWindow(FacadeModelActivity.this);
        View bubbleView = getLayoutInflater().inflate(R.layout.layout_popup_view, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tvContent);
        tvContent.setText(info);
        leftTopWindow.setBubbleView(bubbleView); // 设置气泡内容
        leftTopWindow.show(view, Gravity.BOTTOM, 0); // 显示弹窗
    }
}
