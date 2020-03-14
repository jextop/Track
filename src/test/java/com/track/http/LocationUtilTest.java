package com.track.http;

import com.alibaba.fastjson.JSONObject;
import com.track.util.LogUtil;
import org.junit.Assert;
import org.junit.Test;

public class LocationUtilTest {
    @Test
    public void testGetLocation() {
        JSONObject ret = LocationUtil.getLocation();
        LogUtil.info(ret);
        Assert.assertNotNull(ret);
    }
}
