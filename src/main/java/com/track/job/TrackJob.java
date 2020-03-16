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

        // 发送位置信息
        JSONObject ret = HttpUtil.sendHttpPost(
                String.format("http://localhost:8011/track/%s", uid),
                null, location, new RespJsonObj()
        );
        System.out.println(JsonUtil.toStr(ret));

        // 更新界面
        String address = null;
        if (ret != null) {
            address = ret.getString("msg");
        } else if (location != null) {
            address = (String) location.get("addr");
        }
        TrackFrame.trackListener.positionUpdated(address);
    }
}
