package com.track.util;

import org.junit.Assert;
import org.junit.Test;

public class PoissonUtilTest {
    @Test
    public void testVariable() {
        double lambda = 3.25;
        int sum = 0, count = 20;
        for (int i = 0; i < count; i++) {
            int ret = PoissonUtil.getVariable(lambda);
            sum += ret;
            LogUtil.info(String.format("%d", ret));
        }

        double average = 1.0 * sum / count;
        LogUtil.info(String.format("%f, %f", average, lambda));
        Assert.assertTrue(Math.abs(average - lambda) < 1);
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
        Assert.assertTrue(sum > 0.9 && sum < 1);
    }
}
