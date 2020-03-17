package com.track.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PoissonUtilTest {
    @Test
    public void testGetPoisson() {
        int count = 1000;
        for (double lambda : new double[]{3.25, 3.25}) {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < count; i++) {
                int code = PoissonUtil.getPoissonVariable(lambda);
                list.add(code);
            }

            LogUtil.info(JsonUtil.toStr(list));
            Assert.assertEquals(count, list.size());
        }
    }
}
