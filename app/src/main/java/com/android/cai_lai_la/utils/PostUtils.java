package com.android.cai_lai_la.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.cai_lai_la.config.Config;

import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostUtils {
    public static final String TAG = "PostUtils";
    public static JSONObject postJson(String url, String json) {
        Log.i(TAG, "postJson: 通过json获取数据");
        Log.i(TAG, "postJson: url = " + Config.IP + url);
        Log.i(TAG, "postJson: json= " + json);
        String res = "";  // 结果
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(Config.IP + url)
                .method("POST", body)
                .build();
        try {
            Log.i(TAG, "postJson: 开始请求");
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            res = responseBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "postJson: 获取的数据为" + res.substring(0, Math.min(255, res.length())));
        return JSON.parseObject(res);
    }

    public static JSONObject postParam(String url, FormBody.Builder param) {
        String res = "";  // 结果
        Log.i(TAG, "postParam: 通过参数获取数据");
        Log.i(TAG, "postParam: url = " + Config.IP + url);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
//        MediaType mediaType = MediaType.parse("text/plain");

        Request request = new Request.Builder()
                .url(Config.IP + url)
                .post(param.build())
                .build();
        try {
            Log.i(TAG, "postParam: 开始请求");
            Response response = client.newCall(request).execute();
            res = Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "postParam: 获取的数据为" + res.substring(0, Math.min(255, res.length())));
        return JSON.parseObject(res);
    }
}
