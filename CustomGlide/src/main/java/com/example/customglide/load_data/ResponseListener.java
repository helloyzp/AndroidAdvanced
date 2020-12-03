package com.example.customglide.load_data;

import com.example.customglide.resource.Value;

/**
 * 加载外部资源 成功 和 失败 回调
 */
public interface ResponseListener {

    public void responseSuccess(Value value);

    public void responseException(Exception e);

}
