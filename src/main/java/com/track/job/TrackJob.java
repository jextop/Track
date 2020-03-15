package com.track.job;

import com.alibaba.fastjson.JSONObject;
import com.track.TrackFrame;
import com.track.http.HttpUtil;
import com.track.http.LocationUtil;
import com.track.http.RespJsonObj;
import com.track.util.JsonUtil;
import com.track.util.LogUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TrackJob implements Job {
    @Override
    public void execute(JobExecutionContext var1) throws JobExecutionException {
        String uid = var1.getJobDetail().getKey().getName();
        final JSONObject location = LocationUtil.getLocation();
        LogUtil.info("Send position", uid, JsonUtil.toStr(location));

        JSONObject ret = HttpUtil.sendHttpPost(
                String.format("http://localhost:8011/track/%s", uid),
                null, location, new RespJsonObj()
        );
        System.out.println(JsonUtil.toStr(ret));

        // 更新界面
        TrackFrame.trackListener.positionUpdated(ret.getString("msg"));
    }
}
