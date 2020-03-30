package com.track.job;

import com.alibaba.fastjson.JSONObject;
import com.track.TrackFrame;
import com.track.http.HttpUtil;
import com.track.http.LocationUtil;
import com.track.http.RespJsonObj;
import com.track.util.JsonUtil;
import com.track.util.LogUtil;
import com.track.util.MacUtil;
import com.track.util.PoissonUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TrackJob implements Job {
    private static final double LAMBDA = 3.25;
    private static final int COUNT = 30;

    @Override
    public void execute(JobExecutionContext var1) throws JobExecutionException {
        // 泊松分布启动客户端
        int sum = 0;
        do {
            int i = sum;
            sum += PoissonUtil.getVariable(LAMBDA);
            for (; i < sum && i < COUNT; i++) {
                sendInfo(String.format("%s%02d", MacUtil.gtMacAddr(), i + 1));

                // 任务之间增加随机间隔
                int j = (int) (Math.random() * 1000);
                if (j > 0) {
                    try {
                        Thread.sleep(j);
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        } while (sum <= COUNT);
    }

    private void sendInfo(String uid) {
        JSONObject location = LocationUtil.getLocation();
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
