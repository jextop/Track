package com.track.util;

import org.junit.Test;

public class PoissonUtilTest {
    @Test
    public void testVariable() {
        for (int i = 0; i < 10; i++) {
            int ret = PoissonUtil.getVariable(3.25);
            LogUtil.info(String.format("%d", ret));
        }
    }

    @Test
    public void testProbability() {
        double sum = 0;
        for (int i = 0; i < 10; i++) {
            double ret = PoissonUtil.getProbability(i, 3.25);
            sum += ret;
            LogUtil.info(String.format("%2d, %.5f", i, ret));
        }
        LogUtil.info(String.format("%.5f", sum));
    }
}
