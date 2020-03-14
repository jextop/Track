package com.track.http;

import com.alibaba.fastjson.JSONObject;
import com.track.util.JsonUtil;
import com.track.util.StrUtil;
import org.apache.commons.lang3.ArrayUtils;

public class LocationUtil {
    public static JSONObject getLocation() {
        String address = HttpUtil.sendHttpGet("http://whois.pconline.com.cn/ipJson.jsp");
        if (StrUtil.isEmpty(address)) {
            return null;
        }

        String[] jsonStrArr = StrUtil.parse(address, "\\{\"\\S*\\s*\\S*\"\\}");
        return ArrayUtils.isEmpty(jsonStrArr) ? null : JsonUtil.parseObj(jsonStrArr[0]);
    }
}
