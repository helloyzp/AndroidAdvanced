package com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.firstusestruct;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.javaadvanced.R;
import com.example.javaadvanced.performanceOptimization.CodeStructDesignPattern.firstusestruct.bean.PhotoSetInfo;
import com.yuyh.library.BubblePopupWindow;

/**
 * 以网络请求库的封装为例讲解设计模式
 *
 * 这个例子是未使用设计模式的代码
 *
 * 存在的问题：
 * 1.Activity里存在大量网络请求相关的代码
 * 2.直接引用了第三方网络请求库，如果这个第三方网络请求库后期需要改成其他的第三方网络请求库，则需要修改大量代码
 *
 * 解决方案：
 * 利用门面模式重构代码，参看：FacadeModelActivity
 */
public class HTTPRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    Button button;
    TextView mounth;
    private String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_request);

        button = findViewById(R.id.btn);
        mounth = findViewById(R.id.point);
        button.setOnClickListener(this);
        mQueue = Volley.newRequestQueue(this);
//        initBubble(mounth);
    }

    private void initBubble(View view, String info) {
        BubblePopupWindow leftTopWindow = new BubblePopupWindow(HTTPRequestActivity.this);
        View bubbleView = getLayoutInflater().inflate(R.layout.layout_popup_view, null);
        TextView tvContent = (TextView) bubbleView.findViewById(R.id.tvContent);
        tvContent.setText(info);
        leftTopWindow.setBubbleView(bubbleView); // 设置气泡内容
        leftTopWindow.show(view, Gravity.BOTTOM, 0); // 显示弹窗
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                StringRequest stringRequest = new StringRequest(Request.Method.GET,
                        url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        HttpHelper<PhotoSetInfo> helper = new HttpHelper<PhotoSetInfo>().ge;
                        PhotoSetInfo inof = new BaseType<PhotoSetInfo>() {

                        }.getResult(response);
                        initBubble(mounth, inof.getDesc());
                        Log.d(TAG, "Network result：" + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Network onErrorResponse：" + error.toString());
                    }
                });
                mQueue.add(stringRequest);
                break;
        }
    }
}
