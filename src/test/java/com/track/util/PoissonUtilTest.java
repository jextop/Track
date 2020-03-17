package com.track.util;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.junit.Test;

public class PoissonUtilTest {
    @Test
    public void testPoisson() {
        PoissonDistribution distribution = new PoissonDistribution(15);
        double sum = 0;
        for (int i = 0; i < 30; i++) {
            double ret = distribution.probability(i);
            sum += ret;
            LogUtil.info(String.format("%2d, %.5f", i, ret));
        }
        LogUtil.info(String.format("%.5f", sum));
    }
}
