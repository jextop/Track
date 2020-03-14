package com.track.job;

import com.alibaba.fastjson.JSONObject;
import com.track.http.HttpUtil;
import com.track.http.LocationUtil;
import com.track.http.RespJsonObj;
import com.track.util.JsonUtil;
import com.track.util.LogUtil;
import com.track.util.MacUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.HashMap;

public class TrackJob implements Job {
    @Override
    public void execute(JobExecutionContext var1) throws JobExecutionException {
        final JSONObject location = LocationUtil.getLocation();
        LogUtil.info("Send position", JsonUtil.toStr(location));

        JSONObject ret = HttpUtil.sendHttpPost(
                "http://localhost:8011/track/",
                null, new HashMap<String, Object>() {{
                    put("uid", MacUtil.gtMacAddr());

                    if (location != null) {
                        putAll(location);
                    }
                }}, new RespJsonObj()
        );
        System.out.println(JsonUtil.toStr(ret));
    }
}
