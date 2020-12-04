package com.example.customglide;

import android.content.Context;

/**
 * 建造者模式
 *
 * 有很多参数可以添加
 *
 * ....
 *
 * Build()方法生成结果
 */
public class GlideBuilder {

    public GlideBuilder(Context context) {

    }

    /**
     * 创建Glide
     * @return
     */
    public Glide build() {
        RequestManagerRetriver requestManagerRetriver = new RequestManagerRetriver();
        Glide glide = new Glide(requestManagerRetriver);
        return glide;
    }

}
