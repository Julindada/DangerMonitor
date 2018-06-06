package com.example.julin.data;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
