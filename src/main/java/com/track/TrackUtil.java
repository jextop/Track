package com.track;

import com.alibaba.fastjson.JSONObject;
import com.track.http.HttpUtil;
import com.track.http.RespJsonObj;
import com.track.util.JsonUtil;
import com.track.util.MacUtil;

import java.util.HashMap;

public class TrackUtil {
    public static void sendPosition() {
        RespJsonObj resp = new RespJsonObj();
        JSONObject ret = HttpUtil.sendHttpPost(
                "http://localhost:8011/track/",
                null, new HashMap<String, Object>() {{
                    put("uid", MacUtil.gtMacAddr());
                }}, resp
        );
        System.out.println(JsonUtil.toStr(ret));
    }
}
